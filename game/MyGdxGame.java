package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyGdxGame extends ApplicationAdapter {
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
	
	float buffer = 0.2f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player();
		
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		bombProj = new Projectile(bomb, bombImage, 5d);
		
		items.addItem("Bomb", bombProj);
		items.testing();
	
		blob = new Texture("blob.png");

		bimg = new Texture("background.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void render () {
		//reset screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		//camera
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		//attacking
		buffer = AttackHandler.shoot(player, items.collected.get(0), buffer);
		//TODO: add melee
		//TODO: add bolt
		//TODO: add misc card
		buffer -= 0.2;
		
		//player movement
		player.move();
		//limit where player goes
		if(player.getXPos() < 0) player.setXPos(0);
		if(player.getXPos() > 800 - 64) player.setXPos(800 - 64);
		
		//"actual" rendering
		batch.begin();
		//background
		batch.draw(bimg, 0, 0);
		//player
		batch.draw(player.player, player.getXPos(), player.getYPos());
		//renders each projectile
		for(int i=0; i<AttackHandler.getProjs().size(); i++) {
			AttackHandler.getProjs().get(i).projImage.setPosition((float)AttackHandler.getProjs().get(i).getXPos(), (float)AttackHandler.getProjs().get(i).getYPos());
			stage.addActor(AttackHandler.getProjs().get(i).projImage);
			AttackHandler.getProjs().get(i).move();
			//remove projectiles that reach edges
			if(AttackHandler.getProjs().get(i).getXPos()<0-64-10 || AttackHandler.getProjs().get(i).getYPos()<0-64-10 
					|| AttackHandler.getProjs().get(i).getXPos()>800+64+10 || AttackHandler.getProjs().get(i).getYPos()>480+64+10) {
				AttackHandler.getProjs().remove(i);
				i--;
			}
		}

		batch.end();
		
		//for projectiles
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		bomb.dispose();
		batch.dispose();
		stage.dispose();
		blob.dispose();
	}
}
