package cycle;

import game.Game;
import tools.Const;
import menu.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.SceneMng;
import com.owlengine.tools.Log;

public final class GameAPI {

	private static SceneMng scenes;
	private static OrthographicCamera camera;
	
	// scenes
	private static Menu sceneMenu;
	private static Game sceneGame;

	public GameAPI(SceneMng scenes, OrthographicCamera camera) {
		GameAPI.scenes = scenes;
		GameAPI.camera = camera;
		
		loadAssets();
	}

	private static void loadAssets(){
		Assets.loadFont(Const.FONT_DEFAULT);
	}
	
	public static void loadMenu() {
		sceneMenu = new Menu();
		scenes.loadScene(sceneMenu);
	}
	
	public static void loadGame() {
		sceneGame = new Game();
		scenes.loadScene(sceneGame);
	}
	
	public static void exitGame() {
		scenes.loadScene(sceneMenu);
		sceneGame = null;
	}

	public static void exit() {
		Gdx.app.exit();
	}

	// Log
	public static void logWidgetErr(final String title) {
		Log.err("(Warning): Widget \"" + title + "\" not exist");
	}
	
	//
	public static OrthographicCamera camera(){
		return camera;
	}
}