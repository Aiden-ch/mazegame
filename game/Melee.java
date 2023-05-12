package com.mygdx.game;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Melee {
	private Rectangle box;
	
	private double damage; 
	private double speed; //smaller speed = slower
	private float length;
	private float radius; //in radians
	
	private Texture txte;
	private Image melImg; 
	
	private float tick = 0.0f;
	
	public Melee() {
		;
	}
	
	public Melee(Image melImg, Texture txte, double damage, double speed, float radius) {
		//this.box = new Rectangle();
		
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		this.radius = radius;
		this.damage = damage;
	}
	
	public Melee(double damage, float length, float radius, double speed, Image melImg, Texture txte) {
		this.damage = damage;
		
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		
		this.length = length;
		this.radius = radius;
		
		this.box = new Rectangle();
		this.box.width = 128f;
		this.box.height = 128f;
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
	public Rectangle getBox() {
		return this.box;
	}
	
	public boolean swing() {
		//rotate the thing
		if(tick <= radius*2) {
			melImg.rotateBy(-(float)(180 / Math.PI * speed/radius/4));
			tick += speed/radius/4;
			//System.out.println(tick);
			
			//update box;
			this.box.x = melImg.getImageX();
			this.box.y = melImg.getImageY();
			
			return true;
		} else {
			tick = 0;
			return false;
		}
	}
}
