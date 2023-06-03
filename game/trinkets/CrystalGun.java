package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class CrystalGun extends Trinket {
	public CrystalGun(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "Crystal Gun");
	}
	public CrystalGun(Card card, float refreshTime) {
		super(card, refreshTime, "Crystal Gun");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getRanged(), getCard().getImage()));
		} 
		if(CardHandler.getHeld() != null && !CardHandler.getHeld().getName().equals("Crystal")) {
			getCard().getRanged().reload();
		}
	}
}
