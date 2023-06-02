package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MazeTraversal implements Screen {

	boolean start = true;
	
	final MazeGame game;

	OrthographicCamera camera;
	public BitmapFont font;
	public SpriteBatch batch;

	Texture bimg;

	private static char[][] maze;
	private static char[][] visibleMaze;

	static boolean w = false;
	static boolean a = false;
	static boolean s = false;
	static boolean d = false;
	static boolean inMap = false;

	// map variables
	private static int xPos = 29;
	private static int yPos = 29;
	
	//encounter
	static boolean encount = false;
	
	//inventory
	static boolean inInventory = false;
	static float buffer = 0.0f;

	public MazeTraversal(final MazeGame gaeme) {
		this.game = gaeme;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		if(game.start) {
			maze = Generator.generateMaze();

			visibleMaze = new char[59][59];
			for (int i = 0; i < 59; i++) { 
				for (int o = 0; o < 59; o++) {
					visibleMaze[i][o] = '*';
				}
			}	    
			game.start = false;
		}
		font = new BitmapFont();
		setAllFixedWidth(font);
	}

	public static void setAllFixedWidth(BitmapFont font) {
		BitmapFont.BitmapFontData data = font.getData();
		int maxAdvance = 0;
		for (int index = 0, end = 65536; index < end; index++) {
			BitmapFont.Glyph g = data.getGlyph((char) index);
			if (g != null && g.xadvance > maxAdvance) maxAdvance = g.xadvance;
		}
		for (int index = 0, end = 65536; index < end; index++) {
			BitmapFont.Glyph g = data.getGlyph((char) index);
			if (g == null) continue;
			g.xoffset += (maxAdvance - g.xadvance) / 2;
			g.xadvance = maxAdvance;
			g.kerning = null;
			g.fixedWidth = true;
		}
	}
	
	@Override
	public void resize (int width, int height) {
		// See below for what true means.
	}
	
	static int level = 1;
	
	public void encounter() {
		int encounter = (int)(Math.random() * 1000);
		if (encount) {
			if (xPos > 20 && xPos < 38 && yPos > 20 && yPos < 38) return;
			else if (yPos > 15 && yPos < 43 && yPos > 15 && yPos < 43) {
				if(encounter > 990) {
					level = 3;
					game.combatScreen();
				}
			} else if (yPos > 10 && yPos < 48 && yPos > 10 && yPos < 48) {
				if(encounter > 960) {
					level = 4;
					game.combatScreen();
				}
			} else {
				if(encounter > 940) {
					level = 6;
					game.combatScreen();
				}
			}
		}
	}

	@Override
	public void render(float deltaTime) {
		ScreenUtils.clear(255, 255, 255, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		//		game.font.draw(game.batch, "You do nothing", 100, 150); //placeholders
		//		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);


		mapState();
		if(Gdx.input.isKeyPressed(Input.Keys.T)) {
			level = 6;
			game.combatScreen();
		}
		if(inInventory) {
			game.inventoryScreen();
		} else if (inMap) {
			//Gdx.graphics.setWindowedMode(1500, 1500);
			font.getData().setScale(0.5f);
			for (int i = 0; i < 59; i++) {
				for (int o = 0; o < 59; o++) {
					if (i == yPos && o == xPos) textForMaze += '@';
					else textForMaze += visibleMaze[i][o];
				}
				textForMaze += "\n";
			}
		} else {
			//Gdx.graphics.setWindowedMode(500, 500);
			font.getData().setScale(2.5f);
			if (maze[yPos][xPos] == 'e') return;
			if (maze[yPos][xPos] == '$') {
				maze[yPos][xPos] = ' ';
				visibleMaze[yPos][xPos] = ' ';
				
				// add random item to inventory
				if(InventoryHandler.collectedTrinkets.size() != InventoryHandler.trinkets.size()) {
					int iter = (int)(Math.random() * (InventoryHandler.trinkets.size()-InventoryHandler.collectedTrinkets.size()));
					for (int i = 0; i < InventoryHandler.trinkets.size(); i++) {
						boolean temp = true;
						for (int o = 0; o < InventoryHandler.collectedTrinkets.size(); o++) {
							if (InventoryHandler.collectedTrinkets.get(o) == InventoryHandler.trinkets.get(i)) temp = false;
						}
						if (temp) iter--;
						if (iter == -1) {
							InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get(i));
							break;
						}
						// check whether trinket.get(i) is in collectedTrinkets
					}
//					InventoryHandler.collectedTrinkets.add(InventoryHandler.trinkets.get((int)(Math.random()*InventoryHandler.trinkets.size())));
				}
			}
			//	       if (!inInventory) {
			//	         if (!inFight) {
			visibleMaze[yPos][xPos] = maze[yPos][xPos];
			visibleMaze[yPos + 1][xPos] = maze[yPos + 1][xPos];
			visibleMaze[yPos - 1][xPos] = maze[yPos - 1][xPos];
			visibleMaze[yPos][xPos + 1] = maze[yPos][xPos + 1];
			visibleMaze[yPos][xPos - 1] = maze[yPos][xPos - 1];
			for (int i = yPos - 4; i <= yPos + 4; i++) {
				for (int o = xPos - 4; o <= xPos + 4; o++) {
					if (i == yPos && o == xPos)	textForMaze += "@ ";
					else if (i < 0 || i > 58 || o < 0 || o > 58) textForMaze += "* ";
					else textForMaze += (visibleMaze[i][o] + " ");
				}
				textForMaze += "\n";
			}
			
		}
		font.draw(game.batch, textForMaze, 0, Gdx.graphics.getHeight());
		encounter();
		textForMaze = "";
		encount = false;
		game.batch.end();
		
		
	}
	//	  private static Scanner sc = new Scanner(System.in);

	// fight variables
	//   private double xPosFight = 7.0;
	//   private double yPosFight = 7.0;
	//   private double xVelFight = 0.0;
	//   private double yVelFight = 0.0;

	// buffs
	//   private double speedModifier = 1;

	//	   private boolean inInventory = false;
	//	   private boolean inFight = false;

	static String textForMaze = "";
	
	public static void mapState() {
		int xVel = 0;
		int yVel = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.I) && !inInventory && buffer <= 0f) {
			inInventory = true;
			buffer = 10f;
		} else if ((Gdx.input.isKeyPressed(Input.Keys.M))) {
			inMap = true;
		} else {
			inMap = false;
			if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && !w && maze[yPos - 1][xPos] != '#') {
				w = true;
				yVel--;
			} else if (!(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))){
				w = false;
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && !a && maze[yPos][xPos - 1] != '#') {
				a = true;
				xVel--;
			} else if (!(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))){
				a = false;
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && !s && maze[yPos + 1][xPos] != '#') {
				s = true;
				yVel++;
			} else if (!(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))){
				s = false;
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && !d && maze[yPos][xPos + 1] != '#') {
				d = true;
				xVel++;
			} else if (!(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))){
				d = false;
			}


			if (maze[yPos + yVel][xPos + xVel] != '#') {
				xPos += xVel;
				yPos += yVel;
			}
			
			if(xVel != 0 || yVel != 0) {
				//TODO: change encounter logic
				encount = true;
			}
		}
		buffer -= 0.2f;
	}


	@Override
	public void show() {
		;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		;
	}
}
