package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.trinkets.*;

public class InventoryHandler {
	static ArrayList<Trinket> activeTrinkets = new ArrayList<Trinket>();
	static ArrayList<Trinket> trinkets = new ArrayList<Trinket>();
	
	public BitmapFont font;
	public SpriteBatch batch;
	
	private static boolean start;
	
	public InventoryHandler() {
		;
	}
	
	public static boolean getStart() {
		return start;
	}
	public static void setStart(boolean temp) {
		start = temp;
	}
	
	//manipulating inventory
	public void open() {
		
	}
	public void close() {
		
	}
	
	public void equipTrinkets(Trinket temp) {
		activeTrinkets.add(temp);
	}
	
	public static void testing() {
		Trinket BombLauncher = new BombLauncher(ItemHandler.collected.get(0), 40f);
		Trinket RulerBlade = new RulerBlade(ItemHandler.collected.get(2), 20f);
		Trinket BasicBlade = new BasicBlade(ItemHandler.collected.get(1), 40f);
		activeTrinkets.add(BombLauncher);
		activeTrinkets.add(RulerBlade);
		activeTrinkets.add(BasicBlade);
	}
	
	public static void arrangeByPrecedence() {
		int index = 1;
		for(int i=0; i<activeTrinkets.size(); i++) {
			if(activeTrinkets.get(i).getPrecedence() == index) {
				Trinket temp = activeTrinkets.remove(index-1);
				i--;
				activeTrinkets.add(index-1, activeTrinkets.get(i));
				activeTrinkets.add(temp);
				index++;
			}
		}
	}
	
	//trinket calls all handled here
	public static void activate() {
		if(start) {
			//arrangeByPrecedence();
		}
		//System.out.println(activeTrinkets.size());
		for(int i=0; i<activeTrinkets.size(); i++) {
			//add ifs and what not for different trinket abilities
			activeTrinkets.get(i).resupply();
			//System.out.println(activeTrinkets.get(i).getItem().getName());
		}
		if(start) {
			//arrangeByPrecedence();
			start = false;
		}
	}
}
