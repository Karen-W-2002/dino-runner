package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

	final Main game;
	
	OrthographicCamera camera;
	Music menuMusic;
	
	// BUTTON
	Stage stage;
	
	// BACKGROUND
	private Background bg;
	
	// INPUT PROCESSOR
	MyInputProcessor inputProcessor = new MyInputProcessor();
	
	public MainMenuScreen(final Main game) {
		this.game = game;
		
		// Staged
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// Create the camera
//		Common.initCamera(camera);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		
		// Create the music
//		Common.initMusic(menuMusic, "Music Menu.mp3");
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Menu.mp3"));
		menuMusic.setLooping(true);
		
		// BG
		bg = new Background(game);
		
		
		// BUTTONS
		
	    TextButtonStyle textButtonStyle = new TextButtonStyle();
	    textButtonStyle.font = new BitmapFont();
	    textButtonStyle.fontColor = Color.WHITE;
	    stage.addActor(new TextButton("Custom Btn ", textButtonStyle));
	}
	
	@Override
	public void show() {
		// When the screen is shown
		// Start the music
		menuMusic.play();
	}

	@Override
	public void render(float delta) {
		// Clear screen - prevents screen flicker
		Gdx.gl.glClearColor(0, 0, 0, 1);
		ScreenUtils.clear(0, 0, 0, 1);
		
		// Update camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
//		bg.update();
		
//		game.font.draw(game.batch, "Welcome to Dino Thingy!", 100, 150);
//		game.font.draw(game.batch, "Press any key to begin!", 100, 100);
		
		
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
		
//		stage.act();
		stage.draw();
		
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
