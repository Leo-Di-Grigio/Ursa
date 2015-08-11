package game.data.objects.creatures;

import game.data.objects.Creature;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.owlengine.resources.Assets;

import tools.Const;

public final class Player extends Creature {

	private static final int PLAYER_SIZE_X = 96;
	private static final int PLAYER_SIZE_Y = 62;
	
	private static final int PLAYER_SIZE_STAND_X = 58;
	private static final int PLAYER_SIZE_STAND_GET_UP_0 = 79;
	private static final int PLAYER_SIZE_STAND_GET_UP_1 = 75;
	private static final int PLAYER_SIZE_STAND_GET_UP_2 = 99;
	private static final int PLAYER_SIZE_STAND_GET_UP_3 = 102;
	private static final int PLAYER_SIZE_STAND_Y = 106;
	
	private static final int PLAYER_SIZE_STAIR_X = 64;
	private static final int PLAYER_SIZE_STAIR_Y = 96;
		
	// Timing
	private static final int PLAYER_ANIMATION_STEP = 5;
	private static final int PLAYER_ANIMATION_SWITCH = 7;
	private static final int PLAYER_ANIMATION_SWITCH_TIME = Const.ANIMATION_STAND_GET_UP_ARRAY_SIZE * PLAYER_ANIMATION_SWITCH;
	
	// Graphics
	private int animationTimer = 0;
	private int animation = Const.ANIMATION_IDLE_0;
	
	private int animationStandSwitchTimer = 0;
	private int animationTimerStand = 0;
	private int animationStand = Const.ANIMATION_STAND_MOVEMENT_IDLE_0;

	private int animationTimerStairs = 0;
	private int animationStairs = Const.ANIMATION_STAIR_STEP_0;
	
	private boolean standSwitchMode;
	private boolean standMode;
	
	// Textures (static for loading all players)
	private static TextureRegion [][] texAtlas;
	private static TextureRegion [][] texStand;
	private static TextureRegion [] texStair;
	
	static {
		loadAnimationMovement(Assets.getTex(Const.TEX_PLAYER));
		loadAnimationStand(Assets.getTex(Const.TEX_PLAYER_STAND));
		loadAnimationStair(Assets.getTex(Const.TEX_PLAYER_STAIRS));
	}
	
	public Player() {
		super(Const.OBJ_PLAYER);
	}
	
	private static void loadAnimationMovement(Texture tex) {
		// movement animation
		texAtlas = new TextureRegion[Const.ANIMATION_ARRAY_SIZE][2];
		
		for(int i = 0; i < Const.ANIMATION_ARRAY_SIZE; ++i){
			for(int j = 0; j < 2; ++j){
				texAtlas[i][j] = new TextureRegion(tex, PLAYER_SIZE_X*i, PLAYER_SIZE_Y*j, PLAYER_SIZE_X, PLAYER_SIZE_Y);
			}
		}
	}

	private static void loadAnimationStand(Texture tex) {
		// stand animation
		texStand = new TextureRegion[Const.ANIMATION_STAND_ARRAY_SIZE][2];
		
		// stand movement regions
		for(int i = 0; i < Const.ANIMATION_STAND_MOVEMENT_ARRAY_SIZE; ++i){
			for(int j = 0; j < 2; ++j){
				texStand[i][j] = new TextureRegion(tex, PLAYER_SIZE_STAND_X*i, PLAYER_SIZE_STAND_Y*j, PLAYER_SIZE_STAND_X, PLAYER_SIZE_STAND_Y);
			}
		}
		
		// get up
		for(int j = 0; j < 2; ++j){
			texStand[Const.ANIMATION_STAND_GET_UP_0][j] = new TextureRegion(tex, 464, PLAYER_SIZE_STAND_Y*j, PLAYER_SIZE_STAND_GET_UP_0, PLAYER_SIZE_STAND_Y);
			texStand[Const.ANIMATION_STAND_GET_UP_1][j] = new TextureRegion(tex, 550, PLAYER_SIZE_STAND_Y*j, PLAYER_SIZE_STAND_GET_UP_1, PLAYER_SIZE_STAND_Y);
			texStand[Const.ANIMATION_STAND_GET_UP_2][j] = new TextureRegion(tex, 633, PLAYER_SIZE_STAND_Y*j, PLAYER_SIZE_STAND_GET_UP_2, PLAYER_SIZE_STAND_Y);
			texStand[Const.ANIMATION_STAND_GET_UP_3][j] = new TextureRegion(tex, 738, PLAYER_SIZE_STAND_Y*j, PLAYER_SIZE_STAND_GET_UP_3, PLAYER_SIZE_STAND_Y);
		}
	}

