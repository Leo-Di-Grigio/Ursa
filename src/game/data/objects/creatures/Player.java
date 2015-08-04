package game.data.objects.creatures;

import game.data.location.Location;
import game.data.objects.Creature;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	
	// Weapon
	private int weaponModeMain;
	private int weaponModeUsable;

	public Player() {
		super(Const.OBJ_PLAYER);
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
	
	@Override
	public void attack(Location loc) {
		
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
