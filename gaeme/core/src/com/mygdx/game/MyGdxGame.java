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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.lang.Math;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Animation<TextureRegion> img;
	Texture still;
	Texture walkf1;
	Texture walkf2;
	Texture walkb1;
	Texture walkb2;
	Texture bimg;
	private OrthographicCamera camera;
	private Rectangle box;
	Texture player;
	int prev = 1;
	int time=0;
	String last = "up";
	Texture bomb;
	Image bombImage;
	boolean shot;
	Projectile proj;
	Stage stage;
	//float stateTime = 0.0f;
	Texture blob;
	ArrayList<Projectile> projs;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		walkf1 = new Texture("walkf1.png");
		walkf2 = new Texture("walkf2.png");
		walkb1 = new Texture("walkb1.png");
		walkb2 = new Texture("walkb2.png");
		still = new Texture("still.png");
		bomb = new Texture("bomb.png");
		bombImage = new Image(bomb);
		bombImage.setOrigin(bomb.getWidth()/2,bomb.getHeight()/2);
		boolean shot = false;
		proj = new Projectile();
		blob = new Texture("blob.png");
		
		projs = new ArrayList<Projectile>();
		
		player = walkf1;
		//img = new Animation<TextureRegion>(0.05f, new TextureRegion(walk1), new TextureRegion(walk2));
		//img.setPlayMode(PlayMode.LOOP);
		bimg = new Texture("background.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		/**/
		box = new Rectangle();
		   box.x = 800 / 2 - 64 / 2;
		   box.y = 20;
		   box.width = 64;
		   box.height = 64;
		   /**/
		   
		 
		   stage = new Stage(new ScreenViewport());
	}
	
	/*
	public Texture something(Texture p, String direction) {
		if(p == walkf1 && direction.equals("down")) {
			return walkf2;
		} else if(p==walkf2 && direction.equals("down")) {
			return walkf1;
		} else if(p==walkb2 && direction.equals("up")) {
			return walkb1;
		} else {
			return walkb2;
		}
	}
	*/

	@Override
	public void render () {
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//stateTime += Gdx.graphics.getDeltaTime();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		   
		   
		   
		   if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			   //bombImage.setRotation((float) (-90 + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			   proj = new Projectile(box.x-60, box.y, 3.0, Gdx.input.getX()-box.x, 480-Gdx.input.getY()-box.y, 10, projs.size(), bombImage);
			   bombImage.setRotation((float) (-90 + Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			   proj.setImage(bombImage);
			   //System.out.println((Math.atan2(proj.getVelocity().get(1), proj.getVelocity().get(0)) * 180 / (Math.PI)));
			   /*
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX()-xPos, Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      //box.x = touchPos.x - 64 / 2;
			      box.set(playerPos.x + 20, playerPos.y, plane.getKeyFrames()[0].getRegionWidth() - 20, plane.getKeyFrames()[0].getRegionHeight());
			   */
			   projs.add(proj);
			   }
			 
		   
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			   box.x -= 200 * Gdx.graphics.getDeltaTime();
		   }
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			   box.x += 200 * Gdx.graphics.getDeltaTime();
		   }
		   
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			   box.y += 200 * Gdx.graphics.getDeltaTime();
			   if(time > 10) {
				   if(last.equals("up")) {
					   if(prev == 1) {
						   player = walkb2;
						   prev = 2;
					   } else {
						   player = walkb1;
						   prev = 1;
					   }
				   } else {
					   player = walkb1;
					   last = "up";
				   }
				   time = 0;
			   } else {
				   time ++;
			   }
			   
		   }
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			   box.y -= 200 * Gdx.graphics.getDeltaTime();
			   if(time > 10) {
				   if(last.equals("down")) {
					   if(prev == 1) {
						   player = walkf2;
						   prev = 2;
					   } else {
						   player = walkf1;
						   prev = 1;
					   }
				   } else {
					   player = walkf1;
					   last = "down";
				   }
				   time = 0;
			   } else {
				   time++;
			   }
		   }
		   if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			   player = still;
		   }
		   //if(Gdx.input.isButtonPressed(Input.Buttons.))

		   if(box.x < 0) box.x = 0;
		   if(box.x > 800 - 64) box.x = 800 - 64;
		   /**/
		   batch.begin();
		   batch.draw(bimg, 0, 0);
		   batch.draw(player, box.x, box.y);
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
			   }
		   }
		   
		   //batch.draw(img, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		   //TextureRegion currentFrame = img.getKeyFrame(stateTime, true);
		   //batch.draw(currentFrame, 50, 50);
		   
		   batch.end();
		   stage.act();
		   stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		still.dispose();
		walkf1.dispose();
		walkf2.dispose();
		walkb1.dispose();
		walkb2.dispose();
		bomb.dispose();
		stage.dispose();
	}
}
