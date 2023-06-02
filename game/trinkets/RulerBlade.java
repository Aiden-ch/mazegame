package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.InventoryHandler;
//import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.Trinket;

public class RulerBlade extends Trinket {
	public RulerBlade(Texture txte, Card item, float refreshTime) {
		super(txte, item, refreshTime, "Ruler");
	}
	public RulerBlade(Card item, float refreshTime) {
		super(item, refreshTime, "Ruler");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMel(), getCard().getImage()));
		}
	}
}
