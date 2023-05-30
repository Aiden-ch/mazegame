package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Trinket {
	//inventory things that give items to use in combat
	private Image trkImg;
	private Card card;
	
	private int perkPrecedence;
	private float refreshTime;
	private float buffer = 0.0f;
	
	private String name;
	
	public Trinket(Texture txte, Card card, float refreshTime, String name) {
		trkImg = new Image(txte);
		this.card = card;
		this.refreshTime = refreshTime;
		this.buffer = refreshTime;
		this.name = name;
	}
	public Trinket(Card card, float refreshTime, String anem) {
		this.card = card;
		this.refreshTime = refreshTime;
		this.buffer = refreshTime;
		this.name = anem;
	}
	
	public Image getImage() {
		return this.trkImg;
	}
	public Card getCard() {
		return this.card;
	}
	public String getName() {
		return this.name;
	}
	public void setPrecedence(int num) {
		this.perkPrecedence = num;
	}
	public int getPrecedence() {
		return this.perkPrecedence;
	}
	public float getBuffer() {
		return this.buffer;
	}
	public void setBuffer(float buffer) {
		this.buffer = buffer;
	}
	public void tick() {
		this.buffer += 0.2f; //change value to change speed of refreshes and procs
	}
	public float getRefresh() {
		return this.refreshTime;
	}
	
	public void Perk() {
		//extender
	}
	public void Passive() {
		//extender
	}
	public void resupply() {
		//extender
		if(InventoryHandler.getStart()) {
			CardHandler.getHand().add(new Card(getCard().getName(), getCard().getRanged(), getCard().getImage()));
		} 
	}
}
