package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Card {
	
	private String name;
	private RangedItem rait;
	private Melee mel;
	private Misc misc;
	private int uses;
	
	private Image itemImg;
	
	private char type;
	
	public Card(String name) {
		this.name = name;
		//m for misc
		this.type = 'm';
	}
	public Card(String name, RangedItem rait, int uses, Image img) {
		this.name = name;
		this.rait = rait;
		//p for projectile
		this.type = 'p';
		this.uses = uses;
		
		this.itemImg = img;
	}
	//for copies
	public Card(String name, RangedItem rait, Image img) {
		this.name = name;
		this.rait = rait;
		//p for projectile
		this.type = 'p';
		
		this.itemImg = img;
	}
	public Card(String name, Melee mel, int uses, Image img) {
		this.name = name;
		this.mel = mel;
		//s for swing
		this.type = 's';
		this.uses = uses;
		
		this.itemImg = img;
	}
	//for copies
	public Card(String name, Melee mel, Image img) {
		this.name = name;
		this.mel = mel;
		//s for swing
		this.type = 's';
		
		this.itemImg = img;
	}
	public Card(String name, Misc misc, int uses, Image img) {
		this.name = name;
		this.misc = misc;
		//s for swing
		this.type = 'm';
		this.uses = uses;
		
		this.itemImg = img;
	}
	//for copies
	public Card(String name, Misc misc, Image img) {
		this.name = name;
		this.misc = misc;
		//s for swing
		this.type = 'm';
		
		this.itemImg = img;
	}
	
	public String getName() {
		return name;
	}
	public RangedItem getRanged() {
		return rait;
	}
	public Melee getMel() {
		return mel;
	}
	public Misc getMisc() {
		return misc;
	}
	public char getType() {
		return type;
	}
	
	public int getUses() {
		return this.uses;
	}
	public void setUses(int uses) {
		this.uses = uses;
	}
	public void used() {
		this.uses--;
	}
	
	//card image
	public Image getImage() {
		return this.itemImg;
	}
	
	public String toString() {
		return this.name + " " + this.type;
	}
}
