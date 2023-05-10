package com.mygdx.javagame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final Main game;
	final Player player;
	final Background background;
	final Ground ground;
	
	// Obstacles
//	public static List<Obstacle> obstacles = new ArrayList<Obstacle>();
	List<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	Stage stage;
	OrthographicCamera camera;
	Music gameMusic;
	
	// A variable for tracking elapsed time for the animation
	// TODO: maybe use delta time for the animation
	float stateTime;
	
	GameScreen(final Main game) {
		this.game = game;
		this.player = new Player(game);
		this.background = new Background(game);
		this.ground = new Ground();
		
		// Create the obstacles
		createObstacles();
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.APP_WIDTH_RESOLUTION, Constants.APP_HEIGHT_RESOLUTION);
		
		// Create the music
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Game.mp3"));
		gameMusic.setLooping(true);
		
		// Create the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
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
		// Clear screen - prevents screen flicker
		Gdx.gl.glClearColor(0, 0, 0, 1);
		ScreenUtils.clear(0, 0, 0, 1);
		
		// Update the Camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// Update shape renderers first
		ground.draw();
		
		// Update obstacles and cleanup
		for(Obstacle obstacle : obstacles) {
			obstacle.update(delta);
		}
		spawnObstacle();
		removeOffscreenObstacles();
		
		// Update the Camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// Add to state time
		stateTime += delta; // Accumulate elapsed animation time
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			// Flip the dino
			// and make sure it only presses once!
		}

		
//		if (player.getRect().overlaps(ob1.getRect())) {
//			System.out.println("X");
//		}
		
		// BEGIN BATCH
		game.batch.begin();
		
		background.update();
		player.update(stateTime);
		
		// END BATCH
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
		ground.dispose();
	}
	
	private void spawnObstacle() {
		if(obstacles.size() == 0) {
			createObstacles();
		} else {
			Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
			
			if(lastObstacle.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES) {
				createObstacles();
			}
		}
	}
	
	private void createObstacles() {
		Obstacle obstacle = new Obstacle();
		obstacle.setX(Gdx.graphics.getWidth());
		obstacles.add(obstacle);
	}

	private void removeOffscreenObstacles() {
		if(obstacles.size() > 0) {
			Obstacle firstObstacle = obstacles.get(0);
			
			if(firstObstacle.getX() < 0 - 10)
			{
				obstacles.get(0).dispose();
				obstacles.remove(0);
			}
		}
	}
	
}
