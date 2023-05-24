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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	final Main game;
	static Player player = null;
	final Background background;
	final Ground ground;
	
	// Obstacles
//	public static List<Obstacle> obstacles = new ArrayList<Obstacle>();
	List<Obstacle> obstacles = new ArrayList<Obstacle>();
	List<ReverseFlip> reverseflips = new ArrayList<ReverseFlip>();
	List<Egg> eggs = new ArrayList<Egg>();
	
	Stage stage;
	OrthographicCamera camera;
	Music gameMusic;
	
	Viewport viewport;
	
	// A variable for tracking elapsed time for the animation
	// TODO: maybe use delta time for the animation
	float stateTime;
	
	public enum GameState {
		RUN,
		PAUSE,
	}
	
	// INPUT PROCESSOR
	MyInputProcessor inputProcessor = new MyInputProcessor();
	
	// GAME STATE
	public static GameState state = null;
	
	
	// score
	Score score;
	Health health;
	GameUI ui;
	
	/*
	 * GameScreen Constructor
	 */
	GameScreen(final Main game) {
		this.game = game;
		GameScreen.player = new Player(game);
		this.background = new Background(game);
		this.ground = new Ground();
		
		// Create the obstacles
		createObstacles();
		ui = new GameUI(game);
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		
		// Create the music
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Game.mp3"));
		gameMusic.setLooping(true);
		
		// Create the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		stateTime = 0f;
		
		// INPUT PROCESSOR INIT
		Gdx.input.setInputProcessor(inputProcessor);
		
		viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT, camera);
 
		score = new Score(game);
		health = new Health(game);
		
		// Start the game
		resume();
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
		
		
		
		checkForPause();
		
		
		// SHAPERENDERER ERROR
		Gdx.gl.glDisable(Gdx.graphics.getGL20().GL_BLEND);
		
		// BEGIN BATCH
		game.batch.begin();
		
		
		
		background.update();
		player.update(stateTime);
		health.draw();
		
		// END BATCH
		game.batch.end();
		
		
		score.draw();
		ui.drawUI(stateTime);
		
		
		/*
		 * Draw shape renderer:
		 * 1) Ground
		 * 2) Obstacles
		 */
		ground.draw();
		for(Obstacle obstacle : obstacles) {
			obstacle.draw();
		}
		
		/*
		 * Switch Case GAMESTATE
		 * If GameState is "RUN" then we update
		 * 1. stateTime
		 * 2. collisions
		 * 3. obstacles
		 * 
		 * If GameState is "PAUSE" then do nothing...
		 */
		switch(state) {
		case RUN:
			stateTime += delta; // Accumulate elapsed animation time
			
			
			
			// Check for any collisions
			checkForCollisions();
			
			updateItems(delta);
			score.update(delta);
			
			
			break;
			
		case PAUSE:
			// Do nothing
			break;
		}
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
        camera.update();
        for(Obstacle obstacle : obstacles) {
        	obstacle.resize();
        }
	}

	@Override
	public void pause() {
		GameScreen.state = GameState.PAUSE;
	}

	@Override
	public void resume() {
		GameScreen.state = GameState.RUN;
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
	
	/*
	 * Spawn objects into the game
	 * 
	 * Priority goes from 1 to 3,
	 * For example, the code checks for existing obstacles first, and if they don't exist
	 * Create the obstacle first, and other objects will be generated in the following updates
	 * 1. TODO: Obstacles
	 * 2. TODO: ReverseFlip
	 * 3. TODO: Coins
	 */
	private void spawnItems() {
		if(obstacles.size() == 0) createObstacles();
		else if(reverseflips.size() == 0 && obstacles.get(obstacles.size() - 1).getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES) createReverseFlips();

		else if(obstacles.size() != 0 && reverseflips.size() != 0){
				
			Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
			ReverseFlip lastRf = reverseflips.get(reverseflips.size() - 1);
			
			if(	lastObstacle.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES &&
				lastRf.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES
			) {
				createObstacles();
			}
			else if(lastObstacle.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES &&
					lastRf.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES) {
				createReverseFlips();
			}
			else if(lastObstacle.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_EGGS &&
					lastRf.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_EGGS) {

				if(eggs.size() == 0) createEggs();
				else {
					Egg lastEgg = eggs.get(eggs.size() - 1);
					if(lastEgg.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_EGGS) {
						createEggs();
					}
				}
			}
			
		}
	
	}
	
	/*
	 * Create individual items
	 */
	
	private void createObstacles() {
		Obstacle obstacle = new Obstacle();
		obstacle.setX(Gdx.graphics.getWidth());
		obstacles.add(obstacle);
	}
	
	private void createReverseFlips() {
		ReverseFlip rf = new ReverseFlip(game);
		rf.setX(Gdx.graphics.getWidth());
		reverseflips.add(rf);
	}
	
	private void createEggs() {
		Egg egg = new Egg(game);
		egg.setX(Gdx.graphics.getWidth());
		eggs.add(egg);
	}
	
	private void removeOffscreenEggs() {
		if(eggs.size() > 0) {
			Egg firstEgg = eggs.get(0);
			
			if(firstEgg.getX() < 0 - 10) {
				eggs.get(0).dispose();
				eggs.remove(0);
			}
			
		}
	}

	private void removeOffscreenObstacles() {
		if(obstacles.size() > 0) {
			Obstacle firstObstacle = obstacles.get(0);
			
			if(firstObstacle.getX() < 0 - 10) {
				obstacles.get(0).dispose();
				obstacles.remove(0);
			}
		}
	}
	
	private void removeOffscreenReverseFlips() {
		if(reverseflips.size() > 0) {
			ReverseFlip rf = reverseflips.get(0);
			
			if(rf.getX() < 0 - 10) {
				reverseflips.get(0).dispose();
				reverseflips.remove(0);
			}
		}
	}
	
	/*
	 * Removes off screen objects
	 * 1. TODO: Obstacle
	 * 2. TODO: ReverseFlip
	 * 3. TODO: Coins
	 */
	private void removeOffscreenItems() {
		
	}
	
	private void obstaclesUpdate(float delta) {
		for(Obstacle obstacle : obstacles) {
			obstacle.update(delta);
		}
//		spawnObstacle();
		removeOffscreenObstacles();
	}
	
	private void reverseFlipsUpdate(float delta) {
		for(ReverseFlip rf : reverseflips) {
			rf.update(delta);
		}
//		spawnReverseFlip();
		removeOffscreenReverseFlips();
	}
	
	private void eggsUpdate(float delta) {
		for(Egg egg : eggs) {
			egg.update(delta);
		}
		
		removeOffscreenEggs();
	}
	
	private void updateItems(float delta) {
		obstaclesUpdate(delta);
		reverseFlipsUpdate(delta);
		eggsUpdate(delta);
		spawnItems();
	}
	
	/*
	 * Check for on screen collisions between player and other objects
	 * 1. Obstacle
	 * 2. ReverseFlip
	 * 3. Coins
	 */
	private void checkForCollisions() {
		
		for(Obstacle obstacle : obstacles) {
			if (player.getRect().overlaps(obstacle.getRect())) {
				if(!obstacle.getCollided() && health.getNumberOfLives() > 0) {
					obstacle.turnOffCollision();
					SoundsAndMusic.playCollisionSound();
					health.removeHeart();
				}
					
				if(health.getNumberOfLives() == 0)
					pause();
			}
		}
		
		for(ReverseFlip rf : reverseflips) {
			if(player.getRect().overlaps(rf.getRect())) {
				if(!rf.getCollided()) {
					rf.turnOffCollision();
					player.flip();
				}
			}
		}
		
		for(Egg egg : eggs) {
			if(player.getRect().overlaps(egg.getRect())) {
				if(!egg.getCollided()) {
					egg.turnOffCollision();
					ui.updateEggScore();
					egg.setSize(0, 0);
				}
			}
		}
		
		
	}
	
	private void checkForPause() {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			pause();
		}
	}
	
}
