package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class BombLauncher extends Trinket {

	//shoot Bombs
	//low count, low fire rate
	//inventory item > adds bomb card
	public BombLauncher(Texture txte, Card item, float refreshTime) {
		super(txte, item, refreshTime);
	}
	public BombLauncher(Card item, float refreshTime) {
		super(item, refreshTime);
	}

	
	@Override
	public void resupply() {
		//System.out.println("restocking");
		//aaaa
		if(getBuffer() >= getRefresh()) {
			int index = CardHandler.getHand().size()-1;
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getName().contains("Bomb")) {
					index = i;
				}
			}
			
			if(InventoryHandler.getStart()) {
				//System.out.println("supplied");
				CardHandler.getHand().add(new Card(getItem().getName(), getItem().getRanged(), getItem().getUses(), getItem().getImage()));
			} else if(CardHandler.getHand() == null && CardHandler.getHand().size() == 0) {
				//might not work
				//System.out.println("resupplied");
				getItem().setUses(2);
				//System.out.println(getItem().getUses());
				CardHandler.getHand().add(new Card(getItem().getName(), getItem().getRanged(), getItem().getUses(), getItem().getImage()));
				if(CardHandler.getHand().size() == 1) {
					CardHandler.chooseCard(CardHandler.getHand().get(0));
				}
			} else if(getItem().getUses() < 2 && CardHandler.getHand().size() > 0) { 
				//slow restock
				System.out.println("add");
				getItem().setUses(getItem().getUses()+1);
				CardHandler.getHand().get(index).setUses(getItem().getUses());
			}
			setBuffer(0.0f);
		} else {
			//System.out.println(getBuffer());
			tick();
		}
	}
}
