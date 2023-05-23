package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Misc {
	private Image mImg;
	private Texture texture;
	private String type;
	
	public Misc() {
		;
	}
	
	public Misc(Texture txte) {
		this.mImg = new Image(txte);
	}
	
	public Image getImage() {
		return this.mImg;
	}
	public Texture getTexture() {
		return this.texture;
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
