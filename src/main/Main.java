package main;

import tools.Version;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cycle.Cycle;

public final class Main {

	public static void main(String [] arg) {
		new Config();
		
		LwjglApplicationConfiguration frameConfig = new LwjglApplicationConfiguration();
		
		// frame
		frameConfig.title = Version.TITLE_VERSION;
		frameConfig.width = Config.width();
		frameConfig.height = Config.height();
		frameConfig.fullscreen = Config.fullscreen();
		frameConfig.resizable = Config.resizable();
		frameConfig.addIcon("assets/icon32.png", FileType.Internal);
		
		new LwjglApplication(new Cycle(), frameConfig);
	}
}