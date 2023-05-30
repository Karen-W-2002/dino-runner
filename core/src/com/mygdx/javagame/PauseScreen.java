package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class PauseScreen {

	Main game;
	
	GlyphLayout layout1;
	
	BitmapFont font;
	
	Texture pause;
	
	PauseScreen(Main game) {
		this.game = game;
		
		font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
		
		layout1 = new GlyphLayout(font, "PRESS ESC TO CONTINUE...");
		
		pause = new Texture("Menu/PAUSE.png");
	}
	
	public void draw() {
		game.batch.begin();
		game.batch.draw(pause, Gdx.graphics.getWidth()/2 - pause.getWidth()/2, Gdx.graphics.getHeight()/2);
		font.draw(game.batch, layout1, Gdx.graphics.getWidth() / 2 - layout1.width/2, Gdx.graphics.getHeight() / 5 + layout1.height/2);
		game.batch.end();
	}
	
	public void resize() {
		
	}
	
	public void dispose() {
		font.dispose();
		pause.dispose();
	}
}
