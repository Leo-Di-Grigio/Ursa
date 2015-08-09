package game;

import game.data.GameData;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;
import tools.Const;

import com.owlengine.interfaces.Script;
import com.owlengine.tools.Log;
import com.owlengine.ui.Frame;
import com.owlengine.ui.UI;
import com.owlengine.ui.widgets.Button;
import com.owlengine.ui.widgets.ProgressBar;

import cycle.GameAPI;

final class GameUiLoader {

	public static void load(final UI ui, final GameData gamedata){
		initToolFrame(ui, gamedata);
		initEditFrame(ui, gamedata);
		initPlayerFrame(ui, gamedata);
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
		
		// Build plan
		button = (Button)ui.getWidget("toolbar_button_build_plan");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.buildPlan();
				}
			});
		}
		else{
			Log.err("Game.initToolFrame(): button toolbar_button_build_plan is null ");
		}
		
		// Save location
		button = (Button)ui.getWidget("toolbar_button_save_location");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.saveLocation();
				}
			});
		}
		else{
			Log.err("Game.initToolFrame(): button toolbar_button_build_plan is null ");
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
		
		// Background House1
		button = (Button)ui.getWidget("button_editor_background_house1");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_BACKGROUND_HOUSE1);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_background_house1 is null ");
		}
		
		// Cяся
		button = (Button)ui.getWidget("button_editor_npc_woman");
		if(button != null){
			button.setScriptOnAction(new Script() {
				
				@Override
				public void execute(String key) {
					
				}
				
				@Override
				public void execute() {
					gamedata.setEditObject(Const.OBJ_NPC_WOMAN);
				}
			});
		}
		else{
			Log.err("Game.initEditFrame(): button button_editor_npc_woman is null ");
		}
	}
	
	private static void initPlayerFrame(final UI ui, final GameData gamedata) {
		ProgressBar bar = null;
		
		bar = (ProgressBar)ui.getWidget("player_hpbar");
		if(bar != null){
			ObjectProperties property = Database.getObject(gamedata.getPlayer().type);
			bar.setValue(property.maxHp);
			bar.setMax(property.maxHp);
		}
		else{
			Log.err("Game.initPlayerFrame(): progress bar player_hpbar is null ");
		}
	}
}
