package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ReverseFlip {
	
	final Main game;
	
	/*
	 * Position of this object
	 * posX is constantly updated
	 * posY is a constant
	 */
	private float posX = Gdx.graphics.getWidth();
	private static final float posY = Gdx.graphics.getHeight()/2 + 10;
	
	/*
	 * Texture for this object
	 */
	private Texture texture;
	
	private Sprite arrowTopSprite;
	private Sprite arrowBottomSprite;
	
	// FOR DEBUGGING: outlines collision box;
	private ShapeRenderer shape;
	
	// Rect for collisions
	Rectangle rect;
	
	private boolean isCollided = false;
	
	ReverseFlip(final Main game) {
		this.game = game;
		
		shape = new ShapeRenderer();
		
		texture = new Texture("arrow3.png");
	
		
		arrowTopSprite = new Sprite(texture);
		arrowTopSprite.setScale(0.2f, 0.15f);
		arrowTopSprite.setBounds(posX, posY, arrowTopSprite.getScaleX()*arrowTopSprite.getWidth(), arrowTopSprite.getScaleY()*arrowTopSprite.getHeight());

		arrowBottomSprite = new Sprite(texture);
		arrowBottomSprite.setFlip(true, true);
		arrowBottomSprite.setScale(0.2f, 0.15f);
		arrowBottomSprite.setBounds(posX, posY - arrowBottomSprite.getHeight() - 10, arrowBottomSprite.getScaleX()*arrowBottomSprite.getWidth(), arrowBottomSprite.getScaleY()*arrowBottomSprite.getHeight());
		
		rect = new Rectangle(posX, posY - arrowTopSprite.getHeight() - 10, arrowTopSprite.getWidth(), arrowTopSprite.getHeight()*2 + 10);
	}
	
	public void update(float delta) {
		float xSpeed = Constants.CURRENT_SPEED * delta;
		
		if(Constants.CURRENT_SPEED < Constants.MAX_SPEED)
			Constants.CURRENT_SPEED += delta * 3; // TODO: move to gamescreen
		
		setX(posX -  xSpeed);
		updateRect(posX - xSpeed);

		game.batch.begin();
		game.batch.draw(arrowTopSprite, posX, posY, arrowTopSprite.getWidth(), arrowTopSprite.getHeight());
		game.batch.draw(arrowBottomSprite, posX, posY - arrowBottomSprite.getHeight() - 10, arrowBottomSprite.getWidth(), arrowBottomSprite.getHeight());
		game.batch.end();
	
	}
	
	public void draw() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.rect(posX, posY - arrowTopSprite.getHeight() - 10, arrowTopSprite.getWidth(), arrowTopSprite.getHeight()*2 + 10);
		shape.end();
	}
	
	public void setX(float x) {
		this.posX = x;
	}
	
	public float getX() {
		return this.posX;
	}
	
	void updateRect(float posX) {
		this.rect.x = posX;
	}
	
	Rectangle getRect() {
		return this.rect;
	}
	
	public boolean getCollided() {
		return isCollided;
	}
	
	public void turnOffCollision() {
		isCollided = true;
	}
	
	/*
	 * Disposes of unwanted objects
	 */
	public void dispose() {
		shape.dispose();
		texture.dispose();
	
	}
}
