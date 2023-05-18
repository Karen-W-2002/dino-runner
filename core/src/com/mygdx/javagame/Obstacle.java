package com.mygdx.javagame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
	
	private Random rand;
	
	private ShapeRenderer shape; 
	
	/*
	 * posX: declares and initializes position X of this obstacle
	 * Constantly updated in update() function
	 */
	private float posX = Gdx.graphics.getWidth();
	
//	private float posY = Gdx.graphics.getHeight();
	
	/*
	 * yDir: declares the y-direction of the obstacle (upside or upside down)
	 * Initialized inside the constructor using a random function
	 */
	private float yDir; // later determined with rand
	
	/*
	 * MAX_SPEED:
	 */
	private static final float MAX_SPEED = 1000.0f;
	private static float CURRENT_SPEED = 500.0f;
	
	// Rect for collisions
	Rectangle obstacleRect;
	
	Obstacle() {
		// Generating position Y direction
		rand = new Random();
		int randPos = rand.nextInt(2);
		if(randPos == 0) {
			this.yDir = 150;
			obstacleRect = new Rectangle(posX, 400, 10, 150);
		} else {
			this.yDir = -150;
			obstacleRect = new Rectangle(posX, 400 - 150, 10, 150);
		}
		
		shape = new ShapeRenderer();
	}

	public void update(float delta) {
		float xSpeed = CURRENT_SPEED * (delta);
		CURRENT_SPEED += delta * 3;
		
		setX(posX -  xSpeed);
		updateRect(posX - xSpeed);
	}
	
	public void draw() {		
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.rect(posX, 400, 10, yDir);
		shape.end();
	}

	public void setX(float x) {
		this.posX = x;
	}
	
	public float getX() {
		return this.posX;
	}
	
	void updateRect(float posX) {
		this.obstacleRect.x = posX;
	}
	
	Rectangle getRect() {
		return this.obstacleRect;
	}
	
	public void dispose() {
		shape.dispose();
	}
}
