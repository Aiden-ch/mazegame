package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Card;
import com.mygdx.game.CardHandler;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.Enemy;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Trinket;

public class FrostGun extends Trinket {

	//shoot Bombs
	//low count, low fire rate
	//inventory item > adds bomb card
	public FrostGun(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "Frost Gun");
	}
	public FrostGun(Card card, float refreshTime) {
		super(card, refreshTime, "Frost Gun");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getRanged(), getCard().getImage()));
		} 
	}
	@Override
	public void Perk(double num, String event, Enemy enemy) {
		if(event.equals("hit") && CardHandler.getHeld().getName().equals("Frost Gun")) {
			boolean stacked = false;
			if(enemy.getEffects().size() > 0) {
				for(int i=0; i<enemy.getEffects().size(); i++) {
					if(enemy.getEffects().get(i).getEffect().equals("freeze1")) {
						stacked = true;
						break;
					}
				}
			}
			if(!stacked) {
				enemy.getEffects().add(new EffectHandler(3, "freeze1"));
			}
		}
	}
}