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
//  private BinaryTree<Integer> decision;
  private GridButton nextShot;
  private int aimAtX;
  private int aimAtY;
  private final int INVALID = -1;
  private Cell[][] shotSoFarGrid;
  private final int VERTICAL = 0;
  private final int HORIZONTAL = 1;
  private final int UP = 2;
  private final int DOWN = 3;
  private final int LEFT = 4;
  private final int RIGHT = 5;
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
//    decision = createDecisionTree();
  }
  
//  public <Integer> createDecisionTree() {
    
  
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
  
  //*****************EDITS FROM HERE
  private Coordinate getNextShot(Coordinate lastShot, int direction) { //only called when a boat has been hit
    int x, y;
    switch (direction) {
      case 2: //UP == 2 (not using constant variable UP because of DrJava); x-coordinate is the same
        x = lastShot.getX();
        y = (lastShot.getY() - 1 < GRID_DIMENSIONS) ? INVALID : lastShot.getY() - 1;
        break;
        
      case 3: //DOWN == 3
        x = lastShot.getX();
        y = (lastShot.getY() + 1 > GRID_DIMENSIONS) ? INVALID : lastShot.getY() + 1;
        break;
        
      case 4: //LEFT == 4, y-coordinate is the same
        x = (lastShot.getX() - 1 < GRID_DIMENSIONS) ? INVALID : lastShot.getX() - 1;
        y = lastShot.getY();
        break;
        
      case 5: //RIGHT == 5
        x = (lastShot.getX() + 1 > GRID_DIMENSIONS) ? INVALID : lastShot.getX() + 1;
        y = lastShot.getY();
        break;
        
      default:
        x = y = INVALID;
    }
    return new Coordinate(x, y);
  }
  
  //takeAShot - determiens whether to take a random shot or not
  
  //change to private???
  public int shoot (Player p, int x, int y) throws InvalidShotException {
    int hitOrMiss = p.gotShot(x, y); //uses MISS (0), HIT_NOT_SUNK (1) and HIT_AND_SUNK (2)
    shotSoFarGrid[x-1][y-1].setShotAt(true);
    System.out.println("Computer shooting");
    return hitOrMiss;
  }
  
  public int shoot(Player p) throws InvalidShotException {
    pickAPoint();    
    int hitOrMiss = shoot(p, aimAtX, aimAtY); //aimAtX and aimAtY is set in pickAPoint()
    return hitOrMiss;
  }
  
    //**************** EDITS END HERE
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
    System.out.println("************setCoords() with " + b.boatType + " and direction " + direction + " *************");
//      System.out.println("setCoords(): X");
    //using Boat's instance variables as 'local' variables through its getters/setters
    b.setStartX(makeValidCoord((int)(Math.random()*10)));
//      System.out.println("setCoords(): Y");
    b.setStartY(makeValidCoord((int)(Math.random()*10)));
    int startX = b.getStartX();
    int startY = b.getStartY();
    
    System.out.println("setCoords(): Already a boat? " + grid[startX-1][startY-1].getHasBoat());
    if (grid[startX-1][startY-1].getHasBoat()) { //has a boat
      System.out.println("setCoords(): checking for previous boat. Found one.");
      setCoords(b, direction); //find new starting coordinates
    } else { //no boat
      setEndCoords(b, direction);
    }
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
  }
  
  private boolean isCoordValid (int coord) {
    return (coord > 0  && coord <= GRID_DIMENSIONS);
  }
  
  private boolean doesBoatOverlap(Boat blackPearl, int direction) { //overrides Player's doesBoatOverlap
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