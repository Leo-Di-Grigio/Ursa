package cycle;

import scripts.LuaScripts;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.OwlEngine;
import com.owlengine.input.UserInput;
import com.owlengine.scenes.SceneMng;

public final class Cycle implements ApplicationListener {
	
	private OwlEngine engine;
	private SceneMng scenes;
	
	private SpriteBatch sceneBatch;
	private SpriteBatch uiBatch;
	
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		setupGL();
		setupGDX();
		
		// initiate engine
		engine = new OwlEngine(new LuaScripts());
		scenes = engine.getSceneMng();
		
		// input
		Gdx.input.setInputProcessor(new UserInput(scenes));
		
		// run
		gameRun();
	}

	private void setupGL() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void setupGDX() {
		// batches
		sceneBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		
		// camera
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width, height);
		camera.position.set(width*0.5f, height*0.5f, 0.0f);
	}

	private void gameRun() {
		new GameAPI(scenes, camera);
		GameAPI.loadMenu();
	}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// update
		scenes.update(camera);
		camera.update();
		sceneBatch.setProjectionMatrix(camera.combined);
		
		// render
		scenes.draw(sceneBatch, uiBatch);
		scenes.postUpdate();
		scenes.drawHUD(uiBatch);
	}
	
	@Override
	public void dispose() {
		sceneBatch.dispose();
		uiBatch.dispose();
		engine.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resize(int w, int h) {
	
	}

	@Override
	public void resume() {

	}
}
