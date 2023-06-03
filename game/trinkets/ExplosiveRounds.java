package com.mygdx.game.trinkets;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.Trinket;

import enemies.Enemy;

public class ExplosiveRounds extends Trinket {
	public ExplosiveRounds() {
		super("Explosive Rounds");
	}

	@Override
	public void Perk(double num, String event, Enemy enemy) {
		if(event.equals("hit")) {
			@SuppressWarnings("unused")
			EffectHandler explosion = new EffectHandler(enemy.getXPos(), enemy.getYPos(), 30);
			Circle blast = new Circle(enemy.getXPos(), enemy.getYPos(), (float)30);
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				Enemy tempem = EnemyHandler.getEnemies(i);
				if(Intersector.overlaps(blast, tempem.getBox())) {
					tempem.takeDamage(10);
				}
			}
		}
	}
}
