package com.mygdx.game;

//import com.badlogic.gdx.Gdx;
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
	
	public Melee() {
		;
	}
	
	public Melee(Image melImg, Texture txte, double speed, float radius, String nothing) {
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		this.radius = radius;
	}
	
	public Melee(Image melImg, Texture txte, double speed) {
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
	}
	
	public Melee(double damage, float length, float radius, double speed, Image melImg, Texture txte) {
		this.damage = damage;
		
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		
		this.length = length;
		this.radius = radius;
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
	
	public boolean swing() {
		//rotate the thing
		if(tick <= speed) {
			melImg.rotateBy(-(float)(180 / Math.PI * tick));
			tick += radius/speed;
			System.out.println(tick);
			return true;
		} else {
			tick = 0;
			return false;
		}
	}
}
