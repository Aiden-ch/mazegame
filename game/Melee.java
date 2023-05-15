package com.mygdx.game;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Melee {
	private double xPos;
	private double yPos;
	
	private double damage; 
	private double knockback;
	private double speed; //smaller speed = slower
	private float length;
	private float radius; //in radians
	private float startAngle;
	
	private Texture txte;
	private Image melImg; 
	
	private float tick = 0.0f;
	
	public Melee() {
		;
	}
	
	public Melee(Image melImg, Texture txte, double damage, double knockback, double speed, float radius) {
		//this.box = new Rectangle();
		
		this.txte = txte;
		this.melImg = melImg;
		this.speed = speed;
		this.radius = radius;
		this.damage = damage;
		this.knockback = knockback;
	}
	
	public Melee(double xPos, double yPos, double damage, float length, float radius, double speed, double knockback, float startAngle, Image melImg, Texture txte) {
		this.xPos = xPos;
		this.yPos = yPos;
		
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
	public double getKnockback() {
		return this.knockback;
	}
	public double getSpeed() {
		return this.speed;
	}
	public double getXPos() {
		return this.xPos;
	}
	public double getYPos() {
		return this.yPos;
	}
	public float getStartAngle() {
		return this.startAngle;
	}
	
	public boolean swing(Player player) {
		//rotate the thing
		if(tick <= 1) {
			melImg.rotateBy(180f/(float)Math.PI * (float)radius / (float)speed);
			tick += radius/speed/2;
			//System.out.println(tick);
			
			//box.x = player.getXPos() + ;
			//box.y = player.getYPos() + ;
			move(player.getXPos(), player.getYPos());
			
			return true;
		} else {
			tick = 0;
			return false;
		}
	}
	public void move(double xPos, double yPos) {
		//update box;
		this.xPos = xPos;
		this.yPos = yPos;
	}
}
