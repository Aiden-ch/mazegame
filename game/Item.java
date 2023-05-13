package com.mygdx.game;

public class Item {
	
	private String name;
	private Projectile proj;
	private Melee mel;
	
	private char type;
	
	public Item(String name) {
		this.name = name;
		//m for misc
		this.type = 'm';
	}
	public Item(String name, Projectile proj) {
		this.name = name;
		this.proj = proj;
		//p for projectile
		this.type = 'p';
	}
	public Item(String name, Melee mel) {
		this.name = name;
		this.mel = mel;
		//s for swing
		this.type = 's';
	}
	
	public String getName() {
		return name;
	}
	public Projectile getProj() {
		return proj;
	}
	public Melee getMel() {
		return mel;
	}
	public char getType() {
		return type;
	}
	
	public String toString() {
		return this.name + " " + this.type;
	}
}
