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
	
	ReverseFlip(final Main game) {
		this.game = game;
		
		shape = new ShapeRenderer();
		
		texture = new Texture("arrow3.png");
	
		
		arrowTopSprite = new Sprite(texture);
		arrowTopSprite.setScale(0.2f, 0.15f);
		arrowTopSprite.setBounds(100, posY, arrowTopSprite.getScaleX()*arrowTopSprite.getWidth(), arrowTopSprite.getScaleY()*arrowTopSprite.getHeight());

		arrowBottomSprite = new Sprite(texture);
		arrowBottomSprite.setFlip(true, true);
		arrowBottomSprite.setScale(0.2f, 0.15f);
		arrowBottomSprite.setBounds(100, posY - arrowBottomSprite.getHeight() - 10, arrowBottomSprite.getScaleX()*arrowBottomSprite.getWidth(), arrowBottomSprite.getScaleY()*arrowBottomSprite.getHeight());
		
//		rect = new Rectangle(posX, posY, sizeX, sizeY);
	}
	
	public void update(float delta) {
		float xSpeed = Constants.CURRENT_SPEED * delta;
		
//		game.batch.draw(texture, 100, posY, sizeX, sizeY);
		game.batch.draw(arrowTopSprite, 100, posY, arrowTopSprite.getWidth(), arrowTopSprite.getHeight());
		game.batch.draw(arrowBottomSprite, 100, posY - arrowBottomSprite.getHeight() - 10, arrowBottomSprite.getWidth(), arrowBottomSprite.getHeight());
//		arrowTopSprite.draw(game.batch);
//		arrowBottomSprite.draw(game.batch);

	}
	
	public void draw() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
//		shape.rect();
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
	
	/*
	 * Disposes of unwanted objects
	 */
	public void dispose() {
		shape.dispose();
		texture.dispose();
	
	}
}
