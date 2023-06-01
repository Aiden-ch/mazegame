package com.mygdx.game.trinkets;

import com.mygdx.game.InventoryHandler;
import com.mygdx.game.Player;
import com.mygdx.game.Trinket;

public class ProteinPowder extends Trinket {
	public ProteinPowder() {
		super("Protein Powder");
	}
	
	@Override
	public void Passive(Player player) {
		if(InventoryHandler.getStart()) {
			player.addHealth(50);
		} 
	}
}