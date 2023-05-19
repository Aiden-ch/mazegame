package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.miscItems.*;

public class AttackHandler {

	//active is for projectiles and combat displayed things and stuff
	private static ArrayList<Item> active = new ArrayList<Item>();
	static Item inHand;
	static ArrayList<Item> hand = new ArrayList<Item>();
	//add stack for perks passives etc
	
	public AttackHandler() {
		;
	}
	
	public static void chooseCard(Item item) {
		//System.out.println("chose");
		inHand = item;
	}
	public static void consumeCard(int uses, int index) {
		System.out.println(hand.size());
		if(uses < 1) {
			//hand.remove(index); //This is a problem
			if(hand.size() > 1) {
				System.out.println("next");
				hand.remove(index); //This is a problem
				
				//System.out.println(inHand);
			} else if(hand.size() == 1) {
				hand.remove(0);
				inHand = null;
				//System.out.println(hand.size());
			}
		}
	}
	
	public static float shoot(Player player, Item item, float buffer) {
		//System.out.println(buffer);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			Image projImage = new Image(item.getProj().getTexture());
			projImage.setOrigin(item.getProj().getTexture().getWidth()/2.0f, 0);
			Projectile proj = new Projectile((float)player.getXPos(), (float)player.getYPos(), item.getProj().getDamage(), 
					Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos(), Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), item.getProj().getSpeed(), item.getProj().getPierce(), projImage);
			projImage.setRotation((float) (-90f + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180f / (Math.PI)));
			proj.setImage(projImage);
			Item temp = new Item(item.getName(), proj, item.getImage());
			item.used();
			active.add(temp);

			return 2.2f;
		}
		return buffer;
	}
	
	//TODO: rewrite
	public static float melee(Player player, Item item, float buffer) {
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			
			Image meleeImage = new Image(item.getMel().getTexture());
			meleeImage.setOrigin((float)item.getMel().getTexture().getWidth()/2, 0);
			Float angle = -90f + 180f/(float)Math.PI * (float)(Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos()) - item.getMel().getRadius() / 2);
			meleeImage.setRotation(angle);
			//System.out.println(item.getMel().getKnockback());
			Melee mel = new Melee(player.getXPos(), player.getYPos(), item.getMel().getDamage(), item.getMel().getLength(), 
					item.getMel().getRadius(), item.getMel().getSpeed(), item.getMel().getKnockback(), angle, meleeImage, item.getMel().getTexture());
			Item temp = new Item(item.getName(), mel, item.getImage());
			active.add(temp);

			return (float)mel.getSpeed();
			
		}
		return buffer;
	}
	
	public static float bolt(Player player, Item item, float buffer) {
		return buffer;
	}
	
	public static float use(Player player, Item item, float buffer) {
		//
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			
			Misc temp = item.getMisc();
			Image newImage;
			switch (item.getMisc().getType()) {
			case "speedpotion":
				newImage = new Image(item.getMisc().getTexture());
				newImage.setOrigin((float)item.getMisc().getTexture().getWidth()/2f, (float)item.getMisc().getTexture().getHeight()/2f);
				temp = new SpeedPot(item.getMisc().getTexture(), newImage);

				break;
			case "healthpotion":
				newImage = new Image(item.getMisc().getTexture());
				newImage.setOrigin((float)item.getMisc().getTexture().getWidth()/2f, (float)item.getMisc().getTexture().getHeight()/2f);
				temp = new HealthPot(item.getMisc().getTexture(), newImage);
				break;

			}
			Item tempItem = new Item(item.getName(), temp, item.getImage());
			active.add(tempItem);
			return 2.2f;

		}
		return buffer;
	}
	
	public static ArrayList<Item> getActive() {
		return active;
	}
	public static ArrayList<Item> getHand() {
		return hand;
	}
	public static Item getInHand() {
		return inHand;
	}
	public static void setInHand(Item temp) {
		inHand = temp;
	}
}
