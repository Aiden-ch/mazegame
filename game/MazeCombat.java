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
//import com.mygdx.game.miscItems.*; //add back later

//import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
	Texture bomb;
	Image bombImage;
	//enemy
	Texture blob;
	//camera
	private OrthographicCamera camera;
	//for moving
	Stage stage;
	//player
	Player player;
	//shooting
	Projectile bombProj;
	CardHandler items = new CardHandler();
	
	Texture sword;
	
	float time = 0f;

	float buffer = 0.2f;
	
	Vector3 mouse_position;
	
	Enemy blobEnemy;
	Image blobImage;
	
	Scroller scroll = new Scroller();
	//InputMultiplexer multiplexer = new InputMultiplexer();
	
	Texture arrow;
	Image arrowImage;
	Projectile arrowProj;
	
	Texture potion;
	Image potionImg;
	
	Texture speedPotion;
	Image speedPotionImg;
	
	Texture heartTxte;
	Image heartImage;
	
	UIHandler UI;
	
	Texture bombLauncher;
	Image bombLauncherImg;
	
	int handIndex = 0;
	
	int numSummons = 10;
	
	Texture ruler;
	Image rulerImg;
	Texture rulerBlade;
	Image rulerBladeImg;
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		//Gdx.graphics.setWindowedMode(500, 500);
		
		batch = new SpriteBatch();
		player = new Player();
		
		bomb = new Texture("projectiles/bomb.png");
		bombProj = new Projectile(bomb, 10d, 15d, 0);
		RangedItem bombCard = new RangedItem(bombProj, 10d, 1);
		
		bombLauncher = new Texture("trinkets/bomblauncher.png");
		bombLauncherImg = new Image(bombLauncher);
		
		items.addCard("Bomb", bombCard, 3, bombLauncherImg);
		
//		arrow = new Texture("projectiles/arrow.png");
//		arrowImage = new Image(arrow);
//		arrowImage.setOrigin(arrow.getWidth()/2,arrow.getHeight()/2);
//		arrowProj = new Projectile(arrow, arrowImage, 40d, 2d, 3);
//		
//		items.addItem("Arrow", arrowProj, 5);
//		
//		potion = new Texture("misc/heartpotion.png");
//		potionImg = new Image(potion);
//		Misc potionMisc = new HealthPot(potion, potionImg);
//		
//		items.addItem("Health Potion", potionMisc, 2);
//		
//		speedPotion = new Texture("misc/speedpotion.png");
//		speedPotionImg = new Image(speedPotion);
//		Misc speedPotionMisc = new SpeedPot(speedPotion, speedPotionImg);
//		
//		items.addItem("Speed Potion", speedPotionMisc, 1);
		
		sword = new Texture("melee/sword.png");
		Melee swordMelee = new Melee(10.0, (float)Math.PI/2f, 3.0, 5.0, 5.0f, sword); 
		items.addCard("Sword", swordMelee, 1, new Image(new Texture("trinkets/basicblade.png")));
		
		ruler = new Texture("melee/ruler.png");	
		Melee rulerMelee = new Melee(1.0, (float)Math.PI, 10.0, 4.0, 5.0f, ruler); //smaller speed = faster
		items.addCard("Ruler Slash", rulerMelee, 1, rulerBladeImg);
		
		InventoryHandler.testing();
		
		Gdx.input.setInputProcessor(scroll);
	
		blob = new Texture("enemies/blob.png");
		blobImage = new Image(blob);
		blobEnemy = new Enemy(blob, blobImage, 15f, 2f, 2d);

		bimg = new Texture("background.png");
		
		heartTxte = new Texture("UI/heart.png");
		UI = new UIHandler(heartTxte);
		
		camera = new OrthographicCamera();
		mouse_position = new Vector3(0,0,0);
		camera.setToOrtho(false, Gdx.graphics.getWidth()-50, Gdx.graphics.getHeight()-50);
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
        
        //handle initial hand
        if(time == 0.2f) {
        	handIndex = 0;
        	//System.out.println(AttackHandler.inHand.getType());
        }
        InventoryHandler.activate();
        //TODO: handIndex change
        //System.out.println("restocking");
		//attacking

        
		//change items collected thing to connect to card draw and inventory
        if(CardHandler.getHand().size() > 0 && CardHandler.getHeld() != null) {
        	//System.out.println(AttackHandler.hand.size());
        	if(CardHandler.getHeld().getType() == 'p') {
        		CardHandler.getHeld().getRanged().update(player, stage);
        	} else if(CardHandler.getHeld().getType() == 'm') {
        		//buffer = AttackHandler.use(player, AttackHandler.inHand, buffer);
        	} else if(CardHandler.getHeld().getType() == 's') {
        		CardHandler.getHeld().getMel().update(player, stage);
        	}
        }
		//System.out.println(items.collected.get(1));
		//if(!meleed) {
		//buffer = AttackHandler.melee(player, ItemHandler.collected.get(1), buffer);
		//System.out.println("swunk");
		//}
		//TODO: add bolt
		//TODO: add misc card
		buffer -= 0.2;
		
		//enemy spawn
		if(time % 20 <= 0.6 && numSummons > 0) { //change num after mod to change spawn speed
			//if(time == 1) {
			EnemyHandler.spawn(blobEnemy);//}
			numSummons--;
			//System.out.println(numSummons);
		}

		//player movement
		player.move();
		//limit where player goes
		if(player.getXPos() < 0) player.setXPos(0);
		if(player.getXPos() > Gdx.graphics.getWidth() - 64) player.setXPos(Gdx.graphics.getWidth() - 64);
		if(player.getYPos() < 0) player.setYPos(0);
		if(player.getYPos() > Gdx.graphics.getHeight() - 64) player.setYPos(Gdx.graphics.getHeight() - 64);
		
		//"actual" rendering
		gaeme.batch.begin();
		//background
		gaeme.batch.draw(bimg, 0, 0);
		UIHandler.displayStats(player, gaeme.batch, stage);
		
		//renders each projectile
