package com.mygdx.game;

import java.util.ArrayList;

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
		//System.out.println(etp.size());
		for(int i=0; i<etp.size(); i++) {
			EffectHandler temp = etp.get(i);
			//System.out.println("explosion");
			if(temp.getEffect().equals("explosion") && temp.getDuration() > 0) {
				System.out.println("explosion");
				ShapeRenderer sr = new ShapeRenderer();
				sr.begin(ShapeType.Filled);
				sr.setColor(255, 0, 0, 1);
				sr.circle((float)temp.getXPos(), (float)temp.getYPos(), (float)temp.getRadius());
				sr.end();
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
	
	public static boolean playerEffects(Player player) {
		for(int i=0; i<player.getEffects().size(); i++) {
			switch (player.getEffects().get(i).getEffect()) {
			case "speed+":
				if(player.getEffects().get(i).getDuration() == 10) {
					player.setSpeed(player.getSpeed() + 1.5f);
					player.setAcceleration(player.getAcceleration() + 0.15f);
					System.out.println(player.getAcceleration());
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
				} else if(player.getEffects().get(i).getDuration() <= 0) {
					player.setSpeed(player.getSpeed() - 1.5f);
					player.setAcceleration(player.getAcceleration() - 0.15f);
					System.out.println("no longer speedy");
					player.getEffects().remove(i);
					i--;
				} else {
					player.getEffects().get(i).setDuration(player.getEffects().get(i).getDuration() - 1f/60f);
				}
				break;
			//add more effects
			}
		}
		return true;
	}
	
	/*
	public static boolean enemyEffects(Enemy enemy) {
		//TODO: add effects arraylist for enemy
		//TODO: implement similar thing from ^^^
		for(int i=0; i<enemy.getEffects().size(); i++) {
			switch (enemy.getEffects().get(i).getEffect()) {
			case "slow+":
				if(enemy.getEffects().get(i).getDuration() > 0) {
					enemy.setSpeed(0.5);
					enemy.getEffects().get(i).setDuration(enemy.getEffects().get(i).getDuration() - 1f/60f);
					System.out.println(enemy.getEffects().get(i).getDuration());
				} else {
					enemy.setSpeed(1);
					System.out.println("no longer speedy");
					enemy.getEffects().remove(i);
					i--;
				}
				break;
			//add more effects
			}
		}
		return true;
	}
	*/
}
