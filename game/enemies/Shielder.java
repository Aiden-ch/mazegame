package com.mygdx.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.Player;

public class Shielder extends Enemy {
	private double meleeCoolDown = 0;
	private double maxMeleeCoolDown = 1;
	
	private Texture walkl;
	private Texture walkr;
	//for animation
	private Texture current;
	
	//for alternate behavior
	private int state;

	public Shielder(Texture txte, float health, float maxSpeed, double damage) {
		super(txte, health, maxSpeed, damage);
		walkl = new Texture("enemies/shieldol.png");
		walkr = new Texture("enemies/shieldor.png");
		setType('s');
	}

	public Shielder(float xPos, float yPos, double health, double maxSpeed, double damage, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		walkl = new Texture("enemies/shieldol.png");
		walkr = new Texture("enemies/shieldor.png");
		setType('s');
	}

	@Override
	public void update(Player player, Stage stage, int index, Batch batch) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
		} else {
			meleeCoolDown = Math.max(0, meleeCoolDown - 1/30d);

			double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
			double enan = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());
			if(EnemyHandler.getEnemies().size() > 0) {
				double mind = Math.sqrt(Math.pow(getXPos() - EnemyHandler.getEnemies(0).getXPos(), 2) + Math.pow(getYPos() - EnemyHandler.getEnemies(0).getYPos(), 2));
				for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
					Enemy temp = EnemyHandler.getEnemies(i);
					if(temp.getType() != 's') {
						enan = Math.atan2(temp.getYPos()-getYPos(), temp.getXPos()-getXPos());
						double endistance = Math.sqrt(Math.pow(getXPos() - temp.getXPos(), 2) + Math.pow(getYPos() - temp.getYPos(), 2));
						if(endistance < mind) {
							mind = endistance;
							enan = Math.atan2(temp.getYPos()-getYPos(), temp.getXPos()-getXPos());
						}
						state = 0;
					} else {
						state = 1;
					}
				}
			} else {
				state = 1;
			}
			
			if(angle < Math.PI/2 && angle > -Math.PI/2) {
				setImage(walkr);
				current = walkr;
			} else {
				setImage(walkl);
				current = walkl;
			}
			if (state == 1) {
				setVelX(getVelX() + Math.signum(getCurrentSpeed() * Math.cos(angle) - getVelX()) * 
						Math.min(Math.abs(getAcceleration() * Math.cos(angle)), 
								Math.abs(getCurrentSpeed() * Math.cos(angle) - getVelX())));
				setVelY(getVelY() + Math.signum(getCurrentSpeed() * Math.sin(angle) - getVelY()) * 
						Math.min(Math.abs(getAcceleration() * Math.sin(angle)), 
								Math.abs(getCurrentSpeed() * Math.sin(angle) - getVelY())));
			} else {
				setVelX(getVelX() + Math.signum(getCurrentSpeed() * Math.cos(enan) - getVelX()) * 
						Math.min(Math.abs(getAcceleration() * Math.cos(enan)), 
								Math.abs(getCurrentSpeed() * Math.cos(enan) - getVelX())));
				setVelY(getVelY() + Math.signum(getCurrentSpeed() * Math.sin(enan) - getVelY()) * 
						Math.min(Math.abs(getAcceleration() * Math.sin(enan)), 
								Math.abs(getCurrentSpeed() * Math.sin(enan) - getVelY())));
			} 

			setXPos(getXPos() + (float)getVelX());
			setYPos(getYPos() + (float)getVelY());

			if(player.getBox().overlaps(getBox())) {
				if (meleeCoolDown == 0) {
					player.takeDamage(getDamage(), 15, 90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-player.getYPos(), getXPos()-player.getImage().getWidth()/2-player.getXPos())));
					meleeCoolDown = maxMeleeCoolDown;
				}
			}
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				if(getBox().overlaps(EnemyHandler.getEnemies(i).getBox()) && EnemyHandler.getEnemies(i).getBox() != getBox() && EnemyHandler.getEnemies(i).getType() == 's') {
					//enemy collison
					setKnockback(3f);
					knockback(-90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-EnemyHandler.getEnemies(i).getYPos(), getXPos()-player.getImage().getWidth()/2-EnemyHandler.getEnemies(i).getXPos())));
					setKnockback(5);
				}
			}

			if(getXPos() < 0) setXPos(0);
			if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
			if(getYPos() < 0) setYPos(0);
			if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);
			
			//getImage().setPosition(getXPos(), getYPos());
			batch.draw(current, getXPos(), getYPos());

			//stage.addActor(getImage());
			tickTime();
		}
	}
}