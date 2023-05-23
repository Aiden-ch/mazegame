package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RangedItem {	
	private double shootSpeed; //fire rate
	private int maxShots; //how many projectiles fired at a time
	private int magazine;
	private int magsize;
	private double maxReload; //time takes to add new ammo
	private double reloadTick;
	private double tick;
	
	private boolean shooting = false;
	
	private Projectile projectile;
	private ArrayList<Projectile> projectiles;
	
	public RangedItem(Projectile proj, double shootSpeed, int shots, int magsize, double maxReload) {
		this.projectile = proj;
		this.projectiles = new ArrayList<Projectile>();
		this.shootSpeed = shootSpeed;
		this.maxShots = shots;
		this.magsize = magsize;
		magazine = magsize;
		this.maxReload = maxReload;
		this.tick = 0;
	}
	
	public void rendoor(Player player, Stage stage) {
		if(shooting) {
			for(int i=0; i<projectiles.size(); i++) {
				boolean destroy = false;
				Projectile temp = projectiles.get(i);

				//hit detection
				for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
					Enemy tempem = EnemyHandler.getEnemies(i);
					float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getImage().getWidth()/2-player.getXPos()));

					if(temp.getBox().overlaps(EnemyHandler.getEnemies().get(j).getBox())) {
						EnemyHandler.getEnemies().get(j).takeDamage((float)temp.getDamage(), (float)temp.getKnockback(), enan);
						destroy = true;
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
		if(tick == 0 && magazine/maxShots > 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			for(int i=0; i<maxShots; i++) {
				Projectile proj = new Projectile(projectile.getTexture(), (float)player.getXPos(), (float)player.getYPos(),  
						Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos(), Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), projectile.getSpeed(), projectile.getDamage(), projectile.getPierce(), projectile.getKnockback());
				Image projImage = new Image(this.projectile.getTexture());
				projImage.setOrigin(projImage.getWidth()/2.0f, 0);
				projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
				proj.setImage(projImage);
				projectiles.add(proj);
				magsize--;
			}
			tick = shootSpeed;
			shooting = true;
			return true;
		}
		
		if(projectiles.size() == 0) {
			shooting = false;
		}
		if(magazine < magsize && reloadTick == maxReload) {
			magazine++;
			reloadTick = 0;
		}
		
		return false; //use to tick down uses on card
	}
}
