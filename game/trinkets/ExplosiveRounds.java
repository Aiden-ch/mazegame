package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Projectile;
import com.mygdx.game.RangedItem;
import com.mygdx.game.Trinket;
import com.mygdx.game.projItems.Bomb;

public class ExplosiveRounds extends Trinket {
	public ExplosiveRounds() {
		super("Explosive Rounds");
	}
	
	@Override
	public void Passive(Batch batch) {
		if(InventoryHandler.getStart()) {
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getType() == 'p') {
					RangedItem temp = CardHandler.getHand().get(i).getRanged();
					Projectile bomb = new Bomb(temp.getProj().getTexture(), temp.getProj().getSpeed(), temp.getProj().getDamage(), (int)temp.getProj().getPierce(), 50d, temp.getProj().getKnockback());
					temp.setProj(bomb);
				}
			}
		}
	}
}
