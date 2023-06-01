package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Enemy;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.RangedItem;
import com.mygdx.game.Trinket;
import com.mygdx.game.projItems.Bomb;

public class RocketReciprocator extends Trinket {
	//launches rocket when player hits an enemy
	RangedItem bombCard = new RangedItem(new Bomb(new Texture("projectiles/bomb.png"), 9d, 6d, 0, 45), 0.1d, 1, 0, 1d, 2, 0.1);
	
	public RocketReciprocator() {
		super("Rocket Reciprocator");
	}
	
	@Override
	public void Perk(double num, String event, Enemy enemy) {
		if(event.equals("hit")) {
			bombCard.update(MazeCombat.player, MazeCombat.stage);
		}
	}
	@Override 
	public void update() {
		bombCard.update(MazeCombat.player);
		bombCard.rendoor(MazeCombat.player, MazeCombat.stage, MazeCombat.batch);
	}
}