//		for(int i=0; i<AttackHandler.getActive().size(); i++) {
//			Card temp = AttackHandler.getActive().get(i);
//			
//			if(temp.getType() == 'm') {
//				stage.addActor(temp.getMisc().getImage());
//				if(temp.getMisc().consume(player, i)) {
//					EffectHandler.playerEffects(player);
//					temp.used();
//					i--;
//				}
//			}
//			if(CardHandler.getHand().size() >= 0 && CardHandler.getHeld() != null) {
//				//System.out.println(AttackHandler.inHand);
//				CardHandler.consumeCard(CardHandler.getHeld().getUses(), handIndex);
//			} //TODO: add system so that game wont crash if you have nothing in hand and try to activate card
//			//TODO: card draw / gain thing
//			
//			
//		}
		
		if(player.getEffects().size() > 0) {
			EffectHandler.playerEffects(player);
		}
		
		//render enemies
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			Enemy temp = EnemyHandler.getEnemies().get(i);

			temp.getImage().setPosition(temp.getXPos(), temp.getYPos());
			temp.move(player);
			
			if(temp.getXPos() < 0) temp.setXPos(0);
			if(temp.getXPos() > Gdx.graphics.getWidth() - 64) temp.setXPos(Gdx.graphics.getWidth() - 64);
			if(temp.getYPos() < 0) temp.setYPos(0);
			if(temp.getYPos() > Gdx.graphics.getHeight() - 64) temp.setYPos(Gdx.graphics.getHeight() - 64);

			stage.addActor(temp.getImage());
			
			if(temp.getHealth() <= 0) {
				temp.getImage().remove();
				EnemyHandler.getEnemies().remove(i);
				i--;
			} else 
//				remove blobs that touch player
			if(player.getBox().overlaps(temp.getBox()) && temp.getHealth() > 0) {
				player.takeDamage(temp.getDamage()); //add back
				
				temp.knockback(-90f + 180f/(float)Math.PI * (float)(Math.atan2(temp.getYPos()-player.getYPos(), temp.getXPos()-player.getImage().getWidth()/2-player.getXPos())));
				
				//System.out.println(player.getHealth());
				
				//temp.getImage().remove();
				//EnemyHandler.getEnemies().remove(i);
				//i--;
			}
			
			
		}

		//System.out.println(AttackHandler.getCards().size());
		
		//player
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
		//potion.dispose();
		bomb.dispose();
		batch.dispose();
		stage.dispose();
		blob.dispose();
		//sword.dispose();
		//arrow.dispose();
		//speedPotion.dispose();
		heartTxte.dispose();
		bombLauncher.dispose();
	}

}
