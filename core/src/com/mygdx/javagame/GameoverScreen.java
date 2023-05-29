package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GameoverScreen {
	
	Main game;
	
	GlyphLayout layout1;
	GlyphLayout layout2;
	
	BitmapFont font;
	
	Texture gameover;
	
	GameoverScreen(Main game) {
		this.game = game;
		
		font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
		
		layout1 = new GlyphLayout(font, "PRESS R TO RESTART.");
		layout2 = new GlyphLayout(font, "PRESS ESC TO GO TO MAIN MENU.");
		
		gameover = new Texture("Menu/GAMEOVER.png");
	}
	
	public void draw() {
		game.batch.begin();
		game.batch.draw(gameover, Gdx.graphics.getWidth()/2 - gameover.getWidth()/2, Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/5);
		font.draw(game.batch, layout1, Gdx.graphics.getWidth() / 2 - layout1.width/2, Gdx.graphics.getHeight() / 4 + layout1.height/2);
		font.draw(game.batch, layout2, Gdx.graphics.getWidth() / 2 - layout2.width/2, Gdx.graphics.getHeight() / 6 + layout2.height/2);
		game.batch.end();
	}
	
	public void resize() {
		
	}
	
	public void dispose() {
		font.dispose();
		gameover.dispose();
	}
}
