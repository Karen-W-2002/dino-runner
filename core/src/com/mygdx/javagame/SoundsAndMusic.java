package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundsAndMusic {
	// Sounds
	private static Sound collisionSound = Gdx.audio.newSound(Gdx.files.internal("Collision Sound Effect.mp3"));
	
	// Music
	private static Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Menu.mp3"));
	private static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music Game.mp3"));
	
	// Sound methods
	public static void playCollisionSound() {
		collisionSound.play(1);
	}
	
	// Music methods
	public static void toggleMainMusic() {
		if(menuMusic.isPlaying()) {
			menuMusic.stop();
		}
		else {
			menuMusic.play();
			menuMusic.setLooping(true);
		}
	}
	
	public static void toggleGameMusic() {
		if(gameMusic.isPlaying()) {
			gameMusic.stop();
		}
		else {
			gameMusic.play();
			gameMusic.setLooping(true);
		}
	}
	
	public void dispose() {
		collisionSound.dispose();
		menuMusic.dispose();
		gameMusic.dispose();
	}
}
