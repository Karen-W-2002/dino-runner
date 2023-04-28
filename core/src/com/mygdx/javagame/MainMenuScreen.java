package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	final Main game;
	
	OrthographicCamera camera;
	Music menuMusic;
	
	public MainMenuScreen(final Main game) {
		this.game = game;
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Create the music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Menu.mp3"));
		menuMusic.setLooping(true);
	}
	
	@Override
	public void show() {
		// When the screen is shown
		// Start the music
		menuMusic.play();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Dino Thingy!", 100, 150);
		game.font.draw(game.batch, "Press any key to begin!", 100, 100);
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
		menuMusic.dispose();
	}

	
	
}
