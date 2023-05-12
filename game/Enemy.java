package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;

public class Enemy {
	private Rectangle box;
	private float health;
	private double damage;
	
	private Texture txte;
	private Image enImg;
	
	private float speed;
	
	public Enemy(Texture txte, Image enImg, float health, float speed, double damage) {
		this.txte = txte;
		this.enImg = enImg;
		this.health = health;
		this.speed = speed;
		this.damage = damage;
	}
	
	public Enemy(float xPos, float yPos, float health, float speed, double damage, Texture txte, Image enImg) {
		this.box = new Rectangle();
		this.box.x = xPos;
		this.box.y = yPos;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		
		this.txte = txte;
		this.enImg = enImg;
		this.enImg.setPosition(xPos, yPos);
		
		this.box.width = enImg.getWidth();
		this.box.height = enImg.getHeight();
	}
	
	public void move(Player player) {
		this.box.x += speed*Math.cos(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x));
		this.box.y += speed*Math.sin(Math.atan2(player.getYPos()-box.y, player.getXPos()-box.x));
	}
	
	public float getXPos() {
		return this.box.x;
	}
	public float getYPos() {
		return this.box.y;
	}
	public float getHealth() {
		return this.health;
	}
	public float getSpeed() {
		return this.speed;
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
	
	public void takeDamage(double damage) {
		//if (buffer % 3600f == 0) {
			this.health -= damage;
		//}
	}
	
}
