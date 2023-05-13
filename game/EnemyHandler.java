package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.lang.Math;

public class EnemyHandler {
	
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
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
		Enemy temp;
		Image tempImg = new Image(enemy.getTexture());
		if(wall == 0) {
			temp = new Enemy(0f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture(), tempImg);
		} else if(wall == 1) {
			temp = new Enemy(800f, (float)Math.random()*480, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture(), tempImg);
		} else if(wall == 2) {
			temp = new Enemy((float)Math.random()*800, 0f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture(), tempImg);
		} else {
			temp = new Enemy((float)Math.random()*800, 480f, enemy.getHealth(), enemy.getSpeed(), enemy.getDamage(), enemy.getTexture(), tempImg);			
		}
		enemies.add(temp);
		return temp.getImage();
	}
}
