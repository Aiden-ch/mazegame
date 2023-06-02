package com.mygdx.game.trinkets;

import com.mygdx.game.Player;
import com.mygdx.game.Trinket;

public class MagicArmor extends Trinket {
	public MagicArmor() {
		super("Magic Armor");
	}
	
	@Override
	public void Perk(double num, String event, Player player) {
		if(event.equals("gothit")) {
			if(num > 10) {
				player.addHealth((int)num/2);
			}
		}
	}
}
