package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	
	final MyGame game;
	
	/*
	 * Number of Frames in Sprite
	 */
	private static final int TOTAL_RUN_FRAMES = 6;
	private static final int TOTAL_HURT_FRAMES = 4;
	private static final int TOTAL_DEAD_FRAMES = 5;

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
	private Texture hurtTexture;
	private Texture deadTexture;
	
	// Texture animations
	Animation<TextureRegion> runAnimation;
	TextureRegion[] runFrames = new TextureRegion[TOTAL_RUN_FRAMES];
	
	Animation<TextureRegion> hurtAnimation;
	TextureRegion[] hurtFrames = new TextureRegion[TOTAL_HURT_FRAMES];
	
	Animation<TextureRegion> deadAnimation;
	TextureRegion[] deadFrames = new TextureRegion[TOTAL_DEAD_FRAMES];
	
	// Player position
	private float posX = 0;
	private float posY = POSITION_Y_UPSIDE; // DONT CHANGE THIS
	
	// Collision
	private float collisionPosX;
	private float collisionSizeX;
	private float collisionSizeY;

	Rectangle playerRect;
	
	private boolean isHurt = false;
	private int currentHurtFrame = 0;

	Player(final MyGame game) {
		this.game = game;
		
		// Load in the new texture
		texture = new Texture("Dino/doux_move.png");
		hurtTexture = new Texture("Dino/doux_hurt.png");
		deadTexture = new Texture("Dino/doux_dead.png");
		initSpriteAnimation();
		
		// Initialize player collision box position and size
		initCollisionSize();
		initCollisionPos();
		
		
		// Initialize player rect
		playerRect = new Rectangle(collisionPosX, COLLISION_Y_UPSIDE, collisionSizeX - 5, collisionSizeY);
		
		// FOR DEBUGGING
		shape = new ShapeRenderer();

	}
	
	public void update(float time) {
		if(!isHurt) {
			TextureRegion region = getCurrentRunFrame(time);
			game.batch.draw(region, posX, posY - 10, collisionSizeX*3, collisionSizeY*3);
		} else {
			TextureRegion region = getCurrentHurtFrame(time);
			game.batch.draw(region, posX, posY - 10, collisionSizeX*3, collisionSizeY*3);
			
			currentHurtFrame++;
			if(currentHurtFrame == TOTAL_HURT_FRAMES*10)  {
				isHurt = false;
				currentHurtFrame = 0;
			}
		}
		
	}
	
	public void drawAnimation(float delta) {
		TextureRegion region = getCurrentDeadFrame(delta);
		game.batch.draw(region, Constants.APP_WIDTH/2 - region.getRegionWidth()*5, Constants.APP_HEIGHT/2 - region.getRegionHeight()*5, region.getRegionWidth()*10, region.getRegionHeight()*10);
	}
	
	public void setHurt() {
		this.isHurt = true;
	}
	
	public boolean isHurt() {
		return this.isHurt;
	}

	
	/*
	 * Use type casting to change TextureRegion
	 * keyframe into a sprite
	 */
	TextureRegion getCurrentRunFrame(float time) {
		return runAnimation.getKeyFrame(time, true);
	}
	
	TextureRegion getCurrentHurtFrame(float time) {
		return hurtAnimation.getKeyFrame(time, true);
	}
	
	TextureRegion getCurrentDeadFrame(float time) {
		return deadAnimation.getKeyFrame(time, true);
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
				texture.getWidth()/TOTAL_RUN_FRAMES,
				texture.getHeight());
				
		// Create a 1d region of running frames
		for(int i=0; i<TOTAL_RUN_FRAMES; i++) {
			runFrames[i] = tempRegion[0][i];
		}
				
		// Initialize the Animation with the frame interval and array of frames
		runAnimation = new Animation<TextureRegion>(0.1f, runFrames);
		
		
		// HURT FRAMES
		tempRegion = TextureRegion.split(hurtTexture,
				hurtTexture.getWidth()/TOTAL_HURT_FRAMES,
				hurtTexture.getHeight());
		
		for(int i=0; i<TOTAL_HURT_FRAMES; i++) {
			hurtFrames[i] = tempRegion[0][i];
		}
		
		hurtAnimation = new Animation<TextureRegion>(0.1f, hurtFrames);
		
		// DEAD FRAMES
		tempRegion = TextureRegion.split(deadTexture,
				deadTexture.getWidth()/TOTAL_DEAD_FRAMES,
				deadTexture.getHeight());
		
		for(int i=0; i<TOTAL_DEAD_FRAMES; i++) {
			deadFrames[i] = tempRegion[0][i];
		}
		
		deadAnimation = new Animation<TextureRegion>(0.4f, deadFrames);
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
			
			for(TextureRegion frame : hurtAnimation.getKeyFrames()) {
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
			
			for(TextureRegion frame : hurtAnimation.getKeyFrames()) {
				if(frame.isFlipY())
					frame.flip(false, true);
			}
		}
	}
	
	/*
	 * Returns true if player is flipped
	 * else false
	 * @return boolean
	 */
	public boolean isFlip() {
		return this.getY() != POSITION_Y_UPSIDE;
	}
	
	private void initCollisionPos() {
		collisionPosX = posX + collisionSizeX;
		COLLISION_Y_UPSIDE = posY + collisionSizeY;
		COLLISION_Y_DOWNSIDE = COLLISION_Y_UPSIDE - 80;
	}
	
	private void initCollisionSize() {
		collisionSizeX = texture.getWidth()/TOTAL_RUN_FRAMES;
		collisionSizeY = texture.getHeight();

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
