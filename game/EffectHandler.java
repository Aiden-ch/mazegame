package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

//manage status effects on player and enemies
//also manages visual effects
public class EffectHandler {
	
	//to store effect that corresponds with tick ^^^
	static ArrayList<EffectHandler> etp = new ArrayList<EffectHandler>();  //effect type pair
	
	private double xPos;
	private double yPos;
	private double radius;
	
	public double getXPos() {
		return this.xPos;
	}
	public double getYPos() {
		return this.yPos;
	}
	public double getRadius() {
		return this.radius;
	}
	
	//explosion effect
	public EffectHandler(double xPos, double yPos, double radius) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.radius = radius;
		this.effect = "explosion";
		this.duration = 1;
		etp.add(this);
	}
	public static void display() {
		for(int i=0; i<etp.size(); i++) {
			EffectHandler temp = etp.get(i);
			if(temp.getEffect().equals("explosion") && temp.getDuration() > 0) {
				for(int j=0; j<10; j++) {
					
				}
				temp.setDuration(Math.max(0, temp.getDuration() - 0.2f));
			} else if(temp.getDuration() == 0) {
				etp.remove(i);
				i--;
			}
		}
	}
	
	private float duration;
	private String effect;
	
	//Effect handler also serves as class for effects
	public EffectHandler(float duration, String effect) {
		this.duration = duration;
		this.effect = effect;
	}
	
	public float getDuration() {
		return this.duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	public String getEffect() {
		return this.effect;
	}
	
	public static boolean playerEffects(Player player, Batch batch) {
		for(int i=0; i<player.getEffects().size(); i++) {
			switch (player.getEffects().get(i).getEffect()) {
			case "speed+":
				if(player.getEffects().get(i).getDuration() == 10) {
					player.setSpeed(player.getSpeed() + 2f);
					player.setAcceleration(player.getAcceleration() + 0.15f);
					System.out.println(player.getAcceleration());
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
				} else if(player.getEffects().get(i).getDuration() <= 0) {
					player.setSpeed(player.getSpeed() - 2f);
					player.setAcceleration(player.getAcceleration() - 0.15f);
					System.out.println("no longer speedy");
					player.getEffects().remove(i);
					i--;
				} else {
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
					batch.draw(new Texture("effects/speed+.png"), player.getXPos(), player.getYPos() + 64);
				}
				break;
			case "speedup":
				if(player.getEffects().get(i).getDuration() == 3) {
					player.setSpeed(player.getSpeed() + 2f);
					player.setAcceleration(player.getAcceleration() + 0.15f);
					System.out.println(player.getAcceleration());
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
				} else if(player.getEffects().get(i).getDuration() <= 0) {
					player.setSpeed(player.getSpeed() - 2f);
					player.setAcceleration(player.getAcceleration() - 0.15f);
					System.out.println("no longer speedy");
					player.getEffects().remove(i);
					i--;
				} else {
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
					batch.draw(new Texture("effects/speed+.png"), player.getXPos(), player.getYPos() + 64);
				}
				break;
			case "refresh":
				if(player.getEffects().get(i).getDuration() <= 0) {
					player.getEffects().remove(i);
					i--;
				} else {
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
					batch.draw(new Texture("effects/refresh.png"), player.getXPos(), player.getYPos());
				}
				break;
			case "regen":
				if(player.getEffects().get(i).getDuration() <= 0) {
					player.getEffects().remove(i);
					i--;
				} else {
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
					player.addHealth(1);
					batch.draw(new Texture("effects/regen.png"), player.getXPos(), player.getYPos() + 64);
				}
				break;
			//add more effects
			}
		}
		return true;
	}
	
	
	public static boolean enemyEffects(Enemy enemy, Batch batch) {
		//TODO: add effects arraylist for enemy
		//TODO: implement similar thing from ^^^
		for(int i=0; i<enemy.getEffects().size(); i++) {
			switch (enemy.getEffects().get(i).getEffect()) {
			case "slow-":
				//later
				break;
			case "fire1":
				if(enemy.getEffects().get(i).getDuration() <= 0) {
					enemy.getEffects().remove(i);
					i--;
				} else {
					enemy.getEffects().get(i).setDuration(enemy.getEffects().get(i).getDuration() - 1f/60f);
					enemy.takeDamage(0.1);
					batch.draw(new Texture("effects/fire.png"), enemy.getXPos(), enemy.getYPos());
				}
				break;
			//add more effects
			}
		}
		return true;
	}
}
