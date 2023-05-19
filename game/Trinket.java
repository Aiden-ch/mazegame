package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Trinket {
	//inventory things that give items to use in combat
	private Image trkImg;
	private Item item;
	
	private int perkPrecedence;
	private float refreshTime;
	private float buffer = 0.0f;
	
	public Trinket(Texture txte, Item item, float refreshTime) {
		trkImg = new Image(txte);
		this.item = item;
		this.refreshTime = refreshTime;
		this.buffer = refreshTime;
	}
	public Trinket(Item item, float refreshTime) {
		this.item = item;
		this.refreshTime = refreshTime;
		this.buffer = refreshTime;
	}
	
	public Image getImage() {
		return this.trkImg;
	}
	public Item getItem() {
		return this.item;
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
		if(getBuffer() >= getRefresh()) {
			if(InventoryHandler.getStart()) {
				System.out.println("supplied");
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getProj(), getItem().getUses(), getItem().getImage()));
			} else if(AttackHandler.getInHand() == null) {
				//might not work
				System.out.println("resupplied");
				getItem().setUses(1);
				System.out.println(getItem().getUses());
				AttackHandler.getHand().add(new Item(getItem().getName(), getItem().getProj(), getItem().getUses(), getItem().getImage()));
				if(AttackHandler.getHand().size() == 1) {
					AttackHandler.setInHand(AttackHandler.getHand().get(0));
				}
			}
			setBuffer(0.0f);
		} else {
			//System.out.println(getBuffer());
			tick();
		}
	}
}
