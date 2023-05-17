package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;

public class Projectile {
	private double damage;
	private ArrayList<Double> velocity = new ArrayList<Double>();
	private double speed = 0.0;
	private int pierce;
	
	private Rectangle box;
	
	private Image projImage;
	private Texture txte;
	
	public Projectile() {
		;
	}
	
	public Projectile(Texture txte, Image img, double speed, double damage, int pierce) {
		projImage = img;
		this.txte = txte;
		this.speed = speed;
		this.damage = damage;
		this.pierce = pierce;
	}
	
	public Projectile(double xPos, double yPos, double damage, double velx, double vely, double speed, int pierce, Image img) {
		this.damage = damage;
		
		double mag = Math.sqrt(velx*velx + vely*vely);
		
		this.velocity.add(velx/mag);
		this.velocity.add(vely/mag);
		this.speed = speed;
		this.pierce = pierce;
		
		projImage = img;
		
		this.box = new Rectangle();
		box.x = (float)xPos;
		box.y = (float)yPos;
		box.width = img.getWidth()/4;
		box.height = img.getHeight()/4;
	}
	
//	public Projectile(double xPos, double yPos, double damage, double velx, double vely, double speed, Texture txte, Image img) {
//		this.damage = damage;
//		
//		double mag = Math.sqrt(velx*velx + vely*vely);
//		
//		this.velocity.add(velx/mag);
//		this.velocity.add(vely/mag);
//		this.speed = speed;
//		
//		projImage = img;
//		this.txte = txte;
//		
//		this.box = new Rectangle();
//		box.x = (float)xPos;
//		box.y = (float)yPos;
//		box.width = img.getWidth()/4;
//		box.height = img.getHeight()/4;
//	}
	
	public double getXPos() {
		return box.x;
	}
	public double getYPos() {
		return box.y;
	}
	public double getDamage() {
		return damage;
	}
	public ArrayList<Double> getVelocity() {
		return velocity;
	}
	public double getSpeed() {
		return speed;
	}
	public Image getImage() {
		return projImage;
	}
	public Texture getTexture() {
		return txte;
	}
	public Rectangle getBox() {
		return this.box;
	}
	public int getPierce() {
		return this.pierce;
	}
	
	public void move() {
		this.box.x += this.velocity.get(0) * speed;
		this.box.y += this.velocity.get(1) * speed;
	}
	
	public void setImage(Image img) {
		projImage = img;
	}
	protected void setXPos(double xPos) {
		this.box.x = (float)xPos;
	}
	protected void setYPos(double yPos) {
		this.box.y = (float)yPos;
	}
}
