package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;

public class Projectile {
	private double speed = 0.0;
	private ArrayList<Double> velocity = new ArrayList<Double>();
	
	private double damage;
	private double pierce;
	private double knockback;
	private Rectangle box;
	
	private Image projImage;
	private Texture projTexture;
	
	private String ptype = "r";
	
	public String getType() {
		return this.ptype;
	}
	public void setType(String temp) {
		this.ptype = temp;
	}
	
	public double getRange() {
		return 0;
	}
	
	public Projectile(Texture txte, double speed, double damage, int pierce) {
		projImage = new Image(txte);
		projImage.setOrigin(txte.getWidth()/2, txte.getHeight()/2);
		this.projTexture = txte;
		this.speed = speed;
		this.damage = damage;
		this.pierce = pierce;
	}
	
	//use this constructor when making projectiles in RangedItem classes
	public Projectile(Texture txte, double xPos, double yPos, double velx, double vely, double speed, double damage, double pierce, double knockback) {	
		//movement
		double mag = Math.sqrt(velx*velx + vely*vely);
		this.velocity.add(velx/mag);
		this.velocity.add(vely/mag);
		this.speed = speed;
		
		//damage
		this.pierce = pierce; //how many enemies goes through before destroyed
		this.damage = damage;
		this.knockback = knockback;
		//hitbox
		this.box = new Rectangle();
		this.box.x = (float)xPos;
		this.box.y = (float)yPos;
		this.box.width = txte.getWidth()/4;
		this.box.height = txte.getHeight()/4;
	}
	
	public double getDamage() {
		return damage;
	}
	public double getPierce() {
		return this.pierce;
	}
	public void tickPierce() {
		this.pierce--;
	}
	public double getKnockback() {
		return this.knockback;
	}
	public Rectangle getBox() {
		return this.box;
	}
	public float getXPos() {
		return this.box.x;
	}
	public float getYPos() {
		return this.box.y;
	}
	
	public void setXPos(double x) {
		this.box.x = (float)x;
	}
	public void setYPos(double y) {
		this.box.y = (float)y;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	public ArrayList<Double> getVelocity() {
		return velocity;
	}
	public Image getImage() {
		return this.projImage;
	}
	public Texture getTexture() {
		return this.projTexture;
	}
	public void setImage(Image temp) {
		this.projImage = temp;
	}
	public double getRadius() {
		return 100;
	}
	
	public void move() {
		this.box.x += this.velocity.get(0) * speed;
		this.box.y += this.velocity.get(1) * speed;
		this.projImage.setPosition(this.box.x, this.box.y);
	}
	public void setPos(double xPos, double yPos) {
		this.box.x = (float)xPos;
		this.box.y = (float)yPos;
		this.projImage.setPosition(this.box.x, this.box.y);
	}
	
	//what happens when projectile hits
	//effects and what not
	public void hit() {
		//extender
	}
}
