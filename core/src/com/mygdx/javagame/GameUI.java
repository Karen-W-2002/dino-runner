package com.mygdx.javagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameUI {

	final Main game;
	private Score score; // Game score
	private Health health; // Health (3 hearts)
	private Egg egg; // Egg texture (animated)
	private int eggScore; // Egg score
	private String eggScoreString;
	private BitmapFont font;
	
	GameUI(final Main game) {
		this.game = game;
		
		score = new Score(game);
		health = new Health(game);
		egg = new Egg(game);
		
		reset();
		
		font = new BitmapFont();
	    font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font.getData().setScale(2);
	    font.setColor(Color.WHITE);
	}
	
	/*
	 * Resets the UI, used for game start and restarts
	 * @return void
	 */
	public void reset() {
		eggScore = 0;
		eggScoreString = "x0";
		health.reset();
		score.reset();
	}
	
	/*
	 * Called when GameState is RUN
	 * Increases the current score and updates the
	 * displayed score
	 * @return void
	 */
	public void updateScore(float delta) {
		score.update(delta);
	}
	
	/*
	 * Called when health needs to be -1
	 * @return void
	 */
	public void updateHealth() {
		health.removeHeart();
	}
	
	/* TODO: MOVE THIS TO PLAYER
	 * Checks if player is alive 
	 * @return bool
	 */
	public boolean isAlive() {
		return health.getNumberOfLives() > 0;
	}
	
	/*
	 * Called when an egg is picked up
	 * adds one to the score and updates the
	 * score string
	 * @return void
	 */
	public void updateEggScore() {
		eggScore += 1;
		eggScoreString = "x" + eggScore;
	}
	
	/* 
	 * Draws the UI of the game
	 * @param float stateTime	the current state time of the game, used for animations
	 * @return void
	 */
	public void drawUI(float stateTime) {
		game.batch.begin();
		score.draw();
		health.draw();
		egg.drawAnimation(stateTime); // Draws egg animation
		font.draw(game.batch, eggScoreString, 50, Constants.APP_HEIGHT - 100 - 12); // Draws number of eggs (eg. x0)
		game.batch.end();
	}
	
}
