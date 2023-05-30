package com.mygdx.javagame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.javagame.GameController.GameState;

public class GameScreen implements Screen {
	final MyGame game;
	static Player player = null;
	final Background background;
	final Ground ground;
	
	// Obstacles
	List<Wall> obstacles = new ArrayList<Wall>();
	List<ReverseFlip> reverseflips = new ArrayList<ReverseFlip>();
	List<Egg> eggs = new ArrayList<Egg>();
	
	Stage stage;
	OrthographicCamera camera;
	
	Viewport viewport;
	
	// A variable for tracking elapsed time for the animations
	float stateTime;
	
	// INPUT PROCESSOR
	MyInputProcessor inputProcessor = new MyInputProcessor();
	
	// UI
	GameUI ui;
	
	// Shape renderer
	ShapeRenderer shape;
	
	// Other screens
	PauseScreen pauseScreen;
	GameoverScreen gameoverScreen;
	
	/*
	 * GameScreen Constructor
	 */
	GameScreen(final MyGame game) {
		
		shape = new ShapeRenderer();
		
		this.game = game;
		GameScreen.player = new Player(game);
		this.background = new Background(game);
		this.ground = new Ground();
		
		
		ui = new GameUI(game);
		
		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		
		// Create the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		stateTime = 0f;
		
		// INPUT PROCESSOR INIT
		Gdx.input.setInputProcessor(inputProcessor);
		
		viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT, camera);
 
		// Start the game
		resume();
	    
