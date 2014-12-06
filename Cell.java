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
  * Constructor
  *****************************************************************/
  public Cell() {
    hasBoat = false; //negates a need for a setter, since we only need to do this upon creation?
    shotAt = false;
    background = sea;
  }
  
  //Methods
  /******************************************************************
  * Returns the current hasBoat state. hasBoat shows whether this coordinate
  * has a boat on top of it.
  * 
  * @return          current hasBoat state of this Cell
  *****************************************************************/
  public boolean getHasBoat() {
    return hasBoat;
  }
  
  /******************************************************************
  * Returns the current shotAt state. shotAt shows whether the Player has 
  * already aimed at this coordinate on the Computer's board.
  * 
  * @return          current shotAt state of this CellComp. True means that the Player has already aimed at this coordinate, and false otherwise
  *****************************************************************/
  public boolean getShotAt() {
    return shotAt;
  }
  
  /******************************************************************
  * Sets the hasBoat state of this coordinate. Ideally should only be called
  * upon once when the grid is first created, and afterwards shouldn't be
  * able to change hasBoat. (HOW TO DO THIS???)
  * 
  * @param  boolean  state to set hasBoat to
  *****************************************************************/
  public void setHasBoat(boolean b) {
    hasBoat = b;
  }
  
  /******************************************************************
  * Sets the shotAt state of this coordinate. Ideally should only be called
  * upon once when the computer aims for this coordinate, and afterwards
  * it can't be changed. (HOW TO DO THIS???)
  * 
  * @param  boolean  state to set shotAt to, true meaning the Player has aimed here false otherwise
  *****************************************************************/
  public void setShotAt(boolean b) {
    shotAt = b;
  }
  
  /******************************************************************
    * Sets the background of the coordinate, dependent on whether the coordinate
    * has been shot at yet (!getShotAt) and whether there is a boat here.
    *****************************************************************/
  public void setBackground() {
    if (!getShotAt()) {
      if (getHasBoat()) {
        background = hit;
        setShotAt(true);
      } else {
        background = miss;
        setShotAt(true);
      }
    }
  } 
  
  /******************************************************************
    * Returns the current colour of the cell, indicating whether there is a boat and
    * whether this area has been shot at yet.
    * 
    * @return  Color   current colour of cell
    *****************************************************************/
  //getColor isn't a method yet - don't know if we'll need it. In case we do:
  /*public Color getBackground() {
    return background;
  }*/ 
  
}