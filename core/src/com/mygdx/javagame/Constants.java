package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;

public class Constants {

	// Screen size
	public static final int APP_WIDTH = Gdx.graphics.getWidth();
	public static final int APP_HEIGHT = Gdx.graphics.getHeight();
	
	// For windowed mode
	public static final int APP_WIDTH_RESOLUTION = 1920;
	public static final int APP_HEIGHT_RESOLUTION = 1080;
	
	public static final float GAP_BETWEEN_OBSTACLES = 300.0f;
	public static final float GAP_BETWEEN_EGGS = 150.0f;
	
	/*
	 * Game speed configurations:
	 * MAX_SPEED: speed limit for the game
	 * CURRENT_SPEED: current speed
	 */

	public static final float MAX_SPEED = 1250.0f;
	public static float CURRENT_SPEED = 500.0f;
}
