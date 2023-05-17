package com.mygdx.javagame;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.javagame.GameScreen.GameState;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor implements InputProcessor {
	public boolean keyDown (int keycode) {
		
		if(GameScreen.state == GameState.RUN) {
			switch(keycode) {
			
				case Keys.SPACE:
					GameScreen.player.flip();
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