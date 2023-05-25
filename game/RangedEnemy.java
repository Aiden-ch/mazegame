package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.lang.Math;

public class RangedEnemy extends Enemy {
	
	public RangedEnemy(Texture txte, float health, float maxSpeed, double damage, Projectile proj) {
		super(txte, health, maxSpeed, damage);
		setProj(proj);
		setType('r');
	}

	public RangedEnemy(float xPos, float yPos, double health, double maxSpeed, double damage, Projectile proj, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		setProj(proj);
	}

	public void update(Player player, Stage stage, int index) {
		double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
		double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());

		if (distance < 140) {
			setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX())));
			setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY())));
		} else if (distance < 170) {
			//       velX = 0;
			setVelX(getVelX() - Math.signum(getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(getVelX())));
			setVelY(getVelY() - Math.signum(getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(getVelY())));
		} else {
			setVelX(getVelX() - Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelX())));
			setVelY(getVelY() - Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelY())));
		}

		setXPos((float)getXPos() + (float)getVelX());
		setYPos((float)getYPos() + (float)getVelY());
		
		if(getXPos() < 0) setXPos(0);
		if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
		if(getYPos() < 0) setYPos(0);
		if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);
		
		getImage().setPosition(getXPos(), getYPos());
		
		stage.addActor(getImage());
		tickTime();
	}
}