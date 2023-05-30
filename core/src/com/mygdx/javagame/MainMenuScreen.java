package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

	final Main game;
	
	OrthographicCamera camera;

	Stage stage;
	
	private Background bg;
	
	BitmapFont font2;
	GlyphLayout layout2;
	float text2x;
	float text2y;
	String text2 = "CLICK ON ANY KEY TO START!";
	
	Texture logo;
	
	// INPUT PROCESSOR
	MyInputProcessor inputProcessor = new MyInputProcessor();
	
	public MainMenuScreen(final Main game) {
		this.game = game;
		
		// Staged
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		
		// BG
		bg = new Background(game);
		
	    font2 = new BitmapFont();
	    font2.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    font2.getData().setScale(2);
	    
	    layout2 = new GlyphLayout(font2, text2);
	    
	    text2x = Gdx.graphics.getWidth() / 2 - layout2.width/2;
	    text2y = Gdx.graphics.getHeight() / 5 + layout2.height/2;
	    
	    logo = new Texture("Menu/LOGO.png");
	    Gdx.input.setInputProcessor(inputProcessor);
	}
	
	@Override
	public void show() {
		// When the screen is shown
		// Start the music
		SoundsAndMusic.toggleMainMusic();
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
		bg.update();
		
		game.batch.draw(logo, Gdx.graphics.getWidth()/2 - logo.getWidth()/2, Gdx.graphics.getHeight()/2);
		font2.draw(game.batch, text2, text2x, text2y);
		
		game.batch.end();
		
		
		if(GameController.isRun()) {
			SoundsAndMusic.toggleMainMusic(); // Turn off Main menu music
			SoundsAndMusic.toggleGameMusic(); // Turn on Game music
			game.setScreen(new GameScreen(game));
			dispose();
		}
		
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
		font2.dispose();
	}

	
	
}
