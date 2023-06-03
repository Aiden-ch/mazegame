package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Trinket;

public class SpikeShield extends Trinket {
	//faster refreshing shield up
	//stuns enemies that hit you
	public SpikeShield() {
		super("Spike Shield");
	}
	
	@Override
	public void Passive(Batch batch) {
		if(MazeCombat.player.getBlock() > 1.5) {
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				if(EnemyHandler.getEnemies(i).getBox().overlaps(MazeCombat.player.getBox())) {
					EnemyHandler.getEnemies(i).takeDamage(8, 3, 
							-90f + 180f/(float)Math.PI * (float)(Math.atan2(EnemyHandler.getEnemies(i).getYPos()-MazeCombat.player.getYPos(), EnemyHandler.getEnemies(i).getXPos()-MazeCombat.player.getImage().getWidth()/2-MazeCombat.player.getXPos())));
				}
			}
		}
	}
}