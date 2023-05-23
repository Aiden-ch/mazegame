package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;

public class BossName extends Enemy {
	private double meleeCoolDown = 0;
	private double maxMeleeCoolDown = 1;
	private double lungeCoolDown = 0;
	private double maxLungeCoolDown = 1;
	private boolean lunging = false;

	public BossName(Texture txte, Image enImg, float health, float maxSpeed, double damage) {
		super(txte, enImg, health, maxSpeed, damage);
	}
	
	public BossName(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte, Image enImg) {
		super(xPos, yPos, health, maxSpeed, damage, txte, enImg);
	}
	
	public void update(Player player, Stage stage, int index) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
			index--;
		} else {
      meleeCoolDown = Math.max(0, meleeCoolDown - 1/60);
      
      
      double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
      double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
      
      if (lunging) {
//        setVelX(getVelX() + Math.signum(getSpeed() * Math.cos(angle) - getVelX()) * 
//            Math.min(Math.abs(getAcceleration() * Math.cos(angle) / 4), 
//                Math.abs(getSpeed() * Math.cos(angle) - getVelX())));
//        setVelY(getVelY() + Math.signum(getSpeed() * Math.sin(angle) - getVelY()) * 
//            Math.min(Math.abs(getAcceleration() * Math.sin(angle) / 4), 
//                Math.abs(getSpeed() * Math.sin(angle) - getVelY())));
        
        lungeCoolDown = Math.max(0, lungeCoolDown - 1/60);
        if (lungeCoolDown == 0) {
          lunging = false;
          lungeCoolDown = maxLungeCoolDown;
        }
      } else if (distance < 140) {
        setVelX(getVelX() + Math.signum(getSpeed() * Math.cos(angle) - getVelX()) * 
            Math.min(Math.abs(getAcceleration() * Math.cos(angle)), 
                Math.abs(getSpeed() * Math.cos(angle) - getVelX())));
        setVelY(getVelY() + Math.signum(getSpeed() * Math.sin(angle) - getVelY()) * 
            Math.min(Math.abs(getAcceleration() * Math.sin(angle)), 
                Math.abs(getSpeed() * Math.sin(angle) - getVelY())));
      } else if (distance < 190) {
        //       velX = cos(angle + Math.PI) * Math.min(maxSpeed, (125 - distance));

        setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelX()) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelX())));
        setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelY()) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelY())));
      } else if (distance < 250) {
        if (lungeCoolDown == 0) {
          setVelX(50 * Math.sin(angle));
          setVelY(50 * Math.cos(angle));
          lungeCoolDown = 3;
        } else {
          setVelX(getVelX() - Math.signum(getVelX()) * 
              Math.min(getAcceleration(), 
                  Math.abs(getVelX())));
          setVelY(getVelY() - Math.signum(getVelY()) * 
              Math.min(getAcceleration(), 
                  Math.abs(getVelY())));
          lungeCoolDown = Math.max(0, lungeCoolDown - 1/60);
        }
      } else {
        setVelX(getVelX() - Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelX()) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelX())));
        setVelY(getVelY() - Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelY()) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelY())));
      }

			setXPos(getXPos() + (float)getVelX());
			setYPos(getYPos() + (float)getVelY());

			if(player.getBox().overlaps(getBox())) {
        if (lunging) {
          player.takeDamage(35, 20, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
          lunging = false;
        } else if (meleeCoolDown == 0) {
          player.takeDamage(10, 5, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
          meleeCoolDown = maxMeleeCoolDown;
        }
      }
			
			if(getXPos() < 0) setXPos(0);
			if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
			if(getYPos() < 0) setYPos(0);
			if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);

			getImage().setPosition(getXPos(), getYPos());

			stage.addActor(getImage());
		}
	}
	
	public void takeDamage(double damage, float knockback, float angle) {
		super.takeDamage(damage, knockback/3, angle);
	}
}