/* NAME: ComputerPlayer.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 10 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: 
 * 
 * NOTES:
 * 1. Need to fix the checking for a boat (doesBoatOverlap()) - have to start from the
 * least between start and end.
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
  private int VERTICAL = 0;
  private int HORIZONTAL = 1;
  private int UP = 2;
  private int DOWN = 3;
  private int LEFT = 4;
  private int RIGHT = 5;
  private int boatOrientation;
  
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
      aimAtX = withinGridDimensions(randomX);
      int randomY = (int) (Math.random()*10);
      aimAtY = withinGridDimensions(randomY);
      System.out.println("pickAPoint() used x = " + aimAtX + " and y = " + aimAtY);
    } while (previouslyShotAt(aimAtX, aimAtY));
  }
  
  private int withinGridDimensions (int coordinate) { //not the same as Player's withinGridDimensions
    int valid = (coordinate >= 1) ? coordinate : 1;
    valid = (valid <= GRID_DIMENSIONS) ? valid : GRID_DIMENSIONS;
    System.out.println("withinGridDimensions: coordinate = " + coordinate + " and valid = " + valid);
    return valid;
  }
  
  private boolean previouslyShotAt (int xCoord, int yCoord) {
    return shotSoFarGrid[xCoord-1][yCoord-1].getShotAt();
  }
  
  public boolean shoot(Player other) throws InvalidShotException { //Computer shooting at user
    boolean indicator = false; //miss
    pickAPoint();    
    int hitOrMiss = other.gotShot(aimAtX, aimAtY);
    shotSoFarGrid[aimAtX-1][aimAtY-1].setShotAt(true);
    System.out.println("Computer shooting");
    indicator = (hitOrMiss < 1) ? false : true; //if hitOrMiss is 0 than the opponent missed; 1 means hit and 2 means sunk
    System.out.println("INDICATOR IS: " + indicator);
    return indicator;
  }
  
  public void placeBoats() { // != placeBoat() from Player
    for (int i = 0; i < getNumBoats(); i++) {
      System.out.println("/////////////////////////placeBoats() round " + (i+1) + " //////////////////////////////////");
      boatOrientation = getBoatOrientation();
      int direction = (boatOrientation == VERTICAL) ? getUpOrDown() : getLeftOrRight();
      Boat blackPearl = fleet.get(i);
      setCoords(blackPearl, direction); //makes and checks Boat coordinates
      
      if (direction == UP || direction == DOWN) {
        System.out.println("placeBoats(): Boat's upward/downward. Changing cells to have boat.");
        for (int j = blackPearl.getStartY(); j <= blackPearl.getEndY(); j++) {
        //sets the cells between starting and ending coordinates to hasBoat() state
          grid[blackPearl.getStartX()-1][j-1].setHasBoat(true);
        }
      } else { //direction == LEFT || RIGHT
        System.out.println("placeBoats(): Boat's left/right. Changing cells to have boat.");
        for (int j = blackPearl.getStartX(); j <= blackPearl.getEndX(); j++) {
          grid[j-1][blackPearl.getStartY()-1].setHasBoat(true);
        }
      }
      System.out.println("Boat " + Integer.toString(i+1) + "\n" + blackPearl + "\n");
      System.out.println("placeBoats(): GRID PRINT OUT" + printGrid());
    }
    
    System.out.println(findMyFleet());
  }
  
  //helpers for PlaceBoats
  private void setCoords(Boat b, int direction) {
//    System.out.println("************setCoords() with " + b.getBoatName() + " and direction " + direction + " *************");
//      System.out.println("setCoords(): X");
    //using Boat's instance variables as 'local' variables through its getters/setters
    b.setStartX(withinGridDimensions((int)(Math.random()*10)));
//      System.out.println("setCoords(): Y");
    b.setStartY(withinGridDimensions((int)(Math.random()*10)));
    int startX = b.getStartX();
    int startY = b.getStartY();
    
    System.out.println("setCoords(): Already a boat? " + grid[startX-1][startY-1].getHasBoat());
    if (grid[startX-1][startY-1].getHasBoat()) { //has a boat
      System.out.println("setCoords(): checking for previous boat. Found one.");
      setCoords(b, direction); //find new starting coordinates
    } else { //no boat
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
  
  
  private void setEndCoords(Boat ship, int direction) {
    int startX = ship.getStartX();
    int startY = ship.getStartY();
    int length = ship.getLength();
    boolean foundEndCoords = false;
    int directionsTried = 0;
    
    if (directionsTried > 0) { System.out.println("directionsTried is " + directionsTried + " and new direction is " + direction); }
    
    while (directionsTried <= 4 && !foundEndCoords) {
//        System.out.println("setEndCoords(): directionsTried: " + directionsTried
//                             + " Haven't found the endCoords.");
      if (direction == UP) { //direction == 2
        System.out.println("setEndCoords(): Direction = UP");
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY - (length-1));
//          System.out.println("setEndCoords(): endY = "+ (startY - (length-1)));
        if (isCoordValid(ship.getEndY()) && !doesBoatOverlap(ship, direction)) {
          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is valid");
          foundEndCoords = true;
        } else {
          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ")is not valid. Direction++");
          direction++;
        }
      } else if (direction == DOWN) { //direction == 3
        System.out.println("setEndCoords(): Direction = DOWN");
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY + (length-1));
//          System.out.println("setEndCoords(): endY = "+ (startY + (length-1)));
        if (isCoordValid(ship.getEndY()) && !doesBoatOverlap(ship, direction)) {
          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is valid");
          foundEndCoords = true;
        } else {
          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is not valid. Direction++");
          direction++;
        }
      } else if (direction == LEFT) { //direction == 4
        System.out.println("setEndCoords(): Direction = LEFT");
        directionsTried++;
        ship.setEndX(startX - (length-1));
//          System.out.println("setEndCoords(): endX = "+ (startX - (length-1)));
        ship.setEndY(startY);
        if (isCoordValid(ship.getEndX()) && !doesBoatOverlap(ship, direction)) {
          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is valid");
          foundEndCoords = true;
        } else {
          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is not valid. Direction++");
          direction++;
        }
      } else { //direction == RIGHT (5)
        System.out.println("setEndCoords(): Direction = RIGHT");
        directionsTried++;
        ship.setEndX(startX + (length-1));
//          System.out.println("setEndCoords(): endX = "+ (startX + (length-1)));
        ship.setEndY(startY);
        if (isCoordValid(ship.getEndX()) && !doesBoatOverlap(ship, direction)) {
          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is valid");
          foundEndCoords = true;
        } else {
          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is not valid. Direction++");
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
  
  private boolean isCoordValid (int coord) {
    return (coord > 0  && coord <= GRID_DIMENSIONS);
  }
  
  private boolean doesBoatOverlap(Boat blackPearl, int direction) { //not the same as Player's doesBoatOverlap
    boolean overlap = false;
    if (direction == UP || direction == DOWN) {
      for (int j = blackPearl.getStartY(); j <= blackPearl.getEndY(); j++) {
        System.out.println("doesBoatOverlap(): " + grid[blackPearl.getStartX()-1][j-1].getHasBoat());
        overlap = (grid[blackPearl.getStartX()-1][j-1].getHasBoat()) ? true : overlap;
        //if there's a boat, valid = false, else doesn't change
        if (overlap) { return overlap; } //returns at first instance of boatOverlapping
      }
    } else { //direction == LEFT || RIGHT
      for (int j = blackPearl.getStartX(); j <= blackPearl.getEndX(); j++) {
        System.out.println("doesBoatOverlap(): " + grid[j-1][blackPearl.getStartY()-1].getHasBoat());
        overlap = (grid[j-1][blackPearl.getStartY()-1].getHasBoat()) ? true : overlap;
        //if there's a boat, valid = false, else doesn't change
        if (overlap) { return overlap; } //returns at first instance of boatOverlapping
      }
    }
    return overlap;
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