/* NAME: Player.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 6 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: 
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;

public class Player { //;D
  private final String username;
  private Cell[][] grid;
  private Boat[] fleet;
  private final int DIMENSIONS = 3; //testing size
  
  /******************************************************************
  * Constructor
  *****************************************************************/
  public Player(String name) {
    username = name;
    grid = new Cell[0][0]; //size of grid
    fleet = new Boat[0]; //number of boats in fleet
    fleet[0] = new Boat(0,0); //location of first boat
    
    /*need for multiple of boats: for loop to parse through everything*/
  }
  
  public int shoot(Player other, int x, int y) {
    /*if (other.get(x,y).getShotAt()) { return 1; } //(x,y) not shot at
    if (other.get(x,y).getHasBoat) {
       other.get(x,y).setBackground("red");
       return 2;
    } else { //shot at & has boat
       other.get(x,y).setBackground("blue");
       return 3;
    }*/
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
     
     if (down < DIMENSIONS) {
        shoot(other, middleX, down); //middle column bottom
     }
     
     //LEFT COLUMN
     if (left > -1) { //if the x coordinate isn't at the leftmost wall (coordinate 0)
        shoot(other, left, middleY); //left column middle
        
        if (up > -1) {
           shoot(other, left, up); //left column top
        }
        
        if (down < DIMENSIONS) {
           shoot(other, left, down); //left column bottom
        }
     }
     
     //RIGHT COLUMN
     if (right < DIMENSIONS) { //if x-coordinate isn't at the rightmost wall (at coordinate DIMENSIONS)
        shoot(other, right, middleY); //right column middle
        
        if (up > -1) {
           shoot(other, right, up); //right column top
        }
        
        if (down < DIMENSIONS) {
           shoot(other, right, down); //right column bottom
        }
     }
  }
  
  /*public Cell get(int x, int y) {
    somehow return the pointer to Cell at x and y
  }
  
  public boolean getHasBoat(int x, int y) {
    boolean b = false;
    THEORY: if (at this coordinate + has boat) { b = true; }
    return b;
  }*/
  
}