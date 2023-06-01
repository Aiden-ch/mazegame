package com.mygdx.game.trinkets;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.CardHandler;
import com.mygdx.game.MazeCombat;
import com.mygdx.game.Player;
import com.mygdx.game.Projectile;
import com.mygdx.game.RangedItem;
import com.mygdx.game.Trinket;

public class SwordCast extends Trinket {
	//launches rocket when player hits an enemy
	RangedItem swordlaunch = new RangedItem(new Projectile(new Texture("projectiles/swordbolt.png"), 15d, 5d, 20), 0.1d, 1, 0, 0.1d, 20, 0.1);
	RangedItem swordBlast = new RangedItem(new Projectile(new Texture("projectiles/swordbolt.png"), 15d, 5d, 20), 0.1d, 3, 10, 0.5d, 20, 0.1);
	
	public SwordCast() {
		super("Sword Cast");
	}

	@Override
	public void Perk(double num, String event, Player player) {
		if(event.equals("swing")) {
			if(CardHandler.getHeld().getName().equals("Sacrilege")) {
				swordBlast.update(MazeCombat.player, MazeCombat.stage);
			} else {
				swordlaunch.update(MazeCombat.player, MazeCombat.stage);
			}
		}
	}
	@Override 
	public void update() {
		swordBlast.update(MazeCombat.player);
		swordlaunch.update(MazeCombat.player);
		swordBlast.rendoor(MazeCombat.player, MazeCombat.stage, MazeCombat.batch);
		swordlaunch.rendoor(MazeCombat.player, MazeCombat.stage, MazeCombat.batch);
	}
}
