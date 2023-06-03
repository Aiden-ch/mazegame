package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Misc {
	private Texture txte;
	private String type;
	
	public Misc() {
		;
	}
	
	public Misc(Texture txte) {
		this.txte = txte;
	}
	
	public Texture getTexture() {
		return this.txte;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean update(Player player, Stage stage) {
		return false;
	}
	public boolean consume(Player player) {
		return false;
	}
}
