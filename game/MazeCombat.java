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

//import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
	
	boolean meleed = false;
	
	Vector3 mouse_position;
	
	Enemy blobEnemy;
	Image blobImage;
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		//Gdx.graphics.setWindowedMode(500, 500);
		
		batch = new SpriteBatch();
		player = new Player();
		
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		bombProj = new Projectile(bomb, bombImage, 20d, 3d);
		
		items.addItem("Bomb", bombProj);
		
		sword = new Texture("sword.png");
		swordImage = new Image(sword);
		swordImage.setOrigin(sword.getWidth()/2,0);
		Melee swordMelee = new Melee(swordImage, sword, 10d, 5d, 1f, (float)Math.PI/2);
		
		items.addItem("Sword", swordMelee);
		
		items.testing();
	
		blob = new Texture("blob.png");
		blobImage = new Image(blob);
		blobEnemy = new Enemy(blob, blobImage, 5f, 5f, 2d);

		bimg = new Texture("background.png");
		
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

		//attacking
		//change items collected thing to connect to card draw and inventory
		//buffer = AttackHandler.shoot(player, items.collected.get(0), buffer);
		//System.out.println(items.collected.get(1));
		//if(!meleed) {
		buffer = AttackHandler.melee(player, items.collected.get(1), buffer);
		meleed = true;
		//System.out.println("swunk");
		//}
		//TODO: add bolt
		//TODO: add misc card
		buffer -= 0.2;
		
		//enemy spawn
		if(time % 10 <= 0.2) { //change num after mod to change spawn speed
			EnemyHandler.spawn(blobEnemy);
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
						EnemyHandler.getEnemies().get(j).takeDamage(temp.getProj().getDamage());
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
					//System.out.println("melee:");
					//System.out.println(temp.getMel().getXPos());
					//System.out.println(temp.getMel().getYPos());
					//temp.getMel().moveBox(player.getXPos(), player.getYPos());
					
					temp.getMel().getImage().setPosition(player.getXPos(), player.getYPos()+20);
					stage.addActor(temp.getMel().getImage());
					//System.out.println(temp.getMel().getDamage());
					
					for(int j=0; j<EnemyHandler.getEnemies().size(); j++) {
						Enemy tempem = EnemyHandler.getEnemies(j);
						//System.out.println("distance");
						//System.out.println(Math.sqrt(Math.pow(player.getXPos()-tempem.getXPos(), 2) + Math.pow(player.getYPos()-tempem.getYPos(),2)));
						/*
						System.out.println("enemy");
						System.out.println(tempem.getXPos());
						System.out.println(tempem.getYPos());
						System.out.println("player");
						System.out.println(player.getXPos());
						System.out.println(player.getYPos());
						*/
						if((Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= 95f) &&
								(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getYPos()) 
										<= 0 + temp.getMel().getStartAngle()) &&
								(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getYPos()) 
										>= -1*temp.getMel().getRadius()*180*Math.PI + temp.getMel().getStartAngle())) {
							EnemyHandler.getEnemies().get(j).takeDamage(temp.getMel().getDamage());
							System.out.println("died");
						}
					}
					
				} else {
					temp.getMel().getImage().remove();
					//System.out.println(AttackHandler.getCards().remove(i));
					AttackHandler.getCards().remove(i);
					i--;
					//System.out.println(AttackHandler.getCards().size());
				}
			}
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
			}

			//remove blobs that touch player
			if(player.getBox().overlaps(temp.getBox())) {
				player.takeDamage(temp.getDamage(), time);
				
				System.out.println(player.getHealth());
				
				temp.getImage().remove();
				EnemyHandler.getEnemies().remove(i);
				i--;
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
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		bomb.dispose();
		batch.dispose();
		stage.dispose();
		blob.dispose();
		sword.dispose();
	}

}
