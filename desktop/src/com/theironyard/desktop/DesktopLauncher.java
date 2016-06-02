package com.theironyard.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.theironyard.MyGdxGame;

public class DesktopLauncher {
	static int width;
	static int height;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		width = config.width = 800;
		height = config.height = 600;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
