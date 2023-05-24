package com.mygdx.javagame;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Egg {
	
	final Main game;

	Texture eggTexture;
	Animation<TextureRegion> eggAnimation;
	TextureRegion[] eggMoveFrames = new TextureRegion[TOTAL_ANIMATION_FRAMES + 4];
		
	// Rect for collisions
	private Rectangle rect;
		
	private boolean isCollided = false;
	
	private static final int TOTAL_ANIMATION_FRAMES = 4;
	
	private float posX = Constants.APP_WIDTH;
	private float posY;
	

	private float scale = 2.0f;
	
	private float sizeX;
	private float sizeY;
	
	Egg(final Main game) {
		this.game = game;
		
		eggTexture = new Texture("DinoEgg/egg_blue.png");

				
		initEggAnimation();
		
		Random rand = new Random();
		int randNum = rand.nextInt(2);
		if(randNum == 0) {
			posY = Constants.APP_HEIGHT/2 + 10;
		} else {
			posY = Constants.APP_HEIGHT/2  - eggMoveFrames[0].getRegionHeight()*scale;
		}
		
		sizeX = eggMoveFrames[0].getRegionWidth()*scale;
		sizeY = eggMoveFrames[0].getRegionHeight()*scale;
		
		rect = new Rectangle(posX, posY, sizeX/scale, sizeY);
	}
	
	public void drawStatic() {
		game.batch.begin();
		game.batch.draw(eggMoveFrames[0], posX, posY, sizeX, sizeY);
		game.batch.end();
	}
	
	public void drawAnimation(float delta) {
		TextureRegion region = getCurrentEggFrame(delta);
		game.batch.draw(region,  10, Constants.APP_HEIGHT - 100 - region.getRegionHeight()*scale, region.getRegionWidth()*scale, region.getRegionHeight()*scale);
	}
	
	public void update(float delta) {
		float xSpeed = Constants.CURRENT_SPEED * (delta);
		
		if(Constants.CURRENT_SPEED < Constants.MAX_SPEED)
//			Constants.CURRENT_SPEED += delta * 3; // TODO: move to gamescreen
		
		
		setX(posX -  xSpeed);
		updateRect(posX - xSpeed);
		this.drawStatic();
	}
	
	public void setX(float posX) {
		this.posX = posX;
	}
	
	public float getX() {
		return this.posX;
	}
	
	public void updateRect(float posX) {
		rect.x = posX;
	}
	
	public Rectangle getRect() {
		return this.rect;
	}
	
	TextureRegion getCurrentEggFrame(float time) {
		return eggAnimation.getKeyFrame(time, true);
	}
	
	private void initEggAnimation() {
		TextureRegion[][] tempRegion = TextureRegion.split(eggTexture,
				eggTexture.getWidth()/TOTAL_ANIMATION_FRAMES,
				eggTexture.getHeight());
	
		for(int i=0; i<TOTAL_ANIMATION_FRAMES; i++) {
			eggMoveFrames[i] = tempRegion[0][i];
		}
		
		// ADDS 4 MORE STATIC FRAMES
		eggMoveFrames[4] = tempRegion[0][0];
		eggMoveFrames[5] = tempRegion[0][0];
		eggMoveFrames[6] = tempRegion[0][0];
		eggMoveFrames[7] = tempRegion[0][0];
		
		eggAnimation = new Animation<TextureRegion>(0.1f, eggMoveFrames);
	}
	
	public boolean getCollided() {
		return isCollided;
	}
	
	public void turnOffCollision() {
		isCollided = true;
	}
	
	public void setSize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public void dispose() {
		eggTexture.dispose();
	}
	
}
