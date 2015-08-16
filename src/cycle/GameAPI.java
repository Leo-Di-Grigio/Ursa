package cycle;

import game.Game;
import game.data.objects.creatures.Player;
import tools.Const;
import menu.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.SceneMng;
import com.owlengine.tools.Log;

public final class GameAPI {

	private static SceneMng scenes;
	private static OrthographicCamera camera;
	
	// scenes
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
		scenes.loadScene(new Menu());
	}
	
	public static void loadGame() {
		sceneGame = new Game();
		scenes.loadScene(sceneGame);
	}
	
	public static void exitGame() {
		scenes.loadScene(new Menu());
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
	public static Player getPlayer(){
		return sceneGame.getPlayer();
	}
	
	public static OrthographicCamera camera(){
		return camera;
	}
	
	// Pick camera pixel
	public static void pickPixel(Vector3 vector, float x, float y){
        vector.set(x, y, 0);
        camera.unproject(vector);
	}
}