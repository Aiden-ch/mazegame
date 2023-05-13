package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Misc {
	private Image mImg;
	private Texture texture;
	
	public Misc(Image mImg, Texture txte) {
		this.mImg = mImg;
		this.texture = txte;
	}
	
	public Image getImg() {
		return this.mImg;
	}
	public Texture getTexture() {
		return this.texture;
	}
}
