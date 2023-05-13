package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AttackHandler {

	private static ArrayList<Item> cards = new ArrayList<Item>();
	
	public AttackHandler() {
		;
	}
	
	public static float shoot(Player player, Item item, float buffer) {
		//System.out.println(buffer);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			Image projImage = new Image(item.getProj().getTexture());
//			projImage.setOrigin(item.getProj().txte.getWidth()/2.0f, item.getProj().txte.getHeight()/2.0f);
			projImage.setOrigin(item.getProj().getTexture().getWidth()/2.0f, 0);
			Projectile proj = new Projectile((float)player.getXPos(), (float)player.getYPos(), item.getProj().getDamage(), 
					Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos(), Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), item.getProj().getSpeed(), projImage);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			Item temp = new Item(item.getName(), proj);
			cards.add(temp);
			
			//System.out.println(Gdx.input.getX()-player.getXPos());
			
			/*
			System.out.println("player (x,y): ");
			System.out.print(player.getXPos());
			System.out.print(",");
			System.out.println(player.getYPos());
			System.out.println("mouse (x,y): ");
			System.out.println(Gdx.input.getX());
			System.out.print(",");
			System.out.print(Gdx.input.getY());
			*/
			
			return 2.2f;
		}
		return buffer;
	}
	
	//TODO: rewrite
	public static float melee(Player player, Item item, float buffer) {
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			Image meleeImage = new Image(item.getMel().getTexture());
			meleeImage.setOrigin((float)item.getMel().getTexture().getWidth()/2, 0);
			Float angle = 90f - (float)(180 / Math.PI * item.getMel().getRadius()) + (float)Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos()) * 180f / (float)(Math.PI);
			meleeImage.setRotation(angle);
			//meleeImage.setRotation((float) (90 + (float)(180 / Math.PI * item.getMel().getRadius()) + Math.atan2((Gdx.input.getX()-player.getXPos()) * 180 / (Math.PI), Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos())));
			Melee mel = new Melee(player.getXPos(), player.getYPos(), item.getMel().getDamage(), item.getMel().getLength(), 
					item.getMel().getRadius(), item.getMel().getSpeed(), item.getMel().getKnockback(), angle, meleeImage, item.getMel().getTexture());
			Item temp = new Item(item.getName(), mel);
			cards.add(temp);
			
			/*
			System.out.println("player (x,y): ");
			System.out.print(player.getXPos());
			System.out.print(",");
			System.out.print(player.getYPos());
			System.out.println("mouse (x,y): ");
			System.out.print(Gdx.input.getX());
			System.out.print(",");
			System.out.print(Gdx.input.getY());
			*/
			
			return 4f/(float)mel.getSpeed() + 0.2f;
		}
		return buffer;
	}
	
	public static float bolt(Player player, Item item, float buffer) {
		return buffer;
	}
	
	public static ArrayList<Item> getCards() {
		return cards;
	}
}
