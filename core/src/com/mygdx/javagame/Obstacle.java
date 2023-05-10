package com.mygdx.javagame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Obstacle {
	
//	final Main game;
	private ShapeRenderer shape; 
	
	// Position of Obstacle
	private float posX = 1280;
	
	private static final float MAX_SPEED = 100.0f;
	
	
	Obstacle() {
		shape = new ShapeRenderer();
		
		System.out.println("obstacle created");
	}

	public void update(float delta) {
		float xSpeed = MAX_SPEED * delta;
		setX(posX -  xSpeed);
		draw();
	}
	
	private void draw() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.rect(posX, 400, 10, 150);
		shape.end();
	}

	public void setX(float x) {
		this.posX = x;
	}
	
	public float getX() {
		return this.posX;
	}
	
//	Rectangle getRect() {
//		return this.rect;
//	}
	
	public void dispose() {
		shape.dispose();
	}
}
