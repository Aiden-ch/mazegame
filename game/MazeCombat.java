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
	SpriteBatch batch;
	Texture bimg;
	//enemy
	Texture blob;
	//camera
	private OrthographicCamera camera;
	//for moving
	Stage stage;
	//player
	Player player;
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
	
	int numSummons = 10;
	
	BossName trangle;
	RangedEnemy triangle;
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		//Gdx.graphics.setWindowedMode(500, 500);
		
		batch = new SpriteBatch();
		player = new Player();
		
		Gdx.input.setInputProcessor(scroll);
	
		blob = new Texture("enemies/blob.png");
		blobEnemy = new Enemy(blob, 15f, 2f, 12d);
		
		trangle = new BossName(new Texture("enemies/goldenguardian.png"), 150f, 3, 5);
		
		Projectile bolt = new Projectile(new Texture("projectiles/arrow.png"), 4, 5, 0 );
		triangle = new RangedEnemy(new Texture("enemies/triangle.png"), 10f, 5, 5, bolt);
		
		bimg = new Texture("background.png");
		
		heartTxte = new Texture("UI/heart.png");
		UI = new UIHandler(heartTxte);
		
		camera = new OrthographicCamera();
		mouse_position = new Vector3(0,0,0);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = Gdx.graphics.getWidth()/2; 
	    camera.position.y = Gdx.graphics.getHeight()/2;
		stage = new Stage(new ScreenViewport());
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
		InventoryHandler.activate();
        if(time == 0.2f) {
        	CardHandler.chooseCard(CardHandler.checkHand(1));
        }
        //TODO: handIndex change

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
        		CardHandler.getHeld().getMel().update(player, stage);
        	}
        }
        for(int i=0; i<CardHandler.getALL().size(); i++) {
        	if(CardHandler.getALL().get(i).getType() == 'p') {
        		CardHandler.getALL().get(i).getRanged().rendoor(player, stage);
        	}
        }
		
		//enemy spawn
        if(time <= 0.3f) {
        	EnemyHandler.spawnBoss(trangle);
        }
		if(time % 50 <= 0.6 && numSummons > 0) { //change num after mod to change spawn speed
			EnemyHandler.spawn(triangle);
			EnemyHandler.spawn(blobEnemy);
			numSummons--;
		}

		//player movement
		player.move();
		
		if(player.getEffects().size() > 0) {
			EffectHandler.playerEffects(player);
		}
		
		//render and update enemies
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			Enemy temp = EnemyHandler.getEnemies().get(i);
			temp.update(player, stage, i);
			if(temp.getType() == 'r') {
				temp.rendoor(player, stage);
			}
		}

		//render player
		if(player.getHealth() > 0) {
			gaeme.batch.draw(player.player, player.getXPos(), player.getYPos());
			//System.out.println(player.getXPos());
			//System.out.println(player.getYPos());
		} else {
			//System.out.println("dead");
		}
		
		//for drawing
		stage.act();
		stage.draw();
		
		//HUD
		UIHandler.displayStats(player, gaeme.batch, stage);
		
		//ending combat encounter
		if(numSummons <= 0 && EnemyHandler.getEnemies().size() <= 0) {
			numSummons = 20;
			dispose();
			InventoryHandler.setStart(true);
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
