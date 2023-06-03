package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import enemies.Enemy;

import com.badlogic.gdx.Input;

public class Melee {
	private double damage; 
	private double knockback;
	private double swingTime; // time in seconds
	private float swingRange; //in radians
	private float startAngle;
	
	private Image melImg; 
	
	private double tick = 0;
	private float maxCoolDown;
	private float coolDown = 0.0f;
	private boolean swinging = false;
	
	public Melee() {
		;
	}
	
	public Melee(double damage, float swingRange, double swingTime, double knockback, float maxCoolDown, Texture txte) {
		this.damage = damage;
		this.swingRange = swingRange;
		this.swingTime = swingTime;
		this.knockback = knockback;
		this.maxCoolDown = maxCoolDown;
		
		this.melImg = new Image(txte);
		this.melImg.setOrigin((float)txte.getWidth()/2, 0);
	}
	
	public double getCoolDown() {
		return this.coolDown;
	}
	
	public boolean update(Player player, Stage stage, Batch batch) {
		//rotate the thing
		if (swinging) {
			//hit detection
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				Enemy tempem = EnemyHandler.getEnemies(i);
				float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getImage().getWidth()/2-player.getXPos()));

				if((Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= melImg.getHeight()+10)
		        	   && (Math.abs(this.startAngle - enan + swingRange / 2 * 180 / Math.PI) <= swingRange / 2 * 180 / Math.PI)) {

					EnemyHandler.getEnemies().get(i).takeDamage(damage, (float)knockback, enan);
				}
			}
			
			melImg.rotateBy(180f/(float)Math.PI * (float)swingRange / (float)swingTime);
			tick += swingRange/swingTime;
			stage.addActor(melImg);
		}
		
		// decrement coolDown
		coolDown = Math.max(coolDown -= 0.2f, 0);
		// check whether to start attack
		if(coolDown == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			
			float angle = -90f + 180f/(float)Math.PI * (float)(Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos()) - swingRange / 2);
			melImg.setRotation(angle);
			startAngle = angle; 
			
			swinging = true;
			tick = 0;
			coolDown = maxCoolDown;
			
			InventoryHandler.proc(-1, "swing", MazeCombat.player);
			
			stage.addActor(melImg);
		}
		
		setPosition(player.getXPos(), player.getYPos() + 20);
		
		if(tick >= swingRange) {
			swinging = false;
			melImg.remove();
			tick = 0;
			return true;
		}
		return false; //use to tick down uses on card
	}
	
	//what effects happens when melee hits 
	public void hit(Enemy enemy) {
		//extender
	}
	
	public void setPosition(double xPos, double yPos) {
		//update image
		melImg.setPosition((float)xPos, (float)yPos);
	}
	public void setDamage(double num) {
		this.damage = num;
	}
	public double getDamage() {
		return this.damage;
	}
	public void setKnockback(double num) {
		this.knockback = num;
	}
	public double getKnockback() {
		return this.knockback;
	}
}