package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//player character
public class Player {
	//game features
	private Rectangle box;
	private double health;
	private double maxHealth = 100;
	private double speed = 5;
	private float velX = 0;
	private float velY = 0;
	private float acceleration = 0.5f;
	private ArrayList<EffectHandler> effects = new ArrayList<EffectHandler>(); 
	//^^^ for buffs and debuffs
	//private boolean damaged = false;
	private float invulTimer = 0.0f;
	
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
	
	public Texture block;
	public Texture dash;
	
	public Player() {
		health = maxHealth;
		box = new Rectangle();
		//animation
		walkf1 = new Texture("intern/walkf1.png");
		walkf2 = new Texture("intern/walkf2.png");
		walkb1 = new Texture("intern/walkb1.png");
		walkb2 = new Texture("intern/walkb2.png");
		walkl1 = new Texture("intern/walkl1.png");
		walkl2 = new Texture("intern/walkl2.png");
		walkr1 = new Texture("intern/walkr1.png");
		walkr2 = new Texture("intern/walkr2.png");
		player = walkf1;
		
		block = new Texture("intern/block.png");
		dash = new Texture("intern/speed.png");
		
		pImg = new Image(player);
		pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		
		box.x = Gdx.graphics.getWidth()/2 - 32;
		box.y = Gdx.graphics.getHeight()/2 - 32;
		box.width = pImg.getWidth()/2;
		box.height = pImg.getHeight()/2;
	}
	
	private double dashTimer = 0;
	private double blockTimer = 0;
	
	private boolean blocking = false;
	
	public void setMaxHealth(double num) {
		this.maxHealth = num;
	}
	public double getMaxHealth() {
		return this.maxHealth;
	}
	
	public void move(Batch batch, Stage stage) {
		invulTimer = Math.max(invulTimer-30/60f, 0);
		dashTimer = Math.max(dashTimer-1/60f, 0);
		blockTimer = Math.max(blockTimer-1/180f, 0);
		
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
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && blockTimer == 0 && !blocking) {
			blocking = true;
			blockTimer = 2;
		} else if((Gdx.input.isButtonPressed(Input.Buttons.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && dashTimer == 0) {
			dashTimer = 3;
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
		
		float xConst = (float)Math.cos(Math.atan2(yTarget, xTarget));
		if (xTarget == 0) xConst = 0;
		float yConst = (float)Math.sin(Math.atan2(yTarget, xTarget));
		
		velX += Math.signum(speed * xConst - velX) * 
			Math.min(acceleration, 
				 Math.abs(speed * xConst - velX));
		velY += Math.signum(speed * yConst - velY) * 
			Math.min(acceleration, 
				 Math.abs(speed * yConst - velY));
		
		this.box.x += velX;
		this.box.y += velY; 
		
		if(box.x < 0) box.x = (0);
		if(box.x > Gdx.graphics.getWidth() - 64) box.x = (Gdx.graphics.getWidth() - 64);
		if(box.y < 0) box.y = (0);
		if(box.y > Gdx.graphics.getHeight() - 64) box.y = (Gdx.graphics.getHeight() - 64);
				    
		pImg = new Image(player);
		pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		pImg.setPosition(box.x, box.y);
		batch.draw(player, box.x, box.y);
		
		if(dashTimer > 2.85) {
			box.x += (velX * 1.5);
			box.y += (velY * 1.5);
			batch.draw(dash, box.x, box.y + 64);
		}
		if(blockTimer > 1.5) {
			batch.draw(block, box.x, box.y);
			velX *= 0.75;
			velY *= 0.75;
			invulTimer = 1;
		} else if(blockTimer == 0 && blocking) {
			invulTimer = 0;
			blocking = false;
		}
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
	
	public void addHealth(int health) {
		this.health += health;
	}
	
	public double getDash() {
		return this.dashTimer;
	}
	public double getBlock() {
		return this.blockTimer;
	}
	public void setBlock(double time) {
		this.blockTimer = time;
	}
	public void setDash(double time) {
		this.dashTimer = time;
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
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	public void setVelX(float amount) {
		this.velX = amount;
	}
	public void setVelY(float amount) {
		this.velY = amount;
	}
	
	public void takeDamage(double damage, float knockback, double angle) {
		if((knockback > 0 && damage > 0) && !blocking) {
			InventoryHandler.proc(damage, "gothit", this);
		} else {
			InventoryHandler.proc(damage, "tapped", this);
		}
		
		if (invulTimer == 0) {
			this.health -= damage;
			
			this.velX += knockback * Math.cos(angle * Math.PI / 180);
			this.velY += knockback * Math.sin(angle * Math.PI / 180);
			
			invulTimer = 1f;
		}
	}
}