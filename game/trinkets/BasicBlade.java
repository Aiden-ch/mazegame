package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.AttackHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Item;
import com.mygdx.game.Trinket;

public class BasicBlade extends Trinket {
	public BasicBlade(Texture txte, Item item, float refreshTime) {
		super(txte, item, refreshTime);
	}
	public BasicBlade(Item item, float refreshTime) {
		super(item, refreshTime);
	}
	
	@Override
	public void resupply() {
		if(getBuffer() >= getRefresh()) {
			//System.out.println(InventoryHandler.getStart());
			int index = AttackHandler.getHand().size()-1;
			for(int i=0; i<AttackHandler.getHand().size(); i++) {
				if(AttackHandler.getHand().get(i).getName().contains("Sword")) {
					index = i;
				}
			}
			
			if(InventoryHandler.getStart()) {
				//System.out.println("supplied");
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getMel(), getItem().getUses(), getItem().getImage()));
			} else if(AttackHandler.getInHand() == null && AttackHandler.getHand().size() == 0) {
				//might not work
				//System.out.println("resupplied");
				getItem().setUses(1);
				//System.out.println(getItem().getUses());
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getMel(), getItem().getUses(), getItem().getImage()));
				if(AttackHandler.getHand().size() == 1) {
					AttackHandler.setInHand(AttackHandler.getHand().get(0));
				}
			} else if(getItem().getUses() <= 0 && AttackHandler.getHand().size() > 0) { 
				System.out.println("add");
				getItem().setUses(1);
				AttackHandler.getHand().get(index).setUses(getItem().getUses());
			}
			setBuffer(0.0f);
		} else {
			//System.out.println(getBuffer());
			tick();
		}
	}
}
