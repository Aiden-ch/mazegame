package com.mygdx.game.projItems;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Projectile;

public class Fire extends Projectile {
	//blast radius
	private double range;
	
	public Fire(Texture txte, double speed, double damage, int pierce, double range, double knockback) {
		super(txte, speed, damage, pierce, knockback);
		setType("s");
		this.range = range;
	}
	
	//use this constructor when making projectiles in RangedItem classes
	public Fire(Texture txte, double xPos, double yPos, double velx, double vely, double speed, double damage, double pierce, double knockback, double range) {	
		super(txte, xPos, yPos, velx, vely, speed, damage, pierce, knockback);
		setType("s");
		this.range = range;
	}

	public double getRange() {
		return this.range;
	}
	public void setRange(double num) {
		this.range = num;
	}
	@Override
	public void move() {
		setXPos(getXPos() + getVelocity().get(0) * getSpeed());
		setYPos(getYPos() + getVelocity().get(1) * getSpeed());
		getImage().setPosition(getXPos(), getYPos());
		
		range -= Math.sqrt(getVelocity().get(0) * getVelocity().get(0) * getSpeed() * getSpeed() + getVelocity().get(1) * getVelocity().get(1) * getSpeed() * getSpeed());
	}
}
