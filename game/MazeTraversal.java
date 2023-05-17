package com.mygdx.game;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MazeTraversal implements Screen {

	final MazeGame game;

	OrthographicCamera camera;

	public MazeTraversal(final MazeGame gaeme) {
		this.game = gaeme;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		maze = Generator.generateMaze();

	    visibleMaze = new char[59][59];
	    for (int i = 0; i < 59; i++) { 
	      for (int o = 0; o < 59; o++) {
	        visibleMaze[i][o] = '*';
	      }
	    }
	    
	    while (true) {
	      if (maze[yPos][xPos] == 'e') return;
//	       if (!inInventory) {
//	         if (!inFight) {
	      visibleMaze[yPos][xPos] = maze[yPos][xPos];
	      visibleMaze[yPos + 1][xPos] = maze[yPos + 1][xPos];
	      visibleMaze[yPos - 1][xPos] = maze[yPos - 1][xPos];
	      visibleMaze[yPos][xPos + 1] = maze[yPos][xPos + 1];
	      visibleMaze[yPos][xPos - 1] = maze[yPos][xPos - 1];
	      for (int i = yPos - 4; i <= yPos + 4; i++) {
	        for (int o = xPos - 4; o <= xPos + 4; o++) {
	          if (i == yPos && o == xPos) System.out.print("@ ");
	          else if (i < 0 || i > 58 || o < 0 || o > 58) System.out.print("* ");
	          else System.out.print(visibleMaze[i][o] + " ");
	        }
	        System.out.println();
	      }
	      mapState();
	    }
	    
	}
	
	@Override
	public void render(float deltaTime) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "You do nothing", 100, 150); //placeholders
		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
		game.batch.end();

		
		//combat trigger
//		if (Gdx.input.isTouched()) {
//			game.setScreen(new MazeCombat(game));
//			dispose();
//		}
	}
	
	 private static char[][] maze;
	  private static char[][] visibleMaze;
	  
	   private boolean w = false;
	   private boolean a = false;
	   private boolean s = false;
	   private boolean d = false;
	  
	  // map variables
	  private static int xPos = 29;
	  private static int yPos = 29;
	  private static Scanner sc = new Scanner(System.in);
	  
	  // fight variables
	//   private double xPosFight = 7.0;
	//   private double yPosFight = 7.0;
	//   private double xVelFight = 0.0;
	//   private double yVelFight = 0.0;
	  
	  // buffs
	//   private double speedModifier = 1;
	  
	   private boolean inInventory = false;
	   private boolean inFight = false;
	  
	  public static double abs(double a) {
	    if (a >= 0) return a;
	    return -a;
	  }
	  
	  public static double min(double a, double b) {
	    if (a < b) return a;
	    return b;
	  }
	  
	  public static double max(double a, double b) {
	    if (a > b) return a;
	    return b;
	  }
	  
	  public static double sign(double a) {
	    if (a == 0) return 1;
	    return a / abs(a);
	  }
	  
	  public static void mapState() {
//	     int xVel = 0;
//	     int yVel = 0;
//	     if 'w' pressed && !w
//	       w = true;
//	       yVel++;
//	     else
//	       w = false
//	     if 'a' pressed && !a
//	       a = true;
//	       xVel--;
//	     else
//	       a = false
//	     if 's' pressed && !s
//	       s = true;
//	       yVel--;
//	     else
//	       s = false
//	     if 'd' pressed && !d
//	       d = true;
//	       xVel++;
//	     else
//	       d = false

	    
//	     xPos += xVel;
//	     yPos += yVel;
	    
	    // display stuff
	    
	    
	    String s = sc.next();
	    char c = s.charAt(s.length() - 1);
	    switch (c) {
	      case 'w':
	        if (maze[yPos - 1][xPos] != '#') {
	          yPos--;
	        }
	        break;
	      case 'a':
	        if (maze[yPos][xPos - 1] != '#') {
	          xPos--;
	        }
	        break;
	      case 's':
	        if (maze[yPos + 1][xPos] != '#') {
	          yPos++;
	        }
	        break;
	      case 'd':
	        if (maze[yPos][xPos + 1] != '#') {
	          xPos++;
	        }
	        break;
	      case 'm':
	        for (int i = 0; i < 59; i++) {
	            for (int o = 0; o < 59; o++) {
	                System.out.print(visibleMaze[i][o] + " ");
	            }
	            System.out.println();
	        }
	        break;
	        
	    }
	  }

	@Override
	public void resize(int width, int height) {
		;
	}
	
	@Override
	public void show() {
		;
	}
	
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void dispose() {
		;
	}
}
