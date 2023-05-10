package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
//import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bimg;
	private OrthographicCamera camera;
	int prev = 1;
	int time = 0;
	String last = "up";
	Texture bomb;
	Image bombImage;
	boolean shot;
	Projectile proj;
	Stage stage;
	//float stateTime = 0.0f;
	Texture blob;
	ArrayList<Projectile> projs;
	Player player;
	float buffer = 0.2f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player();
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);

		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		//boolean shot = false;
		proj = new Projectile();
		blob = new Texture("blob.png");
		projs = new ArrayList<Projectile>();

		bimg = new Texture("background.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void render () {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//stateTime += Gdx.graphics.getDeltaTime();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		   
		   
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buffer <= 0.0f) {
			//bombImage.setRotation((float) (-90 + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			bombImage = new Image(bomb);
			proj = new Projectile(player.getXPos(), player.getYPos(), 3.0, Gdx.input.getX()-player.getXPos(), 480-Gdx.input.getY()-player.getYPos(), 10, projs.size(), bombImage);
			bombImage.setRotation((float) (-90 + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			proj.setImage(bombImage);
			//System.out.println((Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			projs.add(proj);
			buffer = 1.2f;
		}
		
		buffer -= 0.05f;
			 
		   player.move();

		   if(player.getXPos() < 0) player.setXPos(0);
		   if(player.getXPos() > 800 - 64) player.setXPos(800 - 64);
		   /**/
		   batch.begin();
		   batch.draw(bimg, 0, 0);
		   batch.draw(player.player, player.getXPos(), player.getYPos());
		   if(true) {
			   for(int i=0; i<projs.size(); i++) {
				   projs.get(i).projImage.setPosition((float)projs.get(i).getXPos(), (float)projs.get(i).getYPos());
				   //batch.draw(bombImage, (float)proj.getXPos(), (float)proj.getYPos());
				   stage.addActor(projs.get(i).projImage);
				   projs.get(i).move();
				   if(projs.get(i).getXPos()<0-64-10 || projs.get(i).getYPos()<0-64-10 || projs.get(i).getXPos()>800+64+10 || projs.get(i).getYPos()>480+64+10) {
					   projs.remove(i);
					   i--;
				   }
				   //System.out.println(projs.get(i));
			   }
		   }
		   
		   batch.end();
		   stage.act();
		   stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bomb.dispose();
		stage.dispose();
	}
}
