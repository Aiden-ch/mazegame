package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import java.lang.Math;
//import com.badlogic.gdx.Gdx;

//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardHandler {
	//store all items
	static ArrayList<Card> allItems;
	
	//in combat
	//currently held card
	private static Card held;
	//all cards in hand
	private static ArrayList<Card> hand = new ArrayList<Card>();
	//to get the held card in hand
	private static int cardNum;
	
	public CardHandler() {
		cardNum = 0;
		allItems = new ArrayList<Card>();
	}
	
	//Initialize all cards
	public void addCard(String name, RangedItem rait, Image img) {
		Card raitCard = new Card(name, rait, img);
		allItems.add(raitCard);
	}
	public void addCard(String name, Melee mel, Image img) {
		Card melCard = new Card(name, mel, img);
		allItems.add(melCard);
	}
	public void addCard(String name, Misc misc, Image img) {
		Card miscCard = new Card(name, misc, img);
		allItems.add(miscCard);
	}
	
	public static int chooseCard(Card card) {
		//System.out.println("chose");
		held = card;
		for(int i=0; i<hand.size(); i++) {
			if(hand.get(i).getName().equals(card.getName())) {
				return i;
			}
		}
		return -1;
	}
	public static void consumeCard(Card card) {
		for(int i=0; i<hand.size(); i++) {
			//System.out.println(hand.get(i).getName());
			if(hand.get(i).getName().contains(card.getName())) {
				System.out.println("removed");
				hand.get(i).getImage().remove();
				hand.remove(i);
				held = null;
			}
		}
	}
	
	public static Card checkHand(float direction) {
		if(hand.size() > 0) {
			if(cardNum == 0 && direction <= 0f) {
				cardNum = hand.size()-1;
			} else if(cardNum >= hand.size()-1 && direction >= 0f) {
				cardNum = 0;
			} else if(direction >= 0f) {
				cardNum++;
			} else {
				cardNum--;
			}
			//System.out.println(cardNum);
			UIHandler.swapped();
			return hand.get(cardNum);
		}
		return null;
	}
	
	public static ArrayList<Card> getALL() {
		return allItems;
	}
	
	public static Card getHeld() {
		return held;
	}
	public static ArrayList<Card> getHand() {
		return hand;
	}
	public static int getIndex() {
		return cardNum;
	}
}
