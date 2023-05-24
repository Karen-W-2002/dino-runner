package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameUI {

	final Main game;
	private Egg egg;
	private int eggScore;
	private String eggScoreString;
	private BitmapFont font;
	
	GameUI(final Main game) {
		this.game = game;
		eggScore = 0;
		eggScoreString = "x0";
		
		egg = new Egg(game);
		
		font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
	}
	
	/*
	 * Called when an egg is picked up
	 * adds one to the score and updates the
	 * score string
	 */
	public void updateEggScore() {
		eggScore += 1;
		eggScoreString = "x" + eggScore;
	}
	
	/* 
	 * Draws the UI of the game
	 * stateTime: the current statetime of the game, used for animations
	 */
	public void drawUI(float stateTime) {
		game.batch.begin();
		font.setColor(Color.WHITE);
		egg.drawAnimation(stateTime);
		font.draw(game.batch, eggScoreString, 50, Constants.APP_HEIGHT - 100 - 12);
		game.batch.end();
	}
	
}
