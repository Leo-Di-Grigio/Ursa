package game.data.objects;

import game.data.sql.Database;

import java.util.HashSet;

import com.badlogic.gdx.physics.box2d.Filter;

import tools.Const;

abstract public class Creature extends Obj {
	
	// Physic
	protected HashSet<Obj> blocks;
	protected HashSet<Obj> stairs;
	protected HashSet<Obj> waters;

	// Modes
	protected Filter filter;
	protected boolean stairMode;
	
	// Movement
	protected int direct = Const.ANIMATION_DIRECT_LEFT;
	protected float movementSpeed = Const.BEAR_SPEED;
	protected float movementJumpSpeed = Const.BEAR_SPEED_JUMP;
	
	// Heals
	protected boolean dead;
	protected int hp;
	protected int maxHp;
	
	// Weapon
	private long lastAttackTime;
	private int weaponModeMain;
	private int weaponModeUsable;
	
	public Creature(final int type) {
		super(type);
		
		blocks = new HashSet<Obj>();
		stairs = new HashSet<Obj>();
		waters = new HashSet<Obj>();
		filter = new Filter();
		
		this.maxHp = Database.getObject(type).maxHp;
		this.hp = maxHp;
	}

	public boolean isGrounded() {
		for(Obj obj: blocks){
			if((int)(obj.y() + obj.sizeY()/2) <= (int)y()){
				return true;
			}
		}
		
		return false;
	}
	
	public void interactStair(boolean value, Obj obj) {
		if(value){
			stairs.add(obj);
		}
		else{
			stairs.remove(obj);
			
			if(stairs.size() <= 0){
				setStairMode(false);
			}
		}
	}
	
	public void interactBlock(boolean value, Obj obj) {
		if(value){
			blocks.add(obj);
		}
		else{
			blocks.remove(obj);
		}
	}
	
	public void interactWater(boolean value, Obj obj) {
		if(value){
			waters.add(obj);
		}
		else{
			waters.add(obj);
		}
	}
	
	@Override
	public void bulletInteract(final int bulletType) {
		this.hp -= 10; // 10 - test damage
		
		if(hp <= 0){
			this.dead = true;
		}
	}
	
	public boolean isDead(){
		return dead;
	}
	
	// Weapon
	public boolean canAttack() {
		if(!stairMode){
			if(System.currentTimeMillis() - lastAttackTime > Const.WEAPON_SHOOT_DELAY){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public void attack() {
		lastAttackTime = System.currentTimeMillis();
	}
	
	public void setWeaponMain(final int weaponMode){
		this.weaponModeMain = weaponMode;
	}
	
	public void setWeaponUsable(final int weaponMode){
		this.weaponModeUsable = weaponMode;
	}
	
	public int getWeaponMain(){
		return weaponModeMain;
	}
	
	public int getWeaponUsable(){
		return weaponModeUsable;
	}
	
	//obj-top: (int)(obj.y() + obj.sizeY()/2) 
	//obj-bottom: (int)(obj.y() - obj.sizeY()/2)
	//this-top: (int)(y() + sizeY()/2)
	//this-bottom: (int)(y() - sizeY()/2)
	public void setDirect(int direct){
		this.direct = direct;
	}
	
	public void moveUp() {
		if(stairMode){
			body.setLinearVelocity(body.getLinearVelocity().x, movementSpeed);
		}
		else{
			if(stairs.size() > 0){
				boolean jump = false;
				
				for(Obj obj: stairs){
					// check stairs top
					if((int)(obj.y() + obj.sizeY()/2) == (int)(y() - sizeY()/2)){
						jump = true;
						break;
					}
				}
				
				if(jump && isGrounded()){
					body.setLinearVelocity(body.getLinearVelocity().x, movementJumpSpeed);
				}
				else{
					setStairMode(true);
				}
			}
			else{
				if(isGrounded()){
					body.setLinearVelocity(body.getLinearVelocity().x, movementJumpSpeed);
				}
			}
		}
	}
	
	public void moveDown() {		
		if(stairMode){
			boolean floorContact = false;
			boolean downStairContact = false;
			
			for(Obj obj: stairs){
				// check stairs bottom
				if((int)(obj.y() - obj.sizeY()/2) == (int)(y() - sizeY()/2)){
					floorContact = true;
				}
				
				if((int)(obj.y() + obj.sizeY()/2) == (int)(y() - sizeY()/2)){
					downStairContact = true;
				}
			}
			
			if(!downStairContact && floorContact){
				setStairMode(false);
			}
			else{
				body.setLinearVelocity(body.getLinearVelocity().x, -movementSpeed);
			}
		}
		else{
			if(stairs.size() > 0){
				// check stair bottom
				for(Obj obj: stairs){
					if((int)(obj.y() - obj.sizeY()/2) < (int)(y() - sizeY()/2)){
						setStairMode(true);
					}
				}
			}
		}
	}
	
	public void moveLeft() {
		setDirect(Const.ANIMATION_DIRECT_LEFT);
		
		if(stairMode){
			setStairMode(false);
			body.setLinearVelocity(-movementSpeed, body.getLinearVelocity().y);
		}
		else{
			body.setLinearVelocity(-movementSpeed, body.getLinearVelocity().y);
		}
	}
	
	public void moveRight() {
		setDirect(Const.ANIMATION_DIRECT_RIGHT);
		
		if(stairMode){
			setStairMode(false);
			body.setLinearVelocity(movementSpeed, body.getLinearVelocity().y);
		}
		else{
			body.setLinearVelocity(movementSpeed, body.getLinearVelocity().y);
		}
	}
	
	public final void moveStopX(){
		body.setLinearVelocity(0.0f, body.getLinearVelocity().y);	
	}
	
	public void moveStopY() {
		if(stairMode){
			body.setLinearVelocity(body.getLinearVelocity().x, 0.0f);
		}
	}
	
	private void setStairMode(boolean value){
		stairMode = value;
		
		if(stairMode){
			if(player){
				filter.categoryBits = Const.CATEGORY_PLAYER_GHOST;
        		filter.maskBits = Const.MASK_PLAYER_GHOST;        	
				fixture.setFilterData(filter);
				body.setGravityScale(0.0f);
			}
			else if(npc){
				filter.categoryBits = Const.CATEGORY_NPC_GHOST;
        		filter.maskBits = Const.MASK_NPC_GHOST;        	
				fixture.setFilterData(filter);
				body.setGravityScale(0.0f);
			}
			
			// stair centring
			for(Obj obj: stairs){
				body.setTransform(obj.x() + obj.sizeX()/2, this.y(), 0.0f);
				break;
			}
		}
		else{
			if(player){
				filter.categoryBits = Const.CATEGORY_PLAYER_NORMAL;
        		filter.maskBits = Const.MASK_PLAYER_NORMAL;
				fixture.setFilterData(filter);
				body.setGravityScale(1.0f);
			}
			else if(npc){
				filter.categoryBits = Const.CATEGORY_NPC_NORMAL;
        		filter.maskBits = Const.MASK_NPC_NORMAL;
				fixture.setFilterData(filter);
				body.setGravityScale(1.0f);
			}
		}
	}

	public int direct() {
		return direct;
	}
	
	public void update() {
		
	}

	public void disactive() {
		filter.categoryBits = Const.CATEGORY_CORPSE;
		filter.maskBits = Const.MASK_CORPSE;
		fixture.setFilterData(filter);
		body.setGravityScale(1.0f);
		body.setLinearVelocity(0.0f, 0.0f);
	}
}
