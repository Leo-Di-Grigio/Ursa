package game;

import game.data.GameData;
import game.data.sql.Database;
import tools.Const;
import tools.Version;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.input.UserInput;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.Scene;

import cycle.GameAPI;

public final class Game extends Scene {
	
	// Assets
	private BitmapFont font;
	
	// Data
	private GameData gamedata;
	
	public Game() {
		new Database();
		initAssets();
		gamedata = new GameData();
		
		// camera scaling
		GameAPI.camera().zoom = 0.13f;
	}
	
	private void initAssets() {
		// textures
		Assets.loadTex(Const.TEX_NULL);
		Assets.loadTex(Const.TEX_BLOCK);
		Assets.loadTex(Const.TEX_STAIRS);
		Assets.loadTex(Const.TEX_PLAYER);
		
		// fonts
		font = Assets.getFont(Const.FONT_DEFAULT);
	}
	
	@Override
	protected void update(OrthographicCamera camera) {
		if(UserInput.key(Keys.SPACE) || UserInput.key(Keys.W)){
			gamedata.playerMoveUp();
		}
		else if(UserInput.key(Keys.S)){
			gamedata.playerMoveDown();
		}
		else {
			gamedata.playerStopY();
		}
		
		if(UserInput.key(Keys.A)){
			gamedata.playerMoveLeft();
		}
		else if(UserInput.key(Keys.D)){
			gamedata.playerMoveRight();
		}
		else{
			gamedata.playerStopX();
		}
		
		gamedata.update(camera);
	}
	
	@Override
	protected void draw(SpriteBatch batch) {
		//Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gamedata.draw(batch);
	}
	
	@Override
	protected void drawHUD(SpriteBatch batch) {
		font.draw(batch, Version.TITLE_VERSION, 5, 15);
		font.draw(batch, "Game", 5, 30);
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 45);
	}
	
	@Override
	public void dispose() {
		gamedata.dispose();
	}
}