package com.mygdx.game;

import java.util.ArrayList;

//import com.badlogic.gdx.Gdx;

//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ItemHandler {
	//store all items
	static ArrayList<Item> allItems;
	static ArrayList<Item> collected;
	private static int cardNum = 0;
	
	public ItemHandler() {
		allItems = new ArrayList<Item>();
		collected = new ArrayList<Item>();
	}
	
	public void addItem(String name, Projectile proj) {
		Item projCard = new Item(name, proj);
		allItems.add(projCard);
	}
	public void addItem(String name, Melee mel) {
		Item melCard = new Item(name, mel);
		allItems.add(melCard);
	}
	public void addItem(String name, Misc misc) {
		Item miscCard = new Item(name, misc);
		allItems.add(miscCard);
	}
	
	public void testing() {
		collected = allItems;
	}
	
	public static Item checkHand(float direction) {
		if(cardNum == 0 && direction <= 0f) {
			cardNum = collected.size()-1;
		} else if(cardNum == collected.size()-1 && direction >= 0f) {
			cardNum = 0;
		} else if(direction >= 0f) {
			cardNum++;
		} else {
			cardNum--;
		}
		System.out.println(collected.get(cardNum).getName());
		return collected.get(cardNum);
	}
}
