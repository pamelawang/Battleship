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
  //to be updated:
  private int VERTICAL = 0;
  private int HORIZONTAL = 1;
  private int UP = 2;
  private int DOWN = 3;
  private int LEFT = 4;
  private int RIGHT = 5;
  private int boatOrientation; //bad coding principles? need to access in findEndIndex()
  
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
    valid = (valid <= GRID_DIMENSIONS) ? valid : GRID_DIMENSIONS;
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
  
  public void placeBoats() { // != placeBoat() from Player
    for (int i = 0; i < getNumBoats(); i++) {
      boatOrientation = getBoatOrientation();
      int direction = (boatOrientation == VERTICAL) ? getUpOrDown() : getLeftOrRight();
      Boat blackPearl = new Boat("enemy" + Integer.toString(i), BOAT_LENGTHS[i]);
      //check coordinates here
      findIndices(blackPearl, direction);
      fleet.add(blackPearl);
      placeBoat(i, blackPearl.getStartX(), blackPearl.getStartY()); //method from Player; i is for boatIndex in fleet
      System.out.println("Boat " + Integer.toString(i) + "\n" + blackPearl + "\n");
    }
    
    System.out.println(findMyFleet());
  }
  
  //helpers for PlaceBoats
  private void findIndices(Boat b, int direction) {
    try {
      b.setStartX(makeValidCoord((int)Math.random()*10)); //using Boat's getters and setters as 'local' variables
      b.setStartY(makeValidCoord((int)Math.random()*10));
      boolean foundEndIndices = false;
      
      if (grid[b.getStartX()-1][b.getStartY()-1].getShotAt()) {
        findIndices(b, direction); //find new coordinates
      }
      
      if (foundEndIndices == false) {
        switch (direction) {
          case 2: //UP, vertical orientation (startX == endX)
            //Java or DrJava doesn't allow for the use of constants in switch statements, so we're hard coding here
            b.setEndX(b.getStartX()); //same x-coordinate
            b.setEndY(findEndIndex(b, direction, 1));
            foundEndIndices = true;
            break;
            
          case 3: //DOWN, vertical orientation
            b.setEndX(b.getStartX());
            b.setEndY(findEndIndex(b, direction, 1));
            foundEndIndices = true;
            break;
            
          case 4: //LEFT, horizontal (startY == endY)
            b.setEndY(b.getEndY()); //same y-coordinate
            b.setEndX(findEndIndex(b, direction, 1));
            foundEndIndices = true;
            break;
            
          case 5: //RIGHT, horizontal
            b.setEndY(b.getEndY());
            b.setEndX(findEndIndex(b, direction, 1));
            foundEndIndices = true;
            break;
            
          default:
            break;
        }
      } else { //foundEndIndices == true
        return;
      }
    } catch (InvalidCoordinateException e) {
      System.out.println("ERROR A - trying again\n");
      //findIndices(b, direction);
    }
  }
  
  private int findEndIndex(Boat ship, int direction, int tries) { //recursive
    int index;
    if (tries > 4) { //tried all directions
      findIndices(ship, direction); //start anew and find new starting coordinates
    } else if (tries > 2) { //tried both directions; tries is 
      if (boatOrientation == VERTICAL) {
        boatOrientation = HORIZONTAL;
        findEndIndex(ship, getLeftOrRight(), tries++);
      } else { //boatOrientation == HORIZONTAL
        boatOrientation = VERTICAL;
        findEndIndex(ship, getUpOrDown(), tries++);
      }
    }
    
    //
    if (boatOrientation == VERTICAL) { //coordinate = endIndexY
      if (direction == UP) {
        index = ship.getStartY() - ship.getLength();
        index = (index < 0) ? findEndIndex(ship, DOWN, tries++) : index;
      } else { //direction == DOWN
        index = ship.getStartY() + ship.getLength();
        index = (index >= GRID_DIMENSIONS) ? findEndIndex(ship, UP, tries++) : index;
      }
    } else { //boatOrientation == HORIZONTAL; coordinate = endIndexX
      if (direction == LEFT) {
        index = ship.getStartX() - ship.getLength();
        index = (index < 0) ? findEndIndex(ship, RIGHT, tries++) : index;
      } else { //direction == DOWN
        index = ship.getStartX() + ship.getLength();
        index = (index >= GRID_DIMENSIONS) ? findEndIndex(ship, LEFT, tries++) : index;
      }
    }
    
    return index;
  }
  
  private int getBoatOrientation() { //0 = vertical, 1 = horizontal
    return (int)Math.round(Math.random());
  }
  
  private int getUpOrDown() { //2 = up, down = 3
    return (int)Math.round(Math.random() + 2);
  }
  
  private int getLeftOrRight() { //4 = left, 5 = right
    return (int)Math.round(Math.random() + 4);
  }
  
  public static void main (String[] args) {
    ComputerPlayer c = new ComputerPlayer();
    c.placeBoats();
  }
  
}