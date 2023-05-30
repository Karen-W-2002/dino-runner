package com.mygdx.javagame;

public class GameController {
	public static enum GameState {
		RUN,
		PAUSE,
		GAMEOVER
	}
	
	public static GameState state = null; // Current game state
	
	public static boolean isRun() {
		return state == GameState.RUN;
	}
	
	public static boolean isPause() {
		return state == GameState.PAUSE;
	}
	
	public static boolean isGameover() {
		return state == GameState.GAMEOVER;
	}
	
	public static void setGameState(GameState state) {
		GameController.state = state;
	}
}
