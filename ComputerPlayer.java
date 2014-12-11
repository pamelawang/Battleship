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
    } while (!previouslyShotAt(aimAtX, aimAtY));
  }
  
  private int makeValidCoord (int coordinate) {
    int valid = (coordinate >= 1) ? coordinate : 1;
    valid = (valid <= 10) ? valid : 10;
    return valid;
  }
  
  private boolean previouslyShotAt (int x, int y) {
    return getCellAt(x,y).getShotAt();
  }
    
  
  public boolean shoot(Player other) { //Computer shooting at user
    pickAPoint();    
    other.gotShot(aimAtX, aimAtY);
    return false;
  }
  
  public static void main (String[] args) {
    
  }
  
}