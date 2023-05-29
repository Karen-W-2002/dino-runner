package com.mygdx.javagame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Wall {
	
	private ShapeRenderer shape; 
	
	/*
	 * posX: declares and initializes position X of this obstacle
	 * Constantly updated in update() function
	 */
	private float posX = Gdx.graphics.getWidth();	
	private float posY = Constants.APP_HEIGHT/2;
	
	private int sizeY = Constants.APP_HEIGHT/5;
	
	private int randPos;
	
	private boolean isCollided = false;
	
	
	// Rect for collisions
	Rectangle obstacleRect;
	
	Wall() {
		// Generating position Y direction
		Random rand = new Random();
		randPos = rand.nextInt(2);
		if(randPos == 0) {
			obstacleRect = new Rectangle(posX, posY, 10, sizeY);
		} else {
			obstacleRect = new Rectangle(posX, posY-sizeY, 10, sizeY);
		}
		
		shape = new ShapeRenderer();
	}

	public void update(float delta) {
		float xSpeed = Constants.CURRENT_SPEED * (delta);
		
		
		
		
		setX(posX -  xSpeed);
		updateRect(posX - xSpeed);
	}
	
	public void draw() {		
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.DARK_GRAY);
		
		if(randPos == 0) {
			shape.rect(posX, posY, 10, sizeY);
		} else {
			shape.rect(posX, posY, 10, -sizeY);
		}
		
		shape.end();
	}

	public void setX(float x) {
		this.posX = x;
	}
	
	public float getX() {
		return this.posX;
	}
	
	void updateRect(float posX) {
		obstacleRect.x = posX;
	}
	
	Rectangle getRect() {
		return this.obstacleRect;
	}
	
	public void resize() {
//		UPDATE X AND Y
		this.posY = Constants.APP_HEIGHT/2;
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
	}
}
