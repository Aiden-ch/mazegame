package com.mygdx.game.miscItems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.AttackHandler;
import com.mygdx.game.Misc;
import com.mygdx.game.Player;

public class HealthPot extends Misc {
	private float tick = 0.0f;
	
	public HealthPot(Texture txte, Image mImg) {
		super(mImg, txte);
		this.getImage().setOrigin(txte.getWidth()/2,txte.getHeight()/2);
		setType("healthpotion");
	}
	
	@Override
	public boolean consume(Player player, int index) {
		if(tick >= 12.2f) {
			this.getImage().setPosition(player.getXPos(), player.getYPos());
			player.addHealth(10);
			System.out.println("health: " + player.getHealth());
			this.getImage().remove();
			AttackHandler.getActive().remove(index);
			return true;
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