	private static void loadAnimationStair(Texture tex) {
		// stairs animation
		texStair = new TextureRegion[Const.ANIMATION_STAIR_ARRAY_SIZE];
		
		for(int i = 0; i < Const.ANIMATION_STAIR_ARRAY_SIZE; ++i){
			texStair[i] = new TextureRegion(tex, PLAYER_SIZE_STAIR_X*i, 0, PLAYER_SIZE_STAIR_X, PLAYER_SIZE_STAIR_Y);
		}
	}

	public void stand() {
		if(!standSwitchMode){
			animationStandSwitchTimer = 0;
			standSwitchMode = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		if(stairMode){
			drawStairMode(batch);
		}
		else{
			if(standSwitchMode){
				drawStandSwitchMode(batch);
			}
			else{
				if(standMode){
					drawGroudedStandMode(batch);
				}
				else{
					drawGroundedNormalMode(batch);
				}
			}
		}	
	}

	private void drawStandSwitchMode(SpriteBatch batch) {
		animationStandSwitchTimer++;
		
		if(standMode){
			if(animationStandSwitchTimer == PLAYER_ANIMATION_SWITCH_TIME){
				standMode = false;
				standSwitchMode = false;
				drawGroundedNormalMode(batch);
			}
			else{
				int frame = animationStandSwitchTimer/PLAYER_ANIMATION_SWITCH;
			
				switch (frame) {
					case 0:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_0, PLAYER_SIZE_STAND_GET_UP_0/8.0f, 12);
						break;
						
					case 1:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_1, PLAYER_SIZE_STAND_GET_UP_1/8.0f, 12);
						break;
						
					case 2:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_2, PLAYER_SIZE_STAND_GET_UP_2/8.0f, 12);
						break;
						
					case 3:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_3, PLAYER_SIZE_STAND_GET_UP_3/8.0f, 12);
						break;
				}
			}
		}
		else{
			
			if(animationStandSwitchTimer == PLAYER_ANIMATION_SWITCH_TIME){
				standMode = true;
				standSwitchMode = false;	
				drawGroudedStandMode(batch);
			}
			else{
				int frame = animationStandSwitchTimer/PLAYER_ANIMATION_SWITCH;
				
				switch (frame) {
					case 0:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_3, PLAYER_SIZE_STAND_GET_UP_3/8.0f, 12);
						break;
						
					case 1:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_2, PLAYER_SIZE_STAND_GET_UP_2/8.0f, 12);
						break;
						
					case 2:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_1, PLAYER_SIZE_STAND_GET_UP_1/8.0f, 12);
						break;
						
					case 3:
						drawStandSwitchFrame(batch, Const.ANIMATION_STAND_GET_UP_0, PLAYER_SIZE_STAND_GET_UP_0/8.0f, 12);
						break;
				}
			}
		}
	}
	
	private void drawStandSwitchFrame(SpriteBatch batch, int id, float sizex, float sizey){
		batch.draw(texStand[id][direct], x() - sizeX()/2, y() - sizeY()/2, sizex, sizey);
	}

	private void drawGroudedStandMode(SpriteBatch batch) {
		if(isGrounded()){
			if(Math.abs(getSpeed().x) > 0.0f){
				animationTimerStand++;
				animationStand = animationTimerStand/PLAYER_ANIMATION_STEP + 1;
				
				if(animationStand == Const.ANIMATION_STAND_MOVEMENT_7){
					animationTimerStand = 0;
				}
			}
			else{
				animationTimerStand = 0;
				animationStand = Const.ANIMATION_STAND_MOVEMENT_IDLE_0;
			}	
		}
		else{
			animationTimerStand = 0;
			animationStand = Const.ANIMATION_STAND_MOVEMENT_IDLE_0;
		}
		
		batch.draw(texStand[animationStand][direct], x() - sizeX()/2, y() - sizeY()/2, 7, 12); // x:y (7:12)
	}

	private void drawGroundedNormalMode(SpriteBatch batch) {
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

	private void drawStairMode(SpriteBatch batch) {
		if(Math.abs(getSpeed().y) > 0.0f){
			animationTimerStairs++;
			animationStairs = animationTimerStairs/PLAYER_ANIMATION_STEP;
			
			if(animationStairs == texStair.length){
				animationTimerStairs = 0;
				animationStairs = Const.ANIMATION_STAIR_STEP_0;
			}
		}
		
		batch.draw(texStair[animationStairs], x() - sizeX()/2, y() - sizeY()/2, sizeY(), sizeX()); // x,y -> y,x
	}
}