package menu;

import tools.Const;
import tools.Version;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.interfaces.Event;
import com.owlengine.interfaces.Script;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.Scene;
import com.owlengine.ui.widgets.Button;

import cycle.GameAPI;

public final class Menu extends Scene {

	private BitmapFont font;
	
	private static final String BUTTON_NEW_GAME = "button_new_game";
	private static final String BUTTON_EXIT = "button_exit";

	public Menu() {
		setUI(Const.UI_MENU);
		
		initAssets();
		initUI();
	}
	
	private void initAssets() {
		font = Assets.getFont(Const.FONT_DEFAULT);
	}

	private void initUI() {
		Button button = null;
		
		//
		button = (Button)getUI().getWidget(BUTTON_NEW_GAME);
		
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
		button = (Button)getUI().getWidget(BUTTON_EXIT);
		
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
	
	@Override
	public void event(final int code) {
		if(code == Event.SCENE_LOAD){
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			GameAPI.camera().zoom = 1.0f;
		}
	}
	
	@Override
	protected void drawHUD(SpriteBatch batch) {
		font.draw(batch, Version.TITLE_VERSION, 5, 15);
		font.draw(batch, "Menu", 5, 30);
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 45);
	}
}
