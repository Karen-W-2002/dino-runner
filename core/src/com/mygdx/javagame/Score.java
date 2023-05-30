package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score {
	
	final MyGame game;
	private float score;
	private String scoreString;
	BitmapFont font;
	
	Score(MyGame game) {
		this.game = game;
		reset();
		
	    font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
	}
	
	/*
	 * Resets score for the UI, used for game start and restarts
	 * @return void
	 */
	public void reset() {
		score = 0f;
		scoreString = "Score: 0";
	}
	
	public void update(float delta) {
		score += (delta * 30);
		scoreString = "Score: " + (int)score;
	}
	
	public void draw() {
		font.setColor(Color.WHITE);
		font.draw(game.batch, scoreString, 10, Constants.APP_HEIGHT - 20);
	}
	
	public void resize() {
		
	}
}
