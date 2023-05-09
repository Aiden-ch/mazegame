package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;

public class Projectile {
	private double xPos;
	private double yPos;
	private double damage;
	private ArrayList<Double> velocity = new ArrayList<Double>();
	
	private double speed;
	public Image projImage;
	
	public int index;
	
	public Projectile() {
		;
	}
	
	public Projectile(double xPos, double yPos, double damage, double velx, double vely, double speed, int index, Image img) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.damage = damage;
		
		double mag = Math.sqrt(velx*velx + vely*vely);
		
		this.velocity.add(velx/mag);
		this.velocity.add(vely/mag);
		
		this.speed = speed;
		this.index = index;
		
		projImage = img;
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
	
	public void move() {
		this.xPos += this.velocity.get(0)*speed;
		this.yPos += this.velocity.get(1)*speed;
	}
	
	public void setImage(Image img) {
		projImage = img;
	}
}
