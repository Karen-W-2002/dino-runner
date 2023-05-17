package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	
	final Main game;
	
	// Size of Player
	private int sizeX;
	private int sizeY;
	private static final int MULTIPLIER = 5;
	
	// Handle constant rows and columns of the sprite sheet
	private static final int TOTAL_FRAME_COLS = 24, FRAME_ROWS = 1;
	
	// Running frames on sprite sheet
	private static final int RUNNING_FRAME_COLS_START = 4;
	private static final int RUNNING_FRAME_COLS_END = 9;
	private static final int RUNNING_FRAME_COLS_NUM = RUNNING_FRAME_COLS_END - RUNNING_FRAME_COLS_START + 1;
	
	// TODO: Collision frames on sprite sheet
	// ...
	
	// FOR DEBUGGING: outlines collision box
	private ShapeRenderer shape; 
	
	// Texture for player sprite
	private Texture spriteSheet;
	Animation<TextureRegion> runAnimation;
	TextureRegion[] runFrames = new TextureRegion[RUNNING_FRAME_COLS_NUM * FRAME_ROWS];
	
	// Player position
	private float posX = 0;
	private float posY = 550; // DONT CHANGE THIS
	
	// Collision
	private float collisionPosX;
	private float collisionPosY;
	private float collisionSizeX;
	private float collisionSizeY;
	
	Rectangle playerRect;
	
	// Flip variables

	Player(final Main game) {
		this.game = game;
		
		// Load in the new texture
		spriteSheet = new Texture("DinoSprites/blue.png");
		initSpriteAnimation();
		
		// Initialize player collision box position and size
		initCollisionPos();
		initCollisionSize();
		
		// Initialize player rect
		playerRect = new Rectangle(collisionPosX, collisionPosY, collisionSizeX, collisionSizeY);
		
		// FOR DEBUGGING
		 shape = new ShapeRenderer();
	}
	
	public void update(float time) {	
//		flipCooldown -= time;
		game.batch.draw(getCurrentRunFrame(time), 0, 550, sizeX, sizeY);
	}

	
	TextureRegion getCurrentRunFrame(float time) {
		return runAnimation.getKeyFrame(time, true);
	}
	
	Rectangle getRect() {
		return this.playerRect;
	}
	
	public void dispose() {
		spriteSheet.dispose();
		shape.dispose();
	}
	
	private void initSpriteAnimation() {
		// Set the display size in pixels
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
	}
	
	// Flips the player
	public void flip() {
		for(TextureRegion frame : runAnimation.getKeyFrames()) {
			frame.flip(false, true);
		}
	}
	
	private void initCollisionPos() {
		collisionPosX = posX + (sizeX/3);
		collisionPosY = posY - sizeY;
	}
	
	private void initCollisionSize() {
		collisionSizeX = sizeX/MULTIPLIER;
		collisionSizeY = sizeY/MULTIPLIER;
	}
	
	// FOR DEBUGGING: Draws out collision box
	public void debug() {
		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.rect(collisionPosX, collisionPosY, collisionSizeX, collisionSizeY);
		shape.end();
	}
}
