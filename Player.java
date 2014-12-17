/* NAME: Player.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 6 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Creates a player. Each player has their own fleet of boats and 
 * a grid on which to place them.
 * 
 * Notes:
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;
import java.util.*;

public class Player { 
  protected Cell[][] grid;
  protected LinkedList<Boat> fleet; //fleet will have a default size in the final version of the game
  private final int NUM_BOATS = 5;
  protected final int GRID_DIMENSIONS = 10; //testing size of the grid
  protected final int INVALID = -1;
  protected final int MISS = 0;
  protected final int HIT_NOT_SUNK = 1;
  protected final int HIT_AND_SUNK = 2;
  private LinkedList<Boat> shipsSunk;
  private final int NOT_OVER = -1;
  private final int GAME_OVER = 0;
  protected int[] BOAT_LENGTHS = {5, 4, 3, 3, 2}; //also used in ComputerPlayer; largest boat first for easy placement
  
  /******************************************************************
    * Constructor: Creates a Player that has a grid to place their
    * boats on, and a fleet of boats to place. 
    *****************************************************************/
  public Player() { 
    grid = new Cell[GRID_DIMENSIONS][GRID_DIMENSIONS]; //size of grid
    for (int i = 0; i < GRID_DIMENSIONS; i++) {
      for (int j = 0; j < GRID_DIMENSIONS; j++) {
        grid[i][j] = new Cell(); //creating all Cells for a new grid
      }
    }
    
    fleet = new LinkedList<Boat>(); //number of boats in fleet
    for (int i = 0; i < NUM_BOATS; i++) {
      Boat temp = new Boat(BOAT_LENGTHS[i]); 
      fleet.add(temp);
    }
    shipsSunk = new LinkedList<Boat>();
  }
  
  /*****************************************************************
    * Allows user to choose where to put their boat.
    * 
    * @param   boatIndex   current Boat's index in user's fleet array
    * @param   startX      start x-coordinate of boat
    * @param   startY      start y-coordinate of boat
    * @param   endX        end x-coordinate of boat
    * @param   endY        end y-coordinate of boat
    *****************************************************************/
  public void placeBoat(int boatIndex, int startX, int startY, int endX, int endY) throws InvalidPlacementException {
    System.out.println("placeBoat(): boatNum " + boatIndex);
    Boat toPlace = getBoatAt(boatIndex);
    //checking if coordinates are within GRID_DIMENSIONS
    if (!withinGridDimensions(startX, startY, endX, endY)) {
      throw new InvalidPlacementException("Your boat isn't on the grid. ");
    }
    
    //checking for any boat overlapping
    if (doesBoatOverlap((startX-1), (startY-1), (endX-1), (endY-1))) {
      throw new InvalidPlacementException("There is already a boat in the area you selected. ");
    }
    
    //checking that the coordinates match boat's length
    if (((startX==endX) && (Math.abs(endY-startY)!=toPlace.getLength()-1)) || ((startY==endY) && (Math.abs(endX-startX)!=toPlace.getLength()-1))) {
      System.out.println(startX +" " + endX + "diff in Y: " + (endY-startY));
      System.out.println(startY +" " + endY + "diff in X: " + (endX-startX));
      throw new InvalidPlacementException("Your boat isn't the right length.");
    }
    
    //ensures boat is vertical/horizontal
    if ((startX != endX) && (startY != endY)) {
      throw new InvalidPlacementException("You need to make your boat either vertical or horizontal.");
    }
    
    //setting boat's start and end coordinates
    toPlace.setStartX(startX);
    toPlace.setStartY(startY);
    System.out.println("placeBoat(): StartX, Y = " + toPlace.getStartX()+" "+
                       toPlace.getStartY());
    toPlace.setEndX(endX);
    toPlace.setEndY(endY);
    System.out.println("placeBoat(): EndX, Y = " + toPlace.getEndX()+" "+
                       toPlace.getEndY());
    
    //setting all checked coordinates of boat to have a boat
    int gridStartX = Math.min(startX, endX);
    int gridEndX = Math.max(startX, endX);
    int gridStartY = Math.min(startY, endY);
    int gridEndY = Math.max(startY, endY);
    if (gridStartX==gridEndX) {
      for (int j = gridStartY; j <= gridEndY; j++) {
        grid[gridStartX-1][j-1].setHasBoat(true); //0indexing
        System.out.println("grid["+(gridStartX-1)+"]["+(j-1)+"] hasBoat: "+grid[gridStartX-1][j-1].getHasBoat());
      }
    } else if (gridStartY==gridEndY) {
      for (int i = gridStartX; i <= gridEndX; i++) {
        grid[i-1][gridStartY-1].setHasBoat(true); //o-indexing
        System.out.println("grid["+(i-1)+"]["+(gridStartY-1)+"] hasBoat: "+grid[i-1][gridStartY-1].getHasBoat());
      }
    }
    System.out.println("Boat " + (boatIndex+1) + "'s coordinates have successfully been set!~*~!~*~!*~!~*~!");
  }
  
  /*****************************************************************
    * Private, used in placeBoats(). Checking if coordinates are
    * within grid dimensions.
    * 
    * @param   startX      start x-coordinate of boat
    * @param   startY      start y-coordinate of boat
    * @param   endX        end x-coordinate of boat
    * @param   endY        end y-coordinate of boat
    * @return  boolean     whether the coordinates are within grid dimensions or not
    *****************************************************************/
  private boolean withinGridDimensions(int startX, int startY, int endX, int endY) {
    boolean within = true;
    int largerX = Math.max(startX, endX);
    int smallerX = Math.min(startX, endX);
    int largerY = Math.max(startY, endY);
    int smallerY = Math.min(startY, endY);
    
    if (smallerX < 1 || smallerY < 1 || largerX > GRID_DIMENSIONS || largerY > GRID_DIMENSIONS) {
      within = false;
    }
    
    return within;
  }
  
  /*****************************************************************
    * Private, used in placeBoats(). Checks if boat overlaps with
    * other boats.
    * 
    * @param   startIndexX      starting x-index of boat
    * @param   startIndexY      starting y-index of boat
    * @param   endIndexX        end x-index of boat
    * @param   endIndexY        end y-index of boat
    * @return  boolean          whether the boat overlaps with another
    *****************************************************************/  
  private boolean doesBoatOverlap(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    boolean overlap = false;
    int startX = Math.min(startIndexX, endIndexX);
    int endX = Math.max(startIndexX, endIndexX);
    int startY = Math.min(startIndexY, endIndexY);
    int endY = Math.max(startIndexY, endIndexY);
    
    for (int i = startX; i <= endX; i++) {
      for (int j = startY; j <= endY; j++) {
        overlap = (grid[i][j].getHasBoat()) ? true : overlap;
        if (overlap) { return overlap; } //returns at first instance of boatOverlapping
      }
    }
    return overlap;
  }
  
  /*****************************************************************
    * States the user's fleet by showing all of their boats, boat lengths and 
    * boat states (sunk or not sunk).
    * 
    * @return   String   a String representation of user's fleet.
    *****************************************************************/
  public String findMyFleet() {
    String boatLocations = "Your fleet consists of " + NUM_BOATS + " boat(s):\n";
    for (int i = 0; i < NUM_BOATS; i++) {
      boatLocations += "Boat " + (i+1) + " is " + fleet.get(i).getLength() + " units long and "
        + "is at (" + fleet.get(i).getStartX() + ", " + fleet.get(i).getStartY() + ") and (" 
        + fleet.get(i).getEndX() + ", " + fleet.get(i).getEndY() + ").";
      boatLocations += (fleet.get(i).getIsSunk()) ? " It has been sunk.\n" : " It has not been sunk.\n";
    }
    return boatLocations;
  }
  
  /****************************************************************************
    * Removes the boat at the specified index. i.e. sets it's coordinates to INVALID,
    * and makes relevant changes to the Player's grid cells.
    * 
    * @param     index     index of boat to be "removed"
    *******************************************************************************/
  public void removeBoat (int index) {
    Boat b = getBoatAt(index);
    int startX = Math.min(b.getStartX(), b.getEndX());
    System.out.println("removeBoat(): startX = " + startX);
    int startY = Math.min(b.getStartY(), b.getEndY());
    System.out.println("removeBoat(): startY = " + startY);
    int endX = Math.max(b.getStartX(), b.getEndX());
    System.out.println("removeBoat(): endX = " + endX);
    int endY = Math.max(b.getStartY(), b.getEndY());
    System.out.println("removeBoat(): endY = " + endY);
    
    //reset cells in grid.
    if (startX == endX) { 
      for (int j = startY; j <= endY; j++) {
        System.out.println("removeBoat(): X constant. Changing coordinate: " + j);
        grid[startX-1][j-1].setHasBoat(false);
        System.out.println("removeBoat(): Cell: " + grid[startX-1][j-1]);
      }   
    } else if (startY == endY) {
      for (int i = startX; i <= endX; i++) {
        System.out.println("removeBoat(): Y constant. Changing coordinate: " + i);
        grid[i-1][startY-1].setHasBoat(false);
        System.out.println("removeBoat(): Cell: " + grid[i-1][startY-1]);
      }
    }
    
    //reset boat
    b.setStartX(INVALID); 
    b.setStartY(INVALID); 
    b.setEndX(INVALID); 
    b.setEndY(INVALID);
    
  }
  
  /***********************************************************************
    * Returns whether or not the opponent's guess was a hit or miss. If it 
    * was a hit, then the necessary changes are made to the Cell that was
    * aimed at and the Boat that was hit.
    * 
    * Note:
    * gotShot() is one-sided: within the Player class, there is only one player;
    * therefore, that player gets shot at, and does not do the shooting directly.
    * In order for the "other" player for be shot, we will use other.gotShot(x,y).
    * 
    * @param     x     x-coordinate of opponent's guess
    * @param     y     y-coordinate of opponent's guess
    * @return   int    0 if opponent missed, 1 if opponent scored hit, 2 if boat is sunk
    ***********************************************************************/
  public int gotShot(int x, int y) throws InvalidShotException {
    int hit = 0; //miss 
//    System.out.println("didMyShipSink: grid[" + indexX + "][" + indexY + "]'s current shotAt state is: (true)"
//                         + grid[indexX][indexY].getShotAt());

    int gridX = x - 1; //0-indexing
    int gridY = y - 1;
    System.out.println("Before: " + grid[gridX][gridY]);
    if (!grid[gridX][gridY].getShotAt()) { //look at your grid, check that cell. hit or miss?
      grid[gridX][gridY].setShotAt(true);
      hit = (grid[gridX][gridY].getHasBoat()) ? HIT_NOT_SUNK : MISS; //if it has a boat, then hit = true.
      if (hit == HIT_NOT_SUNK) {
        hit = didMyShipSink(x, y) ? HIT_AND_SUNK : hit;
      } 
    } else {
      throw new InvalidShotException("You've already shot this coordinate!");
    }
    System.out.println("After: " + grid[gridX][gridY]);
    
    switch (hit) {
      case -1: 
        System.out.println("hit: " + hit + " Already been shot!");
        break;
      case 1: 
        System.out.println("hit: " + hit + " Hit!");
        break;
      case 2: 
        System.out.println("hit: " + hit + " Hit and sunk!");
        break;
      default:
        System.out.println("hit: " + hit + " Miss!");
    }
    return hit;
  }
  
  /***********************************************************************
    * Private. Called within getShot() if opponent is hit. Updates Cells and
    * Boats to 'hit' state, and returns if Boat has been sunk.
    * 
    * @param     x         x-coordinate of opponent's guess - 1 (already adjusted to arrays' first element index being 0)
    * @param     y         y-coordinate of opponent's guess (already adjusted to arrays' first element index being 0)
    * @return   boolean    if boat has been sunk
    ***********************************************************************/
  private boolean didMyShipSink(int x, int y) {
    boolean sunk = false; //assumes ship hasn't sunk
    for (int i = 0; i < fleet.size(); i++) { //going through fleet to find boat
      if (fleet.get(i).getStartX() == x && fleet.get(i).getStartY() == y)  {
        sunk = fleet.get(i).hitAndMaybeSunk(); 
        //System.out.println("didMyShipSink(): " + fleet.get(i).getIsSunk());
        if (sunk) { 
          shipsSunk.add(fleet.get(i)); 
          fleet.remove(i);
        }
      }
    }
    return sunk;
  }
  
  /***********************************************************************
    * Returns how many ships have been sunk in a user's fleet, indicating
    * whether the game is over or not.
    * 
    * @return   boolean    returns true if the player's ships have all sunk (aka game over)
    ***********************************************************************/
  public boolean didILose() {
    return (shipsSunk.size() == NUM_BOATS);
  }
  
  /***********************************************************************
    * Sets off a 3x3 cell bomb, the equivalent of 9 shots simultaenously.
    * Takes in the centre coordinate of the bomb, and the immediate surrounding
    * cells are shot through this method.
    * 
    * @param     x     centre x-coordinate of opponent's guess
    * @param     y     centre y-coordinate of opponent's guess
    ***********************************************************************/
  /* FOR FUTURE IMPLEMENTATION
  public void gotBombed(int x, int y) {
    int left = (x-1 >= 0) ? x-1 : 0;
    int right = (x+1 <= GRID_DIMENSIONS) ? x+1 : GRID_DIMENSIONS;
    int top = (y-1 >= 0) ? y-1 : 0;
    int bottom = (y+1 <= GRID_DIMENSIONS) ? y+1: GRID_DIMENSIONS;
    
    //going through all 9 cells that have been affected
    for (int i = left; i <= right; i++) {
      for (int j = top; j <= bottom; j++) {
        try {
          gotShot(i, j);
        } catch (InvalidShotException oops) {
          //do nothing - hopefully the for loops will keep going..
        }
      }
    }
  }*/
  
   /***********************************************************************
    * Returns boat at specified index in Player's fleet.
    * 
    * @param     index    index of boat
    * @return    Boat     boat at specified index
    ***********************************************************************/
  public Boat getBoatAt (int index) {
//    System.out.println("getBoatAt(): " + fleet.get(index));
    return fleet.get(index);
  }
  
  /***********************************************************************
    * Returns the number of boats for this Player.
    * 
    * @return   int    Player's number of boats
    ***********************************************************************/
  public int getNumBoats() {
    return NUM_BOATS;
  }
  
  /***********************************************************************
    * Returns the number of boats still in the fleet (i.e. not sunk).
    * 
    * @return   int    Number of Player's un-sunk boats
    ***********************************************************************/
  public int getFleetSize() {
    return fleet.size();
  }
  
  /***********************************************************************
    * Returns the size of side of the grid dimensions.
    * 
    * @return   int    length (number of cells) of one side of Player's grid
    ***********************************************************************/
  public int getGridDimensions() {
    return GRID_DIMENSIONS;
  }
  
  /***********************************************************************
    * Returns cell referenced at a specific coordinates. 
    * 
    * @param     x     x-coordinate
    * @param     y     y-coordinate
    * @return   Cell    Cell at input coordinate
    ***********************************************************************/
  public Cell getCellAt (int x, int y) {
    return grid[x-1][y-1];
  }

  /***********************************************************************
    * Returns grid of the current player.
    * 
    * @return   Cell[][]    Player's grid
    ***********************************************************************/
  public Cell[][] getGrid() {
    return grid;
  }
  
  /***********************************************************************
    * Returns a String representation of Player's grid.
    * 
    * @return   String    String representation of grid
    ***********************************************************************/
  public String printGrid() {
    String s = "KEY: \n- (sea) = no boat not shot"
      + "\nM (miss) = no boat SHOT "
      +" \nB (boat) = boat not shot"
      + "\nH (hit) = boat SHOT"
      + "\n***********************************************************\n";
    for (int i = 0; i < GRID_DIMENSIONS; i++) {
      for (int j = 0; j < GRID_DIMENSIONS; j++) {
        s += decideLetter(grid[i][j]) + "\t";
      }
      s += "\n";
    }
    return s;
  }
  
  /***********************************************************************
    * Private, used in printGrid(). Determines if the current cell is one of
    * the 4 states: - (sea), M (miss), B (boat) or H (hit).
    * 
    * @param     c        current Cell we're examining
    * @return   String    whether the Cell is - (sea), M (miss), B (boat) or H (hit)
    ***********************************************************************/
  private String decideLetter(Cell c) {
    String s = "";
    if (!c.getHasBoat() && !c.getShotAt()) {
      s = "-";
    } else if (!c.getHasBoat() && c.getShotAt()) {
      s = "M";
    } else if (c.getHasBoat() && !c.getShotAt()) {
      s = "B";
    } else {
      s = "H";
    }
    return s;
  }
  
  public static void main (String[] args) throws InvalidShotException {
    /**********TESTING CODE FOR BOAT LENGTH OF 1***************
//    Player human = new Player();
//    human.placeBoat(0, 2, 2);
//    System.out.println(human.findMyFleet());
//    human.gotShot(1,2);
//    human.gotShot(2,2);
//    human.placeBoat(1, 2, 1);
//    human.gotShot(1, 2);
//    human.gotShot(2, 1);
    
    Player computer = new Player();
    Player novice = new Player();
    
    //FIX PLACEBAOT TO BE MORE LIKE A LINKEDLIST ADD()
    computer.placeBoat(0, 1, 1);
    computer.placeBoat(1, 1, 2);
    computer.placeBoat(2, 2, 2);
    novice.placeBoat(0, 1, 1);
    novice.placeBoat(1, 2, 2);
    novice.placeBoat(2, 1, 2);
    System.out.println("Printing computer's boats \n" + computer.printGrid());
    System.out.println("\n Printing novice's boats \n" + novice.printGrid());
    //DON'T USE THE ABOVE UNLESS YOU CHANGE IT TO COORDINATES > 0
    
    System.out.println("------------ROUND 1------------");
    
    computer.gotShot(2, 2);
    novice.gotShot(1, 2);
    
    System.out.println("---------------ROUND 2--------------");
    
    computer.gotShot(3, 2);
    novice.gotShot(1, 2);
    
    System.out.println("Printing computer's boats \n" + computer.printGrid());
    System.out.println("\n Printing novice's boats \n" + novice.printGrid());
//    computer.gotShot(1, 1);
//    novice.gotShot(2, 2);
//    computer.gotShot(1, 0);
//    novice.gotShot(1,1); //all works
    //novice.gotBombed(1,1); //bomb works
    
     System.out.println("Fleets:");
     System.out.println("Novice: " + novice.findMyFleet());
     System.out.println("Computer: " + computer.findMyFleet());*/
    
    /**********TESTING CODE FOR VARIABLE BOAT LENGTHS***************/
  /*  Player computer = new Player();
    Player novice = new Player();
    
    //FIX PLACEBAOT TO BE MORE LIKE A LINKEDLIST ADD()
    /*computer.placeBoat(0, 1, 1);
    computer.placeBoat(1, 1, 2);
    computer.placeBoat(2, 2, 2);
    novice.placeBoat(0, 1, 1);
    novice.placeBoat(1, 2, 2);
    novice.placeBoat(2, 1, 2);
    System.out.println("Printing computer's boats \n" + computer.printGrid());
    System.out.println("\n Printing novice's boats \n" + novice.printGrid());
    //DON'T USE THE ABOVE UNLESS YOU CHANGE IT TO COORDINATES > 0
    
    System.out.println("------------ROUND 1------------");
    
    computer.gotShot(2, 2);
    novice.gotShot(1, 2);
    
    System.out.println("---------------ROUND 2--------------");
    
    computer.gotShot(3, 2);
    novice.gotShot(1, 2);*/
    
 /*   System.out.println("Fleets:");
    System.out.println("Novice: " + novice.findMyFleet());
    System.out.println("Computer: " + computer.findMyFleet());
    
    System.out.println("\nTesting setting boats");
    //public void placeBoat(int boatIndex, int startX, int startY)
    //FIX novice.placeBoat(0, 0, 0);
    */
    
    //testing getBoatAt and removeBoat:
    Player human = new Player();
    try {
      human.placeBoat(0, 5, 4, 5, 8);
      human.placeBoat(1, 2, 3, 5, 3);
      System.out.println(human.findMyFleet());
      System.out.println(human.printGrid());
      System.out.println(human.getBoatAt(0));
      human.removeBoat(1);
      //human.resetBoat(human.getBoatAt(1));
      
      System.out.println(human.findMyFleet());
      System.out.println(human.printGrid());
    } catch (InvalidPlacementException oops) {
      System.out.println("Exception here~~~~~~~");
    }
  }
} //closes Player

 /***********************************************************************
   * Exception used in placeBoat() method for any conflicts arising:
   * (i) Boat overlappinng
   * (ii) Boat outside of grid limits
   ***********************************************************************/
class InvalidShotException extends Exception {
  public InvalidShotException(String problem) {
    System.out.println(problem);
  }
}