package com.mygdx.game.miscItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Misc;
import com.mygdx.game.Player;

public class HealthPot extends Misc {
	private float tick = 0.0f;
	private boolean using = false;
	
	private Image temp;
	
	public HealthPot(Texture txte) {
		super(txte);
		setType("healthpotion");
	}
	
	@Override 
	public boolean consume(Player player) {
		if(tick >= 3.0f) {
			temp.setPosition(player.getXPos(), player.getYPos());
			player.addHealth(10);
			System.out.println("health: " + player.getHealth());
			tick = 0;
			return true;
		} 
		if(tick <= 1.5f) {
			temp.rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			temp.setPosition(player.getXPos(), player.getYPos() + 10*tick*60f*0.1f);
		} else if(tick < 3.0f) {
			temp.rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			temp.setPosition(player.getXPos(), player.getYPos() + 10f*1.5f*60*0.1f - 10*(tick-1.5f)*60*0.1f);
		}

		return false;
	}
	
	@Override
	public boolean update(Player player, Stage stage) {
		if(using) {
			boolean ret = consume(player);
			if(!ret) {
				stage.addActor(temp);
				tick+=3*1f/60f;
			} else {
				temp.remove();
				temp = null;
				using = false;
			}
			return ret;
		}
		
		if(tick == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			temp = new Image(getTexture());
			temp.setOrigin(getTexture().getWidth()/2,getTexture().getHeight()/2);
			using = true;
		}

		return false; //use to tick down uses on card
	}
}
