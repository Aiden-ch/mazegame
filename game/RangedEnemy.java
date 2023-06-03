package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;
import java.util.ArrayList;

public class RangedEnemy extends Enemy {
	
	double maxCoolDown = 3;
	double coolDown = 1;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public RangedEnemy(Texture txte, float health, float maxSpeed, double damage, Projectile proj, double mCoolDown) {
		super(txte, health, maxSpeed, damage);
		super.setProj(proj);
		setType('r');
		this.maxCoolDown = mCoolDown;
	}

	public RangedEnemy(float xPos, float yPos, double health, double maxSpeed, double damage, Projectile proj, Texture txte, double mCoolDown) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		super.setProj(proj);
		setType('r');
		this.maxCoolDown = mCoolDown;
	}

	public void update(Player player, Stage stage, int index, Batch batch) {
		if(getHealth() <= 0) {
			getImage().remove();
			EnemyHandler.getEnemies().remove(index);
			index--;
			for(int i=0; i<projectiles.size(); i++) {
				projectiles.get(i).getImage().remove();
			}
			projectiles = new ArrayList<Projectile>();
		} else {
			double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
			double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());

			if (distance < 220) {
				setVelX(Math.max(-getSpeed(), Math.min(getSpeed(), getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (250 - distance)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (250 - distance)) - getVelX())))));
				setVelY(Math.max(-getSpeed(), Math.min(getSpeed(), getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (250 - distance)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (250 - distance)) - getVelY())))));
			} else if (distance < 280) {
				//       velX = 0;
				setVelX(Math.max(-getSpeed(), Math.min(getSpeed(), getVelX() - Math.signum(getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(getVelX())))));
				setVelY(Math.max(-getSpeed(), Math.min(getSpeed(), getVelY() - Math.signum(getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(getVelY())))));
			} else {
				setVelX(Math.max(-getSpeed(), Math.min(getSpeed(), getVelX() + Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 250)) - getVelX()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 250)) - getVelX())))));
				setVelY(Math.max(-getSpeed(), Math.min(getSpeed(), getVelY() + Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 250)) - getVelY()) * 
						Math.min(getAcceleration(), 
								Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 250)) - getVelY())))));
			}
			
			setPrevX(getXPos());
			setPrevY(getYPos());
			
			setXPos((float)getXPos() + (float)getVelX());
			setYPos((float)getYPos() + (float)getVelY());
			
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				if(getBox().overlaps(EnemyHandler.getEnemies(i).getBox()) && EnemyHandler.getEnemies(i).getBox() != getBox()) {
					//enemy collison
					setKnockback(0.5f);
					knockback(-90f + 180f/(float)Math.PI * (float)(Math.atan2(getYPos()-EnemyHandler.getEnemies(i).getYPos(), getXPos()-player.getImage().getWidth()/2-EnemyHandler.getEnemies(i).getXPos())));
					setKnockback(5);
				}
			}

			shoot(player, stage);

			if(getXPos() < 0) setXPos(0);
			if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
			if(getYPos() < 0) setYPos(0);
			if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);

			//getImage().setPosition(getXPos(), getYPos());

			//stage.addActor(getImage());
			batch.draw(getTexture(), getXPos(), getYPos());
		}
		tickTime();
	}
	
	public void rendoor(Player player, Stage stage) {
		for(int i=0; i<projectiles.size(); i++) {
			//System.out.println("updating");
			
			boolean destroy = false;
			Projectile temp = projectiles.get(i);

			//hit detection
			if(player.getBox().overlaps(temp.getBox())) {
				player.takeDamage(temp.getDamage(), (float)temp.getKnockback(), 180f/(float)Math.PI * (float)(Math.atan2(player.getYPos()-temp.getYPos(), player.getImage().getWidth()/2+player.getXPos()-temp.getXPos())));
				destroy = true;
			}

			//out-of-bounds detection
			if(temp.getXPos()<0-64-10 || temp.getYPos()<0-64-10 || temp.getXPos()>Gdx.graphics.getWidth()+64+10 
					|| temp.getYPos()>Gdx.graphics.getHeight()+64+10 || destroy) {
				temp.getImage().remove();
				projectiles.remove(i);
				i--;
			} else {
				projectiles.get(i).move();
				stage.addActor(projectiles.get(i).getImage());
			}
		}
	}
	
	public double getCD() {
		return this.maxCoolDown;
	}
	
	public void shoot(Player player, Stage stage) {
		if(coolDown == 0) {
			double angle = Math.atan2(player.getYPos()-this.getYPos(), 
					player.getXPos()-this.getImage().getWidth()/2-this.getXPos());
			double velX = Math.cos(angle);
			double velY = Math.sin(angle);
			Projectile proj = new Projectile(getProj().getTexture(), (float)this.getXPos(), (float)getYPos(),  
					velX, velY, getProj().getSpeed(), getProj().getDamage(), getProj().getPierce(), getProj().getKnockback());
			Image projImage = new Image(getProj().getTexture());
			projImage.setOrigin(projImage.getWidth()/2.0f, 0);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			projectiles.add(proj);
			
			//System.out.println("pew");
			
			coolDown = maxCoolDown;
		}
		
		coolDown = Math.max(0, coolDown - 1d/60);
	}
}