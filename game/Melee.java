package com.mygdx.game;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Input;

public class Melee {
	private double damage; 
	private double knockback;
	private double swingTime; // time in seconds
	private float swingRange; //in radians
	// 	private float length;
	private float startAngle;
	
	private Texture txte;
	private Image melImg; 
	
	private double tick = 0;
	private float maxCoolDown;
	private float coolDown = 0.0f;
	private boolean swinging = false;
	
	public Melee() {
		;
	}
	
// 	public Melee(Image melImg, Texture txte, double damage, double knockback, double speed, float radius) {
// 		//this.box = new Rectangle();
		
// 		this.txte = txte;
// 		this.melImg = new Image(txte);
// 		this.melImg.setOrigin((float)txte.getWidth()/2, 0);
// // 		this.melImg = melImg;
// 		this.speed = speed;
// 		this.radius = radius;
// 		this.damage = damage;
// 		this.knockback = knockback;
// 	}
	
	public Melee(double damage, float swingRange, double swingTime, double knockback, float maxCoolDown, Texture txte) {
		
		this.damage = damage;
		this.swingRange = swingRange;
		this.swingTime = swingTime;
		this.knockback = knockback;
		this.maxCoolDown = maxCoolDown;
		
		this.txte = txte;
		this.melImg = new Image(txte);
		this.melImg.setOrigin((float)txte.getWidth()/2, 0);
	}
	
// 	public float getLength() {
// 		return this.length;
// 	}
// 	public float getRadius() {
// 		return this.radius;
// 	}
// 	public Texture getTexture() {
// 		return this.txte;
// 	}
// 	public Image getImage() {
// 		return this.melImg;
// 	}
// 	public double getDamage() {
// 		return this.damage;
// 	}
// 	public double getKnockback() {
// 		return this.knockback;
// 	}
// 	public double getSpeed() {
// 		return this.speed;
// 	}
// 	public double getXPos() {
// 		return this.xPos;
// 	}
// 	public double getYPos() {
// 		return this.yPos;
// 	}
// 	public float getStartAngle() {
// 		return this.startAngle;
// 	}
	
	public void update(Player player) {
		//rotate the thing
		if (swinging) {
			melImg.rotateBy(180f/(float)Math.PI * (float)swingRange / (float)time);
			tick += swingRange/time;
		}
		
		
		// decrement coolDown
		coolDown = Math.max(coolDown -= 0.2f, 0);
		// check whether to start attack
		if(coolDown == 0 && Gdx.input.isKeyPressed(Input.Buttons.LEFT)) {
			for(int i=0; i<EnemyHandler.getEnemies().size(); i++) {
				Enemy tempem = EnemyHandler.getEnemies(i);

				float enan = -90f + 180f/(float)Math.PI * (float)(Math.atan2(tempem.getYPos()-player.getYPos(), tempem.getXPos()-player.getImage().getWidth()/2-player.getXPos()));

				if((Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= 10f)
				   || (Math.sqrt(Math.pow(tempem.getXPos()-player.getXPos(), 2) + Math.pow(tempem.getYPos() - player.getYPos(),2)) <= temp.getMel().getTexture().getHeight()+10)
		        	   && (Math.abs(temp.getMel().getStartAngle() - enan + temp.getMel().getRadius() / 2 * 180 / Math.PI) <= temp.getMel().getRadius() / 2 * 180 / Math.PI)) {

					EnemyHandler.getEnemies().get(i).takeDamage(damage, (float)knockback, enan);
				}
			}
			
			float angle = -90f + 180f/(float)Math.PI * (float)(Math.atan2(Gdx.graphics.getHeight()-Gdx.input.getY()-player.getYPos(), Gdx.input.getX()-player.getImage().getWidth()/2-player.getXPos()) - swingRange / 2);
			melImg.setRotation(angle);
			
			swinging = true;
			tick = 0;
			coolDown = maxCoolDown;
		}
		
		setPosition(player.getXPos(), player.getYPos() + 20);
	}
	
// 	public boolean swing(Player player) {
// 		//rotate the thing
// 		if(tick <= 1) {
// 			melImg.rotateBy(180f/(float)Math.PI * (float)radius / (float)speed);
// 			tick += radius/speed/2;
// 			//System.out.println(tick);
			
// 			//box.x = player.getXPos() + ;
// 			//box.y = player.getYPos() + ;
// 			move(player.getXPos(), player.getYPos());
			
// 			return true;
// 		} else {
// 			tick = 0;
// 			return false;
// 		}
// 	}
	public void setPosition(double xPos, double yPos) {
		//update box;
		melImg.setPosition(xPos, yPos);
	}
}
