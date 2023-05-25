package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UIHandler {
	static Image heartImage;
	static BitmapFont font = new BitmapFont();
	static Rectangle box;
	static Rectangle cardbox;
	
	static double tick = 0;
	
	public UIHandler() {
		;
	}
	public UIHandler(Texture txte) {
		heartImage = new Image(txte);
		heartImage.setPosition(30, Gdx.graphics.getHeight() - 64 - 20);
		box = new Rectangle();
		box.setSize(30, 50);
		box.setX(10f);
		box.setY((float)Gdx.graphics.getHeight() - 64f - 10f);
		cardbox = new Rectangle();
		cardbox.setSize(128, 128);
		cardbox.setX(Gdx.graphics.getWidth() - 128f);
		cardbox.setY(0f);
	}
	
	public static void displayStats(Player player, SpriteBatch batch, Stage stage) {
		float a;
		float a2;
		String health = "\n" + (int)player.getHealth();
		if(player.getBox().overlaps(box)) {
			a = 0.5f;
		} else {
			a = 1f;
		}
		heartImage.draw(batch, a);
		font.draw(batch, health, 45, Gdx.graphics.getHeight() - 30);
		
		tick = Math.max(0, tick - 0.2);
		if(player.getBox().overlaps(cardbox)) {
			a2 = 0.5f;
		} else {
			a2 = 1f;
		}
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			if(EnemyHandler.getEnemies(i).getBox().overlaps(cardbox)) {
				a2 = 0.5f;
			}
		}
		
		if(CardHandler.getHand().size() > 0 && CardHandler.getHeld() != null) {
			CardHandler.getHeld().getImage().setPosition(cardbox.x, cardbox.y);
			CardHandler.getHeld().getImage().draw(batch, a2);
		}
		if(tick > 0) {
			font.draw(batch, CardHandler.getHeld().getName(), Gdx.graphics.getWidth()-128-150, 20);
		}
	}
	public static void swapped() {
		tick = 5;
	}
}
