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
		super(txte, card, refreshTime);
	}
	public SimplePotionStand(Card card, float refreshTime) {
		super(card, refreshTime);
	}
	
	@Override
	public void resupply() {
		if(getBuffer() >= getRefresh()) {
			//System.out.println(InventoryHandler.getStart());
			int index = CardHandler.getHand().size()-1;
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getName().contains("Health Potion")) { //fix search system
					index = i;
				}
			}
			
			if(InventoryHandler.getStart()) {
				//System.out.println("supplied");
				CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMisc(), getCard().getUses(), getCard().getImage()));
			} else if(CardHandler.getHeld() == null && CardHandler.getHand().size() == 0) {
				//might not work
				getCard().setUses(1);
				CardHandler.getHand().add(new Card(getCard().getName(), getCard().getMisc(), getCard().getUses(), getCard().getImage()));
				if(CardHandler.getHand().size() == 1) {
					CardHandler.chooseCard(CardHandler.getHand().get(0));
				}
			} else if(getCard().getUses() <= 0 && CardHandler.getHand().size() > 0) { 
				System.out.println("add");
				getCard().setUses(1);
				CardHandler.getHand().get(index).setUses(getCard().getUses());
			}
			setBuffer(0.0f);
		} else {
			//System.out.println(getBuffer());
			tick();
		}
	}
}
