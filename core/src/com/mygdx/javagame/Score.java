package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score {
	
	final Main game;
	private float score;
	private String scoreString;
	BitmapFont font;
	
	Score(Main game) {
		this.game = game;
		
		score = 0f;
		scoreString = "Score: 0";
	    font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
	    
	}
	
	public void update(float delta) {
		score += (delta * 30);
		scoreString = "Score: " + (int)score;
	}
	
	public void draw() {
		game.batch.begin();
		font.setColor(Color.WHITE);
		font.draw(game.batch, scoreString, 10, Constants.APP_HEIGHT - 20);
		game.batch.end();
	}
	
	public void resize() {
		
	}
}
