package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class BombLauncher extends Trinket {

	//shoot Bombs
	//low count, low fire rate
	//inventory item > adds bomb card
	public BombLauncher(Texture txte, Item item, float refreshTime) {
		super(txte, item, refreshTime);
	}
	public BombLauncher(Item item, float refreshTime) {
		super(item, refreshTime);
	}

	
	@Override
	public void resupply() {
		//System.out.println("restocking");
		//aaaa
		if(getBuffer() >= getRefresh()) {
			int index = AttackHandler.getHand().size();
			for(int i=0; i<AttackHandler.getHand().size(); i++) {
				if(AttackHandler.getHand().get(i).getName().equals(getItem().getName())) {
					index = i;
				}
			}
			
			if(InventoryHandler.getStart()) {
				System.out.println("supplied");
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getProj(), getItem().getUses(), getItem().getImage()));
			} else if(AttackHandler.getInHand() == null && AttackHandler.getHand().size() == 0) {
				//might not work
				System.out.println("resupplied");
				getItem().setUses(2);
				System.out.println(getItem().getUses());
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getProj(), getItem().getUses(), getItem().getImage()));
				if(AttackHandler.getHand().size() == 1) {
					AttackHandler.setInHand(AttackHandler.getHand().get(0));
				}
			} else if(getItem().getUses() < 2 && AttackHandler.getHand().size() > 0) { 
				//slow restock
				System.out.println("add");
				getItem().setUses(getItem().getUses()+1);
				AttackHandler.getHand().get(index).setUses(getItem().getUses());
			}
			setBuffer(0.0f);
		} else {
			//System.out.println(getBuffer());
			tick();
		}
	}
}
