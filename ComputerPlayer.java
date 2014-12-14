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
    int gridX = xCoord-1;
    int gridY = yCoord-1; //unconcise for clarity reasons
    return shotSoFarGrid[gridX][gridY].getShotAt();
  }
    
  
  public boolean shoot(Player other) throws InvalidShotException { //Computer shooting at user
    pickAPoint();    
    other.gotShot(aimAtX, aimAtY);
    int gridX = aimAtX - 1;
    int gridY = aimAtY - 1;
    shotSoFarGrid[gridX] [gridY].setShotAt(true);
    System.out.println("Computer shoot()");
    return false;
  }
  
  public void placeAllBoats() {
    //just for now:
    placeBoat(0, 1, 1);
    placeBoat(1, 2, 2);
    placeBoat(2, 3, 3);
    //TO COME BACKT O, use Player's placeBoat() method
  }
  
  public static void main (String[] args) {
    
  }
  
}