package com.mygdx.javagame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;

public class Background {

	final Main game;
	private List<Texture> texture;
//	private Sprite sprite;
	
	Background(Main game) {
		this.game = game;
		texture = new ArrayList<Texture>();
		
//		texture.add(new Texture("Background/Layer_0000_9.png"));
//		texture.add(new Texture("Background/Layer_0001_8.png"));
//		texture.add(new Texture("Background/Layer_0002_7.png"));
//		texture.add(new Texture("Background/Layer_0003_6.png"));
//		texture.add(new Texture("Background/Layer_0004_Lights.png"));
//		texture.add(new Texture("Background/Layer_0005_5.png"));
//		texture.add(new Texture("Background/Layer_0006_4.png"));
//		texture.add(new Texture("Background/Layer_0007_Lights.png"));
//		texture.add(new Texture("Background/Layer_0008_3.png"));
//		texture.add(new Texture("Background/Layer_0009_2.png"));
//		texture.add(new Texture("Background/Layer_0010_1.png"));
//		texture.add(new Texture("Background/Layer_0011_0.png"));
//		this.sprite = new Sprite(texture);
	}
	
	private void render() {
		for(int i=texture.size()-1; i>=0; i--) {
			game.batch.draw(texture.get(i), 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		}
	}
	
	public void update() {
		this.render();
	}
}
