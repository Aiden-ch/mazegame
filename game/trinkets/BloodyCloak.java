package com.mygdx.game.trinkets;

import com.mygdx.game.EffectHandler;
import com.mygdx.game.Enemy;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Trinket;

public class BloodyCloak extends Trinket {
	//resets dash after every kill
	public BloodyCloak() {
		super("Bloody Cloak");
	}
	
	@Override
	public void Perk(double num, String event, Enemy enemy) {
		if(event.equals("kill")) {
			MazeCombat.player.setDash(0);
			MazeCombat.player.getEffects().add(new EffectHandler(3f, "speedup"));
			MazeCombat.player.getEffects().add(new EffectHandler(2f, "refresh"));
		}
	}
}
