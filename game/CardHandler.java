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
	private static int cardNum = 0;
	
	public CardHandler() {
		allItems = new ArrayList<Card>();
	}
	
	//Initialize all cards
	public void addCard(String name, RangedItem rait, int uses, Image img) {
		Card raitCard = new Card(name, rait, uses, img);
		allItems.add(raitCard);
	}
	public void addCard(String name, Melee mel, int uses, Image img) {
		Card melCard = new Card(name, mel, uses, img);
		allItems.add(melCard);
	}
	public void addCard(String name, Misc misc, int uses, Image img) {
		Card miscCard = new Card(name, misc, uses, img);
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
	public static void consumeCard(int uses, int index) {
		if(uses <= 0) {		
			hand.remove(index);
			held = null;
			if(held == null && hand.size() >= 1) {
				System.out.println("new");
				held = CardHandler.checkHand(1f);
			}
		}
	}
	
	public static Card checkHand(float direction) {
		if(hand.size() > 0) {
			if(cardNum == 0 && direction <= 0f) {
				cardNum = hand.size()-1;
			} else if(cardNum == hand.size()-1 && direction >= 0f) {
				cardNum = 0;
			} else if(direction >= 0f) {
				cardNum++;
			} else {
				cardNum--;
			}

			return hand.get(cardNum);
		}
		return null;
	}
	
	public static Card getHeld() {
		return held;
	}
	public static ArrayList<Card> getHand() {
		return hand;
	}
}
