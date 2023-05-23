package com.mygdx.game.miscItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Misc;
import com.mygdx.game.Player;

public class HealthPot extends Misc {
	private float tick = 0.0f;
	boolean using = false;
	
	public HealthPot(Texture txte) {
		super(txte);
		this.getImage().setOrigin(txte.getWidth()/2,txte.getHeight()/2);
		setType("healthpotion");
	}
	
	@Override
	public boolean update(Player player, Stage stage) {
		if(using) {
			if(tick >= 12.2f) {
				getImage().setPosition(player.getXPos(), player.getYPos());
				player.addHealth(10);
				System.out.println("health: " + player.getHealth());
				getImage().remove();
				tick = 0;
				return true;
			} 
			if(tick <= 6.1f) {
				getImage().rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
				getImage().setPosition(player.getXPos(), player.getYPos() + 10*tick);
			} else if(tick < 12.2f) {
				getImage().rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
				getImage().setPosition(player.getXPos(), player.getYPos() + 10f*6.2f - 10*(tick-6.1f));
			}
			stage.addActor(getImage());
			tick+=0.35;
			return false;
		}
		
		if(tick == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			using = true;
		}

		return false; //use to tick down uses on card
	}
}
