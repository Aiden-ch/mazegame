package com.mygdx.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.Player;

import java.lang.Math;

public class GoldenGuardian extends Enemy {
	private double meleeCoolDown = 0;
	private double maxMeleeCoolDown = 1;
	private double lungeCoolDown = 0;
//	private double maxLungeCoolDown = 1;
	private boolean lunging = false;
	private double lungetime = 0;
	
	private Texture walk1;
	private Texture walk2;
	private Texture jump;
	//for animation
	private Texture current;
	private int alternate = 0;
	private double anitick = 0.0;
	
	//for stun
	private Texture stunned;
	private double stunTick = 0;

	public GoldenGuardian(Texture txte, float health, float maxSpeed, double damage) {
		super(txte, health, maxSpeed, damage);
	}

	public GoldenGuardian(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		walk1 = new Texture("enemies/gg1.png");
		walk2 = new Texture("enemies/gg2.png");
		jump = new Texture("enemies/ggjump.png");
		stunned = new Texture("enemies/ggstunned.png");
		setType('g');
	}

	@Override
	public void update(Player player, Stage stage, int index, Batch batch) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
			EnemyHandler.bossOnField = null;
		} else {
			meleeCoolDown = Math.max(0, meleeCoolDown - 1/30d);
			lungeCoolDown = Math.max(0, lungeCoolDown - 1d/60d);

			double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
			double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
			
			anitick = Math.max(anitick - 0.2, 0);
			if(!lunging) {
				if(alternate == 0 && anitick == 0) {
					setImage(walk1);
					alternate = 1;
					anitick = 2;
					current = walk1;
				} else if(alternate == 1 && anitick == 0) {
					setImage(walk2);
					alternate = 0;
					anitick = 2;
					current = walk2;
				}
			}
			
			if(lunging && lungetime > 5) {
				lunging = false;
				stunTick = 0.5;
			}
			
			if(stunTick > 0) {
				setImage(stunned);
				current = stunned;
				lungetime = 0;
			} else if(lunging) {
				setImage(jump);
				current = jump;
				lungetime += 0.2;
			} else if (distance < 70) {
				setVelX(getVelX() + Math.signum(getCurrentSpeed() * Math.cos(angle) - getVelX()) * 
						Math.min(Math.abs(getAcceleration() * Math.cos(angle)), 
								Math.abs(getCurrentSpeed() * Math.cos(angle) - getVelX())));
				setVelY(getVelY() + Math.signum(getCurrentSpeed() * Math.sin(angle) - getVelY()) * 
						Math.min(Math.abs(getAcceleration() * Math.sin(angle)), 
								Math.abs(getCurrentSpeed() * Math.sin(angle) - getVelY())));
			} else if (distance < 90) {
				setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getCurrentSpeed(), 0.6 * (100 - distance)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle + Math.PI) * Math.min(getCurrentSpeed(), 0.6 * (100 - distance)) - getVelX())));
				setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getCurrentSpeed(), 0.6 * (100 - distance)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle + Math.PI) * Math.min(getCurrentSpeed(), 0.6 * (100 - distance)) - getVelY())));
			} else if (distance < 105) {
				if (lungeCoolDown == 0) {
					setVelX(10 * Math.sin(90-angle));
					setVelY(10 * Math.cos(90-angle));
					lungeCoolDown = 3;
					lunging = true;
				}
			} else {
				setVelX(getVelX() + Math.signum(Math.cos(angle) * Math.min(getCurrentSpeed(), 0.6 * (distance - 100)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle) * Math.min(getCurrentSpeed(), 0.6 * (distance - 100)) - getVelX())));
				setVelY(getVelY() + Math.signum(Math.sin(angle) * Math.min(getCurrentSpeed(), 0.6 * (distance - 100)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle) * Math.min(getCurrentSpeed(), 0.6 * (distance - 100)) - getVelY())));
			}

			if(stunTick == 0) {
				setXPos(getXPos() + (float)getVelX());
				setYPos(getYPos() + (float)getVelY());
				
				for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
					boolean buffed = false;
					for(int j=0; j<EnemyHandler.getEnemies(i).getEffects().size(); j++) {
						if(EnemyHandler.getEnemies(i).getEffects().get(j).getEffect().equals("gold") || EnemyHandler.getEnemies(i).getType() == 'g') {
							buffed = true;
						}
					}
					if(!buffed) {
//						System.out.println("golden");
						EnemyHandler.getEnemies(i).getEffects().add(new EffectHandler(100, "gold"));
					}
				}
			}
			
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				if(getBox().overlaps(EnemyHandler.getEnemies(i).getBox()) && EnemyHandler.getEnemies(i).getBox() != getBox()  && EnemyHandler.getEnemies(i).getType() == 'g') {
					//enemy collison
					setKnockback(0.5f);
					knockback(-90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-EnemyHandler.getEnemies(i).getYPos(), getXPos()-player.getImage().getWidth()/2-EnemyHandler.getEnemies(i).getXPos())));
					setKnockback(5);
				}
			}

			if(player.getBox().overlaps(getBox())) {
				if (lunging) {
					player.takeDamage(35, 20, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
					lunging = false;
				} else if (meleeCoolDown == 0) {
					player.takeDamage(10, 5, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
					meleeCoolDown = maxMeleeCoolDown;
				}
			}

			if(!lunging) {
				if(getXPos() < 0) setXPos(0);
				if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
				if(getYPos() < 0) setYPos(0);
				if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);
			} else {
				if(getXPos() < 0) {
					setXPos(0);
					lunging = false;
					stunTick = 25;
				}
				if(getXPos() > Gdx.graphics.getWidth() - 64) {setXPos(Gdx.graphics.getWidth() - 64);
					lunging = false; stunTick = 25;
				}
				if(getYPos() < 0) {setYPos(0);
					lunging = false; stunTick = 25;
				}
				if(getYPos() > Gdx.graphics.getHeight() - 64) {setYPos(Gdx.graphics.getHeight() - 64);
					lunging = false; stunTick = 25;
				}
			}
			stunTick = Math.max(0, stunTick-0.2f);
			
			//getImage().setPosition(getXPos(), getYPos());
			batch.draw(current, getXPos(), getYPos());

			//stage.addActor(getImage());
			tickTime();
		}
	}

	public void takeDamage(double damage, float knockback, float angle) {
		super.takeDamage(damage, knockback/3, angle);
	}
}