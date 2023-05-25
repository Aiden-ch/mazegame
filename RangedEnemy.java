package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class RangedEnemy extends Enemy {
	
	public RangedEnemy(Texture txte, Image enImg, float health, float maxSpeed, double damage) {
    super(txte, enImg, health, maxSpeed, damage);
// 		this.txte = txte;
// 		this.enImg = enImg;
// 		this.health = health;
// 		this.maxSpeed = maxSpeed;
// 		this.damage = damage;
	}
	
	public RangedEnemy(float xPos, float yPos, float health, float maxSpeed, double damage, Texture txte, Image enImg) {
    super(xPos, yPos, health, maxSpeed, damage, txte, enImg);
// 		this.box = new Rectangle();
// 		this.box.x = xPos;
// 		this.box.y = yPos;
// 		this.maxSpeed = maxSpeed;
// 		this.health = health;
// 		this.damage = damage;
		
// 		this.txte = txte;
// 		this.enImg = enImg;
// 		this.enImg.setPosition(xPos, yPos);
		
// 		this.box.width = enImg.getWidth()/2;
// 		this.box.height = enImg.getHeight()/2;
	}
	
	public void move(Player player) {
    double distance = Math.sqrt(Math.pow(box.x - player.getXPos(), 2) + Math.pow(box.y - player.getYPos(), 2));
    double angle = Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x);
    
    if (distance < 100) {
//       velX = cos(angle + Math.PI) * Math.min(maxSpeed, (125 - distance));
      
      velX += Math.signum(Math.cos(angle + Math.PI) * Math.min(maxSpeed, 0.6 * (125 - distance)) - velX) * 
        Math.min(acceleration, 
                 Math.abs(Math.cos(angle + Math.PI) * Math.min(maxSpeed, 0.6 * (125 - distance)) - velX));
      velY += Math.signum(Math.sin(angle + Math.PI) * Math.min(maxSpeed, 0.6 * (125 - distance)) - velY) * 
        Math.min(acceleration, 
                 Math.abs(Math.sin(angle + Math.PI) * Math.min(maxSpeed, 0.6 * (125 - distance)) - velY));
    } else if (distance < 150) {
//       velX = 0;
      velX -= Math.signum(velX) * 
        Math.min(acceleration, 
                 Math.abs(velX);
      velY -= Math.signum(velY) * 
        Math.min(acceleration, 
                 Math.abs(velY);
    } else {
//       velX = cos(angle) * Math.min(maxSpeed, (125 - distance));
      velX -= Math.signum(Math.cos(angle) * Math.min(maxSpeed, 0.6 * (distance - 125)) - velX) * 
        Math.min(acceleration, 
                 Math.abs(Math.cos(angle) * Math.min(maxSpeed, 0.6 * (distance - 125)) - velX));
      velY -= Math.signum(Math.sin(angle) * Math.min(maxSpeed, 0.6 * (distance - 125)) - velY) * 
        Math.min(acceleration, 
                 Math.abs(Math.sin(angle) * Math.min(maxSpeed, 0.6 * (distance - 125)) - velY));
    }
		
		super.box.x += velX;
		super.box.y += velY;
		timer += 0.5f;
	}
	
// 	public ArrayList<EffectHandler> getEffects() {
// 		return this.effects;
// 	}
	
// 	public float getXPos() {
// 		return this.box.x;
// 	}
// 	public float getYPos() {
// 		return this.box.y;
// 	}
// 	public float getSpeed() {
// 		return this.maxSpeed;
// 	}
// 	public float getHealth() {
// 		return this.health;
// 	}
// 	public Texture getTexture() {
// 		return this.txte;
// 	}
// 	public Image getImage() {
// 		return this.enImg;
// 	}
// 	public Rectangle getBox() {
// 		return this.box;
// 	}
// 	public double getDamage() {
// 		return this.damage;
// 	}
// 	public float getVelX() {
// 		return this.velX;
// 	}
// 	public float getVelY() {
// 		return this.velY;
// 	}
// 	public float getAcceleration() {
// 		return this.acceleration;
// 	}
// 	public void setVelX(float amount) {
// 		this.velX = amount;
// 	}
// 	public void setVelY(float amount) {
// 		this.velY = amount;
// 	}
// 	public void setXPos(float amount) {
// 		this.box.x = amount;
// 	}
// 	public void setYPos(float amount) {
// 		this.box.y = amount;
// 	}
	
  // the same as superclass, may not need
	public void takeDamage(double damage, float knockback, float angle) {
		
		if(!tookDamage) {
			this.health -= damage;

			//System.out.println(knockback);
			this.velX += knockback * Math.cos((angle+90f) * Math.PI/180f);
			this.velY += knockback * Math.sin((angle+90f) * Math.PI/180f);
			//			this.velX += 50;
			//			this.velY += 50;
			tookDamage = true;
			timer = 0.0f;
		} else {
			if(timer >= 3.0f) {
				tookDamage = false;
			}
		}
	}
	
	public void knockback(float angle) {
		this.velX += enKnockback * Math.cos((angle+90f) * Math.PI/180f);
		this.velY += enKnockback * Math.sin((angle+90f) * Math.PI/180f);
	}
	
}
