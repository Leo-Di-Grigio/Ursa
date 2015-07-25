package menu;

import com.owlengine.interfaces.Script;
import com.owlengine.ui.UI;
import com.owlengine.ui.widgets.Button;

import cycle.GameAPI;

final class MenuUiLoader {

	private static final String BUTTON_NEW_GAME = "button_new_game";
	private static final String BUTTON_EXIT = "button_exit";
	
	public static void load(final UI ui){
		Button button = null;
		
		//
		button = (Button)ui.getWidget(BUTTON_NEW_GAME);
		
		if(button == null){
			GameAPI.logWidgetErr(BUTTON_NEW_GAME);
		}
		else{
			button.setScriptOnAction(new Script() {
				@Override
				public void execute(String key) {}
				@Override
				public void execute() { 
					GameAPI.loadGame(); 
				}
			});
		}
		
		//
		button = (Button)ui.getWidget(BUTTON_EXIT);
		
		if(button == null){
			GameAPI.logWidgetErr(BUTTON_EXIT);
		}
		else{
			button.setScriptOnAction(new Script() {
				@Override
				public void execute(String key) {}
				
				@Override
				public void execute() { 
					GameAPI.exit();
				}
			});
		}
	}
}
