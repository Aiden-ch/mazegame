package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.lang.Math;

public class RangedEnemy extends Enemy {
	
	double maxCoolDown = 5;
	double coolDown = maxCoolDown;
	private ArrayList<Projectile> projectiles;
	
	public RangedEnemy(Texture txte, float health, float maxSpeed, double damage, Projectile proj) {
		super(txte, health, maxSpeed, damage);
		setProj(proj);
		setType('r');
	}

	public RangedEnemy(float xPos, float yPos, double health, double maxSpeed, double damage, Projectile proj, Texture txte) {
		super(xPos, yPos, health, maxSpeed, damage, txte);
		setProj(proj);
	}

	public void update(Player player, Stage stage, int index) {
		double distance = Math.sqrt(Math.pow(getXPos() - player.getXPos(), 2) + Math.pow(getYPos() - player.getYPos(), 2));
		double angle = Math.atan2(player.getYPos()-getYPos(), player.getXPos()-getXPos());

		if (distance < 140) {
			setVelX(getVelX() + Math.signum(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.cos(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelX())));
			setVelY(getVelY() + Math.signum(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.sin(angle + Math.PI) * Math.min(getSpeed(), 0.6 * (125 - distance)) - getVelY())));
		} else if (distance < 170) {
			//       velX = 0;
			setVelX(getVelX() - Math.signum(getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(getVelX())));
			setVelY(getVelY() - Math.signum(getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(getVelY())));
		} else {
			setVelX(getVelX() - Math.signum(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelX()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.cos(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelX())));
			setVelY(getVelY() - Math.signum(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelY()) * 
					Math.min(getAcceleration(), 
							Math.abs(Math.sin(angle) * Math.min(getSpeed(), 0.6 * (distance - 125)) - getVelY())));
		}

		setXPos((float)getXPos() + (float)getVelX());
		setYPos((float)getYPos() + (float)getVelY());
		
		if(getXPos() < 0) setXPos(0);
		if(getXPos() > Gdx.graphics.getWidth() - 64) setXPos(Gdx.graphics.getWidth() - 64);
		if(getYPos() < 0) setYPos(0);
		if(getYPos() > Gdx.graphics.getHeight() - 64) setYPos(Gdx.graphics.getHeight() - 64);
		
		getImage().setPosition(getXPos(), getYPos());
		
		stage.addActor(getImage());
		tickTime();
	}
	
	public void rendoor(Player player, Stage stage) {
		if(shooting) {
			for(int i=0; i<projectiles.size(); i++) {
				boolean destroy = false;
				Projectile temp = projectiles.get(i);

				//hit detection
				if(player.getBox().overlaps(temp.getBox())) {
					player.takeDamage(damage, (float)enKnockback, 180f/(float)Math.PI * (float)(Math.atan2(player.getYPos()-temp.getYPos(), player.getImage().getWidth()/2+player.getXPos()-temp.getXPos())));
				}
				
				for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
					if (tempem != this) {
						Enemy tempem = EnemyHandler.getEnemies(j);
						float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-temp.getYPos(), tempem.getXPos()-temp.getImage().getWidth()/2-temp.getXPos()));

						if(temp.getBox().overlaps(tempem.getBox())) {
							tempem.takeDamage((float)temp.getDamage(), (float)temp.getKnockback(), enan);
							destroy = true;
						}
					}
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
	}
	
	public boolean update(Player player, Stage stage) {
		tick = Math.max(tick - 0.2f, 0);
		reloadTick = Math.max(reloadTick - 0.2f, 0);
		if(coolDown == 0) {
			double angle = spread*Math.PI/180d + Math.atan2(Gdx.graphics.getHeight()-getYPos()-player.getYPos(), 
					player.getXPos()-getImage().getWidth()/2-getXPos());
			double velX = Math.cos(angle);
			double velY = Math.sin(angle);
			Projectile proj = new Projectile(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
					velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback());
			Image projImage = new Image(this.projectile.getTexture());
			projImage.setOrigin(projImage.getWidth()/2.0f, 0);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			projectiles.add(proj);
			
			coolDown = maxCoolDown;
		}
		
		coolDown = Math.max(0, coolDown - 1/60);
	}
}
