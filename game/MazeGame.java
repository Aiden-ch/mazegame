package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.miscItems.HealthPot;
import com.mygdx.game.miscItems.SpeedPot;


public class MazeGame extends Game {

	boolean start = true;
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public CardHandler thing = new CardHandler();

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		//Gdx.graphics.setWindowedMode(800, 480);
		
		//initialize ALL trinkets and cards
		Texture bomb = new Texture("projectiles/bomb.png");
		Projectile bombProj = new Projectile(bomb, 10d, 15d, 0);
		RangedItem bombCard = new RangedItem(bombProj, 4d, 3, 0, 1d, 3, 15);
		
		Texture bombLauncher = new Texture("cards/bomblauncher.png");
		Image bombLauncherImg = new Image(bombLauncher);
		
		CardHandler.addCard("Bomb", bombCard, bombLauncherImg);
		
		Texture sword = new Texture("melee/sword.png");
		Melee swordMelee = new Melee(7.0, (float)Math.PI/2f, 15.0, 1.0, 5.0f, sword); 
		Image swordImage = new Image(new Texture("cards/basicblade.png"));
		CardHandler.addCard("Sword", swordMelee, swordImage);
		
		Texture ruler = new Texture("melee/ruler.png");	
		Melee rulerMelee = new Melee(3.0, (float)Math.PI, 10.0, 14.0, 5.0f, ruler); //smaller speed = faster
		Image rulerBladeImg = new Image(new Texture("cards/ruler.png"));
		CardHandler.addCard("Ruler Slash", rulerMelee, rulerBladeImg);
		
		Texture potion = new Texture("misc/heartpotion.png");
		Texture potionCard = new Texture("cards/heartpotion.png");
		Misc potionMisc = new HealthPot(potion);
		
		CardHandler.addCard("Health Potion", potionMisc, new Image(potionCard));
		
		Texture speedPotion = new Texture("misc/speedpotion.png");
		Image speedPotionImg = new Image(new Texture("cards/speedpotion.png"));
		Misc speedPotionMisc = new SpeedPot(speedPotion);
		
		CardHandler.addCard("Speed Potion", speedPotionMisc, speedPotionImg);
		
		//god gun
		Projectile crystalProj = new Projectile(new Texture("projectiles/crystal.png"), 15, 5, 2);
		RangedItem crystalCard = new RangedItem(crystalProj, 8d, 15, 70, 0d, 115, 0.1);

		CardHandler.addCard("Crystal", crystalCard, new Image(new Texture("cards/crystalgun.png")));
		
		//god gun
		Projectile thornProj = new Projectile(new Texture("projectiles/thorn.png"), 20, 10, 3);
		RangedItem rifleCard = new RangedItem(thornProj, 0.5d, 2, 10, 0.9d, 25, 0.1);

		CardHandler.addCard("Rifle", rifleCard, new Image(new Texture("cards/makeshiftblaster.png")));
		
		Melee giantMelee = new Melee(5.0, (float)Math.PI/3, 3.0, 24.0, 15.0f, new Texture("melee/giantsbane.png")); //smaller speed = faster
		CardHandler.addCard("Giant's Bane", giantMelee, new Image(new Texture("cards/giantsbane.png")));
		
		InventoryHandler.testing();
		
		InventoryHandler.activeTrinkets.add(InventoryHandler.collectedTrinkets.get(1));
		
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
	public void closeInventory() {
		this.setScreen(new MazeTraversal(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
