package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Item;
import com.mygdx.game.Trinket;

public class RulerBlade extends Trinket {
	public RulerBlade(Texture txte, Item item, float refreshTime) {
		super(txte, item, refreshTime);
	}
	public RulerBlade(Item item, float refreshTime) {
		super(item, refreshTime);
	}
	
	@Override
	public void resupply() {
		//might change later
	}
}
