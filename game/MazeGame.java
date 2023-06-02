package com.mygdx.game;

import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeGame extends Game {

	boolean start = true;
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public CardHandler thing = new CardHandler();

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		//Gdx.graphics.setWindowedMode(800, 480);

		InventoryHandler.testing();
		//InventoryHandler.initTrinkets();
		//InventoryHandler.initStarter();
		
		this.setScreen(new MazeTraversal(this));
	}
	
	public void combatScreen() {
		InventoryHandler.setStart(true);
		this.setScreen(new MazeCombat(this));
	}
	public void mazeScreen() {
		this.setScreen(new MazeTraversal(this));
	}
	public void inventoryScreen() {
		this.setScreen(new InventoryHandler(this));
	}
	public void deadScreen() {
		this.setScreen(new MazeDeath(this));
	}
	public void closeInventory() {
		this.setScreen(new MazeTraversal(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		thing = new CardHandler();
	}

}
