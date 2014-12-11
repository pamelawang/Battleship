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
 * 2. Got rid of "getCell", because the grid is a 2D array - we don't need 
 * the method.
 * 3. Deleted "hasBoat()" because we're checking that in the gotShot(), and
 * using Cell's getHasBoat() method.
 * 4. Will have to redo "fireBomb()" because it's no longer us firing, but us 
 * being fired upon.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;
import java.util.*;

public class Player { 
//  private final String username;
  private Cell[][] grid;
  private Boat[] fleet; //fleet will have a default size in the final version of the game
  private final int NUM_BOATS = 3;
  private final int GRID_DIMENSIONS = 3; //testing size of the grid, MEERA I CHANGED THE NAME OF THIS
  private final int MISS = 0;
  private final int HIT_NOT_SUNK = 1;
  private final int HIT_AND_SUNK = 2;
  //dk if this is a good idea or not, but..
  private LinkedList<Boat> shipsSunk;
  private final int NOT_OVER = -1;
  private final int GAME_OVER = 0;
  
  /******************************************************************
    * Constructor: Creates a Player that has a username, grid to place their
    * boats on, and a fleet of boats to place. 
    *****************************************************************/
  public Player() { //took out the username & the @param thing
//    username = name;
    grid = new Cell[GRID_DIMENSIONS][GRID_DIMENSIONS]; //size of grid
    for (int i = 0; i < GRID_DIMENSIONS; i++ ) { //creating all Cells for a new grid
      for (int j = 0; j < GRID_DIMENSIONS; j++) {
        grid[i][j] = new Cell();
      }
    }
    fleet = new Boat[NUM_BOATS]; //number of boats in fleet
    for (int i = 0; i < NUM_BOATS; i++) {
      fleet[i] = new Boat(); //for stage 1: single cell boats
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
    System.out.println("placeBoat(): x = " + startX + " and y = " + startY);
    fleet[boatIndex].setStartX(startX);
    System.out.println("placeBoat(): set boat's x-coordinate");
    fleet[boatIndex].setStartY(startY);
    System.out.println("placeBoat(): set boat's y-coordinate");
    grid[startX][startY].setHasBoat(true); //(x-1) because 0 indexing.
    System.out.println("placeBoat(): set cell's hasBoat state to true.");
  }
  
  /*****************************************************************
    * States the user's fleet by showing all of their boats, boat lengths and boat states (sunk or not sunk).
    * 
    * @return   String   a String that specifices all of the user's boats, boat lengths and boat state (sunk or not sunk).
    *****************************************************************/
  public String findMyFleet() {
    String boatLocations = "Your fleet consists of " + NUM_BOATS + " boat(s):\n";
    for (int i = 0; i < NUM_BOATS; i++) {
      boatLocations += "Boat " + (i+1) + " is " + fleet[i].getLength() + " units long and "
        + "is at (" + fleet[i].getStartX() + ", " + fleet[i].getStartY() + "). ";
      boatLocations += (fleet[i].getIsSunk()) ? "It has been sunk.\n" : "It has not been sunk.\n";
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
  public int gotShot(int x, int y) { //throws InvalidShotException {
    int hit = 0;
    if (!grid[x][y].getShotAt()) { //look at your grid, check that cell. hit or miss?
      hit = (grid[x][y].getHasBoat()) ? HIT_NOT_SUNK : MISS; //if it has a boat, then hit = true.
      //if hit, what boat?    PAM: shouldn't indicate what boat has been hit, should just say HIT or MISS
      if (hit == HIT_NOT_SUNK) {
        //youShotMe(x, y);
        hit = didMyShipSink(x,y) ? HIT_AND_SUNK : hit;
      }
    } else {
      System.out.println("You've already guessed ("+x+", "+y+")!");
    }
    switch (hit) {
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
//    if (fleet[i].getStartX <= x && fleet[i].getEndX >= x && 
//        fleet[i].getStartY <= y && fleet[i].getEndY >= y) {
//      fleet[i].hitAndMaybeSunk();
//    }
//  }
  
  
  /***********************************************************************
    * Private. Called within getShot() if opponent is hit. Updates Cells and
    * Boats to 'hit' state, and returns if Boat has been sunk.
    * 
    * @param     x     x-coordinate of opponent's guess
    * @param     y     y-coordinate of opponent's guess
    * @return   boolean    if boat has been sunk
    ***********************************************************************/
  private boolean didMyShipSink(int x, int y) {
    boolean sunk = false; //assumes ship hasn't sunk
    grid[x][y].setShotAt(true); //updates cell that was shot at
    for (int i = 0; i < NUM_BOATS; i++) { //going through fleet to find boat
      if (fleet[i].getStartX() == x && fleet[i].getStartY() == y)  {
        //do the needful with the boat
        sunk = fleet[i].hitAndMaybeSunk(); 
        System.out.println("didMyShipSink(): " + fleet[i].getIsSunk());
        if (sunk) { shipsSunk.add(fleet[i]); } //MEERA want to remove in fleet to show which boats are leftover?
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
  } //will be used in game? to see when the game's over.
  
  //not sure if this will work. we'll see. it works.
  public void gotBombed(int x, int y) {
    int left = (x-1 >= 0) ? x-1 : 0;
    int right = (x+1 <= GRID_DIMENSIONS) ? x+1 : GRID_DIMENSIONS;
    int top = (y-1 >= 0) ? y-1 : 0;
    int bottom = (y+1 <= GRID_DIMENSIONS) ? y+1: GRID_DIMENSIONS;
    
    //going through all 9 cells that have been affected
    for (int i = left; i <= right; i++) {
      for (int j = top; j <= bottom; j++) {
//        try {
        gotShot(i, j);
//        } catch (InvalidShotException oops) {
          //do nothing - hopefully the for loops will keep going..
//        }
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
  
//Deleted getCell()
  
//deleted hasBoat()
  
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
    computer.placeBoat(0, 1, 0);
    computer.placeBoat(1, 0, 2);
    computer.placeBoat(2, 2, 2);
    novice.placeBoat(0, 0, 0);
    novice.placeBoat(1, 2, 2);
    novice.placeBoat(2, 1, 2);
    
//    computer.gotShot(2, 1);
//    novice.gotShot(0, 0);
//    computer.gotShot(1, 1);
//    novice.gotShot(2, 2);
//    computer.gotShot(1, 0);
//    novice.gotShot(1,1); //all works
    novice.gotBombed(1,1); //bomb works
    
    System.out.println("Fleets:");
    System.out.println("Novice: " + novice.findMyFleet());
    System.out.println("Computer: " + computer.findMyFleet());
  }
  
}

class InvalidShotException extends Exception {
  
  public InvalidShotException(String problem) {
    System.out.println(problem);
  }
}