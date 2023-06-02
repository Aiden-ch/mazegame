package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.miscItems.HealthPot;
import com.mygdx.game.miscItems.SpeedPot;
import com.mygdx.game.projItems.Bomb;
import com.mygdx.game.projItems.Fire;
import com.mygdx.game.trinkets.*;

public class InventoryHandler implements Screen {
	static ArrayList<Trinket> activeTrinkets = new ArrayList<Trinket>(); //stores trinkets you are using
	static ArrayList<Trinket> collectedTrinkets = new ArrayList<Trinket>(); //stores all trinkets that you found
	static ArrayList<Trinket> trinkets = new ArrayList<Trinket>(); //stores all trinkets

	//so player can equip and unequip
	static ArrayList<Rectangle> eqptBoxes = new ArrayList<Rectangle>(); //for equipped items
	static ArrayList<Rectangle> neBoxes = new ArrayList<Rectangle>(); //for not equipped items
	static Rectangle mouseBox = new Rectangle();

	final MazeGame game;

	Texture bimg = new Texture("inventoryScreen.png");

	public BitmapFont font;
	public SpriteBatch batch;
	OrthographicCamera camera;

	private static boolean start;
	static boolean opened = true;

	public InventoryHandler(final MazeGame gaeme) {
		this.game = gaeme;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		font = new BitmapFont();

		mouseBox.width = 10;
		mouseBox.height = 10;
	}

	public static boolean getStart() {
		return start;
	}
	public static void setStart(boolean temp) {
		start = temp;
	}

	public void equipTrinkets(Trinket temp) {
		activeTrinkets.add(temp);
	}

	public static void initCards() {
		//initialize ALL cards
		Texture bomb = new Texture("projectiles/bomb.png");
		Projectile bombProj = new Bomb(bomb, 9d, 30d, 0, 85, 2);
		RangedItem bombCard = new RangedItem(bombProj, 4d, 1, 0, 1d, 3, 25);

		Texture bombLauncher = new Texture("cards/bomblauncher.png");
		Image bombLauncherImg = new Image(bombLauncher);

		CardHandler.addCard("Bomb", bombCard, bombLauncherImg);

		Texture sword = new Texture("melee/sword.png");
		Melee swordMelee = new Melee(25.0, (float)Math.PI/3f, 12.0, 1.0, 5.0f, sword); 
		Image swordImage = new Image(new Texture("cards/basicblade.png"));
		CardHandler.addCard("Sword", swordMelee, swordImage);

		Texture ruler = new Texture("melee/ruler.png");	
		Melee rulerMelee = new Melee(3.0, (float)Math.PI, 7.0, 14.0, 5.0f, ruler); //smaller speed = faster
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
		Projectile crystalProj = new Projectile(new Texture("projectiles/crystal.png"), 15, 8, 2, 0.5);
		RangedItem crystalCard = new RangedItem(crystalProj, 8d, 7, 50, 0d, 49, 30.1);

		CardHandler.addCard("Crystal", crystalCard, new Image(new Texture("cards/crystalgun.png")));

		//god gun
		Projectile thornProj = new Projectile(new Texture("projectiles/thorn.png"), 10, 7, 3, 0.8);
		RangedItem rifleCard = new RangedItem(thornProj, 0.5d, 2, 10, 0.9d, 36, 18.1);

		CardHandler.addCard("Rifle", rifleCard, new Image(new Texture("cards/makeshiftblaster.png")));

		Melee giantMelee = new Melee(55.0, (float)Math.PI*2f/3, 3.0, 24.0, 35.0f, new Texture("melee/giantsbane.png")); //smaller speed = faster
		CardHandler.addCard("Giant's Bane", giantMelee, new Image(new Texture("cards/giantsbane.png")));

		//starter ranged
		Projectile arcaneProj = new Projectile(new Texture("projectiles/arcanebolt.png"), 17, 3, 1, 0.2);
		RangedItem bookCard = new RangedItem(arcaneProj, 4d, 1, 15, 0.9d, 1, 0.1);
		CardHandler.addCard("Guide", bookCard, new Image(new Texture("cards/internspellguide.png")));

		Melee blastMelee = new Melee(25.0, (float)Math.PI*2f/3, 9.0, 12.0, 5.0f, new Texture("melee/swordcast.png")); //smaller speed = faster
		CardHandler.addCard("Sacrilege", blastMelee, new Image(new Texture("cards/swordcaster.png")));

		Projectile flame = new Fire(new Texture("effects/red.png"), 12, 0, 100, 180, 0);
		RangedItem flameCard = new RangedItem(flame, 0.1d, 30, 45, 0.1d, 90, 30.1);
		CardHandler.addCard("Flame Gun", flameCard, new Image(new Texture("cards/flamegun.png")));
		
		Projectile frost = new Fire(new Texture("projectiles/frost.png"), 9, 1, 100, 180, 0);
		RangedItem frostCard = new RangedItem(frost, 0.1d, 50, 75, 0.1d, 250, 50.1);
		CardHandler.addCard("Frost Gun", frostCard, new Image(new Texture("cards/frostgun.png")));
	}

