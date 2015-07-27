package game;

import game.data.GameData;
import tools.Const;

import com.owlengine.interfaces.Script;
import com.owlengine.tools.Log;
import com.owlengine.ui.Frame;
import com.owlengine.ui.UI;
import com.owlengine.ui.widgets.Button;

import cycle.GameAPI;

final class GameUiLoader {

	public static void load(final UI ui, final GameData gamedata){
		initToolFrame(ui, gamedata);
		initEditFrame(ui, gamedata);
	}
	
	private static void initToolFrame(final UI ui, final GameData gamedata) {
		Button button = null;
		
		// Editor
		button = (Button)ui.getWidget("toolbar_button_editor");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					Frame frame = ui.getFrame("Editor_Frame");
					
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
		button = (Button)ui.getWidget("toolbar_button_exit");
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

	private static void initEditFrame(final UI ui, final GameData gamedata) {
		Button button = null;
		
		// Null
		button = (Button)ui.getWidget("button_editor_null");
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
		button = (Button)ui.getWidget("button_editor_block");
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
		button = (Button)ui.getWidget("button_editor_back_wall");
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
		button = (Button)ui.getWidget("button_editor_stair");
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
		
		// Water
		button = (Button)ui.getWidget("button_editor_water");
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
		
		// Wall
		button = (Button)ui.getWidget("button_editor_block_wall");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_BLOCK_VERTICAL);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_wall is null ");
		}
		
		// Cube
		button = (Button)ui.getWidget("button_editor_block_cube");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_BLOCK_CUBE);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_block_cube is null ");
		}
	}
}
