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
		cardbox.setSize(128, 80);
		cardbox.setX(Gdx.graphics.getWidth()/2 - 64f);
		cardbox.setY(0f);
	}
	
	public static void displayStats(Player player, SpriteBatch batch, Stage stage) {
		float alpha;
		float alpha2;
		String health = "\n" + (int)player.getHealth();
		if(player.getBox().overlaps(box)) {
			alpha = 0.5f;
		} else {
			alpha = 1f;
		}
		heartImage.draw(batch, alpha);
		font.draw(batch, health, 45, Gdx.graphics.getHeight() - 30);
		
		if(player.getBox().overlaps(cardbox)) {
			alpha2 = 0.5f;
		} else {
			alpha2 = 1f;
		}
		
		if(CardHandler.getHand().size() > 0 && CardHandler.getHeld() != null) {
			CardHandler.getHeld().getImage().setPosition(cardbox.x, cardbox.y);
			CardHandler.getHeld().getImage().draw(batch, alpha2);
		}
	}
}
