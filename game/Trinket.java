package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Trinket {
	//inventory things that give items to use in combat
	private Image trkImg;
	private Item item;
	
	public Trinket(Texture txte, Item item) {
		trkImg = new Image(txte);
		this.item = item;
	}
	
	public Image getImage() {
		return this.trkImg;
	}
	public Item getItem() {
		return this.item;
	}
	
	public void Perk() {
		//extender
	}
	public void Passive() {
		//extender
	}
	public void resupply() {
		//extender
		boolean inHand = false;
		for (int i=0; i<AttackHandler.getHand().size(); i++) {
			if(AttackHandler.getHand().get(i).getName().equals(getItem().getName())) {
				inHand = true;
			}
		}
		if(!inHand) {
			AttackHandler.getHand().add(getItem());
		}
	}
}
