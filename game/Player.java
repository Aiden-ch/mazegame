package com.mygdx.game;

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
	
	//animation
	private Texture still;
	private Texture walkf1;
	private Texture walkf2;
	private Texture walkb1;
	private Texture walkb2;
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
		still = new Texture("still.png");
		player = walkf1;
		
		pImg = new Image(player);
		pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		
		box.x = Gdx.graphics.getWidth()/2 - 32;
		box.y = Gdx.graphics.getHeight()/2 - 32;
		box.width = pImg.getWidth()/2;
		box.height = pImg.getHeight()/2;
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			   box.x -= 200 * Gdx.graphics.getDeltaTime();
			   if(time > 10) {
				   if(last.equals("up")) {
					   if(prev == 1) {
						   player = walkb2;
						   prev = 2;
					   } else {
						   player = walkb1;
						   prev = 1;
					   }
				   } else if(last.equals("down")) {
					   if(prev == 1) {
						   player = walkf2;
						   prev = 2;
					   } else {
						   player = walkf1;
						   prev = 1;
					   }
				   }
				   time = 0;
			   } else {
				   time++;
			   }
			   
		   }
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			   box.x += 200 * Gdx.graphics.getDeltaTime();
			   if(time > 10) {
				   if(last.equals("up")) {
					   if(prev == 1) {
						   player = walkb2;
						   prev = 2;
					   } else {
						   player = walkb1;
						   prev = 1;
					   }
				   } else if(last.equals("down")) {
					   if(prev == 1) {
						   player = walkf2;
						   prev = 2;
					   } else {
						   player = walkf1;
						   prev = 1;
					   }
				   }
				   time = 0;
			   } else {
				   time++;
			   }
		   }
		   
		   if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			   box.y += 200 * Gdx.graphics.getDeltaTime();
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
			  box.y -= 200 * Gdx.graphics.getDeltaTime();
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
			   player = still;
		   }
		   
		   pImg = new Image(player);
		   pImg.setOrigin(player.getWidth()/2, player.getHeight()/2);
		   pImg.setPosition(box.x, box.x);
		   //System.out.println(box.x);
		   //System.out.println(box.y);
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
	public void takeDamage(double damage, double time) {
		//if(time % 2 == 0) {
			this.health -= damage;
		//}
	}
	
}
