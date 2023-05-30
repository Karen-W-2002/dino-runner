package com.mygdx.javagame;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.javagame.GameController.GameState;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor implements InputProcessor {
	
	public boolean keyDown (int keycode) {
		
		if(GameController.isRun()) {
			switch(keycode) {
			
				// Flip the player when space is pressed
				case Keys.SPACE:
					GameScreen.player.flip();
					break;
					
				// Set game state to PAUSE when escape is pressed
				case Keys.ESCAPE:
					GameController.setGameState(GameState.PAUSE);
					break;
					
			}
		}
		
		else if(GameController.isPause()) {
			switch(keycode) {
			
				// Set game state to RUN when escape is pressed
				case Keys.ESCAPE:
					GameController.setGameState(GameState.RUN);
					break;
			
			}
		}
		
		else if(GameController.isGameover()) {
			switch(keycode) {
			
				case Keys.R:
					System.out.println("RESTART");
					break;
				case Keys.ESCAPE:
					System.out.println("Game screen");
					break;
				default:
					break;
					
			}
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
}