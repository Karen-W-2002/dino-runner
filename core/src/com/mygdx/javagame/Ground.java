package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * CLASS GROUND
 * This is the ground object that the Dino runs on
 */
public class Ground {
	
	private ShapeRenderer shape;
	private float posY = Constants.APP_HEIGHT/2;
	
	Ground() {
		shape = new ShapeRenderer();
	}
	
	public void draw() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.DARK_GRAY);
		shape.rect(0, posY, Constants.APP_WIDTH, 10);
		shape.end();
	}
	
	public void dispose() {
		shape.dispose();
	}
}
