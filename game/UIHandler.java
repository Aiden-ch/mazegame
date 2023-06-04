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
	
	static Texture reticle;
	static Texture bossHeart;
	
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
		reticle = new Texture("UI/reticle.png");
		bossHeart = new Texture("UI/bossHealth.png");
	}
	
	public static void displayStats(Player player, SpriteBatch batch, Stage stage) {
//		float a;
//		float a2;
		String health = "\n" + (int)player.getHealth();
//		if(player.getBox().overlaps(box)) {
//			a = 0.5f;
//		} else {
//			a = 1f;
//		}
		heartImage.draw(batch, 1);
		font.draw(batch, health, 45, Gdx.graphics.getHeight() - 30);
		
		tick = Math.max(0, tick - 0.2);
//		if(player.getBox().overlaps(cardbox)) {
//			a2 = 0.5f;
//		} else {
//			a2 = 1f;
//		}
//		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
//			if(EnemyHandler.getEnemies(i).getBox().overlaps(cardbox)) {
//				a2 = 0.5f;
//			}
//		}
		
		if(CardHandler.getHand().size() > 0 && CardHandler.getHeld() != null) {
			CardHandler.getHeld().getImage().setPosition(cardbox.x, cardbox.y);
			CardHandler.getHeld().getImage().draw(batch, 1);
		}
		if(tick > 0) {
			font.draw(batch, CardHandler.getHeld().getName(), Gdx.graphics.getWidth()-128-150, 20);
		}
		if(CardHandler.getHeld() != null) {
			if(CardHandler.getHeld().getRefresh() > 0) {
				String out = "cooldown: " + (int)CardHandler.getHeld().getRefresh();
				font.draw(batch, out, Gdx.graphics.getWidth()-128-150, 70);
			}
			if(CardHandler.getHeld().getCharges() >= 0) {
				String out = "Magazine: " + (int)CardHandler.getHeld().getCharges();
				font.draw(batch, out, Gdx.graphics.getWidth()-128-150, 120);
			}
		}
		if(player.getBlock() > 0) {
			String out = "Block: " + player.getBlock();
			font.draw(batch, out, Gdx.graphics.getWidth()-128, 128+60);
		} else {
			String out = "Block: Ready";
			font.draw(batch, out, Gdx.graphics.getWidth()-128, 128+60);
		}
		if(player.getDash() > 0) {
			String out = "Dash: " + player.getDash();
			font.draw(batch, out, Gdx.graphics.getWidth()-128, 128+30);
		} else {
			String out = "Dash: Ready";
			font.draw(batch, out, Gdx.graphics.getWidth()-128, 128+30);
		}
		if(EnemyHandler.bossOnField != null) {
			String out = "Boss Health: " + (int)EnemyHandler.bossOnField.getHealth();
			batch.draw(bossHeart, Gdx.graphics.getWidth()-150, Gdx.graphics.getHeight() - 100);
			font.draw(batch, out, Gdx.graphics.getWidth()-150, Gdx.graphics.getHeight() - 70);
		}
		
		batch.draw(reticle, Gdx.input.getX() - 32, Gdx.graphics.getHeight() - 32 - Gdx.input.getY());
	}
	public static void swapped() {
		tick = 5;
	}
}
