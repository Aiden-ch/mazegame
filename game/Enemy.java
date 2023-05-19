package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class Enemy {
	private Rectangle box;
	private float health;
	private double damage;
	private ArrayList<EffectHandler> effects = new ArrayList<EffectHandler>(); 
	private float velX = 0;
	private float velY = 0;
	private float acceleration = 0.5f;
	
	private Texture txte;
	private Image enImg;
	
	private float maxSpeed;
	
	private boolean tookDamage = false;
	private float timer = 0.0f;
	
	private float enKnockback = 10f;
	
	public Enemy(Texture txte, Image enImg, float health, float maxSpeed, double damage) {
		this.txte = txte;
		this.enImg = enImg;
		this.health = health;
		this.maxSpeed = maxSpeed;
		this.damage = damage;
	}
	
	public Enemy(float xPos, float yPos, float health, float maxSpeed, double damage, Texture txte, Image enImg) {
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
	
	public void move(Player player) {
// 		float velocityX = maxSpeed * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x));
// 		float velocityY = maxSpeed * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x));
		velX += Math.signum(maxSpeed * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velX) * 
			Math.min(Math.abs(acceleration * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x))), 
					Math.abs(maxSpeed * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velX));
		velY += Math.signum(maxSpeed * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velY) * 
			Math.min(Math.abs(acceleration * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x))), 
				 Math.abs(maxSpeed * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velY));
		
		this.box.x += velX;
		this.box.y += velY;
		timer += 0.5f;
	}
	
	public ArrayList<EffectHandler> getEffects() {
		return this.effects;
	}
	
	public float getXPos() {
		return this.box.x;
	}
	public float getYPos() {
		return this.box.y;
	}
	public float getSpeed() {
		return this.maxSpeed;
	}
	public float getHealth() {
		return this.health;
	}
	public Texture getTexture() {
		return this.txte;
	}
	public Image getImage() {
		return this.enImg;
	}
	public Rectangle getBox() {
		return this.box;
	}
	public double getDamage() {
		return this.damage;
	}
	public float getVelX() {
		return this.velX;
	}
	public float getVelY() {
		return this.velY;
	}
	public float getAcceleration() {
		return this.acceleration;
	}
	public void setVelX(float amount) {
		this.velX = amount;
	}
	public void setVelY(float amount) {
		this.velY = amount;
	}
	public void setXPos(float amount) {
		this.box.x = amount;
	}
	public void setYPos(float amount) {
		this.box.y = amount;
	}
	
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