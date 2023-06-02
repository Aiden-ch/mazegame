package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.RangedItem;
import com.mygdx.game.Trinket;

public class AmmoCondenser extends Trinket {
	//weapons shoot slower and shoot only 1 projectile at a time but the projectile deals massive damage, has perfect accuracy, and infinite pierce
	public AmmoCondenser() {
		super("Ammo Condenser");
	}
	
	@Override
	public void Passive(Batch batch) {
		if(InventoryHandler.getStart()) {
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getType() == 'p') {
					RangedItem temp = CardHandler.getHand().get(i).getRanged();
					temp.getProj().setDamage(temp.getProj().getDamage()*temp.getMaxShots() + 5);
					temp.setMag(1);
					temp.getProj().setPierce(100);
					temp.setAccuracy(0); //0 means perfect
					temp.setMaxReload(30);
					temp.setShootSpeed(temp.getShootSpeed() * 3.5);
					temp.setMaxShots(1);
					if(temp.getProj().getType().equals("s")) {
						temp.getProj().setRange(1000);
					}
				}
			}
		}
	}
}