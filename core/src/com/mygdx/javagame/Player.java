package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	
	final Main game;
	
	// Size of Player
	private int sizeX;
	private int sizeY;
	private static final int MULTIPLIER = 7;
	
	// Constant rows and columns of the sprite sheet
	private static final int TOTAL_FRAME_COLS = 24, FRAME_ROWS = 1;
	
	private static final int RUNNING_FRAME_COLS_START = 4;
	private static final int RUNNING_FRAME_COLS_END = 9;
	private static final int RUNNING_FRAME_COLS_NUM = RUNNING_FRAME_COLS_END - RUNNING_FRAME_COLS_START + 1;
	
	private Texture spriteSheet;
	Animation<TextureRegion> runAnimation;
	TextureRegion[] runFrames = new TextureRegion[RUNNING_FRAME_COLS_NUM * FRAME_ROWS];
	
	// Player position
	private float posX = 0;
	private float posY = 540; // DONT CHANGE THIS
	
	Rectangle playerRect;
	
	Player(final Main game) {
		this.game = game;
		
		// Load in the new texture
		spriteSheet = new Texture("DinoSprites/blue.png");
		
		// Get the size desired
		sizeX = spriteSheet.getWidth()/TOTAL_FRAME_COLS * MULTIPLIER;
		sizeY = spriteSheet.getHeight()/FRAME_ROWS * MULTIPLIER;
		
		// Use the split method to create a 2D array of TextureRegions
		TextureRegion[][] tempRegion = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth()/TOTAL_FRAME_COLS,
				spriteSheet.getHeight()/FRAME_ROWS);
		
		// Create a 1d region of running frames
		for(int i=0; i<RUNNING_FRAME_COLS_NUM; i++) runFrames[i] = tempRegion[0][i + RUNNING_FRAME_COLS_START];
		
		// Initialize the Animation with the frame interval and array of frames
		runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
		
		// Initialize player rect
		playerRect = new Rectangle(posX, posY, sizeX, sizeY);
	}
	
	public void update(float time) {
		game.batch.draw(this.getCurrentRunFrame(time), posX, posY, sizeX, sizeY);
	}
	
	TextureRegion getCurrentRunFrame(float time) {
		return runAnimation.getKeyFrame(time, true);
	}
	
	void updateRect(float posX, float posY) {
		this.playerRect.x = posX;
		this.playerRect.y = posY;
	}
	
	Rectangle getRect() {
		return this.playerRect;
	}
	
	public void dispose() {
		spriteSheet.dispose();
	}
}
