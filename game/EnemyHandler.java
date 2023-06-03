package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import enemies.Enemy;
import enemies.GoldenGuardian;
import enemies.Healer;
import enemies.InkShade;
import enemies.RangedEnemy;
import enemies.Shielder;

import java.lang.Math;

public class EnemyHandler {
	
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static Enemy bossOnField;
	
	public static ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	public static Enemy getEnemies(int index) {
		return enemies.get(index);
	}
	public static void addEnemies(Enemy enemy) {
		enemies.add(enemy);
	}
	
	//add theme parameter for different enemy types
	public static Image spawn(Enemy enemy) {
		int wall = (int)(Math.random()*4);
		//if(MazeCombat.player.) maybe add more spawns when player clsoe to wall to discourage kiting
		Enemy temp = null;
		if(enemy.getType() == 'm') {
			if(wall == 0) {
				temp = new Enemy(0f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 1) {
				temp = new Enemy(800f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 2) {
				temp = new Enemy((float)Math.random()*800, 0f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else {
				temp = new Enemy((float)Math.random()*800, 480f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());			
			}
		} else if(enemy.getType() == 'r') {
			if(wall == 0) {
				temp = new RangedEnemy(0f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getProj(), enemy.getTexture(), enemy.getCD());
			} else if(wall == 1) {
				temp = new RangedEnemy(800f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getProj(), enemy.getTexture(), enemy.getCD());
			} else if(wall == 2) {
				temp = new RangedEnemy((float)Math.random()*800, 0f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getProj(), enemy.getTexture(), enemy.getCD());
			} else {
				temp = new RangedEnemy((float)Math.random()*800, 480f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getProj(), enemy.getTexture(), enemy.getCD());			
			}
		} else if(enemy.getType() == 's') {
			if(wall == 0) {
				temp = new Shielder(0f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 1) {
				temp = new Shielder(800f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 2) {
				temp = new Shielder((float)Math.random()*800, 0f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else {
				temp = new Shielder((float)Math.random()*800, 480f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());			
			}
		} else if(enemy.getType() == 'h') {
			if(wall == 0) {
				temp = new Healer(0f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 1) {
				temp = new Healer(800f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else if(wall == 2) {
				temp = new Healer((float)Math.random()*800, 0f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());
			} else {
				temp = new Healer((float)Math.random()*800, 480f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture());			
			}
		}
		enemies.add(temp);
		return temp.getImage();
	}
	
	public static Image spawnBoss(GoldenGuardian boss) {
		int wall = (int)(Math.random()*4);
		Enemy temp;
		if(wall == 0) {
			temp = new GoldenGuardian(0f, (float)Math.random()*480, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else if(wall == 1) {
			temp = new GoldenGuardian(800f, (float)Math.random()*480, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else if(wall == 2) {
			temp = new GoldenGuardian((float)Math.random()*800, 0f, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else {
			temp = new GoldenGuardian((float)Math.random()*800, 480f, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());			
		}
		bossOnField = temp;
		enemies.add(temp);
		return temp.getImage();
	}
	public static Image spawnBoss(InkShade boss) {
		int wall = (int)(Math.random()*4);
		Enemy temp;
		if(wall == 0) {
			temp = new InkShade(0f, (float)Math.random()*480, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else if(wall == 1) {
			temp = new InkShade(800f, (float)Math.random()*480, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else if(wall == 2) {
			temp = new InkShade((float)Math.random()*800, 0f, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());
		} else {
			temp = new InkShade((float)Math.random()*800, 480f, boss.getHealth(), boss.getSpeed(), boss.getDamage(), boss.getTexture());			
		}
		bossOnField = temp;
		enemies.add(temp);
		return temp.getImage();
	}
}