	public static void initTrinkets() {
		initCards();
		
		Trinket BombLauncher = new BombLauncher(CardHandler.allItems.get(0), 15f);
		Trinket RulerBlade = new RulerBlade(CardHandler.allItems.get(2), 20f);
		Trinket BasicBlade = new BasicBlade(CardHandler.allItems.get(1), 20f);
		Trinket PotionStand = new SimplePotionStand(CardHandler.allItems.get(3), 40f);
		Trinket MagicBurette = new MagicBurette(CardHandler.allItems.get(4), 40f);
		Trinket CrystalGun = new CrystalGun(CardHandler.allItems.get(5), 40f);
		Trinket MakeshiftBlaster = new MakeshiftBlaster(CardHandler.allItems.get(6), 40f);
		Trinket GiantsBane = new GiantsBane(CardHandler.allItems.get(7), 40f);
		Trinket SpellGuide = new InternSpellguide(CardHandler.allItems.get(8), 40f);

		trinkets.add(BombLauncher);
		trinkets.add(RulerBlade);
		trinkets.add(BasicBlade);
		trinkets.add(PotionStand);
		trinkets.add(MagicBurette);
		trinkets.add(CrystalGun);
		trinkets.add(MakeshiftBlaster);
		trinkets.add(GiantsBane);
		trinkets.add(SpellGuide);

		//starter passive
		Trinket PrescriptionMeds = new PrescriptionMeds();
		trinkets.add(PrescriptionMeds);
		Trinket expRounds = new ExplosiveRounds(); //10
		trinkets.add(expRounds);
		Trinket mArmor = new MagicArmor(); //11
		trinkets.add(mArmor); //halves damage taken when it is over 10
		Trinket pPowder = new ProteinPowder(); //12
		trinkets.add(pPowder);
		Trinket rReciprocator = new RocketReciprocator(); //13
		trinkets.add(rReciprocator); //launches a rocket when hit enemy
		Trinket bCloak = new BloodyCloak(); //14
		trinkets.add(bCloak); //resets dash on kill

		Trinket steroid = new Steroids(); //15
		trinkets.add(steroid); //
		Trinket sCast = new SwordCast(); //16
		trinkets.add(sCast); //

		Trinket sCaster = new SwordCaster(CardHandler.allItems.get(9), 40f);
		trinkets.add(sCaster);
		Trinket fGun = new FlameGun(CardHandler.allItems.get(10), 40f);
		trinkets.add(fGun);
		
		Trinket aShield = new AncientShield();
		trinkets.add(aShield);
		Trinket sShield = new SpikeShield();
		trinkets.add(sShield);
		
		Trinket frostGun = new FrostGun(CardHandler.allItems.get(11), 40f);
		trinkets.add(frostGun);
		
		Trinket bHammer = new BerserkerStone();
		trinkets.add(bHammer);
		Trinket aCond = new AmmoCondenser();
		trinkets.add(aCond);
	}

