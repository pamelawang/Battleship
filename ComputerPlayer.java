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
  private int boatOrientation; //bad coding principles? need to access in setEndCoords()
  
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
      Boat blackPearl = fleet.get(i);
      setCoords(blackPearl, direction); //makes and checks Boat coordinates
//      placeBoat(i, blackPearl.getStartX(), blackPearl.getStartY()); //method from Player; i is for boatIndex in fleet
      System.out.println("Boat " + Integer.toString(i) + "\n" + blackPearl + "\n");
    }
    
    System.out.println(findMyFleet());
  }
  
  //helpers for PlaceBoats
  private void setCoords(Boat b, int direction) {
    //THEORETICALLY CAN STILL USE PICKAPOINT()
    System.out.println("setCoords(): X");
    b.setStartX(makeValidCoord((int)Math.random()*10)); //using Boat's getters and setters as 'local' variables
    System.out.println("setCoords(): Y");
    b.setStartY(makeValidCoord((int)Math.random()*10));
    System.out.println("setCoords(): " + grid[b.getStartX()-1][b.getStartY()-1].getHasBoat());
    if (grid[b.getStartX()-1][b.getStartY()-1].getHasBoat()) { //no boat
      System.out.println("setCoords(): checking for previous boat. Found one.");
      setCoords(b, direction); //find new coordinates
    } else {
      setEndCoords(b, direction);
    }
    
    
    
    /*OLD CODE BELOW
     if (foundEndCoords == false) {
     switch (direction) {
     case 2: //UP, vertical orientation (startX == endX)
     //Java or DrJava doesn't allow for the use of constants in switch statements, so we're hard coding here
     b.setEndX(b.getStartX()); //same x-coordinate
     b.setEndY(setEndCoords(b, direction, 1)); //here with Meera
     foundEndCoords = true;
     break;
     
     case 3: //DOWN, vertical orientation
     b.setEndX(b.getStartX());
     b.setEndY(setEndCoords(b, direction, 1));
     foundEndCoords = true;
     break;
     
     case 4: //LEFT, horizontal (startY == endY)
     b.setEndY(b.getEndY()); //same y-coordinate
     b.setEndX(setEndCoords(b, direction, 1));
     foundEndCoords = true;
     break;
     
     case 5: //RIGHT, horizontal
     b.setEndY(b.getEndY());
     b.setEndX(setEndCoords(b, direction, 1));
     foundEndCoords = true;
     break;
     
     default:
     break;
     }
     } else { //foundEndCoords == true
     return;
     }*/
  }
  
  
  private void setEndCoords(Boat ship, int direction) { //recursive
    int startX = ship.getStartX();
    int startY = ship.getStartY();
    int length = ship.getLength();
    boolean foundEndCoords = false;
    int directionsTried = 0;
    
    while (directionsTried <= 4 && !foundEndCoords) {
      if (direction == UP) { //direction
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY - (length-1));
        if (isCoordValid(ship.getEndY())) {
          foundEndCoords = true;
        } else {
          direction++;
        }
      } else if (direction == DOWN) { //direction == 3
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY + (length-1));
        if (isCoordValid(ship.getEndY())) {
          foundEndCoords = true;
        } else {
          direction++;
        }
      } else if (direction == LEFT) { //direction == 4
        directionsTried++;
        ship.setEndX(startX - (length-1));
        ship.setEndY(startY);
        if (isCoordValid(ship.getEndX())) {
          foundEndCoords = true;
        } else {
          direction++;
        }
      } else { //direction == RIGHT (5)
        directionsTried++;
        ship.setEndX(startX + (length-1));
        ship.setEndY(startY);
        if (isCoordValid(ship.getEndX())) {
          foundEndCoords = true;
        } else {
          direction = UP; //restarting while loop
        }
      }
    }
    
    if (!foundEndCoords) {
      setCoords(ship, direction); //finding new starting coordinates
    }
    
    /*OLD CODE
     int coordinate = -1;
     if (tries > 4) { //tried all directions
     setCoords(ship, direction); //start anew and find new starting coordinates
     } else if (tries > 2) { //tried both directions; tries is 
     if (boatOrientation == VERTICAL) {
     boatOrientation = HORIZONTAL;
     setEndCoords(ship, getLeftOrRight(), tries);
     } else { //boatOrientation == HORIZONTAL
     boatOrientation = VERTICAL;
     setEndCoords(ship, getUpOrDown(), tries);
     }
     }
     
     //
     if (boatOrientation == VERTICAL) { //coordinate = endIndexY
     if (direction == UP) {
     coordinate = ship.getStartY() - (ship.getLength()-1);
     coordinate = (coordinate < 1) ? setEndCoords(ship, DOWN, tries++) : coordinate;
     } else if (direction == DOWN) {
     coordinate = ship.getStartY() + (ship.getLength()-1);
     coordinate = (coordinate > GRID_DIMENSIONS) ? setEndCoords(ship, UP, tries++) : coordinate;
     }
     } else { //boatOrientation == HORIZONTAL; coordinate = endIndexX
     if (direction == LEFT) {
     coordinate = ship.getStartX() - (ship.getLength()-1);
     coordinate = (coordinate < 1) ? setEndCoords(ship, RIGHT, tries++) : coordinate;
     } else { //direction == RIGHT
     coordinate = ship.getStartX() + (ship.getLength()-1);
     coordinate = (coordinate > GRID_DIMENSIONS) ? setEndCoords(ship, LEFT, tries++) : coordinate;
     }
     }
     
     return coordinate;*/
  }
  
  private boolean isCoordValid(int coord) {
    return (coord < 1 || coord > GRID_DIMENSIONS);
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
    System.out.println("findMyFleet(): " + c.findMyFleet());
    c.placeBoats();
  }
  
}