package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.lang.Math;

public class BossName extends Enemy {
	private double meleeCoolDown = 0;
	private double maxMeleeCoolDown = 1;
	private double lungeCoolDown = 0;
//	private double maxLungeCoolDown = 1;
	private boolean lunging = false;
	
	private Texture walk1;
	private Texture walk2;
	private Texture jump;
	//for animation
	private int alternate = 0;
	private double anitick = 0.0;

	public BossName(Texture txte, float health, float maxSpeed, double damage) {
		super(txte, health, maxSpeed, damage);
		walk1 = new Texture("enemies/gg1.png");
		walk2 = new Texture("enemies/gg2.png");
		jump = new Texture("enemies/ggjump.png");
	}

	public BossName(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		walk1 = new Texture("enemies/gg1.png");
		walk2 = new Texture("enemies/gg2.png");
		jump = new Texture("enemies/ggjump.png");
	}

	@Override
	public void update(Player player, Stage stage, int index) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
		} else {
			meleeCoolDown = Math.max(0, meleeCoolDown - 1/6d);
			lungeCoolDown = Math.max(0, lungeCoolDown - 1d/60d);

			double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
			double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
			
			anitick = Math.max(anitick - 0.2, 0);
			if(!lunging) {
				if(alternate == 0 && anitick == 0) {
					setImage(walk1);
					alternate = 1;
					anitick = 2;
				} else if(alternate == 1 && anitick == 0) {
					setImage(walk2);
					alternate = 0;
					anitick = 2;
				}
			}
			
			if(lunging) {
				setImage(jump);
			} else if (distance < 70) {
				setVelX(getVelX() + Math.signum(getSpeed() * Math.cos(angle) - getVelX()) * 
						Math.min(Math.abs(getAcceleration() * Math.cos(angle)), 
								Math.abs(getSpeed() * Math.cos(angle) - getVelX())));
				setVelY(getVelY() + Math.signum(getSpeed() * Math.sin(angle) - getVelY()) * 
						Math.min(Math.abs(getAcceleration() * Math.sin(angle)), 
								Math.abs(getSpeed() * Math.sin(angle) - getVelY())));
			} else if (distance < 90) {
				setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelX())));
				setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (100 - distance)) - getVelY())));
			} else if (distance < 125) {
				if (lungeCoolDown == 0) {
					setVelX(10 * Math.sin(90-angle));
					setVelY(10 * Math.cos(90-angle));
					lungeCoolDown = 3;
					lunging = true;
				}
			} else {
				setVelX(getVelX() + Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelX())));
				setVelY(getVelY() + Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 100)) - getVelY())));
			}

			setXPos(getXPos() + (float)getVelX());
			setYPos(getYPos() + (float)getVelY());

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
				}
				if(getXPos() > Gdx.graphics.getWidth() - 64) {setXPos(Gdx.graphics.getWidth() - 64);
					lunging = false;
				}
				if(getYPos() < 0) {setYPos(0);
					lunging = false;
				}
				if(getYPos() > Gdx.graphics.getHeight() - 64) {setYPos(Gdx.graphics.getHeight() - 64);
					lunging = false;
				}
			}
			
			getImage().setPosition(getXPos(), getYPos());

			stage.addActor(getImage());
		}
	}

	public void takeDamage(double damage, float knockback, float angle) {
		super.takeDamage(damage, knockback/3, angle);
	}
}