package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class BombLauncher extends Trinket {

	//shoot Bombs
	//low count, low fire rate
	//inventory item > adds bomb card
	public BombLauncher(Texture txte, Card card, float refreshTime) {
		super(txte, card, refreshTime, "Bomb Launcher");
	}
	public BombLauncher(Card card, float refreshTime) {
		super(card, refreshTime, "Bomb Launcher");
	}
	
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getRanged(), getCard().getImage()));
		} 
	}
}
