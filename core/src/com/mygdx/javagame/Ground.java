package com.mygdx.javagame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ground {
	
	private ShapeRenderer shape; 
	
	Ground() {
		shape = new ShapeRenderer();
	}
	
	public void draw() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.rect(0, 400, Constants.APP_WIDTH, 10);
		shape.end();
	}
	
	public void dispose() {
		shape.dispose();
	}
}
