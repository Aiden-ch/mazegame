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
	ItemHandler items = new ItemHandler();
	
	Texture sword;
	Image swordImage;
	
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
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		bombProj = new Projectile(bomb, bombImage, 10d, 15d, 0);
		
		bombLauncher = new Texture("trinkets/bomblauncher.png");
		bombLauncherImg = new Image(bombLauncher);
		
		items.addItem("Bomb", bombProj, 3, bombLauncherImg);
		
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
		swordImage = new Image(sword);
		swordImage.setOrigin(sword.getWidth()/2,0);
		Melee swordMelee = new Melee(swordImage, sword, 20d, 15d, 10f, (float)Math.PI/2f); //smaller speed = faster
		
		items.addItem("Sword", swordMelee, 1, new Image(new Texture("trinkets/basicblade.png")));
		
		ruler = new Texture("melee/ruler.png");
		rulerImg = new Image(ruler);
		rulerBlade = new Texture("trinkets/ruler.png");
		rulerBladeImg = new Image(rulerBlade);
		
		rulerImg.setOrigin(ruler.getWidth()/2,0);
		Melee rulerMelee = new Melee(rulerImg, ruler, 3d, 15d, 3f, (float)Math.PI/2f); //smaller speed = faster
		
		items.addItem("Ruler Slash", rulerMelee, 1, rulerBladeImg);
		
		items.testing();
		InventoryHandler.testing();
		
		Gdx.input.setInputProcessor(scroll);
	
		blob = new Texture("enemies/blob.png");
		blobImage = new Image(blob);
		blobEnemy = new Enemy(blob, blobImage, 15f, 2f, 2d);

		bimg = new Texture("background.png");
		
		heartTxte = new Texture("UI/heart.png");
		UI = new UIHandler(heartTxte);
//		heartImage = new Image(heartTxte);
//		heartImage.setPosition(10, Gdx.graphics.getHeight() - 10);
		
		camera = new OrthographicCamera();
		mouse_position = new Vector3(0,0,0);
		camera.setToOrtho(false, Gdx.graphics.getWidth()-50, Gdx.graphics.getHeight()-50);
		camera.position.x = Gdx.graphics.getWidth()/2; 
	    camera.position.y = Gdx.graphics.getHeight()/2;
		stage = new Stage(new ScreenViewport());
		
		//InventoryHandler.setStart(false);
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
        	InventoryHandler.activate();
        	handIndex = AttackHandler.chooseCard(ItemHandler.collected.get(0));
        	//System.out.println(AttackHandler.inHand.getType());
        } else {
        	InventoryHandler.activate();
        	//TODO: handIndex change
        	//System.out.println("restocking");
        }
		//attacking
        //System.out.println(AttackHandler.inHand.getUses());
        
		//change items collected thing to connect to card draw and inventory
        if(AttackHandler.hand.size() > 0 && AttackHandler.inHand != null) {
        	//System.out.println(AttackHandler.hand.size());
        	if(AttackHandler.inHand.getType() == 'p') {
        		buffer = AttackHandler.shoot(player, AttackHandler.inHand, buffer);
        	} else if(AttackHandler.inHand.getType() == 'm') {
        		buffer = AttackHandler.use(player, AttackHandler.inHand, buffer);
        	} else if(AttackHandler.inHand.getType() == 's') {
        		buffer = AttackHandler.melee(player, AttackHandler.inHand, buffer);
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
		for(int i=0; i<AttackHandler.getActive().size(); i++) {
			boolean destroy = false;
			Item temp = AttackHandler.getActive().get(i);
			
			if(temp.getType() == 'p') { //projectiles
				temp.getProj().getImage().setPosition((float)temp.getProj().getXPos(), (float)temp.getProj().getYPos());
				temp.getProj().move();
				
				stage.addActor(temp.getProj().getImage());
				
				for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
					if(temp.getProj().getBox().overlaps(EnemyHandler.getEnemies().get(j).getBox())) {
						EnemyHandler.getEnemies().get(j).takeDamage(temp.getProj().getDamage(), 0, 0);
						//System.out.println(EnemyHandler.getEnemies().get(j).getHealth());
						destroy = true;
					}
				}
					
				//remove projectiles that reach edges
				if(temp.getProj().getXPos()<0-64-10 || temp.getProj().getYPos()<0-64-10 
						|| temp.getProj().getXPos()>Gdx.graphics.getWidth()+64+10 || 
						temp.getProj().getYPos()>Gdx.graphics.getHeight()+64+10 || destroy) {
					temp.getProj().getImage().remove();
					AttackHandler.getActive().remove(i);
					i--;
					//System.out.println(AttackHandler.hand.get(handIndex));
					
				}
			} else if(temp.getType() == 's') { //melee
				if (temp.getMel().swing(player)) { //might cause problems if fight lasts too long 
					//System.out.println("melee:");	//System.out.println(temp.getMel().getXPos());//System.out.println(temp.getMel().getYPos());//temp.getMel().moveBox(player.getXPos(), player.getYPos());
					
					temp.getMel().getImage().setPosition(player.getXPos(), player.getYPos()+20);
					stage.addActor(temp.getMel().getImage());
					//System.out.println(temp.getMel().getDamage());
					
					for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
						Enemy tempem = EnemyHandler.getEnemies(j);
				
						float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getImage().getWidth()/2-player.getXPos()));
//						float enan = (float)Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getXPos())*180f/(fladoat)Math.PI;
						
						if((Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= 10f)
								|| (Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= temp.getMel().getTexture().getHeight()+10)
								//add angle check
								&&
								(Math.abs(temp.getMel().getStartAngle() - enan + temp.getMel().getRadius() / 2 * 180 / Math.PI) <= temp.getMel().getRadius() / 2 * 180 / Math.PI)
								) {
							
							EnemyHandler.getEnemies().get(j).takeDamage(temp.getMel().getDamage(), (float)temp.getMel().getKnockback(), enan);
						}
					}
					
				} else {
					temp.getMel().getImage().remove();
					temp.used();
					System.out.println("used");
					AttackHandler.getActive().remove(i);
					i--;
				}
			} else if(temp.getType() == 'm') {
				stage.addActor(temp.getMisc().getImage());
				if(temp.getMisc().consume(player, i)) {
					EffectHandler.playerEffects(player);
					temp.used();
					i--;
				}
			}
			if(AttackHandler.hand.size() >= 0 && AttackHandler.inHand != null) {
				//System.out.println(AttackHandler.inHand);
				AttackHandler.consumeCard(AttackHandler.inHand.getUses(), handIndex);
			} //TODO: add system so that game wont crash if you have nothing in hand and try to activate card
			//TODO: card draw / gain thing
			
			
		}
		
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
			for(int i=0; i<AttackHandler.getActive().size(); i++) {
				AttackHandler.getActive().remove(i);
			}
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
