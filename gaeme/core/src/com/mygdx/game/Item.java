package com.mygdx.game;

public class Item {
	
	private String name;
	private Projectile proj;
	
	public Item(String name) {
		this.name = name;
	}
	public Item(String name, Projectile proj) {
		this.name = name;
		this.proj = proj;
	}
	
	public String getName() {
		return name;
	}
	public Projectile getProj() {
		return proj;
	}
	
}
