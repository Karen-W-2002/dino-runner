package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final Main game;
	final Player player;
	OrthographicCamera camera;
	Music gameMusic;
	
	// A variable for tracking elapsed time for the animation
	float stateTime;
	
	GameScreen(final Main game) {
		this.game = game;
		this.player = new Player();
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);
		
		// Create the music
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Game.mp3"));
		gameMusic.setLooping(true);
		
		stateTime = 0f;
	}
	
	@Override
	public void show() {
		// When the screen is shown
		// Start the music
		gameMusic.play();
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = player.getCurrentRunFrame(stateTime);
		game.batch.begin();
		game.batch.draw(currentFrame, 50, 20, 256, 256);
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		player.dispose();
		gameMusic.dispose();
	}

}
