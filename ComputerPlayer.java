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
  private Cell[][] shotSoFarGrid;
  
  public ComputerPlayer() {
    super();
    aimAtX = INVALID;
    aimAtY = INVALID;
    shotSoFarGrid = new Cell[GRID_DIMENSIONS][GRID_DIMENSIONS];
    for (int i = 0; i < GRID_DIMENSIONS; i++) {
      for (int j = 0; j < GRID_DIMENSIONS; j++) {
        shotSoFarGrid[i][j] = new Cell();
      }
    }
  }
  
  private void pickAPoint() { //randomiser
    do {
    int randomX = (int) (Math.random()*10);
    aimAtX = makeValidCoord(randomX);
    int randomY = (int) (Math.random()*10);
    aimAtY = makeValidCoord(randomY);
    System.out.println("pickAPoint() used x = " + aimAtX + " and y = " + aimAtY);
    } while (previouslyShotAt(aimAtX, aimAtY));
  }
  
  private int makeValidCoord (int coordinate) { //checks if coordinate is within grid dimensions
    int valid = (coordinate >= 1) ? coordinate : 1;
    valid = (valid <= getGridDimensions()) ? valid : getGridDimensions();
    System.out.println("makeValidCoord: coordinate = " + coordinate + " and valid = " + valid);
    return valid;
  }
  
  private boolean previouslyShotAt (int xCoord, int yCoord) {
    return shotSoFarGrid[xCoord-1][yCoord-1].getShotAt();
  }
    
  
  public boolean shoot(Player other) throws InvalidShotException { //Computer shooting at user
    pickAPoint();    
    other.gotShot(aimAtX, aimAtY);
    shotSoFarGrid[aimAtX-1][aimAtY-1].setShotAt(true);
    System.out.println("Computer shooting (Y)");
    return false;
  }
  
  public static void main (String[] args) {
    
  }
  
}