package com.mygdx.javagame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.javagame.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		// 1080p most common gaming resolution
		config.setWindowedMode(1920, 1080);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("JavaGame");
		new Lwjgl3Application(new Main(), config);
	}
}
