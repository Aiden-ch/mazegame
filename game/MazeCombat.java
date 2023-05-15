package com.mygdx.game;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.miscItems.*;

//import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

//same as MyGdxGame
//maze traversal code should have similar structure to this
public class MazeCombat implements Screen {
	
	private final MazeGame gaeme;
	
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
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		//Gdx.graphics.setWindowedMode(500, 500);
		
		batch = new SpriteBatch();
		player = new Player();
		
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		bombProj = new Projectile(bomb, bombImage, 10d, 15d);
		
		items.addItem("Bomb", bombProj);
		
		arrow = new Texture("arrow.png");
		arrowImage = new Image(arrow);
		arrowImage.setOrigin(arrow.getWidth()/2,arrow.getHeight()/2);
		arrowProj = new Projectile(arrow, arrowImage, 40d, 2d);
		
		items.addItem("Arrow", arrowProj);
		
		potion = new Texture("heartpotion.png");
		potionImg = new Image(potion);
		Misc potionMisc = new HealthPot(potion, potionImg);
		
		items.addItem("Health Potion", potionMisc);
		
		speedPotion = new Texture("speedpotion.png");
		speedPotionImg = new Image(speedPotion);
		Misc speedPotionMisc = new SpeedPot(speedPotion, speedPotionImg);
		
		items.addItem("Speed Potion", speedPotionMisc);
		
		sword = new Texture("sword.png");
		swordImage = new Image(sword);
		swordImage.setOrigin(sword.getWidth()/2,0);
		Melee swordMelee = new Melee(swordImage, sword, 3d, 5d, 10f, (float)Math.PI/2f); //smaller speed = faster
		
		items.addItem("Sword", swordMelee);
		
		
		items.testing();
		
		Gdx.input.setInputProcessor(scroll);
	
		blob = new Texture("blob.png");
		blobImage = new Image(blob);
		blobEnemy = new Enemy(blob, blobImage, 5f, 2f, 2d);

		bimg = new Texture("background.png");
		
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
		//System.out.println("mouse" + Gdx.input.getX() + ", " + Gdx.input.getY());
		//System.out.println("graphic" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
		time += 0.2;
		// TODO Auto-generated method stub
		//reset screen
		ScreenUtils.clear(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//camera
		camera.update();
		gaeme.batch.setProjectionMatrix(camera.combined);
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse_position);

        
        if(time == 0.2f) {
        	AttackHandler.chooseCard(ItemHandler.collected.get(0));
        	//System.out.println(AttackHandler.inHand.getType());
        }
        
		//attacking
        
		//change items collected thing to connect to card draw and inventory
        if(AttackHandler.inHand.getType() == 'p') {
        	buffer = AttackHandler.shoot(player, AttackHandler.inHand, buffer);
		} else if(AttackHandler.inHand.getType() == 'm') {
			buffer = AttackHandler.use(player, AttackHandler.inHand, buffer);
		} else if(AttackHandler.inHand.getType() == 's') {
			buffer = AttackHandler.melee(player, AttackHandler.inHand, buffer);
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
		if(time % 20 <= 0.6) { //change num after mod to change spawn speed
			//if(time == 1) {
		EnemyHandler.spawn(blobEnemy);//}
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
		
		
		//renders each projectile
		for(int i=0; i<AttackHandler.getCards().size(); i++) {
			boolean destroy = false;
			Item temp = AttackHandler.getCards().get(i);
			
			if(temp.getType() == 'p') { //projectiles
				temp.getProj().getImage().setPosition((float)temp.getProj().getXPos(), (float)temp.getProj().getYPos());
				temp.getProj().move();
				
				stage.addActor(temp.getProj().getImage());
				
				for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
					if(temp.getProj().getBox().overlaps(EnemyHandler.getEnemies().get(j).getBox())) {
						EnemyHandler.getEnemies().get(j).takeDamage(temp.getProj().getDamage(), 0, 0);
						System.out.println(EnemyHandler.getEnemies().get(j).getHealth());
						destroy = true;
					}
				}
					
				//remove projectiles that reach edges
				if(temp.getProj().getXPos()<0-64-10 || temp.getProj().getYPos()<0-64-10 
						|| temp.getProj().getXPos()>Gdx.graphics.getWidth()+64+10 || 
						temp.getProj().getYPos()>Gdx.graphics.getHeight()+64+10 || destroy) {
					temp.getProj().getImage().remove();
					AttackHandler.getCards().remove(i);
					i--;
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
								|| (Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= 70f)
								//add angle check
								&&
								(Math.abs(temp.getMel().getStartAngle() - enan + temp.getMel().getRadius() / 2 * 180 / Math.PI) <= temp.getMel().getRadius() / 2 * 180 / Math.PI)
								) {
							
							EnemyHandler.getEnemies().get(j).takeDamage(temp.getMel().getDamage(), (float)temp.getMel().getKnockback(), enan);
							
							//System.out.println("died");
						}
					}
					
				} else {
					temp.getMel().getImage().remove();
					//System.out.println(AttackHandler.getCards().remove(i));
					AttackHandler.getCards().remove(i);
					i--;
					//System.out.println(AttackHandler.getCards().size());
				}
			} else if(temp.getType() == 'm') {
				stage.addActor(temp.getMisc().getImage());
				if(temp.getMisc().consume(player, i)) {
					EffectHandler.playerEffects(player);
					i--;
				}
				//temp.getMisc().getImage().setPosition(player.getXPos(), player.getYPos()+20);
			}
		}
		
		if(player.getEffects().size() > 0) {
			EffectHandler.playerEffects(player);
		}
		
		//render enemies
		for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
			Enemy temp = EnemyHandler.getEnemies().get(i);

			temp.getImage().setPosition(temp.getXPos(), temp.getYPos());
			temp.move(player);

			stage.addActor(temp.getImage());
			
			if(temp.getHealth() <= 0) {
				temp.getImage().remove();
				EnemyHandler.getEnemies().remove(i);
				i--;
			} else 
//				remove blobs that touch player
			if(player.getBox().overlaps(temp.getBox())) {
				//player.takeDamage(temp.getDamage(), time);
				
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
		
		//for projectiles
		stage.act();
		stage.draw();
		

		gaeme.batch.end();

		
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
		potion.dispose();
		bomb.dispose();
		batch.dispose();
		stage.dispose();
		blob.dispose();
		sword.dispose();
		arrow.dispose();
		speedPotion.dispose();
	}

}
