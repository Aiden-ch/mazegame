package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Melee {
	
	private double damage; 
	private double speed; //smaller speed = slower
	private float length;
	private float radius;
	
	private Texture txte;
	private Image melImg; 
	
	private float tick = 0.0f;
	private float start;
	
	private float startAngle;
	
	public Melee() {
		;
	}
	
	public Melee(Image melImg, Texture txte, double speed) {
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
	}
	
	public Melee(Image melImg, Texture txte, double speed, float startAngle) {
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		this.startAngle = startAngle;
	}
	
	public Melee(double damage, float length, float radius, double speed, Image melImg, Texture txte, float startAngle) {
		this.damage = damage;
		
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		
		this.length = length;
		this.radius = radius;
		
		this.startAngle = startAngle;
	}
	
	public float getLength() {
		return this.length;
	}
	public float getRadius() {
		return this.radius;
	}
	public Texture getTexture() {
		return this.txte;
	}
	public Image getImage() {
		return this.melImg;
	}
	public double getDamage() {
		return this.damage;
	}
	public double getSpeed() {
		return this.speed;
	}
	
	public boolean swing(float time) {
		//rotate the thing
		//setImage(projImage.setRotation());
		if(tick < time-start) {
			melImg.setRotation(startAngle-(float)(Math.atan2(Gdx.input.getY(), Gdx.input.getX())));
			tick += speed;
			return true;
		} else {
			tick = 0;
			return false;
		}
	}
	
	public void setStartAngle(float angle) {
		this.startAngle = angle;
	}
}
