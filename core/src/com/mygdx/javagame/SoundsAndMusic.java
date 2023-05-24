package com.mygdx.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundsAndMusic {
	private static Sound collisionSound = Gdx.audio.newSound(Gdx.files.internal("Collision Sound Effect.mp3"));
	
	public static void playCollisionSound() {
		collisionSound.play(1);
	}
	
	public void dispose() {
		collisionSound.dispose();
	}
}
