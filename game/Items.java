package com.mygdx.game;

import java.util.ArrayList;

//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Items {
	//store all items
	static ArrayList<Item> allItems;
	ArrayList<Item> collected;
	
	public Items() {
		allItems = new ArrayList<Item>();
		collected = new ArrayList<Item>();
	}
	
	public void addItem(String name, Projectile proj) {
		Item bombCard = new Item(name, proj);
		allItems.add(bombCard);
	}
	public void addItem(String name, Melee mel) {
		Item bombCard = new Item(name, mel);
		allItems.add(bombCard);
	}
	
	public void testing() {
		collected = allItems;
	}
}
