package com.mygdx.game.projItems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.Projectile;
import com.mygdx.game.enemies.Enemy;

public class Bomb extends Projectile {
	//blast radius
	private double radius;
	
	public Bomb(Texture txte, double speed, double damage, int pierce, double radius, double knockback) {
		super(txte, speed, damage, pierce, knockback);
		setType("b");
		this.radius = radius;
	}
	
	//use this constructor when making projectiles in RangedItem classes
	public Bomb(Texture txte, double xPos, double yPos, double velx, double vely, double speed, double damage, double pierce, double knockback, double radius) {	
		super(txte, xPos, yPos, velx, vely, speed, damage, pierce, knockback);
		setType("b");
		this.radius = radius;
	}
	
	public void hit() {
		//System.out.println("explosion");
		@SuppressWarnings("unused")
		EffectHandler explosion = new EffectHandler(getXPos(), getYPos(), radius);
		Circle blast = new Circle(getXPos(), getYPos(), (float)radius);
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			Enemy tempem = EnemyHandler.getEnemies(i);
			if(Intersector.overlaps(blast, tempem.getBox())) {
				tempem.takeDamage(getDamage());
			}
		}
	}
	public double getRadius() {
		return this.radius;
	}
}
