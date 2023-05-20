package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.miscItems.*;

public class AttackHandler {
	
	public static float use(Player player, Card item, float buffer) {
		//
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			
			Misc temp = item.getMisc();
			Image newImage;
			switch (item.getMisc().getType()) {
			case "speedpotion":
				newImage = new Image(item.getMisc().getTexture());
				newImage.setOrigin((float)item.getMisc().getTexture().getWidth()/2f, (float)item.getMisc().getTexture().getHeight()/2f);
				temp = new SpeedPot(item.getMisc().getTexture(), newImage);

				break;
			case "healthpotion":
				newImage = new Image(item.getMisc().getTexture());
				newImage.setOrigin((float)item.getMisc().getTexture().getWidth()/2f, (float)item.getMisc().getTexture().getHeight()/2f);
				temp = new HealthPot(item.getMisc().getTexture(), newImage);
				break;

			}
			Card tempItem = new Card(item.getName(), temp, item.getImage());
			active.add(tempItem);
			return 2.2f;

		}
		return buffer;
	}
}
