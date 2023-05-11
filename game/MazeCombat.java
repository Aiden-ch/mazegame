package com.mygdx.game;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	Items items = new Items();
	
	Texture sword;
	Image swordImage;
	
	float time = 0f;

	float buffer = 0.2f;
	
	//create
	public MazeCombat(MazeGame gaeme) {
		this.gaeme = gaeme;
		
		batch = new SpriteBatch();
		player = new Player();
		
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		bombProj = new Projectile(bomb, bombImage, 5d);
		
		items.addItem("Bomb", bombProj);
		
		sword = new Texture("sword.png");
		swordImage = new Image(sword);
		swordImage.setOrigin(sword.getWidth()/2,0);
		Melee swordMelee = new Melee(swordImage, sword, 5d);
		
		items.addItem("Sword", swordMelee);
		
		items.testing();
	
		blob = new Texture("blob.png");

		bimg = new Texture("background.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		time += 0.2;
		// TODO Auto-generated method stub
		//reset screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(0, 0, 0.2f, 1);

		//camera
		camera.update();
		gaeme.batch.setProjectionMatrix(camera.combined);

		//attacking
		//change items collected thing to connect to card draw and inventory
		////buffer = AttackHandler.shoot(player, items.collected.get(0), buffer);
		//System.out.println(items.collected.get(1));
		buffer = AttackHandler.melee(player, items.collected.get(1), buffer);
		//TODO: add bolt
		//TODO: add misc card
		buffer -= 0.2;

		//player movement
		player.move();
		//limit where player goes
		if(player.getXPos() < 0) player.setXPos(0);
		if(player.getXPos() > 800 - 64) player.setXPos(800 - 64);

		//"actual" rendering
		gaeme.batch.begin();
		//background
		gaeme.batch.draw(bimg, 0, 0);
		//player
		gaeme.batch.draw(player.player, player.getXPos(), player.getYPos());
		//renders each projectile
		
		//stage.addActor(swordImage);
		
		for(int i=0; i<AttackHandler.getCards().size(); i++) {
			
			if(AttackHandler.getCards().get(i).getType() == 'p') {
				AttackHandler.getCards().get(i).getProj().projImage.setPosition((float)AttackHandler.getCards().get(i).getProj().getXPos(), (float)AttackHandler.getCards().get(i).getProj().getYPos());
				AttackHandler.getCards().get(i).getProj().move();
				
				stage.addActor(AttackHandler.getCards().get(i).getProj().projImage);
				
				//remove projectiles that reach edges
				if(AttackHandler.getCards().get(i).getProj().getXPos()<0-64-10 || AttackHandler.getCards().get(i).getProj().getYPos()<0-64-10 
						|| AttackHandler.getCards().get(i).getProj().getXPos()>800+64+10 || AttackHandler.getCards().get(i).getProj().getYPos()>480+64+10) {
					AttackHandler.getCards().remove(i);
					i--;
				}
			} else if(AttackHandler.getCards().get(i).getType() == 'm') {
				if (AttackHandler.getCards().get(i).getMel().swing(time)) { //might cause problems if fight lasts too long 
					AttackHandler.getCards().get(i).getMel().getImage().setPosition(player.getXPos(), player.getYPos());
					stage.addActor(AttackHandler.getCards().get(i).getMel().getImage());
				} else {
					AttackHandler.getCards().remove(i);
					i--;
				}
			}
			
			
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
		// TODO Auto-generated method stub
		bomb.dispose();
		batch.dispose();
		stage.dispose();
		blob.dispose();
	}

}
