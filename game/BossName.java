package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class BossName extends Enemy {
	private double meleeCoolDown = 0;
	private double meleeMaxCoolDown = 1;
	private double lungeCoolDown = 0;
  private boolean lunging = false;
	
	public BossName(Texture txte, Image enImg, float health, float maxSpeed, double damage) {
		this.txte = txte;
		this.enImg = enImg;
		this.health = health;
		this.maxSpeed = maxSpeed;
		this.damage = damage;
	}
	
	public BossName(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte, Image enImg) {
		this.box = new Rectangle();
		this.box.x = xPos;
		this.box.y = yPos;
		this.maxSpeed = maxSpeed;
		this.health = health;
		this.damage = damage;
		
		this.txte = txte;
		this.enImg = enImg;
		this.enImg.setPosition(xPos, yPos);
		
		this.box.width = enImg.getWidth()/2;
		this.box.height = enImg.getHeight()/2;
	}
	
	public void update(Player player, Stage stage, int index) {
		if(health <= 0) {
			enImg.remove();
			EnemyHandler.getEnemies().remove(index);
			index--;
		} else {
      meleeCoolDown = Math.max(0, meleeCoolDown - 1/60);
      
      
      double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
      double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
      
      if (lunging) {
        velX += Math.signum(maxSpeed * Math.cos(angle) - velX) * 
            Math.min(Math.abs(acceleration * Math.cos(angle) / 4), 
                Math.abs(maxSpeed * Math.cos(angle) - velX));
        velY += Math.signum(maxSpeed * Math.sin(angle) - velY) * 
            Math.min(Math.abs(acceleration * Math.sin(angle) / 4), 
                Math.abs(maxSpeed * Math.sin(angle) - velY));
        
        lungeCoolDown = Math.max(0, lungeCoolDown - 1/60);
        if (lungeCoolDown == 0) {
          lunging = false;
          lungeCoolDown = maxLungeCoolDown;
        }
      } else if (distance < 40) {
        velX += Math.signum(maxSpeed * Math.cos(angle) - velX) * 
            Math.min(Math.abs(acceleration * Math.cos(angle)), 
                Math.abs(maxSpeed * Math.cos(angle) - velX));
        velY += Math.signum(maxSpeed * Math.sin(angle) - velY) * 
            Math.min(Math.abs(acceleration * Math.sin(angle)), 
                Math.abs(maxSpeed * Math.sin(angle) - velY));
      } else if (distance < 90) {
        //       velX = cos(angle + Math.PI) * Math.min(maxSpeed, (125 - distance));

        velX += Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - velX) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - velX)));
        velY += Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - velY) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - velY)));
      } else if (distance < 150) {
        if (lungeCoolDown == 0) {
          velX = 20 * Math.sin(angle);
          velY = 20 * Math.cos(angle);
          lungeCoolDown = 3;
        } else {
          velX -= Math.signum(velX) * 
              Math.min(getAcceleration(), 
                  Math.abs(velX)));
          velY -= Math.signum(velY) * 
              Math.min(getAcceleration(), 
                  Math.abs(velY)));
          lungeCoolDown = Math.max(0, lungeCoolDown - 1/60);
        }
      } else {
        velX -= Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - velX) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - velX)));
        velY -= Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - velY) * 
            Math.min(getAcceleration(), 
                Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - velY)));
      }

			this.box.x += velX;
			this.box.y += velY;

			if(player.getBox().overlaps(box)) {
        if (lunging) {
          player.takeDamage(35, 20, 90f + 180f/(float)Math.PI * (float)(Math.atan2(box.y-player.getYPos(), box.x-player.getImage().getWidth()/2-player.getXPos())));
          lunging = false;
        } else if (meleeCoolDown == 0) {
          player.takeDamage(10, 5, 90f + 180f/(float)Math.PI * (float)(Math.atan2(box.y-player.getYPos(), box.x-player.getImage().getWidth()/2-player.getXPos())));
          meleeCoolDown = maxMeleeCoolDown;
        }
      }
			
			if(box.x < 0) box.x = 0;
			if(box.x > Gdx.graphics.getWidth() - 64) box.x = (Gdx.graphics.getWidth() - 64);
			if(box.y < 0) box.y = 0;
			if(box.y > Gdx.graphics.getHeight() - 64) box.y = (Gdx.graphics.getHeight() - 64);

			enImg.setPosition(box.x, box.y);

			stage.addActor(enImg);
		}
		
		timer += 0.5f;
	}
	
	public ArrayList<EffectHandler> getEffects() {
		return super.effects;
	}
	
	public float getXPos() {
		return super.box.x;
	}
	public float getYPos() {
		return super.box.y;
	}
	public double getSpeed() {
		return super.maxSpeed;
	}
	public double getHealth() {
		return super.health;
	}
	public Texture getTexture() {
		return super.txte;
	}
	public Image getImage() {
		return super.enImg;
	}
	public Rectangle getBox() {
		return super.box;
	}
	public double getDamage() {
		return this.damage;
	}
	public double getTime() {
		return this.timer;
	}
	public void tickTime() {
		this.timer += 0.5f;
	}
	public double getVelX() {
		return this.velX;
	}
	public double getVelY() {
		return this.velY;
	}
	public double getAcceleration() {
		return this.acceleration;
	}
	public void setVelX(double amount) {
		this.velX = amount;
	}
	public void setVelY(double amount) {
		this.velY = amount;
	}
	public void setXPos(float amount) {
		this.box.x = amount;
	}
	public void setYPos(float amount) {
		this.box.y = amount;
	}
	
	public void takeDamage(double damage, float knockback, float angle) {
    this.health -= damage;
    this.velX += knockback * Math.cos((angle+90f) * Math.PI/180f) / 3;
    this.velY += knockback * Math.sin((angle+90f) * Math.PI/180f) / 3;
	}
	
}
