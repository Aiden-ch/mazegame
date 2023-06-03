package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Trinket;

public class PrescriptionMeds extends Trinket {
	public PrescriptionMeds() {
		super("Prescription Meds");
	}
	
	@Override
	public void Passive(Batch batch) {
		tick();
		if(getBuffer() == 0 && MazeCombat.player.getHealth() < 99 && MazeCombat.player.getHealth() > 0) {
			MazeCombat.player.addHealth(2);
			setBuffer(50);
		}
	}
}
