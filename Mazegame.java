import java.util.*;

public class Mazegame {
  
  private static char[][] maze;
  
//   private bool w = false;
//   private bool a = false;
//   private bool s = false;
//   private bool d = false;
  
  // map variables
  private static int xPos = 49;
  private static int yPos = 49;
  private static Scanner sc = new Scanner(System.in);
  
  // fight variables
//   private double xPosFight = 7.0;
//   private double yPosFight = 7.0;
//   private double xVelFight = 0.0;
//   private double yVelFight = 0.0;
  
  // buffs
//   private double speedModifier = 1;
  
//   private bool inInventory = false;
//   private bool inFight = false;
  
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
//     int xVel = 0;
//     int yVel = 0;
//     if 'w' pressed && !w
//       w = true;
//       yVel++;
//     else
//       w = false
//     if 'a' pressed && !a
//       a = true;
//       xVel--;
//     else
//       a = false
//     if 's' pressed && !s
//       s = true;
//       yVel--;
//     else
//       s = false
//     if 'd' pressed && !d
//       d = true;
//       xVel++;
//     else
//       d = false

    
//     xPos += xVel;
//     yPos += yVel;
    
    // display stuff
    
    
    char c = sc.next().charAt(0);
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
    }
  }
  
//   public static void fightState() {
//     int xVel = 0;
//     int yVel = 0;
//     if 'w' pressed
//       w = true;
//       yVel++;
//     else
//       w = false
//     if 'a' pressed
//       a = true;
//       xVel--;
//     else
//       a = false
//     if 's' pressed
//       s = true;
//       yVel--;
//     else
//       s = false
//     if 'd' pressed
//       d = true;
//       xVel++;
//     else
//       d = false

//     xVelFight += sign(xVelFight - xVel) * min(0.1, xVelFight - xVel);
//     yVelFight += sign(yVelFight - yVel) * min(0.1, yVelFight - yVel);
//     xPosFight += xVelFight * speedModifier;
//     yPosFight += yVelFight * speedModifier;

//     if clicking
//       do specified weapon attack in mouse direction
//       vector: <mouseX - xPosFight, mouseY - yPosFight>
    
//     // display stuff
//   }
  
  public static void main(String[] args) {
    
    maze = Generator.generateMaze();
    char[][] visibleMaze = new char[99][99];
    for (int i = 0; i < 99; i++) { 
      for (int o = 0; o < 99; o++) {
        visibleMaze[i][o] = '*';
      }
    }
    // initialization stuff
    
    while (true) {
      if (maze[yPos][xPos] == 'e') return;
//       if (!inInventory) {
//         if (!inFight) {
      visibleMaze[yPos][xPos] = maze[yPos][xPos];
      visibleMaze[yPos + 1][xPos] = maze[yPos + 1][xPos];
      visibleMaze[yPos - 1][xPos] = maze[yPos - 1][xPos];
      visibleMaze[yPos][xPos + 1] = maze[yPos][xPos + 1];
      visibleMaze[yPos][xPos - 1] = maze[yPos][xPos - 1];
      for (int i = yPos - 4; i <= yPos + 4; i++) {
        for (int o = xPos - 4; o <= xPos + 4; o++) {
          if (i == yPos && o == xPos) System.out.print("@ ");
          else if (i < 0 || i > 98 || o < 0 || o > 98) System.out.print("* ");
          else System.out.print(visibleMaze[i][o] + " ");
        }
        System.out.println();
      }
      mapState();
//         } else {
//           fightState();
//         }
//       } else {
        // inventory interface
//       }
    }
    
  }
}
