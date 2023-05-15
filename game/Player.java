package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//player character
public class Player {
	//game features
	private Rectangle box;
	private double health;
	private double speed = 1;
	private float velX = 0;
	private float velY = 0;
	private float acceleration = 0.2f;
	private ArrayList<EffectHandler> effects = new ArrayList<EffectHandler>(); 
	//^^^ for buffs and debuffs
	
	//animation
	private Texture walkf1;
	private Texture walkf2;
	private Texture walkb1;
	private Texture walkb2;
	private Texture walkl1;
	private Texture walkl2;
	private Texture walkr1;
	private Texture walkr2;
	public Texture player;
	int prev = 1;
	int time = 0;
	String last = "up";
	private Image pImg;
	
	public Player() {
		health = 15d;
		box = new Rectangle();
		//animation
		walkf1 = new Texture("walkf1.png");
		walkf2 = new Texture("walkf2.png");
		walkb1 = new Texture("walkb1.png");
		walkb2 = new Texture("walkb2.png");
		walkl1 = new Texture("walkl1.png");
		walkl2 = new Texture("walkl2.png");
		walkr1 = new Texture("walkr1.png");
		walkr2 = new Texture("walkr2.png");
		player = walkf1;
		
		pImg = new Image(player);
		pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		
		box.x = Gdx.graphics.getWidth()/2 - 32;
		box.y = Gdx.graphics.getHeight()/2 - 32;
		box.width = pImg.getWidth()/2;
		box.height = pImg.getHeight()/2;
	}
	
	public ArrayList<EffectHandler> getEffects() {
		return this.effects;
	}
	public double getSpeed() {
		return this.speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public float getXPos() {
		return this.box.x;
	}
	public float getYPos() {
		return this.box.y;
	}
	public Image getImage() {
		return this.pImg;
	}
	public Rectangle getBox() {
		return this.box;
	}
	public double getHealth() {
		return this.health;
	}
	
	public void setXPos(float xPos) {
		this.box.x = xPos;
	}
	public void setYPos(float yPos) {
		this.box.y = yPos;
	}
// 	public void takeDamage(double damage, double time) {
// 		//if(time % 2 == 0) {
// 			this.health -= damage;
// 		//}
// 	}
	
	public void addHealth(int health) {
		this.health += health;
	}
	
	public void move() {
		double xTarget = 0;
		double yTarget = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			xTarget -= 1;
			if(time > 10) {
			if(last.equals("left")) {
			   if(prev == 1) {
				   player = walkl2;
				   prev = 2;
			   } else {
				   player = walkl1;
				   prev = 1;
			   }
			} else {
			   player = walkl1;
			   last = "left";
			}
			time = 0;
			} else {
			   time ++;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			xTarget += 1;
			   if(time > 10) {
				   if(last.equals("right")) {
					   if(prev == 1) {
						   player = walkr2;
						   prev = 2;
					   } else {
						   player = walkr1;
						   prev = 1;
					   }
				   } else {
					   player = walkr1;
					   last = "right";
				   }
				   time = 0;
			   } else {
				   time ++;
			   }
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			yTarget += 1;
			   if(time > 10) {
				   if(last.equals("up")) {
					   if(prev == 1) {
						   player = walkb2;
						   prev = 2;
					   } else {
						   player = walkb1;
						   prev = 1;
					   }
				   } else {
					   player = walkb1;
					   last = "up";
				   }
				   time = 0;
			   } else {
				   time ++;
			   }

		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			yTarget -= 1;
			   if(time > 10) {
				   if(last.equals("down")) {
					   if(prev == 1) {
						   player = walkf2;
						   prev = 2;
					   } else {
						   player = walkf1;
						   prev = 1;
					   }
				   } else {
					   player = walkf1;
					   last = "down";
				   }
				   time = 0;
			   } else {
				   time++;
			   }
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
		   if(last.equals("down")) {
			   player = walkf1;
		   } else if(last.equals("right")) {
			   player = walkr1;
		   } else if(last.equals("left")) {
			   player = walkl1;
		   } else {
			   player = walkb1;
		   }
		}
		
		float angle = (float)Math.atan2(yTarget, xTarget);
		if (yTarget == 0 && xTarget == 0) 
		velX += Math.signum(speed * Math.cos(Math.atan2(yTarget, xTarget)) - velX) * 
			Math.min(Math.abs(acceleration * Math.cos(Math.atan2(yTarget, xTarget))), 
					Math.abs(speed * Math.cos(Math.atan2(yTarget, xTarget)) - velX));
		velY += Math.signum(speed * Math.sin(Math.atan2(yTarget, xTarget)) - velY) * 
			Math.min(Math.abs(acceleration * Math.sin(Math.atan2(yTarget, xTarget))), 
					Math.abs(speed * Math.sin(Math.atan2(yTarget, xTarget)) - velY));
		
		this.box.x += velX;
		this.box.y += velY; 
				    
		pImg = new Image(player);
		pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		pImg.setPosition(box.x, box.y);
	}
	public float getVelX() {
		return this.velX;
	}
	public float getVelY() {
		return this.velY;
	}
	public float getAcceleration() {
		return this.acceleration;
	}
	public void setVelX(float amount) {
		this.velX = amount;
	}
	public void setVelY(float amount) {
		this.velY = amount;
	}
	
	public void takeDamage(double damage, float knockback, double angle) {
		//if (buffer % 3600f == 0) {
			this.health -= damage;
		//}
		
		this.velX += knockback * Math.cos(angle * Math.PI / 180);
		this.velY += knockback * Math.sin(angle * Math.PI / 180);
	}
	
}
