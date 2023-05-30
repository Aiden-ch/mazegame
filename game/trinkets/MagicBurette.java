package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class MagicBurette extends Trinket {
	public MagicBurette(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "Magic Burette");
	}
	public MagicBurette(Card card, float refreshTime) {
		super(card, refreshTime, "Magic Burette");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			//System.out.println("supplied");
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMisc(), getCard().getImage()));
		}
	}
}
