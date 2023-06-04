package com.mygdx.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EffectHandler;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.Player;

public class InkShade extends Enemy {

	private double meleeCoolDown = 0;
	private double maxMeleeCoolDown = 1;
	private double speedCoolDown = 0;
//	private double maxLungeCoolDown = 1;
	private boolean speeding = false;
	
	private Texture right;
	private Texture left;
	private Texture rights1;
	private Texture lefts1;
	private Texture rights2;
	private Texture lefts2;
	private Texture haze;
	//for animation
	private Texture current;
	private double anitick = 0.0;

	public InkShade(Texture txte, float health, float maxSpeed, double damage) {
		super(txte, health, maxSpeed, damage);
	}
	public InkShade(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		right = new Texture("enemies/isr.png");
		left = new Texture("enemies/isl.png");
		right = new Texture("enemies/isr.png");
		left = new Texture("enemies/isl.png");
		rights1 = new Texture("enemies/isrswing1.png");
		lefts1 = new Texture("enemies/islswing1.png");
		rights2 = new Texture("enemies/isrswing2.png");
		lefts2 = new Texture("enemies/islswing2.png");
		haze = new Texture("effects/inkhaze.png");
		
		speedCoolDown = 3;
		setType('i');
	}
	
	private double maxSpeedTick = 5;
	private double speedTick = maxSpeedTick;
	
	private double maxHazeTick = 10;
	private double hazeTick = maxHazeTick;
	public boolean hazing = false;
	
	@Override
	public void update(Player player, Stage stage, int index, Batch batch) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
			EnemyHandler.bossOnField = null;
		} else {
			getImage().remove();
			
			meleeCoolDown = Math.max(0, meleeCoolDown - 1/30d);
			speedCoolDown = Math.max(0, speedCoolDown - 1d/60d);

			//double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
			double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
			
			anitick = Math.max(anitick - 0.2, 0);
			if(!speeding) {
				setCurrentSpeed(getSpeed());
			}
			
			if(angle < Math.PI/2 && angle > -Math.PI/2) {
				setImage(right);
				current = right;
			} else {
				setImage(left);
				current = left;
			}
			if(speeding) {
				if(angle < Math.PI/2 && angle > -Math.PI/2) {
					setImage(rights1);
					current = rights1;
				} else {
					setImage(lefts1);
					current = lefts1;
				}
				setCurrentSpeed(getSpeed() * 5);
			}
			setVelX(getVelX() + Math.signum(getCurrentSpeed() * Math.cos(angle) - getVelX()) * 
					Math.min(Math.abs(getAcceleration() * Math.cos(angle)), 
							Math.abs(getCurrentSpeed() * Math.cos(angle) - getVelX())));
			setVelY(getVelY() + Math.signum(getCurrentSpeed() * Math.sin(angle) - getVelY()) * 
					Math.min(Math.abs(getAcceleration() * Math.sin(angle)), 
							Math.abs(getCurrentSpeed() * Math.sin(angle) - getVelY())));
			
			if (speedCoolDown == 0) {
				speeding = true;
				speedCoolDown = 10;
			}
			if (speedTick == 0 && speeding) {
				speedTick = maxSpeedTick;
				speeding = false;
				anitick = 8;
			} else if(speeding) {
				speedTick = Math.max(0, speedTick - 1/60f);
			}
			
			if(player.getBox().overlaps(getBox())) {
				if (speeding) {
					if(angle < Math.PI/2 && angle > -Math.PI/2) {
						setImage(rights2);
						current = rights2;
					} else {
						setImage(lefts2);
						current = lefts2;
					}
					anitick = 12;
					player.takeDamage(getDamage()*3, 15, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
					speeding = false;
				} else if (meleeCoolDown == 0) {
					player.takeDamage(getDamage(), 2, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
					meleeCoolDown = maxMeleeCoolDown;
				}
			}
			if(anitick > 0) {
				if(angle < Math.PI/2 && angle > -Math.PI/2) {
					setImage(rights2);
					current = rights2;
				} else {
					setImage(lefts2);
					current = lefts2;
				}
			}
			
			if(anitick == 0) {
				setXPos(getXPos() + (float)getVelX());
				setYPos(getYPos() + (float)getVelY());
			}
			
			if(hazeTick > 5 && hazing) {
				batch.draw(haze, 0, 0);
				if(hazeTick == maxHazeTick - 1f/60f) {
					for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
						EnemyHandler.getEnemies(i).getEffects().add(new EffectHandler(4.9f, "inkystrength"));
					}
				}
			} else if(hazing && hazeTick == 0) {
				hazeTick = maxHazeTick;
				hazing = false;
			} else if(!hazing && hazeTick == 0) {
				hazeTick = maxHazeTick;
				hazing = true;
			}
			hazeTick = Math.max(hazeTick - 1f/60f, 0);

			if(!speeding) {
				if(getXPos() < 0) setXPos(0);
				if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
				if(getYPos() < 0) setYPos(0);
				if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);
			}
			
			//getImage().setPosition(getXPos(), getYPos());
			batch.draw(current, getXPos(), getYPos());

			//stage.addActor(getImage());
			tickTime();
		}
	}
}
