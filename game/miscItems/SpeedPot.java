package com.mygdx.game.miscItems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.*;

public class SpeedPot extends Misc {
	private float tick = 0.0f;
	private boolean canActivate = true;
	
	public SpeedPot(Texture txte, Image mImg) {
		super(mImg, txte);
		this.getImage().setOrigin(txte.getWidth()/2,txte.getHeight()/2);
		setType("speedpotion");
	}
	
	@Override
	public boolean consume(Player player, int index) {
		if(tick >= 12.2f) {
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
			this.getImage().remove();
			AttackHandler.getActive().remove(index);
		} 
		if(tick <= 6.1f) {
			getImage().rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			getImage().setPosition(player.getXPos(), player.getYPos() + 10*tick);
		} else if(tick < 12.2f) {
			getImage().rotateBy((float)Math.PI/14f * 180f/(float)Math.PI);
			getImage().setPosition(player.getXPos(), player.getYPos() + 10f*6.2f - 10*(tick-6.1f));
		}
		tick+=0.35;
		return false;
	}
}