	    // other screens
	    gameoverScreen = new GameoverScreen(game);
	    pauseScreen = new PauseScreen(game);
	}
	
	/*
	 * Called when Game needs initializing
	 * such as start or restart
	 * @return void
	 */
	private void initGame() {
		removeAllObstacles();
		ui.reset();
		if(player!=null && player.isFlip()) player.flip(); // Flips the player upside before starting
	}
	
	public void restartGame() {
		initGame();
	}
	
	@Override
	public void show() {
		// When the screen is shown
		initGame();
	}

	@Override
	public void render(float delta) {
		if(GameController.isRestart()) {
			initGame();
			GameController.setGameState(GameState.RUN);
		}
		
		if(GameController.isMainMenu()) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		// Clear screen - prevents screen flicker
		Gdx.gl.glClearColor(0, 0, 0, 1);
		ScreenUtils.clear(0, 0, 0, 1);
		
		// Update the Camera
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		
		
		Gdx.graphics.getGL20();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		// BEGIN BATCH
		game.batch.begin();
		
		background.update();
		player.update(stateTime);
		
		
		// END BATCH
		game.batch.end();
		
		ui.drawUI(stateTime);
		
		/*
		 * Draw shape renderer:
		 * 1) Ground
		 * 2) Obstacles
		 */
		ground.draw();
		
		drawAllObstacles();
		
		/*
		 * Switch Case GAMESTATE
		 * If GameState is "RUN" then we update
		 * 1. stateTime
		 *  TODO:
		 * 2. obstacles
		 * 
		 */
		switch(GameController.state) {
		case RUN:
			stateTime += delta; // Accumulate elapsed animation time
			
			// Speeds up the game until MAX_SPEED is reached
			if(Constants.CURRENT_SPEED < Constants.MAX_SPEED)
				Constants.CURRENT_SPEED += delta * 3;
			
			checkForCollisions(); // Check for any collisions
			updateItems(delta);
			ui.updateScore(delta);
			
			
			break;
			
		case PAUSE:
			blurBackground();
			pauseScreen.draw();
			break;
			
		case GAMEOVER:
			blurBackground();
			gameoverScreen.draw(delta); 
			break;
		default:
			// Do nothing
			break;
		}
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
        camera.update();
        for(Wall obstacle : obstacles) {
        	obstacle.resize();
        }
	}

	@Override
	public void pause() {
		GameController.state = GameController.GameState.PAUSE;
	}

	@Override
	public void resume() {
		GameController.state = GameController.GameState.RUN;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		player.dispose();
		ground.dispose();
		pauseScreen.dispose();
		gameoverScreen.dispose();
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
		if(obstacles.size() == 0) createWalls();
		else if(reverseflips.size() == 0 && obstacles.get(obstacles.size() - 1).getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES) createReverseFlips();

		else if(obstacles.size() != 0 && reverseflips.size() != 0){
				
			Wall lastObstacle = obstacles.get(obstacles.size() - 1);
			ReverseFlip lastRf = reverseflips.get(reverseflips.size() - 1);
			
			if(	lastObstacle.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES &&
				lastRf.getX() < Constants.APP_WIDTH - Constants.GAP_BETWEEN_OBSTACLES
			) {
				createWalls();
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
	

	private void createWalls() {
		Wall obstacle = new Wall();
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
	
	/*
	 * Removes off screen objects
	 * 1. Obstacle
	 * 2. ReverseFlip
	 * 3. Coins
	 */
	private void removeOffscreenItems() {
		if(obstacles.size() > 0) {
			Wall firstObstacle = obstacles.get(0);
			
			if(firstObstacle.getX() < 0 - 10) {
				obstacles.get(0).dispose();
				obstacles.remove(0);
			}
		}
		
		if(reverseflips.size() > 0) {
			ReverseFlip rf = reverseflips.get(0);
			
			if(rf.getX() < 0 - 10) {
				reverseflips.get(0).dispose();
				reverseflips.remove(0);
			}
		}
		
		if(eggs.size() > 0) {
			Egg firstEgg = eggs.get(0);
			
			if(firstEgg.getX() < 0 - 10) {
				eggs.get(0).dispose();
				eggs.remove(0);
			}
			
		}
	}
	
	/*
	 * Remove all screen objects to restart the game
	 * @return void
	 */
	private void removeAllObstacles() {
		for(int i=obstacles.size()-1; i>=0; i--) {	
			obstacles.get(i).dispose();
			obstacles.remove(obstacles.get(i));
		}
		
		for(int i=reverseflips.size()-1; i>=0; i--) {
			reverseflips.get(i).dispose();
			reverseflips.remove(reverseflips.get(i));
		}
		
		for(int i=eggs.size()-1; i>=0; i--) {
			eggs.get(i).dispose();
			eggs.remove(eggs.get(i));
		}
	}
	
	private void obstaclesUpdate(float delta) {
		for(Wall obstacle : obstacles) {
			obstacle.update(delta);
		}
	}
	
	private void reverseFlipsUpdate(float delta) {
		for(ReverseFlip rf : reverseflips) {
			rf.update(delta);
		}
	}
	
	private void eggsUpdate(float delta) {
		for(Egg egg : eggs) {
			egg.update(delta);
		}
	}
	
	private void drawAllObstacles() {
		for(Wall obstacle : obstacles) {
			obstacle.draw();
		}
		
		for(ReverseFlip rf : reverseflips) {
			rf.draw();
		}
		
		for(Egg egg : eggs) {
			egg.drawStatic();
		}
	}
	
	private void updateItems(float delta) {
		obstaclesUpdate(delta);
		reverseFlipsUpdate(delta);
		eggsUpdate(delta);
		removeOffscreenItems();
		spawnItems();
	}
	
	/*
	 * Check for on screen collisions between player and other objects
	 * 1. Obstacle
	 * 2. ReverseFlip
	 * 3. Coins
	 */
	private void checkForCollisions() {
		
		for(Wall obstacle : obstacles) {
			if(!player.isHurt()) {
				if (player.getRect().overlaps(obstacle.getRect())) {
					if(!obstacle.getCollided() && ui.isAlive()) {
						obstacle.turnOffCollision();
						player.setHurt();
						SoundsAndMusic.playCollisionSound();
						ui.updateHealth();
					}
						
					if(!ui.isAlive() && !GameController.isGameover())
						GameController.state = GameController.GameState.GAMEOVER;
				}
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
	
	private void blurBackground() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.setColor(new Color(0, 0, 0, 0.6f));
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		shape.end();
	}
	
}
