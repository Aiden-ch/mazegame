package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import java.lang.Math;
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
	
	public void addItem(String name, Projectile proj, int uses, Image img) {
		Item projCard = new Item(name, proj, uses, img);
		allItems.add(projCard);
	}
	public void addItem(String name, Melee mel, int uses, Image img) {
		Item melCard = new Item(name, mel, uses, img);
		allItems.add(melCard);
	}
	public void addItem(String name, Misc misc, int uses, Image img) {
		Item miscCard = new Item(name, misc, uses, img);
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
		//System.out.println(collected.get(cardNum));
		return collected.get(cardNum);
	}
	
}
