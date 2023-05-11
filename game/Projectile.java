package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;

public class Projectile {
	private double xPos;
	private double yPos;
	private double damage;
	private ArrayList<Double> velocity = new ArrayList<Double>();
	private double speed = 0.0;
	
	public Image projImage;
	public Texture txte;
	
	public Projectile() {
		;
	}
	
	public Projectile(Texture txte, Image img) {
		projImage = img;
		this.txte = txte;
		this.speed = 0.0;
	}
	
	public Projectile(Texture txte, Image img, double speed) {
		projImage = img;
		this.txte = txte;
		this.speed = speed;
	}
	
	public Projectile(double xPos, double yPos, double damage, double speed, Image img, Texture txte) {
		
	}
	
	public Projectile(double xPos, double yPos, double damage, double velx, double vely, double speed, Image img) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.damage = damage;
		
		double mag = Math.sqrt(velx*velx + vely*vely);
		
		this.velocity.add(velx/mag);
		this.velocity.add(vely/mag);
		this.speed = speed;
		
		projImage = img;
	}
	
	public Projectile(double xPos, double yPos, double damage, double velx, double vely, double speed, Texture txte, Image img) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.damage = damage;
		
		double mag = Math.sqrt(velx*velx + vely*vely);
		
		this.velocity.add(velx/mag);
		this.velocity.add(vely/mag);
		this.speed = speed;
		
		projImage = img;
		this.txte = txte;
	}
	
	public double getXPos() {
		return xPos;
	}
	public double getYPos() {
		return yPos;
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
	
	public void move() {
		this.xPos += this.velocity.get(0) * speed;
		this.yPos += this.velocity.get(1) * speed;
	}
	
	public void setImage(Image img) {
		projImage = img;
	}
	protected void setXPos(double xPos) {
		this.xPos = xPos;
	}
	protected void setYPos(double yPos) {
		this.yPos = yPos;
	}
}
