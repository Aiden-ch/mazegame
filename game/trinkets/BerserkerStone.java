package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Melee;
import com.mygdx.game.Player;
import com.mygdx.game.Trinket;

public class BerserkerStone extends Trinket {
	//take 2x more damage but deal more melee damage
	public BerserkerStone() {
		super("Berserker Stone");
	}
	
	@Override
	public void Perk(double num, String event, Player player) {
		if(event.equals("gothit")) {
			//System.out.println("pouch");
			player.takeDamage(num, 0, 0);
		}
	}
	@Override
	public void Passive(Batch batch) {
		if(InventoryHandler.getStart()) {
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getType() == 's') {
					Melee temp = CardHandler.getHand().get(i).getMel();
					temp.setDamage(temp.getDamage() + 30);
				}
			}
		}
	}
}