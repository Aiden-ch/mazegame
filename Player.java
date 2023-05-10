package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	//animation
	Texture still;
	Texture walkf1;
	Texture walkf2;
	Texture walkb1;
	Texture walkb2;
	public Texture player;
	int prev = 1;
	int time = 0;
	String last = "up";
	
	private Rectangle box;
	
	public Player() {
		//animation
		walkf1 = new Texture("walkf1.png");
		walkf2 = new Texture("walkf2.png");
		walkb1 = new Texture("walkb1.png");
		walkb2 = new Texture("walkb2.png");
		still = new Texture("still.png");
		player = walkf1;
		
		//movement
		box = new Rectangle();
		box.x = 800 / 2 - 64 / 2;
		box.y = 20;
		box.width = 64;
		box.height = 64;
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			   box.x -= 200 * Gdx.graphics.getDeltaTime();
		   }
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			   box.x += 200 * Gdx.graphics.getDeltaTime();
		   }
		   
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
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
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
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
	}
	
	public float getXPos() {
		return this.box.x;
	}
	public float getYPos() {
		return this.box.y;
	}
	public void setXPos(float xPos) {
		this.box.x = xPos;
	}
	public void setYPos(float yPos) {
		this.box.y = yPos;
	}
	
}
