package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	
	final Main game;
	
	// Size of Player
//	private int sizeX;
//	private int sizeY;
//	private static final int MULTIPLIER = 5;
	
	// Handle constant rows and columns of the sprite sheet
	private static final int TOTAL_FRAME_COLS = 24, FRAME_ROWS = 1;
	
	/*
	 * Running Frame Animations
	 */
	private static final int RUNNING_FRAME_COLS_START = 4;
	private static final int RUNNING_FRAME_COLS_END = 9;
	private static final int RUNNING_FRAME_COLS_NUM = RUNNING_FRAME_COLS_END - RUNNING_FRAME_COLS_START + 1;
	
	// Constant Position Y of Dino
	private static final float POSITION_Y_UPSIDE = Constants.APP_HEIGHT/2 + 10;
	private static final float POSITION_Y_DOWNSIDE = Constants.APP_HEIGHT/2 - 48;
	
	// Constant Position Y of Dino Collision Rect
	float COLLISION_Y_UPSIDE;
	float COLLISION_Y_DOWNSIDE;
//	
	// TODO: Collision frames on sprite sheet
	// ...
	
	// FOR DEBUGGING: outlines collision box
	private ShapeRenderer shape; 
	
	// Texture for player sprite
	private Texture texture;
	
	// Texture animations
	Animation<TextureRegion> runAnimation;
	TextureRegion[] runFrames = new TextureRegion[RUNNING_FRAME_COLS_NUM * FRAME_ROWS];
	
	// Player position
	private float posX = 0;
	private float posY = POSITION_Y_UPSIDE; // DONT CHANGE THIS
	
	// Collision
	private float collisionPosX;
	private float collisionSizeX;
	private float collisionSizeY;

	Rectangle playerRect;

	Player(final Main game) {
		this.game = game;
		
		// Load in the new texture
		texture = new Texture("DinoSprites/blue.png");
		initSpriteAnimation();
		
		// Initialize player collision box position and size
		initCollisionSize();
		initCollisionPos();
		
		
		// Initialize player rect
		playerRect = new Rectangle(collisionPosX, COLLISION_Y_UPSIDE, collisionSizeX, collisionSizeY);
		
		// FOR DEBUGGING
		shape = new ShapeRenderer();

	}
	
	public void update(float time) {	
//		flipCooldown -= time;
		TextureRegion region = getCurrentRunFrame(time);
		game.batch.draw(region, posX, posY - 10, collisionSizeX*3, collisionSizeY*3);
	}

	
	/*
	 * Use type casting to change TextureRegion
	 * keyframe into a sprite
	 */
	TextureRegion getCurrentRunFrame(float time) {
		return runAnimation.getKeyFrame(time, true);
	}
	
	Rectangle getRect() {
		return this.playerRect;
	}
	
	public void dispose() {
		texture.dispose();
		shape.dispose();
	}
	
	private void initSpriteAnimation() {
				
		// Use the split method to create a 2D array of TextureRegions
		TextureRegion[][] tempRegion = TextureRegion.split(texture,
				texture.getWidth()/TOTAL_FRAME_COLS,
				texture.getHeight()/FRAME_ROWS);
				
		// Create a 1d region of running frames
		for(int i=0; i<RUNNING_FRAME_COLS_NUM; i++) {
			runFrames[i] = tempRegion[0][i + RUNNING_FRAME_COLS_START];
		}
				
		// Initialize the Animation with the frame interval and array of frames
		runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
	}
	
	// Flips the player's animation and its position
	public void flip() {
		
		// Flips the player upside down
		if(this.getY() == POSITION_Y_UPSIDE) {
			this.setY(POSITION_Y_DOWNSIDE);
			this.setRectY(COLLISION_Y_DOWNSIDE);
		
			for(TextureRegion frame : runAnimation.getKeyFrames()) {
				if(!frame.isFlipY())
					frame.flip(false, true);
			}
			
		// Flips the player upside
		} else {
			this.setY(POSITION_Y_UPSIDE);
			this.setRectY(COLLISION_Y_UPSIDE);
			
			for(TextureRegion frame : runAnimation.getKeyFrames()) {
				
				if(frame.isFlipY())
					frame.flip(false, true);
			}
		}
	}
	
	private void initCollisionPos() {
		collisionPosX = posX + collisionSizeX;
		COLLISION_Y_UPSIDE = posY + collisionSizeY;
		COLLISION_Y_DOWNSIDE = COLLISION_Y_UPSIDE - 80;
	}
	
	private void initCollisionSize() {
		collisionSizeX = texture.getWidth()/TOTAL_FRAME_COLS;
		collisionSizeY = texture.getHeight()/FRAME_ROWS;

	}
	
	public float getY() {
		return this.posY;
	}
	
	public void setY(float posY) {
		this.posY = posY;
	}
	
	public void setRectY(float posY) {
		this.getRect().setY(posY); 
	}
	
	// FOR DEBUGGING: Draws out collision box
	public void debug() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.rect(collisionPosX, this.getRect().getY(), collisionSizeX, collisionSizeY);

		shape.end();
	}
}
