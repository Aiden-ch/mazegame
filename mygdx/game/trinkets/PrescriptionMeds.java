package com.mygdx.game.trinkets;

import com.mygdx.game.Player;
import com.mygdx.game.Trinket;

public class PrescriptionMeds extends Trinket {
	public PrescriptionMeds() {
		super("Prescription Meds");
	}
	
	@Override
	public void Passive(Player player) {
		tick();
		if(getBuffer() == 0 && player.getHealth() < 99 && player.getHealth() > 0) {
			player.addHealth(2);
			setBuffer(50);
		}
	}
}
