import java.util.*;

public class Mazegame {
  
  private bool w = false;
  private bool a = false;
  private bool s = false;
  private bool d = false;
  
  // map variables
  private int xPos = 49;
  private int yPos = 49;
  
  // fight variables
  private double xPosFight = 7.0;
  private double yPosFight = 7.0;
  private double xVelFight = 0.0;
  private double yVelFight = 0.0;
  
  // buffs
  private double speedModifier = 1;
  
  private bool inInventory = false;
  private bool inFight = false;
  
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
    int xVel = 0;
    int yVel = 0;
    if 'w' pressed && !w
      w = true;
      yVel++;
    else
      w = false
    if 'a' pressed && !a
      a = true;
      xVel--;
    else
      a = false
    if 's' pressed && !s
      s = true;
      yVel--;
    else
      s = false
    if 'd' pressed && !d
      d = true;
      xVel++;
    else
      d = false

    xPos += xVel;
    yPos += yVel;
    
    // display stuff
  }
  
  public static void fightState() {
    int xVel = 0;
    int yVel = 0;
    if 'w' pressed
      w = true;
      yVel++;
    else
      w = false
    if 'a' pressed
      a = true;
      xVel--;
    else
      a = false
    if 's' pressed
      s = true;
      yVel--;
    else
      s = false
    if 'd' pressed
      d = true;
      xVel++;
    else
      d = false

    xVelFight += sign(xVelFight - xVel) * min(0.1, xVelFight - xVel);
    yVelFight += sign(yVelFight - yVel) * min(0.1, yVelFight - yVel);
    xPosFight += xVelFight * speedModifier;
    yPosFight += yVelFight * speedModifier;

    if clicking
      do specified weapon attack in mouse direction
      vector: <mouseX - xPosFight, mouseY - yPosFight>
    
    // display stuff
  }
  
  public static void main(String[] args) {
    
    // initialization stuff
    
    while (true) {
      if (!inInventory) {
        if (!inFight) {
          mapState();
        } else {
          fightState();
        }
      } else {
        // inventory interface
      }
    }
    
  }
}
