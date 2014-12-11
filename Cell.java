/* NAME: Cell.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 5 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: One instance of one of the grid coordinates on the battleship screen
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;

public class Cell {
  private boolean hasBoat;
  private boolean shotAt;
  private Color background; //background of coordinate square; uses the colours below
  private Color hit = Color.red;
  private Color miss = Color.white;
  private Color sea = Color.blue.darker();
  
  /******************************************************************
  * Constructor: The default cell has no boat, and has not been shot at;
  * its background is navy blue. 
  *****************************************************************/
  public Cell() {
    hasBoat = false;
    shotAt = false;
    background = sea;
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
  * @return   boolean   current shotAt state of this Cell. If true, 
  * then this cell has been shot at.
  *****************************************************************/
  public boolean getShotAt() {
    return shotAt;
  }
  
  /******************************************************************
  * Sets the hasBoat state of this coordinate. Ideally should only be called
  * upon once when the grid is first created, and afterwards shouldn't be
  * able to change hasBoat. (HOW TO DO THIS???)
  * 
  * @param  boolean   boolean that hasBoat will be set to
  *****************************************************************/
  public void setHasBoat(boolean b) {
    hasBoat = b;
    setBackground(); //unnecessary. hi.
  }
  
  /******************************************************************
  * Sets the shotAt state of this coordinate. Ideally should only be called
  * upon once when the computer aims for this coordinate, and afterwards
  * it can't be changed. (HOW TO DO THIS???)
  * 
  * @param  boolean  boolean that shotAt will be set to 
  *****************************************************************/
  public void setShotAt(boolean b) {
    shotAt = b;
    setBackground();
  }
  
  /******************************************************************
  * Sets the background of the coordinate, dependent on the current hasBoat and 
  * shotAt values. Void method that takes in no parameters.
  ******************************************************************/
  public void setBackground() {
    if (getShotAt()) {
      if (getHasBoat()) {
        background = hit;
      } else {
        background = miss;
      }
    } else {
      background = sea;
    }
    //System.out.println("Color is " + background);
  } 
  
  /******************************************************************
    * Returns the current colour of the cell, indicating whether there is a boat and
    * whether this area has been shot at yet.
    * 
    * @return  Color   current colour of cell
    *****************************************************************/
  //getColor isn't a method yet - don't know if we'll need it. In case we do: - could use for bombed animation (.lighter())
  public Color getBackground() {
    return background;
  } 
  
  /******************************************************************
    * Cell's toString
    * 
    * @return  String   current states (hasBoat, shotAt, background) of cell
    *****************************************************************/
  public String toString() {
    String s = "This cell ";
    s += (hasBoat) ? "has a boat " : "doesn't have a boat ";
    s += "and ";
    s += (shotAt) ? "has been shot at. " : "hasn't been shot at. ";
    s += "Its colour is ";
    s += (background.equals(hit)) ? "red." : "";
    s += (background.equals(miss)) ? "white." : "";
    s += (background.equals(sea)) ? "blue." : "";
    return s;
  }
  
  //******************TESTER CODE***************************************
  public static void main (String[] args) {
    Cell c = new Cell();
    System.out.println("SCENARIO: no boat, no hits");
    System.out.println("Does c have a boat? (false) " + c.getHasBoat());
    System.out.println("What colour is c now? (dark blue) <" + c.getBackground() + ">");
    c.setBackground();
    System.out.println("What colour is c now? (white) <" + c.getBackground() + ">");
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
    c.setBackground();
    System.out.println("Has c been shot at? (true) " + c.getShotAt()); //set to true after setBackground()
    System.out.println("What colour is c now? (red) <" + c.getBackground() + ">");
    System.out.println("TESTING TOSTRING()");
    System.out.println(c);
    System.out.println("--------------------------");
    
    System.out.println("SCENARIO: boat, has been hit");
    System.out.println("--->c is being shot at");
    c.setShotAt(true);
    System.out.println("Has c been shot at? (true) " + c.getShotAt());
    c.setBackground();
    System.out.println("What colour is c now? (red) <" + c.getBackground() + ">");
    System.out.println("TESTING TOSTRING()");
    System.out.println(c);
  }
  
}