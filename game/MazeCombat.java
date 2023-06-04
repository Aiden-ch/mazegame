package com.mygdx.game;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import enemies.Enemy;
import enemies.GoldenGuardian;
import enemies.Healer;
import enemies.InkShade;
import enemies.RangedEnemy;
import enemies.Shielder;

//import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//same as MyGdxGame
//maze traversal code should have similar structure to this
public class MazeCombat implements Screen {
	
	private final MazeGame gaeme;
	
	static BitmapFont font = new BitmapFont();
	
	//sprite stuff
	public static SpriteBatch batch;
	Texture bimg;
	//enemy
	Texture blob;
	//camera
	private OrthographicCamera camera;
	//for moving
	public static Stage stage;
	//player
	public static Player player;
	CardHandler items = new CardHandler();
	
	float time = 0f;

	float buffer = 0.2f;
	
	Vector3 mouse_position;
	
	Enemy blobEnemy;
	
	Scroller scroll = new Scroller();
	//InputMultiplexer multiplexer = new InputMultiplexer();
	
	Texture heartTxte;
	Image heartImage;
	
	UIHandler UI;
	
	int numSummons = 10 * MazeTraversal.level/6;
	
	GoldenGuardian trangle;
	RangedEnemy triangle;
	
	InkShade shade;
	
	Shielder shieldo;
	
	Healer fae;
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		//Gdx.graphics.setWindowedMode(500, 500);
		
		batch = new SpriteBatch();
		player = new Player();
		
		Gdx.input.setInputProcessor(scroll);
	
		blob = new Texture("enemies/blob.png");
		blobEnemy = new Enemy(blob, 35f, 1.3f, 5d);
		
		trangle = new GoldenGuardian(new Texture("enemies/goldenguardian.png"), 350f, 2, 5);
		
		Projectile bolt = new Projectile(new Texture("projectiles/arrow.png"), 8, 10, 0, 0.5);
		triangle = new RangedEnemy(new Texture("enemies/triangle.png"), 25f, 1.7f, 1, bolt, 1.15);
		
		shade = new InkShade(new Texture("enemies/isl.png"), 200, 1, 30);
		
		shieldo = new Shielder(new Texture("enemies/shieldol.png"), 50f, 1.7f, 8d);
		
		fae = new Healer(new Texture("enemies/fael.png"), 18f, 3f, 5d);
		
		bimg = new Texture("background.png");
		
		heartTxte = new Texture("UI/heart.png");
		UI = new UIHandler(heartTxte);
		
		camera = new OrthographicCamera();
		mouse_position = new Vector3(0,0,0);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = Gdx.graphics.getWidth()/2; 
	    camera.position.y = Gdx.graphics.getHeight()/2;
		stage = new Stage(new ScreenViewport());
		
		//change numbers to check screen resolution
		//resize(1920, 1080);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		//game timer
		time += 0.2;
		
		//reset screen
		ScreenUtils.clear(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//camera
		camera.update();
		gaeme.batch.setProjectionMatrix(camera.combined);
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse_position);        
		
		//make sure to add actors and stuff after batch begins
		gaeme.batch.begin();
		
		//background
		gaeme.batch.draw(bimg, 0, 0);
		
		//handle initial hand
		InventoryHandler.activate(gaeme.batch);
        if(time == 0.2f) {
        	numSummons = 5 * MazeTraversal.level/2;
        	blobEnemy.setHealth(35 * MazeTraversal.level/5);
        	trangle.setHealth(350 * MazeTraversal.level/5);
        	CardHandler.chooseCard(CardHandler.checkHand(1));
        }
		
		//HUD
		UIHandler.displayStats(player, gaeme.batch, stage);
        
