package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Texture;

public class Health {

	final Main game;
	private Texture heart;
	private Texture heartEmpty;
	
	private int numberOfLives;
	
	Health(Main game) {
		
		this.game = game;
		
		// Initialize the number of lives to 3
		numberOfLives = 3;
		
		heart = new Texture("Health/heart.png");
		heartEmpty = new Texture("Health/heart_empty.png");
	}
	

	public void draw() {
		switch(this.getNumberOfLives()) {
			case 3:
				game.batch.draw(heart, 10, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				game.batch.draw(heart, 60, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				game.batch.draw(heart, 110, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				break;
				
			case 2:
				game.batch.draw(heart, 10, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				game.batch.draw(heart, 60, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				game.batch.draw(heartEmpty, 110, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				break;
				
			case 1:
				game.batch.draw(heart, 10, Constants.APP_HEIGHT - 100, heart.getWidth()*2.5f, heart.getHeight()*2.5f);
				game.batch.draw(heartEmpty, 60, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				game.batch.draw(heartEmpty, 110, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				break;
				
			case 0:
				game.batch.draw(heartEmpty, 10, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				game.batch.draw(heartEmpty, 60, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				game.batch.draw(heartEmpty, 110, Constants.APP_HEIGHT - 100, heartEmpty.getWidth()*2.5f, heartEmpty.getHeight()*2.5f);
				break;
		}
		
	}
	
	public int getNumberOfLives() {
		return this.numberOfLives;
	}
	
	/*
	 * Removes a heart when player collides with an obstacle
	 */
	public void removeHeart() {
		this.numberOfLives--;
	}
	
	public void resize() {
		
	}
	
	public void dispose() {
		heart.dispose();
		heartEmpty.dispose();
	}
}
