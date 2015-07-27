package menu;

import tools.Const;
import tools.Version;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.interfaces.Event;
import com.owlengine.resources.Assets;
import com.owlengine.scenes.Scene;

import cycle.GameAPI;

public final class Menu extends Scene {

	private BitmapFont font;
	private Music music;
	private MenuAnimation logo;

	public Menu() {
		setUI(Const.UI_MENU);
		MenuUiLoader.load(getUI());
		initAssets();
	}
	
	private void initAssets() {
		font = Assets.getFont(Const.FONT_DEFAULT);

		Assets.loadMusic(Const.OST_MENU);
		music = Assets.getMusic(Const.OST_MENU);
		music.setLooping(true);
	}
	
	@Override
	public void event(final int code) {
		if(code == Event.SCENE_LOAD){
			// set defaults params
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			GameAPI.camera().zoom = 1.0f;
			GameAPI.camera().position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0.0f);
			logo = new MenuAnimation();
			music.play();
		}
		else if(code == Event.SCENE_CLOSE){
			music.stop();
		}
	}
	
	@Override
	protected void draw(SpriteBatch batch) {
		logo.draw(batch, Gdx.graphics.getDeltaTime());
	}
	
	@Override
	protected void drawHUD(SpriteBatch batch) {
		font.draw(batch, Version.TITLE_VERSION, 5, 15);
		font.draw(batch, "Menu", 5, 30);
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 45);
	}
	
	@Override
	public void dispose() {
		
	}
}
