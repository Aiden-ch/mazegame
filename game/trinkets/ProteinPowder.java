package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Trinket;

public class ProteinPowder extends Trinket {
	public ProteinPowder() {
		super("Protein Powder");
	}
	
	@Override
	public void Passive(Batch batch) {
		if(InventoryHandler.getStart()) {
			MazeCombat.player.addHealth(50);
		} 
	}
}