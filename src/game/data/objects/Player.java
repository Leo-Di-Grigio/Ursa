package game.data.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.owlengine.resources.Assets;

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
	
	public Player() {
		super(Const.CREATURE_BEAR_1);
		loadAssets();
	}
	
	private void loadAssets() {
		Texture tex = Assets.getTex(Const.TEX_BEAR_1);
		
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
