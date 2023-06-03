package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.InventoryHandler;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Trinket;

public class AncientShield extends Trinket {
	//faster refreshing shield up
	//stuns enemies that hit you
	public AncientShield() {
		super("Ancient Shield");
	}
	
	@Override
	public void Passive(Batch batch) {
		if(MazeCombat.player.getBlock() > 1.5) {
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				if(EnemyHandler.getEnemies(i).getBox().overlaps(MazeCombat.player.getBox())) {
					EnemyHandler.getEnemies(i).getEffects().add(new EffectHandler(2,"stunned"));
				}
			}
		}
		if(MazeCombat.player.getBlock() < 1 && MazeCombat.player.getBlock() != 0) {
			MazeCombat.player.setBlock(0);
			MazeCombat.player.getEffects().add(new EffectHandler(2f, "refresh"));
		}
	}
	@Override
	public void resupply() {
		if(InventoryHandler.getStart()) {
			MazeCombat.player.block = new Texture("effects/ancientshield.png");
		}
	}
}