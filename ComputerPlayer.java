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
  private int boatOrientation; //used for placing boats
  private int direction; //direction we're shooting in after hitting something
  private boolean wasRandomShot; //if true, then the last shot taken was a random shot 
  private int lastShotResult; //the last shot was a hit/miss/sunk
  
  /**********************************************************************************
    * Constructor: Creates a new ComputerPlayer, and sets instance variables to 
    * default. 
    *********************************************************************************/
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
    wasRandomShot = true;
    lastShotResult = 0;
  }
  
  
  /**********************************************************************************
    * Private. Randomly chooses the computer's next pair of coordinates to shoot at. 
    * Sets aimAtX and aimAtY as these coordinates.
    **********************************************************************************/
private void pickAPoint() { //randomiser
    do {
      int randomX = (int) (Math.random()*10);
      aimAtX = makeValidCoord(randomX);
      int randomY = (int) (Math.random()*10);
      aimAtY = makeValidCoord(randomY);
      System.out.println("pickAPoint() used x = " + aimAtX + " and y = " + aimAtY);
    } while (previouslyShotAt(aimAtX, aimAtY));
  }
  
 /**********************************************************************************
    * Private. Ensures the input coordinate is between 1 and GRID_DIMENSIONS.
    * Returns a valid int.
    * 
    * @param    coordinate    int whose validity is to be checked.
    * @return     int      int that is valid.
    *********************************************************************************/
 private int makeValidCoord (int coordinate) { //checks if coordinate is within grid dimensions
    int valid = (coordinate >= 1) ? coordinate : 1;
    valid = (valid <= GRID_DIMENSIONS) ? valid : GRID_DIMENSIONS;
    System.out.println("makeValidCoord: coordinate = " + coordinate + " and valid = " + valid);
    return valid;
  }
  
   /********************************************************************************
    * Private. Returns whether the input coordinate pair has already been shot at. Uses Cell.
    * 
    * @param    xCoord    the x-coordinate 
    * @param    yCoord    the y-coordinate
    * @return    boolean    has this coordinate pair been shot at before
    *********************************************************************************/
  private boolean previouslyShotAt (int xCoord, int yCoord) {
    return shotSoFarGrid[xCoord-1][yCoord-1].getShotAt();
  }
  
  /********************************************************************************
    * Private. Resets aimAtX and aimAtY to the next coordinate pair to be shot at.
    * Called by takeAShot(). Decides according to direction what the next coordinates
    * should be
    *********************************************************************************/
  private void setNextShot() { //resets aimAtX and aimAtY to next coordinate to be shot at
    System.out.println("setNextShot(): aimAtX = " + aimAtX + ", aimAtY = " + aimAtY);
    System.out.println("setNextShot(): direction = " + direction);
    if (direction == UP) { //x-coordinate is the same; UP == 2
        System.out.println(direction + "\t(should be 2/UP)");
        if (isValidCoord(aimAtY-1)) {
        aimAtY--;
        System.out.println("setNextShot(): new x,y = " + aimAtX + ", " + aimAtY);
      } else { 
        System.out.println("setNextShot(): aimAtY-- isn't valid. direction++");
        direction++; //nothing up, check down
      }
    } else if (direction == DOWN) { //DOWN == 3
        System.out.println(direction + "\t(should be 3/DOWN)");
        if (isValidCoord(aimAtY+1)) {
        aimAtY++;
        System.out.println("setNextShot(): new x,y = " + aimAtX + ", " + aimAtY);
      } else if (wasRandomShot) { //still trying to figure out if boat is VERTICAL or HORIZONTAL
        System.out.println("setNextShot(): aimAtY++ isn't valid. wasRandomShot. direction++");
        direction++; //nothing down, check left
      } else {
        //wasn't random, exhausted both up and down. shouldn't come to this, but if it does,
        // then we assume that the boat has been sunk. Reset boatDirection to up.
        System.out.println("setNextShot(): aimAtY++ isn't valid. !wasRandomShot. direction=UP");
        direction = UP;
      }
    } else if (direction == LEFT){ // y-coordinate is the same; LEFT == 4
        System.out.println(direction +"\t(should be 4/LEFT)");
        if (isValidCoord(aimAtX-1)) { //same y-coordinate
        aimAtX--;
        System.out.println("setNextShot(): new x,y = " + aimAtX + ", " + aimAtY);
      } else { 
        System.out.println("setNextShot(): aimAtX-- isn't valid. direction++");
        direction++; //nothing up, check down
      }
    } else { //RIGHT == 5
        System.out.println(direction+ "\t(should be 5/RIGHT)");
        if (isValidCoord(aimAtY+1)) {
        aimAtY++;
        System.out.println("setNextShot(): new x,y = " + aimAtX + ", " + aimAtY);
      } else { //random or not, doesn't matter.
        System.out.println("setNextShot(): aimAtY++ isn't valid. direction=UP");
        direction = UP;
      }
    } 
  }
  
