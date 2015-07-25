package game.data.objects.creatures;

import java.util.HashSet;

import game.data.objects.Creature;
import game.data.objects.Obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;

import tools.Const;

public final class Player extends Creature {

	private static final int PLAYER_SIZE_X = 96;
	private static final int PLAYER_SIZE_Y = 62;
	
	private static final int PLAYER_ANIMATION_STEP = 5;
	
	//
	private int animationTimer = 0;
	
	private int direct = Const.ANIMATION_DIRECT_LEFT;
	private int animation = Const.ANIMATION_IDLE_0;
	
	private TextureRegion [][] texAtlas;
	
	//
	private HashSet<Fixture> collisions;
	
	// Interact
	private int stair;
	
	public Player() {
		super(Const.OBJ_PLAYER);		
		collisions = new HashSet<Fixture>();
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
	
	public void interactStair(boolean value) {
		if(value){
			stair++;
			
			if(stair > 0){
				body.setGravityScale(0.0f);
			}
		}
		else{
			stair--;
			
			if(stair == 0){
				body.setGravityScale(1.0f);
			}
		}
	}
	
	public void interactBlock(boolean value, Fixture objectFixture, Obj obj) {
		
		if(value){
			if(objectFixture.getBody().getPosition().y + obj.sizeY()/2 <= y()){
				collisions.add(objectFixture);
			}
		}
		else{
			collisions.remove(objectFixture);
		}
	}
	
	public void interactWater(boolean value) {
		
	}
	
	@Override
	public void moveUp() {
		if(stair > 0){
			body.setLinearVelocity(body.getLinearVelocity().x, Const.BEAR_SPEED);
		}
		else{
			super.moveUp();
		}
	}
	
	@Override
	public void moveDown() {
		if(stair > 0){
			body.setLinearVelocity(body.getLinearVelocity().x, -Const.BEAR_SPEED);
		}
	}

	public void moveStopY() {
		if(stair > 0){
			body.setLinearVelocity(body.getLinearVelocity().x, 0.0f);
		}
	}
	
	@Override
	public void moveLeft() {
		super.moveLeft();
	}
	
	@Override
	public void moveRight() {
		super.moveRight();
	}
	
	@Override
	public boolean isGrounded() {
		return !collisions.isEmpty();
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
