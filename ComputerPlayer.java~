/* NAME: ComputerPlayer.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 10 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: 
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

public class ComputerPlayer extends Player {
  
  //instance variables:
//  private BinaryTree
  private int aimAtX;
  private int aimAtY;
//  private boolean lastShotWasHit; //will be used when we're using boats longer than a cell
  private final int INVALID = -1;
  
  public ComputerPlayer() {
    super();
    aimAtX = INVALID;
    aimAtY = INVALID;
  }
  
  private void pickAPoint() { //randomiser
    do {
    int randomX = (int) (Math.random()*10);
    aimAtX = makeValidCoord(randomX);
    int randomY = (int) (Math.random()*10);
    aimAtY = makeValidCoord(randomY);
    System.out.println("pickAPoint() used x = " + aimAtX + " and y = " + aimAtY);
    } while (previouslyShotAt(aimAtX-1, aimAtY-1));
  }
  
  private int makeValidCoord (int coordinate) {
    int valid = (coordinate >= 1) ? coordinate : 1;
    valid = (valid <= getGridDimensions()) ? valid : getGridDimensions();
    System.out.println("makeValidCoord: coordinate = " + coordinate + " and valid = " + valid);
    return valid;
  }
  
  private boolean previouslyShotAt (int x, int y) {
    return getCellAt(x,y).getShotAt();
  }
    
  
  public boolean shoot(Player other) throws InvalidShotException { //Computer shooting at user
    pickAPoint();    
    other.gotShot(aimAtX, aimAtY);
    System.out.println("Computer shooting (Y)");
    return false;
  }
  
  public static void main (String[] args) {
    
  }
  
}