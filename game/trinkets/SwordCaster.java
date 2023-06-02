package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class SwordCaster extends Trinket {
	public SwordCaster(Texture txte, Card item, float refreshTime) {
		super(txte, item, refreshTime, "Sacrilege");
	}
	public SwordCaster(Card item, float refreshTime) {
		super(item, refreshTime, "Sacrilege");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMel(), getCard().getImage()));
		}
	}
}