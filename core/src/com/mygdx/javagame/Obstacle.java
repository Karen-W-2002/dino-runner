package com.mygdx.javagame;

import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
	
	private Random rand;
	
	private ShapeRenderer shape; 
	
	// Position of Obstacle
	private float posX = 1280;
	private float yDir; // later determined with rand
	
	private static final float MAX_SPEED = 500.0f;
	
	// Rect for collisions
	Rectangle obstacleRect;
	
	
	Obstacle() {
		// Generating position Y direction
		rand = new Random();
		int randPos = rand.nextInt(2);
		if(randPos == 0) this.yDir = 150;
		else this.yDir = -150;
		
		obstacleRect = new Rectangle(posX, 400, 10, yDir);
		
		
		shape = new ShapeRenderer();
		
		System.out.println("obstacle created");
	}

	public void update(float delta) {
		float xSpeed = MAX_SPEED * delta;
		
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
