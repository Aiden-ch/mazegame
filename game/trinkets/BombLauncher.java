package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class BombLauncher extends Trinket {

	//shoot Bombs
	//low count, low fire rate
	//inventory item > adds bomb card
	public BombLauncher(Texture txte, Item item) {
		super(txte, item);
	}
	
	@Override
	public void resupply() {
		//aaaa
	}
}
