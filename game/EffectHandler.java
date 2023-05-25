package com.mygdx.game;

public class EffectHandler {
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