		//attacking
		//change items collected thing to connect to card draw and inventory
        if(CardHandler.getHand().size() > 0 && CardHandler.getHeld() != null) {
        	if(CardHandler.getHeld().getType() == 'p') {
        		CardHandler.getHeld().getRanged().update(player, stage);
        	} else if(CardHandler.getHeld().getType() == 'm') {
        		if(CardHandler.getHeld().getMisc().update(player, stage)) {
        			CardHandler.consumeCard(CardHandler.getHeld());
        		}
        	} else if(CardHandler.getHeld().getType() == 's') {
        		CardHandler.getHeld().getMel().update(player, stage, gaeme.batch);
        	}
        }
        for(int i=0; i<CardHandler.getALL().size(); i++) {
        	if(CardHandler.getALL().get(i).getType() == 'p') {
        		CardHandler.getALL().get(i).getRanged().rendoor(player, stage, gaeme.batch);
        	}
        }
		
		//enemy spawn
        if(MazeTraversal.level < 11) {
        	if(time <= 0.3f && Math.random() < MazeTraversal.level*0.04d && MazeTraversal.level > 6) {
        		double choice = Math.random();
        		if(choice < 0.35) {
        			EnemyHandler.spawnBoss(shade);
        		} else {
        			EnemyHandler.spawnBoss(trangle);
        		}
            	numSummons--;
            }
    		if((time > 1 && time % 60 <= MazeTraversal.level*0.02 && numSummons > 0) || (EnemyHandler.getEnemies().size() == 0)) { //change num after mod to change spawn speed
    			if(time % 25 <= 0.2 && MazeTraversal.level >= 7) {
    				EnemyHandler.spawn(triangle);
    			}
    			if(time % 45 <= 0.2 && MazeTraversal.level >= 6) {
    				EnemyHandler.spawn(shieldo);
    				EnemyHandler.spawn(shieldo);
    				numSummons--;
    			}
    			if(MazeTraversal.level >= 5) {
    				EnemyHandler.spawn(fae);
    			}
    			EnemyHandler.spawn(blobEnemy);
    			numSummons--;
    		}
        } else {
        	if(time <= 0.3f) {
        		EnemyHandler.spawnBoss(trangle);
        		EnemyHandler.spawnBoss(shade);
        		EnemyHandler.spawn(shieldo);
        		EnemyHandler.spawn(shieldo);
        		EnemyHandler.spawn(fae);
        		EnemyHandler.spawn(fae);
        		EnemyHandler.spawn(blobEnemy);
        		EnemyHandler.spawn(blobEnemy);
        		EnemyHandler.spawn(triangle);
        		EnemyHandler.spawn(triangle);
        	}
        }

		//player movement
		if(player.getHealth() > 0) {
			player.move(gaeme.batch, stage);
			if(player.getEffects().size() > 0) {
				EffectHandler.playerEffects(player, gaeme.batch);
			}
		} else {
			gaeme.deadScreen();
		}

		//render and update enemies
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			Enemy temp = EnemyHandler.getEnemies().get(i);
			temp.update(player, stage, i, gaeme.batch);
			if(temp.getType() == 'r') {
				temp.rendoor(player, stage);
			}
			if(temp.getEffects().size() > 0) {
				EffectHandler.enemyEffects(temp, gaeme.batch);
			}
		}
		
		//particle effects
		EffectHandler.display(gaeme.batch);
		
		//for drawing
		stage.act();
		stage.draw();
		
		//ending combat encounter
		if(numSummons <= 0 && EnemyHandler.getEnemies().size() <= 0) {
			dispose();
			InventoryHandler.setStart(true);
			CardHandler.reset();
			for(int i=0; i<CardHandler.getHand().size(); i++) {
				if(CardHandler.getHand().get(i).getType() == 'p') {
					for(int j=0; j<CardHandler.getHand().get(i).getRanged().getProjs().size(); j++) {
						CardHandler.getHand().get(i).getRanged().getProjs().get(j).getImage().remove();
					}
				}
			}
			if(player.getHealth() > player.getMaxHealth() - 20) {
				player.setMaxHealth(player.getMaxHealth() + 20);
			}
			gaeme.mazeScreen();
		}
		
		gaeme.batch.end();
	}

	@Override
	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
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
		batch.dispose();
		stage.dispose();
		blob.dispose();
		heartTxte.dispose();
	}

}
