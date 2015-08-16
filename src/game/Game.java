package game;

import game.data.GameData;
import game.data.PlayerHandler;
import game.data.objects.creatures.Player;
import game.data.sql.Database;
import game.editor.Editor;
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
	private PlayerHandler playerHandler;
	
	public Game() {
		// Editor
		new Editor();
		
		// Game data
		new Database();
		initAssets();
		gamedata = new GameData();
		playerHandler = gamedata.buildPlayerHandler();
		
		// UI
		setUI(Const.UI_GAME);
		GameUiLoader.load(getUI(), gamedata, playerHandler);
	}

	private void initAssets() {		
		// Textures
		Assets.loadTex(Const.TEX_BULLET);
		Assets.loadTex(Const.TEX_PLAYER_STAND);
		Assets.loadTex(Const.TEX_PLAYER_STAIRS);
		
		// fonts
		font = Assets.getFont(Const.FONT_DEFAULT);
	}
	
	public Player getPlayer() {
		return playerHandler.getPlayer();
	}
	
	@Override
	protected void update(OrthographicCamera camera) {
		if(UserInput.key(Keys.W)){
			playerHandler.moveUp();
		}
		else if(UserInput.key(Keys.S)){
			playerHandler.moveDown();
		}
		else {
			playerHandler.moveStopY();
		}
		
		if(UserInput.key(Keys.A)){
			playerHandler.moveLeft();
		}
		else if(UserInput.key(Keys.D)){
			playerHandler.moveRight();
		}
		else{
			playerHandler.moveStopX();
		}
		
		if(UserInput.key(Keys.SPACE)){
			playerHandler.attack();
		}
		
		gamedata.update(camera, playerHandler);
	}
	
	@Override
	public void event(final int code) {
		if(code == Event.SCENE_LOAD){
			GameAPI.camera().zoom = 0.125f;
			gamedata.setLocColor();
		}
		else if(code == Event.MOUSE_KEY_LEFT){
			gamedata.eventLeftClick();
		}
		else if(code == Event.MOUSE_KEY_RIGHT){
			gamedata.eventRightClick();
		}
		else if(code == Event.MOUSE_MOVE){
			gamedata.sceneMouseMove();
		}
	}
	
	@Override
	public void event(int code, int data) {
		if(code == Event.MOUSE_SCROLL){
			gamedata.eventMouseScroll(data);
		}
		else if(code == Event.KEY_DOWN){
			switch (data) {
				case Keys.SPACE:
					playerHandler.attack();
					break;

				case Keys.ESCAPE:
					gamedata.eventEscape();
					break;
					
				case Keys.FORWARD_DEL:
					gamedata.eventDel();
					break;
					
				case Keys.E:
					playerHandler.playerStand();
					break;
					
				case Keys.Z:
					if(UserInput.key(Keys.CONTROL_LEFT) || UserInput.key(Keys.CONTROL_RIGHT)){
						gamedata.eventRevert();
					}
					break;
					
				default:
					break;
			}
		}
	}
	
	@Override
	protected void draw(SpriteBatch batch) {
		gamedata.draw(batch);
	}
	
	@Override
	protected void drawHUD(SpriteBatch batch) {
		gamedata.drawHUD(font, batch, playerHandler);
	}
	
	@Override
	protected void postUpdate() {
		gamedata.postUpdate();
	}
	
	@Override
	public void dispose() {
		Editor.dispose();
		gamedata.dispose();
	}
}