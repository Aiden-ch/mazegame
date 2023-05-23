package com.mygdx.game.miscItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.*;

public class SpeedPot extends Misc {
	private float tick = 0.0f;
	private boolean canActivate = true;
	private boolean using = false;
	
	private Image temp;
	
	public SpeedPot(Texture txte) {
		super(txte);
		
		setType("speedpotion");
	}
	
	@Override
	public boolean consume(Player player) {
		if(tick >= 12.25f) {
			tick = 0;
			for(int i=0; i<player.getEffects().size(); i++) {
				if(player.getEffects().get(i).getEffect().equals("speed+")) {
					canActivate = false;
				}
			}
			if(canActivate) {
				player.getEffects().add(new EffectHandler(10f, "speed+"));
				System.out.println("speed up!");
				return true;
			}
		} 
		if(tick <= 6.1f) {
			temp.rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			temp.setPosition(player.getXPos(), player.getYPos() + 10*tick);
		} else if(tick < 12.2f) {
			temp.rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			temp.setPosition(player.getXPos(), player.getYPos() + 10f*6.2f - 10*(tick-6.1f));
		}
		
		return false;
	}
	
	@Override
	public boolean update(Player player, Stage stage) {
		if(using) {
			boolean ret = consume(player);
			if(!ret) {
				stage.addActor(temp);
				tick+=0.35;
			} else {
				temp.remove();
				temp = null;
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
