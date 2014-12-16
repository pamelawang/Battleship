/* NAME: Cell.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 5 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: One instance of one of the grid coordinates on the battleship screen
 * 
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;

public class Cell {
  private boolean hasBoat;
  private boolean shotAt;
  private int allInfo;
  private static int NADA = 0;
  private static int BOAT_ONLY = 1;
  private static int SHOT_ONLY = 2;
  private static int BOAT_SHOT = 3;
  
  /******************************************************************
  * Constructor: The default cell has no boat, and has not been shot at;
  * its background is navy blue. 
  *****************************************************************/
  public Cell() {
    hasBoat = false;
    shotAt = false;
    allInfo = NADA;
  }
  
  //Methods
  /******************************************************************
  * Returns the current hasBoat state. hasBoat shows whether this coordinate
  * has a boat on top of it.
  * 
  * @return   boolean   current hasBoat state of this Cell.
  *****************************************************************/
  public boolean getHasBoat() {
    return hasBoat;
  }
  
  /******************************************************************
  * Returns the current shotAt state. shotAt shows whether the Player has 
  * already aimed at this coordinate on the Computer's board.
  * 
  * @return   boolean   current shotAt state of this Cell. If true, then this cell has been shot at.
  *****************************************************************/
  public boolean getShotAt() {
    return shotAt;
  }
  
  /******************************************************************
  * Sets the hasBoat state of this coordinate. Ideally should only be called
  * upon once when the grid is first created, and afterwards shouldn't be
  * able to change hasBoat.
  * 
  * @param  b   boolean that hasBoat will be set to
  *****************************************************************/
  public void setHasBoat(boolean b) {
    hasBoat = b;
    allInfo = BOAT_ONLY;
  }
  
  /******************************************************************
  * Sets the shotAt state of this coordinate. Ideally should only be called
  * upon once when the computer aims for this coordinate, and afterwards
  * it can't be changed.
  * 
  * @param  b  boolean that shotAt will be set to 
  *****************************************************************/
  public void setShotAt(boolean b) {
    shotAt = b;
    allInfo = (getHasBoat()) ? BOAT_SHOT : SHOT_ONLY;
  }
  
  /******************************************************************
    * Returns an int representing the cell state.
    * 
    * @return  int   current state of cell (hasBoat and shotAt)
    *****************************************************************/
  public int getAllInfo() {
    return allInfo;
  }
  
  /******************************************************************
    * Returns a String representation of Cell.
    * 
    * @return  String   current states (hasBoat, shotAt, background) of cell
    *****************************************************************/
  public String toString() {
    String s = "This cell ";
    s += (hasBoat) ? "has a boat " : "doesn't have a boat ";
    s += "and ";
    s += (shotAt) ? "has been shot at. " : "hasn't been shot at. ";
    return s;
  }
  
  /******************TESTER CODE***************************************/
  public static void main (String[] args) {
    Cell c = new Cell();
    System.out.println("SCENARIO: no boat, no hits");
    System.out.println("Does c have a boat? (false) " + c.getHasBoat());


    System.out.println("--------------------------");
    
    System.out.println("SCENARIO: no boat, has been hit");
    System.out.println("Has c been shot at? (true) " + c.getShotAt()); //set to true after setBackground()
    System.out.println("TESTING TOSTRING()");
    System.out.println(c);
    System.out.println("--------------------------");
    
    System.out.println("SCENARIO: boat, no hits");
    c.setShotAt(false); //shotAt was set to true by the earlier setBackground() method
    System.out.println("--->Adding a boat to c");
    c.setHasBoat(true);
    System.out.println("Does c have a boat? (true) " + c.getHasBoat());
    System.out.println("--------------------------");
    
    System.out.println("SCENARIO: boat, has been hit");
    System.out.println("Has c been shot at? (true) " + c.getShotAt()); //set to true after setBackground()
    System.out.println("TESTING TOSTRING()");
    System.out.println(c);
    System.out.println("--------------------------");
    
    System.out.println("SCENARIO: boat, has been hit");
    System.out.println("--->c is being shot at");
    c.setShotAt(true);
    System.out.println("Has c been shot at? (true) " + c.getShotAt());
    System.out.println("TESTING TOSTRING()");
    System.out.println(c);
  }
  
}