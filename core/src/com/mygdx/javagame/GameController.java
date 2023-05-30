package com.mygdx.javagame;

public class GameController {
	
	public static enum GameState {
		RUN,
		PAUSE,
		GAMEOVER,
		RESTART,
		MAINMENU
	}
	
	public static GameState state = GameState.MAINMENU; // Current game state
	
	public static boolean isRun() {
		return state == GameState.RUN;
	}
	
	public static boolean isPause() {
		return state == GameState.PAUSE;
	}
	
	public static boolean isGameover() {
		return state == GameState.GAMEOVER;
	}
	
	public static boolean isRestart() {
		return state == GameState.RESTART;
	}
	
	public static boolean isMainMenu() {
		return state == GameState.MAINMENU;
	}
	
	public static void setGameState(GameState state) {
		GameController.state = state;
	}
}
