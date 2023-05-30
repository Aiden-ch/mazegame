package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class Enemy {
	private Rectangle box;
	private double health;
	private double damage;
	private ArrayList<EffectHandler> effects = new ArrayList<EffectHandler>(); 
	private double velX = 0;
	private double velY = 0;
	private double acceleration = 0.5;
	
	private Texture txte;
	private Image enImg;
	
	private double maxSpeed;
	
	private boolean tookDamage = false;
	private double timer = 0.0f;
	
	private double enKnockback = 5f;
	
	private char entype;
	private Projectile proj;
	
	private double prevX;
	private double prevY;
	
	public Enemy(Texture txte, float health, float maxSpeed, double damage) {
		this.txte = txte;
		this.health = health;
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		
		entype = 'm'; //m for melee
	}
	
	public Enemy(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte) {
		this.box = new Rectangle();
		this.box.x = xPos;
		this.box.y = yPos;
		this.maxSpeed = maxSpeed;
		this.health = health;
		this.damage = damage;
		
		this.txte = txte;
		this.enImg = new Image(txte);
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
			velX += Math.signum(maxSpeed * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velX) * 
					Math.min(Math.abs(acceleration * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x))), 
							Math.abs(maxSpeed * Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velX));
			velY += Math.signum(maxSpeed * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velY) * 
					Math.min(Math.abs(acceleration * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x))), 
							Math.abs(maxSpeed * Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x)) - velY));

			prevX = box.x;
			prevY = box.y;
			
			this.box.x += velX;
			this.box.y += velY;

			if(player.getBox().overlaps(box) && health > 0) {
				player.takeDamage(damage, (float)enKnockback, -180f + 180f/(float)Math.PI * (float)(Math.atan2(box.y-player.getYPos(), box.x-player.getImage().getWidth()/2-player.getXPos())));

				knockback(-90f + 180f/(float)Math.PI * (float)(Math.atan2(box.y-player.getYPos(), box.x-player.getImage().getWidth()/2-player.getXPos())));
			}
//			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
//				if(getBox().overlaps(EnemyHandler.getEnemies(i).getBox()) && EnemyHandler.getEnemies(i).getBox() != getBox()) {
//					//enemy collison
//					box.x = (float)prevX;
//					box.y = (float)prevY;
//				}
//			}
			
			
			if(box.x < 0) box.x = 0;
			if(box.x > Gdx.graphics.getWidth() - 64) box.x = (Gdx.graphics.getWidth() - 64);
			if(box.y < 0) box.y = 0;
			if(box.y > Gdx.graphics.getHeight() - 64) box.y = (Gdx.graphics.getHeight() - 64);

			enImg.setPosition(box.x, box.y);

			stage.addActor(enImg);
		}
		
		tickTime();
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
	public double getSpeed() {
		return this.maxSpeed;
	}
	public double getHealth() {
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
	public Projectile getProj() {
		return this.proj;
	}
	public char getType() {
		return this.entype;
	}
	public void setType(char enType) {
		this.entype = enType;
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
	public void setImage(Texture txte) {
		this.enImg.remove();
		this.enImg = null;
		this.enImg = new Image(txte);
	}
	public void setProj(Projectile proj) {
		this.proj = proj;
	}
	public void takeDamage(double damage) {
		this.health -= damage;
	}
	public void setPrevX(double x) {
		this.prevX = x;
	}
	public void setPrevY(double y) {
		this.prevY = y;
	}
	public double getPrevX() {
		return this.prevX;
	}
	public double getPrevY() {
		return this.prevY;
	}
	
	public void rendoor(Player player, Stage stage) {
		;
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