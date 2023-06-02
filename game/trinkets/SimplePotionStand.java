package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class SimplePotionStand extends Trinket {
	//health potion
	//add potion card to hand every few seconds
	public SimplePotionStand(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "Simple Potion Stand");
	}
	public SimplePotionStand(Card card, float refreshTime) {
		super(card, refreshTime, "Simple Potion Stand");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			//System.out.println("supplied");
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMisc(), getCard().getImage()));
		} 
	}
}
