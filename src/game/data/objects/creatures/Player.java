package game.data.objects.creatures;

import java.util.HashSet;

import game.data.objects.Creature;
import game.data.objects.Obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Filter;

import tools.Const;

public final class Player extends Creature {

	private static final int PLAYER_SIZE_X = 96;
	private static final int PLAYER_SIZE_Y = 62;
	
	private static final int PLAYER_ANIMATION_STEP = 5;
	
	// Graphics
	private int animationTimer = 0;
	
	private int direct = Const.ANIMATION_DIRECT_LEFT;
	private int animation = Const.ANIMATION_IDLE_0;
	
	private TextureRegion [][] texAtlas;
	
	// Physic
	private HashSet<Obj> blocks;
	private HashSet<Obj> stairs;
	private HashSet<Obj> waters;

	// Modes
	private Filter filter;
	private boolean stairMode;
	
	public Player() {
		super(Const.OBJ_PLAYER);
		blocks = new HashSet<Obj>();
		stairs = new HashSet<Obj>();
		waters = new HashSet<Obj>();
		
		filter = new Filter();
	}
	
	@Override
	public void setTex(Texture tex) {
		texAtlas = new TextureRegion[Const.ANIMATION_ARRAY_SIZE][2];
		
		for(int i = 0; i < Const.ANIMATION_ARRAY_SIZE; ++i){
			for(int j = 0; j < 2; ++j){
				texAtlas[i][j] = new TextureRegion(tex, PLAYER_SIZE_X*i, PLAYER_SIZE_Y*j, PLAYER_SIZE_X, PLAYER_SIZE_Y);
			}
		}
	}
	
	public void setDirect(int direct){
		this.direct = direct;
	}

	@Override
	public boolean isGrounded() {
		for(Obj obj: blocks){
			if(obj.getBody().getPosition().y + obj.sizeY()/2 <= y()){
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
	

	//(int)(obj.y() + obj.sizeY()/2) 
	//(int)(obj.y() - obj.sizeY()/2)
	//(int)(y() + sizeY()/2)
	//(int)(y() - sizeY()/2)
	
	@Override
	public void moveUp() {
		if(stairMode){
			body.setLinearVelocity(body.getLinearVelocity().x, Const.BEAR_SPEED);
		}
		else{
			if(stairs.size() > 0){
				setStairMode(true);
			}
			else{
				super.moveUp();
			}
		}
	}
	
	@Override
	public void moveDown() {		
		if(stairMode){
			boolean floorContact = false;
			
			for(Obj obj: stairs){
				// check stairs bottom
				if((int)(obj.y() - obj.sizeY()/2) == (int)(y() - sizeY()/2)){
					floorContact = true;
					break;
				}
			}
			
			if(floorContact){
				setStairMode(false);
			}
			else{
				body.setLinearVelocity(body.getLinearVelocity().x, -Const.BEAR_SPEED);
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
			else{
				super.moveDown();
			}
		}
	}

	public void moveStopY() {
		if(stairMode){
			body.setLinearVelocity(body.getLinearVelocity().x, 0.0f);
		}
	}
	
	private void setStairMode(boolean value){
		stairMode = value;
		
		if(stairMode){
        	filter.categoryBits = Const.CATEGORY_PLAYER_GHOST;
        	filter.maskBits = Const.MASK_PLAYER_GHOST;        	
			fixture.setFilterData(filter);
			body.setGravityScale(0.0f);
		}
		else{
        	filter.categoryBits = Const.CATEGORY_PLAYER_NORMAL;
        	filter.maskBits = Const.MASK_PLAYER_NORMAL;
			fixture.setFilterData(filter);
			body.setGravityScale(1.0f);
		}
	}
	
	@Override
	public void moveLeft() {
		if(stairMode){
			setStairMode(false);
			super.moveLeft();
		}
		else{
			super.moveLeft();
		}
	}
	
	@Override
	public void moveRight() {
		if(stairMode){
			setStairMode(false);
			super.moveRight();
		}
		else{
			super.moveRight();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if(isGrounded()){
			if(Math.abs(getSpeed().x) > 0.0f){
				// movement
				animationTimer++;
				animation = animationTimer/PLAYER_ANIMATION_STEP + 1;
				
				if(animation == texAtlas.length){
					animationTimer = 0;
					animation--;
				}
			}
			else{
				// idle
				animationTimer = 0;
				animation = Const.ANIMATION_IDLE_0;
			}
		}
		else{
			animationTimer = 0;
			animation = Const.ANIMATION_IDLE_0;
		}
		
		batch.draw(texAtlas[animation][direct], x() - sizeX()/2, y() - sizeY()/2, sizeX(), sizeY());	
	}
}
