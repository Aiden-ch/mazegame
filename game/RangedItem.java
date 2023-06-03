package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.projItems.Bomb;
import com.mygdx.game.projItems.Fire;

public class RangedItem {	
	private double shootSpeed; //fire rate
	private int maxShots; //how many projectiles fired at a time
	private double maxSpread; //max angle range of shots in degrees
	private double maxShotTime; //time between each shot at once 
	private double shotTick;
	private int magazine;
	private int magsize; //maximum ammo can be loaded
	private double maxReload; //time takes to add new ammo
	private double reloadTick;
	private double tick;
	
	private int currentShots = 0;
	private boolean volleying = false; //to see if there should be more shots being fired
	
	private boolean shooting = false;
	
	private Projectile projectile;
	private ArrayList<Projectile> projectiles;
	
	public RangedItem(Projectile proj, double shootSpeed, int shots, double spread, double maxShotTime, int magsize, double maxReload) {
		this.projectile = proj;
		this.projectiles = new ArrayList<Projectile>();
		this.shootSpeed = shootSpeed;
		this.maxShots = shots;
		this.magsize = magsize;
		magazine = magsize;
		this.maxReload = maxReload;
		this.tick = 0;
		this.maxShotTime = maxShotTime;
		this.shotTick = 0;
		this.maxSpread = spread;
		volleying = false;
	}
	
	public double getReloadTick() {
		return this.reloadTick;
	}
	public double getMaxReload() {
		return this.maxReload;
	}
	public double getShootSpeed() {
		return this.shootSpeed;
	}
	public int getMag() {
		return this.magazine;
	}
	public void setMag(int num) {
		this.magazine = num;
		this.magsize = num;
	}
	public int getMaxShots() {
		return this.maxShots;
	}
	public void setMaxShots(int num) {
		this.maxShots = num;
	}
	public void setAccuracy(double spread) {
		this.maxSpread = spread;
	}
	public void setShootSpeed(double speed) {
		this.shootSpeed = speed;
	}
	public void setMaxReload(double reload) {
		this.maxReload = reload;
	}
	
	public void rendoor(Player player, Stage stage, Batch batch) {
		if(shooting) {
			for(int i=0; i<projectiles.size(); i++) {
				boolean destroy = false;
				Projectile temp = projectiles.get(i);

				//hit detection
				for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
					Enemy tempem = EnemyHandler.getEnemies(j);
					float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getImage().getWidth()/2-player.getXPos()));

					if(projectile.getType().equals("s")) {
						if(temp.getRange() <= 0) {
							destroy = true;
						}
					}
					
					if(temp.getBox().overlaps(tempem.getBox()) && !destroy) {
						tempem.takeDamage((float)temp.getDamage(), (float)temp.getKnockback(), enan);
						temp.hit();
						if(temp.getPierce() > 0) {
							temp.tickPierce();
						} else {
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
					temp.move();
					//batch.draw(temp.getTexture(), temp.getXPos(), temp.getYPos());
					stage.addActor(projectiles.get(i).getImage());
				}
			}
		}
	}
	
	public void reload() {
		tick = Math.max(tick - 0.2f, 0);
		reloadTick = Math.max(reloadTick - 0.2f, 0);
	}
	
	public boolean update(Player player, Stage stage) {
		tick = Math.max(tick - 0.2f, 0);
		reloadTick = Math.max(reloadTick - 0.2f, 0);
		if((tick == 0 && magazine/maxShots > 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) && !volleying) {
			volleying = true;
		}
		if(volleying && currentShots < maxShots && shotTick == 0) {
			currentShots++;
			double spread = (Math.random()*maxSpread - maxSpread/2d);
			double angle = spread*Math.PI/180d + Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), 
					Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos());
			double velX = Math.cos(angle);
			double velY = Math.sin(angle);
			Projectile proj = new Projectile(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
					velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback());
			switch (projectile.getType()) {
				case "b":
					proj = new Bomb(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
							velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback(), projectile.getRadius());
					break;
				case "s":
					proj = new Fire(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
					velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback(), projectile.getRange());
					break;
			}
			Image projImage = new Image(this.projectile.getTexture());
			projImage.setOrigin(projImage.getWidth()/2.0f, 0);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			projectiles.add(proj);
			magazine--;
			tick = shootSpeed;
			shotTick = maxShotTime;
			shooting = true;
			return true;
		} else if(currentShots >= maxShots) {
			volleying = false;
			currentShots = 0;
		}
		shotTick = Math.max(0, shotTick - 0.2);
		
		if(projectiles.size() == 0) {
			shooting = false;
		}
		if(magazine < magsize && reloadTick == 0 && !volleying) {
			magazine+=maxShots;
			reloadTick = maxReload;
		}
		
		return false; //use to tick down uses on card
	}
	
	public void update(Player player) {
		tick = Math.max(tick - 0.2f, 0);
		reloadTick = Math.max(reloadTick - 0.2f, 0);
		if(volleying && currentShots < maxShots && shotTick == 0) {
			currentShots++;
			double spread = (Math.random()*maxSpread - maxSpread/2d);
			double angle = spread*Math.PI/180d + Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), 
					Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos());
			double velX = Math.cos(angle);
			double velY = Math.sin(angle);
			Projectile proj = new Projectile(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
					velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback());
			switch (projectile.getType()) {
				case "b":
					proj = new Bomb(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
							velX, velY, projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback(), projectile.getRadius());
					break;
			}
			Image projImage = new Image(this.projectile.getTexture());
			projImage.setOrigin(projImage.getWidth()/2.0f, 0);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			projectiles.add(proj);
			magazine--;
			tick = shootSpeed;
			shotTick = maxShotTime;
			shooting = true;
		} else if(currentShots >= maxShots) {
			volleying = false;
			currentShots = 0;
		}
		shotTick = Math.max(0, shotTick - 0.2);
		
		if(projectiles.size() == 0) {
			shooting = false;
		}
		if(magazine < magsize && reloadTick == 0) {
			magazine+=maxShots;
			reloadTick = maxReload;
		}
	}
	
	public void setProj(Projectile proj) {
		this.projectile = proj;
	}
	public Projectile getProj() {
		return this.projectile;
	}
	public ArrayList<Projectile> getProjs() {
		return this.projectiles;
	}
}
