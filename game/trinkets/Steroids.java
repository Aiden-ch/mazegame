package com.mygdx.game.trinkets;

import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class Steroids extends Trinket {
	//increase melee damage and knockback
	public Steroids() {
		super("Steroids");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			for(int i=0; i<CardHandler.getALL().size(); i++) {
				if(CardHandler.getALL().get(i).getType() == 's') {
					CardHandler.getALL().get(i).getMel().setDamage(CardHandler.getALL().get(i).getMel().getDamage() + 5);
					CardHandler.getALL().get(i).getMel().setKnockback(CardHandler.getALL().get(i).getMel().getKnockback() + 15);
				}
			}
		} 
	}
}
