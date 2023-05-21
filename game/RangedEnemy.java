package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;

public class RangedEnemy extends Enemy {

	public RangedEnemy(Texture txte, Image enImg, float health, float maxSpeed, double damage) {
		super(txte, enImg, health, maxSpeed, damage);
	}

	public RangedEnemy(float xPos, float yPos, float health, float maxSpeed, double damage, Texture txte, Image enImg) {
		super(xPos, yPos, health, maxSpeed, damage, txte, enImg);
	}

	public void update(Player player, Stage stage, int index) {
		double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
		double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());

		if (distance < 100) {
			//       velX = cos(angle + Math.PI) * Math.min(maxSpeed, (125 - distance));

			setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX())));
			setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY())));
		} else if (distance < 150) {
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
		tickTime();
	}
}