/********************************************************************************
  * Decides whether to take a random shot or to shoot at (aimAtX, aimAtY). This 
  * decision is dependent on lastShotResult and wasRandomShot. Calls on setNextShot().
  * Shoots at the Player "other". Returns result of shot.
  * 
  * @param      other       Player at whom the computer is shooting
  * @return     int      whether the shot was a miss, hit, and if the boat sunk
  *********************************************************************************/
  public int takeAShot (Player other) {
    System.out.println("takeAShot(): lastShotResult = " + lastShotResult + 
                       "\twasRandomShot = "+ wasRandomShot);
    if ((lastShotResult == MISS && wasRandomShot == true) || lastShotResult == HIT_AND_SUNK) {
      direction = UP; //default
      try {
        shoot(other);
        wasRandomShot = true; //because we just took a random shot.
        if (lastShotResult > 0) { //it was a hit and a random shot.
          setNextShot();
          wasRandomShot = false; //(next shot will not be random shot)
        }       
      } catch (InvalidShotException oops) {
        //do nothing. The program should never have this problem, because of pickAPoint().
      }
    } else if (lastShotResult == MISS) { //figuring out if direction is up/down/left/right; wasRandomShot == false
      direction++;
      setNextShot();
      try {
        shoot(other);
        wasRandomShot = true; //because we just took a random shot.
        if (lastShotResult > 0) { //it was a hit.
          setNextShot();
          //don't change wasRandomShot to false, because this was a random shot. 
          //the next shot will NOT be random.
        }
      } catch (InvalidShotException oops) {
        //do nothing. The program should never have this problem, because of pickAPoint().
      } 
    } else { //lastShotResult == HIT_NOT_SUNK || HIT_AND_SUNK
      try {
        if (goBack(other, aimAtX, aimAtY)) { findOtherBoatEnd(other); }
        shoot(other, aimAtX, aimAtY); //shooting at a specified (not random) point
        wasRandomShot = false;
        if (lastShotResult != HIT_AND_SUNK) {
          setNextShot(); //reset aimAtX and aimAtY
//          if (lastShotResult == HIT_NOT_SUNK) {
//        }
        }
      } catch (InvalidShotException oops) {
        //do nothing. The program should never have this problem, because of pickAPoint().
      }
    }
    return lastShotResult;
  }
  
  /********************************************************************************
    * Private. Returns whether or not the ComputerPlayer must go backwards and start
    * guessing again. Returns true if player should go back: (i) they have hit a wall
    * or (ii) they have already guessed (x,y).
    * 
    * @param      p      Player being shot at/ComputerPlayer's opponent.
    * @param      x      current x-coordinate of pair that is checked
    * @param      y      current y-coordinate of pair that is checked
    * @return     boolean       whether or not the ComputerPlayer should go back.
    *********************************************************************************/ 
  private boolean goBack(Player p, int x, int y) {
    boolean shouldTurnAround = false;
    int newUp = y - 1;
    int newDown = y + 1;
    int newLeft = x - 1;
    int newRight = x + 1;
    
    if (newUp < GRID_DIMENSIONS || newDown > GRID_DIMENSIONS
          || newLeft < GRID_DIMENSIONS || newRight > GRID_DIMENSIONS) { //if next coord is off the grid
      shouldTurnAround = true;
    } else if (previouslyShotAt(x, newUp) || previouslyShotAt(x, newDown)
                 || previouslyShotAt(newLeft, y) || previouslyShotAt(newRight, y)) { //if next coord has already been hit
      shouldTurnAround = true;
    }
    return shouldTurnAround;
  }

  ///COMMENT HERE
  
  private void findOtherBoatEnd(Player p) {
    int line;
    //the line (different x- or y-coordinates) across the grid we are following to find the other boat end
    //e.g. the line for HORIZONTAL would be the x-axis, since the x-axis changes
    
    if (direction == UP || direction == DOWN) {
      line = aimAtY;
      direction = (direction == UP) ? direction++ : direction-- ; //sets UP to DOWN, DOWN to UP
        while (p.grid[aimAtX][line].getShotAt()) { //while the current location we're checking has been shot at
          line++; //check the next coordinate
        }
        aimAtY = line;
      } else { //direction == RIGHT || LEFT
        line = aimAtX;
        direction = (direction == LEFT) ? direction++ : direction-- ; //sets LEFT to RIGHT, RIGHT to LEFT
        while (p.grid[line][aimAtY].getShotAt()) {
          line++; //check the coordinate below
        }
        aimAtX = line;
      }
  }
  
  /********************************************************************************
    * Shoots at Player p, with the input coordinates. Sets lastShotResult according to
    * output of p.gotShot(x,y). 
    * 
    * @param     p     Player to be shot at
    * @param     x       x-coordinate to shoot at
    * @param     y       y-coordinate to shoot at
    *********************************************************************************/
  public void shoot (Player p, int x, int y) throws InvalidShotException {
    System.out.println("shoot(P, x, y): (" + aimAtX + ", " + aimAtY + ").");
    try {
      lastShotResult = p.gotShot(x, y); //uses MISS (0), HIT_NOT_SUNK (1) and HIT_AND_SUNK (2)
      shotSoFarGrid[x-1][y-1].setShotAt(true);
    } catch (InvalidShotException e) {
      System.out.println("shoot(p, x, y): Exception caught");
      System.out.println("Old aimAtX: " + aimAtX + "\taimAtY: " + aimAtY);
      if (direction == UP || direction == DOWN) {
        aimAtY = (direction == UP) ? aimAtY-- : aimAtY++ ;
      } else { //direction LEFT || RIGHT
        aimAtX = (direction == LEFT) ? aimAtX-- : aimAtY++ ;
      }
      if (goBack(p, aimAtX, aimAtY)) {
        System.out.println("@@@@@@@@@@@@@@@Re-doing shoot(p, x, y)");
        shoot(p, aimAtX, aimAtY);
        System.out.println("New aimAtX: " + aimAtX + "\taimAtY: " + aimAtY);
      }
    }
    System.out.println("lastShotResult = " + lastShotResult);
    shotSoFarGrid[x-1][y-1].setShotAt(true);
//    System.out.println("Computer shooting");
  }
  
  /********************************************************************************
    * Picks a random point to shoot at, and then calls on shoot(Player p, int x, int y).
    * 
    * @param      p     Player to shoot at.
    *********************************************************************************/
  public void shoot(Player p) throws InvalidShotException {
    System.out.println("shoot(P):");
    pickAPoint(); 
    System.out.println("Point: (" + aimAtX + ", " + aimAtY + ").");
    shoot(p, aimAtX, aimAtY); //aimAtX and aimAtY is set in pickAPoint()
  }

   /******************************************************************************
    * Places all the ComputerPlayer's boats randomly. Chooses an orientation (vertical/
    * horizontal) and then a direction, and then sets the first coordinate randomly
    * and calculates the last coordinate according to direction. 
    * Sets each boat's start and end coordinates, and then sets the cells themselves.
    *******************************************************************************/  
 public void placeBoats() { // != placeBoat() from Player
    for (int i = 0; i < getNumBoats(); i++) {
      //System.out.println("\n/////////////////////////placeBoats() round " + (i+1) + " //////////////////////////////////");
      boatOrientation = getBoatOrientation();
      int direction = (boatOrientation == VERTICAL) ? getUpOrDown() : getLeftOrRight();
      Boat blackPearl = fleet.get(i);
      setCoords(blackPearl, direction); //makes and checks Boat coordinates
      
      //setting the cells to haveBoat. first get the min and max x,y values. then loop through.
      int startX = Math.min(blackPearl.getStartX(), blackPearl.getEndX());
      int startY = Math.min(blackPearl.getStartY(), blackPearl.getEndY());
      int endX = Math.max(blackPearl.getStartX(), blackPearl.getEndX());
      int endY = Math.max(blackPearl.getStartY(), blackPearl.getEndY());
      System.out.println("startX, Y, endX, Y: " + startX + " " + startY+ " " + endX+" "+endY);
      
      if (startX == endX) { //direction == UP || DOWN
        System.out.println("placeBoats(): Boat's upward/downward. Changing cells to have boat.");
        for (int j = startY; j <= endY; j++) {
        //sets the cells between starting and ending coordinates to hasBoat() state
          grid[startX-1][j-1].setHasBoat(true);
          System.out.println("grid["+(startX-1)+"]["+(j-1)+"] hasBoat: " + grid[startX-1][j-1].getHasBoat());
        }
      } else if (startY == endY) { //direction == LEFT || RIGHT
        System.out.println("placeBoats(): Boat's left/right. Changing cells to have boat.");
        for (int j = startX; j <= endX; j++) {
          grid[j-1][startY-1].setHasBoat(true);
          System.out.println("grid["+(j-1)+"]["+(startY-1)+"] hasBoat: " + grid[j-1][startY-1].getHasBoat());
        }
      }
      //System.out.println("Boat " + Integer.toString(i+1) + "\n" + blackPearl + "\n");
      System.out.println("placeBoats(): GRID PRINT OUT" + printGrid());
    }
    
    System.out.println(findMyFleet());
  }
  
  //helpers for PlaceBoats
  private void setCoords(Boat b, int direction) {
//    System.out.println("************setCoords() with " + b.boatType + " and direction " + direction + " *************");
//      System.out.println("setCoords(): X");
    //using Boat's instance variables as 'local' variables through its getters/setters
    b.setStartX(makeValidCoord((int)(Math.random()*10)));
//      System.out.println("setCoords(): Y");
    b.setStartY(makeValidCoord((int)(Math.random()*10)));
    int startX = b.getStartX();
    int startY = b.getStartY();
    
//    System.out.println("setCoords(): Already a boat? " + grid[startX-1][startY-1].getHasBoat());
    if (grid[startX-1][startY-1].getHasBoat()) { //has a boat
//      System.out.println("setCoords(): checking for previous boat. Found one.");
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
//        System.out.println("setEndCoords(): Direction = UP");
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY - (length-1));
//          System.out.println("setEndCoords(): endY = "+ (startY - (length-1)));
        if (isValidCoord(ship.getEndY()) && !doesBoatOverlap(ship, direction)) {
//          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is valid");
          foundEndCoords = true;
        } else {
//          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ")is not valid. Direction++");
          direction++;
        }
      } else if (direction == DOWN) { //direction == 3
//        System.out.println("setEndCoords(): Direction = DOWN");
        directionsTried++;
        ship.setEndX(startX);
        ship.setEndY(startY + (length-1));
//          System.out.println("setEndCoords(): endY = "+ (startY + (length-1)));
        if (isValidCoord(ship.getEndY()) && !doesBoatOverlap(ship, direction)) {
//          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is valid");
          foundEndCoords = true;
        } else {
//          System.out.println("setEndCoords(): endY (" + ship.getEndY() + ") is not valid. Direction++");
          direction++;
        }
      } else if (direction == LEFT) { //direction == 4
//        System.out.println("setEndCoords(): Direction = LEFT");
        directionsTried++;
        ship.setEndX(startX - (length-1));
//          System.out.println("setEndCoords(): endX = "+ (startX - (length-1)));
        ship.setEndY(startY);
        if (isValidCoord(ship.getEndX()) && !doesBoatOverlap(ship, direction)) {
//          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is valid");
          foundEndCoords = true;
        } else {
//          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is not valid. Direction++");
          direction++;
        }
      } else { //direction == RIGHT (5)
//        System.out.println("setEndCoords(): Direction = RIGHT");
        directionsTried++;
        ship.setEndX(startX + (length-1));
//          System.out.println("setEndCoords(): endX = "+ (startX + (length-1)));
        ship.setEndY(startY);
        if (isValidCoord(ship.getEndX()) && !doesBoatOverlap(ship, direction)) {
//          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is valid");
          foundEndCoords = true;
        } else {
//          System.out.println("setEndCoords(): endX (" + ship.getEndX() + ") is not valid. Direction++");
          direction = UP; //restarting while loop
        }
      }
    }
    
    if (!foundEndCoords) {
      setCoords(ship, direction); //finding new starting coordinates
    }
  }
   /******************************************************************************
    * Private. Returns true if input is within grid dimensions.
    * 
    * @param     coord     coordinate value we're checking
    * @return     boolean      returns true if the coordinate is valid.
    *******************************************************************************/
  private boolean isValidCoord (int coord) {
    return (coord > 0  && coord <= GRID_DIMENSIONS);
  }
  
   /******************************************************************************
    * Private. Returns true if current boat is overlapping another, already-placed 
    * boat. 
    * 
    * @param     blackPearl     Boat the ComputerPlayer is trying to place.
    * @param     direction     Boat's direction (UP/DOWN/LEFT/RIGHT).
    * @return     boolean      returns true if the boat is overlapping another.
    *******************************************************************************/
  private boolean doesBoatOverlap(Boat blackPearl, int direction) { //overrides Player's doesBoatOverlap
    boolean overlap = false;
    if (blackPearl.getStartX()==blackPearl.getEndX()) {
      int startY = Math.min(blackPearl.getStartY(), blackPearl.getEndY());
      int endY = Math.max(blackPearl.getStartY(), blackPearl.getEndY());
      for (int j = startY; j <= endY; j++) {
//        System.out.println("doesBoatOverlap(): " + grid[blackPearl.getStartX()-1][j-1].getHasBoat());
        overlap = (grid[blackPearl.getStartX()-1][j-1].getHasBoat()) ? true : overlap;
        //if there's a boat, valid = false, else doesn't change
        if (overlap) { return overlap; } //returns at first instance of boatOverlapping
      }
    } else { //Y values are equal
      int startX = Math.min(blackPearl.getStartX(), blackPearl.getEndX());
      int endX = Math.max(blackPearl.getStartX(), blackPearl.getEndX());
      for (int j = startX; j <= endX; j++) {
//        System.out.println("doesBoatOverlap(): " + grid[j-1][blackPearl.getStartY()-1].getHasBoat());
        overlap = (grid[j-1][blackPearl.getStartY()-1].getHasBoat()) ? true : overlap;
        //if there's a boat, valid = false, else doesn't change
        if (overlap) { return overlap; } //returns at first instance of boatOverlapping
      }
    }
    return overlap;
  }
  
   /******************************************************************************
    * Returns a random boat orientation.
    * 
    * @return     int    boatOrientation (0 or 1)
    *******************************************************************************/
  private int getBoatOrientation() { //0 = vertical, 1 = horizontal
    return (int)Math.round(Math.random());
  }
  
  /******************************************************************************
    * Returns an int for vertical boat direction.
    * 
    * @return     int    boat direction (UP or DOWN)
    *******************************************************************************/
  private int getUpOrDown() { //2 = up, down = 3
    return (int)Math.round(Math.random() + 2);
  }
  
  /******************************************************************************
    * Returns an int for horizontal boat direction.
    * 
    * @return     int    boat direction (LEFT or RIGHT)
    *******************************************************************************/
  private int getLeftOrRight() { //4 = left, 5 = right
    return (int)Math.round(Math.random() + 4);
  }
  
  /******************************************************************************
    * Returns aimAtX.
    * 
    * @return     int    aimAtX.
    *******************************************************************************/
  public int getAimAtX() {
    return aimAtX;
  }
  
   /******************************************************************************
    * Returns aimAtY.
    * 
    * @return     int    aimAtY.
    *******************************************************************************/
  public int getAimAtY() {
    return aimAtY;
  }

  //testing main
  public static void main (String[] args) {
    ComputerPlayer c = new ComputerPlayer();
    c.placeBoats();
    //System.out.println("COMPUTER\nfindMyFleet(): " + c.findMyFleet());
    //System.out.println(c.printGrid());
    ComputerPlayer human = new ComputerPlayer();
    human.placeBoats();
    //System.out.println("HUMAN\n" + human.findMyFleet());
    //System.out.println(human.printGrid());
    
    System.out.println("***************************************************BEGIN GUESSING***************************************************");
    int count = 0;
    while (count < 8) {
      System.out.println("Computer: ");
      c.takeAShot(human);
      System.out.println("Human: ");
      human.takeAShot(c);
      count++;
      System.out.println("--------------COMPUTER\tfindMyFleet(): " + c.printGrid());
      System.out.println("//////////////HUMAN\tfindMyFleet(): " + human.printGrid());
      System.out.println("Turn complete");
    }
  }
}