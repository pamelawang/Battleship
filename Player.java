/* NAME: Player.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 6 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Creates a player. Each player has their own fleet of boats and 
 * a grid on which to place them.
 * 
 * Notes:
 * 1. Completely changed the "shoot" idea. Explanation below, before 
 * gotShot method.
 * 2. IMPORTANT: Boat's start
 * 3. QUESTION FOR PROFS: ERROR FOR REPEATED GUESS??????
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;
import java.util.*;

public class Player { 
//  private final String username;
  private Cell[][] grid;
  private LinkedList<Boat> fleet; //fleet will have a default size in the final version of the game
  private final int NUM_BOATS = 3;
  protected final int GRID_DIMENSIONS = 3; //testing size of the grid
  private final int INVALID_SHOT = -1;
  private final int MISS = 0;
  private final int HIT_NOT_SUNK = 1;
  private final int HIT_AND_SUNK = 2;
  //dk if this is a good idea or not, but..
  private LinkedList<Boat> shipsSunk;
  private final int NOT_OVER = -1;
  private final int GAME_OVER = 0;
  
  /******************************************************************
    * Constructor: Creates a Player that has a grid to place their
    * boats on, and a fleet of boats to place. 
    *****************************************************************/
  public Player() {
    grid = new Cell[GRID_DIMENSIONS][GRID_DIMENSIONS]; //size of grid
    for (int i = 0; i < GRID_DIMENSIONS; i++ ) { //creating all Cells for a new grid
      for (int j = 0; j < GRID_DIMENSIONS; j++) {
        grid[i][j] = new Cell();
      }
    }
    
    fleet = new LinkedList<Boat>(); //number of boats in fleet
    for (int i = 0; i < NUM_BOATS; i++) {
      Boat temp = new Boat(); //for stage 1: single cell boats
      fleet.add(temp);
    }
    shipsSunk = new LinkedList<Boat>();
  }
  
  /* assumption: program will give the user one boat at a time from
   * the fleet, starting from index 0 to the end. user will then put
   * boat (single-cell in stage 1) wherever they want.
   * To be used in conjunction with the clickable cells feature.*/
  /*****************************************************************
    * Allows user to choose where to put their boat.
    * 
    * @param   boatIndex   current Boat's index in user's fleet array
    * @param   startX      x-coordinate of boat (single-cell)
    * @param   startY      y-coordinate of boat (single-cell)
    *****************************************************************/
  public void placeBoat(int boatIndex, int startX, int startY) {
//    int gridX = startX - 1;
//    int gridY = startY - 1;
//    fleet.get(boatIndex).setStartX(startX);
//    fleet.get(boatIndex).setStartY(startY);
//    grid[gridX][gridY].setHasBoat(true); 
    int adjustedX = startX-1; //(x-1) because 0 indexing.
    int adjustedY = startY-1;
    try {
      fleet.get(boatIndex).setStartX(adjustedX);
      fleet.get(boatIndex).setStartY(adjustedY);
      grid[adjustedX][adjustedY].setHasBoat(true); 
    } catch (InvalidCoordinateException e) { //from Boat
      System.out.println("Starting coordinates are incorrect, please enter coordinates again.");
    }
  }
  
  /*****************************************************************
    * States the user's fleet by showing all of their boats, boat lengths and boat states (sunk or not sunk).
    * 
    * @return   String   a String that specifices all of the user's boats, boat lengths and boat state (sunk or not sunk).
    *****************************************************************/
  public String findMyFleet() {
    String boatLocations = "Your fleet consists of " + NUM_BOATS + " boat(s):\n";
    for (int i = 0; i < NUM_BOATS; i++) {
      boatLocations += "Boat " + (i+1) + " is " + fleet.get(i).getLength() + " units long and "
        + "is at (" + fleet.get(i).getStartX() + ", " + fleet.get(i).getStartY() + "). ";
      boatLocations += (fleet.get(i).getIsSunk()) ? "It has been sunk.\n" : "It has not been sunk.\n";
    }
    return boatLocations;
  }
  
  /* cuz we're dealing with only one player's side of things. the grid
   * we're working with is the grid that our boats are on. we can't shoot
   * and see if we hit/miss, because that's the other player's business.
   * the only thing we can do with our grid is take in a guess and tell
   * the other person if they hit a boat or not.
   * our 'shoot' becomes the other player's gotShot
   */
  /***********************************************************************
    * Returns whether or not the opponent's guess was a hit or miss. If it 
    * was a hit, then the necessary changes are made to the Cell that was
    * aimed at and the Boat that was hit.
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
  
//  for when we use multi-cell ships.
//  for (int i = 0; i < NUM_BOATS; i++) {
//    if (fleet.get(i).getStartX <= x && fleet.get(i).getEndX >= x && 
//        fleet.get(i).getStartY <= y && fleet.get(i).getEndY >= y) {
//      fleet.get(i).hitAndMaybeSunk();
//    }
//  }
  
  
  /***********************************************************************
    * Private. Called within getShot() if opponent is hit. Updates Cells and
    * Boats to 'hit' state, and returns if Boat has been sunk.
    * 
    * @param     indexX     x-coordinate of opponent's guess - 1 (already adjusted to arrays' first element index being 0)
    * @param     indexY     y-coordinate of opponent's guess (already adjusted to arrays' first element index being 0)
    * @return   boolean    if boat has been sunk
    ***********************************************************************/
  private boolean didMyShipSink(int x, int y) {
    boolean sunk = false; //assumes ship hasn't sunk
    for (int i = 0; i < fleet.size(); i++) { //going through fleet to find boat
      if (fleet.get(i).getStartX() == x && fleet.get(i).getStartY() == y)  {
        //do the needful with the boat
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
  
  //not sure if this will work. we'll see. it works.
  /***********************************************************************
    * Sets off a 3x3 cell bomb, the equivalent of 9 shots simultaenously.
    * Takes in the centre coordinate of the bomb, and the immediate surrounding
    * cells are shot through this method.
    * 
    * @param     x     centre x-coordinate of opponent's guess
    * @param     y     centre y-coordinate of opponent's guess
    ***********************************************************************/
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
    
    
  }
  
  /* 
   public int shoot(Player other, int x, int y) {
   if (other.get(x,y).getShotAt()) { return 1; } //(x,y) not shot at
   if (other.get(x,y).getHasBoat) {
   other.get(x,y).setBackground("red");
   return 2;
   } else { //shot at & has boat
   other.get(x,y).setBackground("blue");
   return 3;
   }
   return -1;
   }
   
   public void fireBomb(Player other, int middleX, int middleY) { //3x3 area
   int left = middleX - 1;
   int right = middleX + 1;
   int up = middleY - 1;
   int down = middleY + 1;
   
   //MIDDLE COLUMN
   shoot(other, middleX, middleY); //centre coordinate
   
   if (up > -1) {
   shoot(other, middleX, up); //middle column top
   }
   
   if (down < GRID_DIMENSIONS) {
   shoot(other, middleX, down); //middle column bottom
   }
   
   //LEFT COLUMN
   if (left > -1) { //if the x coordinate isn't at the leftmost wall (coordinate 0)
   shoot(other, left, middleY); //left column middle
   
   if (up > -1) {
   shoot(other, left, up); //left column top
   }
   
   if (down < GRID_DIMENSIONS) {
   shoot(other, left, down); //left column bottom
   }
   }
   
   //RIGHT COLUMN
   if (right < GRID_DIMENSIONS) { //if x-coordinate isn't at the rightmost wall (at coordinate GRID_DIMENSIONS)
   shoot(other, right, middleY); //right column middle
   
   if (up > -1) {
   shoot(other, right, up); //right column top
   }
   
   if (down < GRID_DIMENSIONS) {
   shoot(other, right, down); //right column bottom
   }
   }
   } */
  
  /***********************************************************************
    * Returns the number of boats for this Player.
    * 
    * @return   int    Player's number of boats
    ***********************************************************************/
  public int getNumBoats() {
    return NUM_BOATS;
  }
  
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
    * Returns cell referenced at a specific coordinates. - DO WE EVER USE THIS?
    * 
    * @param     x     x-coordinate
    * @param     y     y-coordinate
    * @return   Cell    Player's number of boats
    ***********************************************************************/
  public Cell getCellAt(int x, int y) {
    return grid[x][y];
  }

  /***********************************************************************
    * Returns grid of the current player. - DO WE EVER USE THIS?
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
    String s = "KEY: \nS (sea) = no boat not shot"
      + "\nM (miss) = no boat SHOT "
      +" \nB (boat) = boat not shot"
      + "\nH (hit) = boat SHOT\n";
    for (int i = 0; i < this.getGridDimensions(); i++) {
      for (int j = 0; j < this.getGridDimensions(); j++) {
        s += decideLetter(grid[i][j]) + "\t";
      }
      s += "\n";
    }
    return s;
  }
  
  /***********************************************************************
    * Private, used in printGrid(). Determines if the current cell is one of
    * the 4 states: S (sea), M (miss), B (boat) or H (hit).
    * 
    * @param     c        current Cell we're examining
    * @return   String    whether the Cell is S (sea), M (miss), B (boat) or H (hit)
    ***********************************************************************/
  private String decideLetter(Cell c) {
    String s = "";
    if (!c.getHasBoat() && !c.getShotAt()) {
      s = "S";
    } else if (!c.getHasBoat() && c.getShotAt()) {
      s = "M";
    } else if (c.getHasBoat() && !c.getShotAt()) {
      s = "B";
    } else {
      s = "H";
    }
    return s;
  }
  
  /******************TESTER CODE***************************************/
  public static void main (String[] args) throws InvalidShotException {
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
    
    /* System.out.println("Fleets:");
     System.out.println("Novice: " + novice.findMyFleet());
     System.out.println("Computer: " + computer.findMyFleet());*/
  }
  
} //closes Player

 /***********************************************************************
   * Exception used in gotShot() method for when the Cell currently being
   * aimed at has already been shot at.
   ***********************************************************************/
class InvalidShotException extends Exception {
  public InvalidShotException(String problem) {
    System.out.println(problem);
  }
}