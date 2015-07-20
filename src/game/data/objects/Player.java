package game.data.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.owlengine.resources.Assets;

import tools.Const;

public final class Player extends Creature {

	private static final int PLAYER_SIZE_X = 96;
	private static final int PLAYER_SIZE_Y = 62;
	
	private int texAnmation = Const.ANIMATION_IDLE_0;
	private TextureRegion [] texAtlas;
	
	public Player() {
		super(Const.CREATURE_BEAR_1);
		loadAssets();
	}
	
	private void loadAssets() {
		Texture tex = Assets.getTex(Const.TEX_BEAR_1);
		
		texAtlas = new TextureRegion[Const.ANIMATION_ARRAY_SIZE];
		
		for(int i = 0; i < Const.ANIMATION_ARRAY_SIZE; ++i){
			texAtlas[i] = new TextureRegion(tex, PLAYER_SIZE_X*i, 0, PLAYER_SIZE_X, PLAYER_SIZE_Y);
		}
	}
	
	@Override
	public int sizeX() {
		return texAtlas[texAnmation].getRegionWidth();
	}
	
	@Override
	public int sizeY() {
		return texAtlas[texAnmation].getRegionHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
	}
}
