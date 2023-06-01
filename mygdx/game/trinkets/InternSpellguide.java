package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class InternSpellguide extends Trinket {
	//shoot random basic projectiles
	// > small bolt, quick zap, fireball
	//starter item for intern > adds Spell Guide card
	public InternSpellguide(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "SpeLl GuIDe");
	}
	public InternSpellguide(Card card, float refreshTime) {
		super(card, refreshTime, "SpeLl GuIDe");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getRanged(), getCard().getImage()));
		} 
	}
}