	public static void initStarter() {
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(1));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(1));
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(8));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(8));
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(9));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(9));

		//for testing
		//		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(14));
		//		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(14));

	}

	public static void testing() {
		initTrinkets();
		for(int i=0; i<trinkets.size(); i++) {
			collectedTrinkets.add(trinkets.get(i));
		}
	}

	//trinket calls all handled here
	public static void activate(Batch batch) {
		for(int i=0; i<activeTrinkets.size(); i++) {
			activeTrinkets.get(i).resupply();
		}
		for(int i=0; i<activeTrinkets.size(); i++) {
			activeTrinkets.get(i).Passive(batch);
			activeTrinkets.get(i).update();
		}
		if(start) {
			//arrangeByPrecedence();
			start = false;
		}
	}
	//hit event means player hit something with an attack, gothit means player got hit
	public static void proc(double num, String event, Player player) {
		for(int i=0; i<activeTrinkets.size(); i++) {
			activeTrinkets.get(i).Perk(num, event, player);
		}
	}
	public static void proc(double num, String event, Enemy enemy) {
		for(int i=0; i<activeTrinkets.size(); i++) {
			activeTrinkets.get(i).Perk(num, event, enemy);
		}
	}

	//manipulating inventory
	public void showTrinkets() {
		eqptBoxes = new ArrayList<Rectangle>();
		neBoxes = new ArrayList<Rectangle>();

		font.draw(game.batch, "Equipped:", 40, Gdx.graphics.getHeight() - 40);
		font.draw(game.batch, "All collected trinkets:", 280, Gdx.graphics.getHeight() - 40);
		for(int i=0; i<activeTrinkets.size(); i++) {
			font.draw(game.batch, activeTrinkets.get(i).getName(), 40, Gdx.graphics.getHeight() - 80 - i*30);

			Rectangle temp = new Rectangle();
			temp.width = 170;
			temp.height = 20;
			temp.x = 40;
			temp.y = 80 + i*30;
			eqptBoxes.add(temp);

			trinketYPos = temp.y;

		}
		for(int i=0; i<collectedTrinkets.size(); i++) {
			font.draw(game.batch, collectedTrinkets.get(i).getName(), 280 + (170 * (int)(i / 12)), Gdx.graphics.getHeight() - 80 - i*30 % (30*12));

			Rectangle temp = new Rectangle();
			temp.width = 170;
			temp.height = 20; //add scrolling to active trinkets
			temp.x = 280 + (170 * (int)(i / 12));
			temp.y = 80 + i*30 % (30*12);
			neBoxes.add(temp);

		}
		//		System.out.println(collectedTrinkets.size());
		//		System.out.println(trinkets.size());
	}

	static double timer = 0;

	static double trinketYPos = 80;

	static Texture cursor = new Texture("UI/pointer.png");

	public void moveTrinkets(Batch batch) {
		boolean removed = false;

		mouseBox.x = Gdx.input.getX();
		mouseBox.y = Gdx.input.getY();
		//System.out.println(mouseBox.x + "," + mouseBox.y);
		batch.draw(cursor, mouseBox.x, Gdx.graphics.getHeight() - 64 - mouseBox.y);

		timer = Math.max(timer-0.5d, 0);
		if(timer == 0) {
			for(int i=0; i<activeTrinkets.size(); i++) {
				if(mouseBox.overlaps(eqptBoxes.get(i)) && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !removed) {
					activeTrinkets.remove(i);
					eqptBoxes.remove(i);
					removed = true;
					i = 10000;
					trinketYPos -= 30;
					timer = 3;
				}
			}
			for(int i=0; i<collectedTrinkets.size(); i++) {
				if(mouseBox.overlaps(neBoxes.get(i)) && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !removed) {
					boolean found = false;
					for(int j=0; j<activeTrinkets.size(); j++) {
						if(activeTrinkets.get(j).getName().equals(collectedTrinkets.get(i).getName())) {
							found = true;
						}
					}
					if(!found) {
						activeTrinkets.add(collectedTrinkets.get(i));
						Rectangle temp = new Rectangle();
						temp.width = 240;
						temp.height = 30;
						temp.x = 40;
						temp.y = (float)trinketYPos + 30f;
						eqptBoxes.add(temp);
						trinketYPos = temp.y;

						removed = true;
						i = 10000;
						timer = 3;
					}
				}
			}
		}
	}
	public void close() {
		MazeTraversal.inInventory = false;
		opened = true;
		eqptBoxes = new ArrayList<Rectangle>();
		neBoxes = new ArrayList<Rectangle>();
		game.mazeScreen();
	}

	private double buffer = 0;

	@Override
	public void render(float delta) {
		game.batch.begin();
		game.batch.draw(bimg, 0, 0);
		if(opened) {
			buffer = 10;
			showTrinkets();
			opened = false;
		}
		//System.out.println(trinketYPos);

		moveTrinkets(game.batch);
		showTrinkets();

		buffer = Math.max(0, buffer - 0.5);
		if(buffer == 0 && Gdx.input.isKeyPressed(Input.Keys.I)) {
			close();
		}
		game.batch.end();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
