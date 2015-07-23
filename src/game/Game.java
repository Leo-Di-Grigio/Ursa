package game;

import game.data.GameData;
import game.data.sql.Database;
import tools.Const;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.input.UserInput;
import com.owlengine.interfaces.Event;
import com.owlengine.interfaces.Script;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.Scene;
import com.owlengine.tools.Log;
import com.owlengine.ui.Frame;
import com.owlengine.ui.widgets.Button;

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
		initUI();
	}

	private void initAssets() {		
		// fonts
		font = Assets.getFont(Const.FONT_DEFAULT);
	}
	
	private void initUI() {
		initToolFrame();
		initEditFrame();
	}

	private void initToolFrame() {
		Button button = null;
		
		// Editor
		button = (Button)getUI().getWidget("toolbar_button_editor");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					Frame frame = getUI().getFrame("Editor_Frame");
					
					if(frame != null){
						frame.setVisible(!frame.visible());
						gamedata.setEditMode(frame.visible());
					}
					else{
						Log.err("Game.initToolFrame(): frame Editor_Frame is null ");
					}
				}
			});
		}
		else{
			Log.err("Game.initToolFrame(): button toolbar_button_editor is null ");
		}
		
		// Exit
		button = (Button)getUI().getWidget("toolbar_button_exit");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					GameAPI.exitGame();
				}
			});
		}
		else{
			Log.err("Game.initToolFrame(): button toolbar_button_exit is null ");
		}
	}

	private void initEditFrame() {
		Button button = null;
		
		// Null
		button = (Button)getUI().getWidget("button_editor_null");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_NULL);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_null is null ");
		}
		
		// Block
		button = (Button)getUI().getWidget("button_editor_block");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_BLOCK);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_block is null ");
		}
		
		// Back wall
		button = (Button)getUI().getWidget("button_editor_back_wall");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_BACKGROUND_WALL);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_back_wall is null ");
		}
		
		// Stair
		button = (Button)getUI().getWidget("button_editor_stair");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_STAIRS);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_stair is null ");
		}
		
		// Stair
		button = (Button)getUI().getWidget("button_editor_water");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_WATER);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_water is null ");
		}
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
	public void event(final int code) {
		if(code == Event.SCENE_LOAD){
			GameAPI.camera().zoom = 0.13f;
			Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
			if(data == Keys.ESCAPE){
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