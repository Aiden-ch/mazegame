package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AttackHandler {

	private static ArrayList<Projectile> projs = new ArrayList<Projectile>();
	private static ArrayList<Item> cards = new ArrayList<Item>();
	
	public AttackHandler() {
		;
	}
	
	public static float shoot(Player player, Item item, float buffer) {
		//System.out.println(buffer);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			Image projImage = new Image(item.getProj().txte);
			projImage.setOrigin(item.getProj().txte.getWidth()/2, item.getProj().txte.getHeight()/2);
			Projectile proj = new Projectile(player.getXPos(), player.getYPos(), 3, Gdx.input.getX()-player.getXPos(), 480-Gdx.input.getY()-player.getYPos(), item.getProj().getSpeed(), projImage);
			projImage.setRotation((float) (-90 + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			proj.setImage(projImage);
			Item temp = new Item(item.getName(), proj);
			cards.add(temp);
			/*
			System.out.println("player (x,y): ");
			System.out.print(player.getXPos());
			System.out.print(",");
			System.out.print(player.getYPos());
			
			System.out.println("proj (x,y): ");
			System.out.print(proj.getXPos());
			System.out.print(",");
			System.out.print(proj.getYPos());
			*/
			return 2.2f;
		}
		return buffer;
	}
	
	public static float melee(Player player, Item item, float buffer) {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			Image meleeImage = new Image(item.getMel().getTexture());
			meleeImage.setOrigin(item.getMel().getTexture().getWidth()/2, 0);
			Melee mel = new Melee(item.getMel().getDamage(), item.getMel().getLength(), 
					item.getMel().getRadius(), item.getMel().getSpeed(), item.getMel().getImage(), item.getMel().getTexture(), 
					(float) (Math.atan2(Gdx.input.getY(), Gdx.input.getX()) * 180 / (Math.PI)));
			Item temp = new Item(item.getName(), mel);
			cards.add(temp);
			return (float)mel.getSpeed() + 0.2f;
		}
		return buffer;
	}
	
	public static float bolt(Player player, Item item, float buffer) {
		return buffer;
	}
	
	public static ArrayList<Projectile> getProjs() {
		return projs;
	}
	public static ArrayList<Item> getCards() {
		return cards;
	}
}
