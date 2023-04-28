package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 24, FRAME_ROWS = 1;
	
	private Texture spriteSheet;
	Animation<TextureRegion> runAnimation;
	TextureRegion[] runFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	
	Player() {
		// Load in the new texture
		spriteSheet = new Texture("DinoSprites/blue.png");
		
		// Use the split method to create a 2D array of TextureRegions
		TextureRegion[][] tempRegion = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth()/FRAME_COLS,
				spriteSheet.getHeight()/FRAME_ROWS);
		
		// Create a 1d region of running frames
		for(int i=0; i<FRAME_COLS; i++) runFrames[i] = tempRegion[0][i];
		
		// Initialize the Animation with the frame interval and array of frames
		runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
	}
	
	TextureRegion getCurrentRunFrame(float stateTime) {
		return runAnimation.getKeyFrame(stateTime, true);
	}
	
	public void dispose() {
		spriteSheet.dispose();
	}
}
