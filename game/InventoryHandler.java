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

	public static void initTrinkets() {
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
	}

	public static void initStarter() {
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(1));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(1));
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(8));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(8));
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(9));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(9));

		//for testing
		InventoryHandler.activeTrinkets.add(InventoryHandler.trinkets.get(14));
		InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(14));

	}

	public static void testing() {
		initTrinkets();
		for(int i=0; i<trinkets.size(); i++) {
			collectedTrinkets.add(trinkets.get(i));
		}
	}

	//trinket calls all handled here
	public static void activate(Player player) {
		for(int i=0; i<activeTrinkets.size(); i++) {
			//add ifs and what not for different trinket abilities
			activeTrinkets.get(i).resupply();
			activeTrinkets.get(i).Passive(player);
			//System.out.println(activeTrinkets.get(i).getItem().getName());
			//			if(activeTrinkets.get(i).getUpdate()) {
			activeTrinkets.get(i).update();
			
			//			}
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
