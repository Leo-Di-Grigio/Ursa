package game;

import game.data.GameData;
import game.data.objects.creatures.Player;
import game.data.sql.Database;
import tools.Const;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.input.UserInput;
import com.owlengine.interfaces.Event;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.Scene;

import cycle.GameAPI;

public final class Game extends Scene {
	
	// Assets
	private BitmapFont font;
	
	// Data
	private GameData gamedata;
	
	public Game() {
		// Game data
		new Database();
		initAssets();
		gamedata = new GameData();
		
		// UI
		setUI(Const.UI_GAME);
		GameUiLoader.load(getUI(), gamedata);
	}

	private void initAssets() {		
		// Textures
		Assets.loadTex(Const.TEX_BULLET);
		
		// fonts
		font = Assets.getFont(Const.FONT_DEFAULT);
	}
	
	public Player getPlayer() {
		return gamedata.getPlayer();
	}
	
	@Override
	protected void update(OrthographicCamera camera) {
		if(UserInput.key(Keys.W)){
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
		
		if(UserInput.key(Keys.SPACE)){
			gamedata.playerAttack();
		}
		
		gamedata.update(camera);
	}
	
	@Override
	public void event(final int code) {
		if(code == Event.SCENE_LOAD){
			GameAPI.camera().zoom = 0.13f;
			gamedata.setLocColor();
		}
		else if(code == Event.MOUSE_KEY_LEFT){
			gamedata.sceneLeftClick();
		}
		else if(code == Event.MOUSE_KEY_RIGHT){
			gamedata.sceneRightClick();
		}
		else if(code == Event.MOUSE_MOVE){
			gamedata.sceneMouseMove();
		}
	}
	
	@Override
	public void event(int code, int data) {
		if(code == Event.MOUSE_SCROLL){
			gamedata.cameraZoom(data);
		}
		else if(code == Event.KEY_DOWN){
			if(data == Keys.SPACE){
				gamedata.playerAttack();
			}
			else if(data == Keys.ESCAPE){
				gamedata.sceneEscape();
			}
			else if(data == Keys.FORWARD_DEL){
				gamedata.sceneDel();
			}
		}
	}
	
	@Override
	protected void draw(SpriteBatch batch) {
		gamedata.draw(batch);
	}
	
	@Override
	protected void drawHUD(SpriteBatch batch) {
		gamedata.drawHUD(font, batch);
	}
	
	@Override
	protected void postUpdate() {
		gamedata.postUpdate();
	}
	
	@Override
	public void dispose() {
		gamedata.dispose();
	}
}