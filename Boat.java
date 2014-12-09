/* NAME: Boat.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 5 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: One instance of a boat on the battleship screen.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

public class Boat {
  
  private String boatType;
  private int startX;
  private int startY; //start coordinates of boat
  //private int endX; 
  //private int endY; //currently commented out because we're starting with single-cell boats
  private int length;
  private int numHits; //will count how many shots have been taken
  private boolean isSunk;
  private final int DEFAULT_LENGTH = 1;
  
/******************************************************************
   * Constructor: Creates a Boat object, taking in the name and length of 
   * the boat. Start/end x,y coordinates will be set later.
   * 
   * @param     name     name of boat
   * @param     length     length of boat (>0)
  ******************************************************************/
  public Boat (String name, int length) {
    boatType = name;
    this.length = length;
    numHits = 0;
    isSunk = false;
  }
  
  /******************************************************************
   * Constructor: Creates a Boat object of type "tester" and length 
   * default. Start/end x,y coordinates will be set later.
   * 
   * @param     name     name of boat
   * @param     length     length of boat (>0)
  ******************************************************************/
  public Boat() {
    new Boat ("tester", DEFAULT_LENGTH);
  }
  
  //METHODS
  /******************************************************************
    * Sets the starting x-coordinate of boat.
    * 
    * @param     xStart      starting x-coordinate of boat
    *****************************************************************/
  public void setStartX (int xStart) {
    startX = xStart; 
  }
  
  /******************************************************************
    * Sets the starting y-coordinate of boat.
    * 
    * @param     yStart     starting y-coordinate of boat
    *****************************************************************/
  public void setStartY (int yStart) {
    startY = yStart; 
  }
  
  /******************************************************************
    * Returns the starting x-coordinate of boat.
    * 
    * @return  int     starting x-coordinate of boat
    *****************************************************************/
  public int getStartX () {
    return startX; 
  }
  
  /******************************************************************
    * Returns the starting y-coordinate of boat.
    * 
    * @return  int     starting y-coordinate of boat
    *****************************************************************/
  public int getStartY () {
    return startY; 
  }
  
  /******************************************************************
    * Sets the last ('ending') x-coordinate of boat. Not yet functional.
    * 
    * @param  xEnd     last ('ending') x-coordinate of boat
    *****************************************************************/
// public void setEndX (int xEnd) {
//   endX = xEnd;
// }
// 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Not yet functional.
    * 
    * @param  yEnd     last ('ending') y-coordinate of boat
    *****************************************************************/
// public void setEndY (int yEnd) {
//   endY = yEnd;
// }
  
  /******************************************************************
    * Returns the last ('ending') x-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') x-coordinate of boat
    *****************************************************************/
// public int getEndX() {
//   return endX;
// }
// 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') y-coordinate of boat
    *****************************************************************/
// public int getEndY() {
//   return endY;
// }
  
  /******************************************************************
    * Gets the length of the boat
    * 
    * @return  int     boat length
    *****************************************************************/
  public int getLength() {
    return length;
  }
  
  /******************************************************************
    * Gets the numbers of times the boat has been hit.
    * 
    * @return  int     number of hits the boat has taken
    *****************************************************************/
  public int getNumHits() {
    return numHits;
  }
  
  /******************************************************************
    * Returns whether or not the boat has been sunk.
    * 
    * @return  boolean     if boat has sunk or not
    *****************************************************************/
  public boolean getIsSunk() {
    return isSunk;
  }
  
  /******************************************************************
    * Occurs when boat has been hit. Increments numHits and returns
    * the current state of boat (whether it has been sunk or not).
    * 
    * @return  boolean current state of boat (sunk or not)
    *****************************************************************/
  public boolean hitAndMaybeSunk() {
    numHits++;
    isSunk = (numHits == length) ? true : false;
    return isSunk;
  }
  
  /******************************************************************
    * Returns a string representation of the boat.
    * 
    * @return  String     representation of the boat object
    *****************************************************************/
  public String toString() {
    String s = boatType + "'s current status is:\n";
    s += "Position: (" + startX + ", " + startY + ").\n";
    s += "Length: " + length + "\tnumHits: " + numHits + "\t\tisSunk: " + isSunk;
    return s;
  }
  
// /******************************************************************
//  * Unsupported operation.
//  *****************************************************************/
// public void setStartX() {
//   throw new UnsupportedOperationException("Boat starting x-coordinate cannot be changed.");
// }
// 
// /******************************************************************
//  * Unsupported operation.
//  *****************************************************************/
// public void setStartY() {
//   throw new UnsupportedOperationException("Boat starting y-coordinate cannot be changed.");
// }
  
  /******************************************************************
    * Unsupported operation.
    *****************************************************************/
  public void setLength() {
    throw new UnsupportedOperationException("Boat length cannot be changed.");
  }
  
  /******************************************************************
    * Unsupported operation.
    *****************************************************************/
  public void setNumHits() {
    throw new UnsupportedOperationException("The number of times a boat has been hit cannot be changed.");
  }
  
  //Testing main:
  public static void main (String[] args) {
    Boat sub = new Boat("submarine", 3);
    sub.setStartX(2);
    sub.setStartY(3);
    System.out.println(sub);
    sub.hitAndMaybeSunk();
    System.out.println(sub);
    sub.hitAndMaybeSunk();
    System.out.println(sub);
    sub.hitAndMaybeSunk();
    System.out.println(sub);
  }
}

// public Boat(String name, int x1, int y1, int length) {
//   boatType = name;
//   startX = x1;
//   startY = y1;
//   this.length = length;
//   isSunk = false;
//   //endX = x2, endY = y2
//   numHits = 0;
// }
//   
// public Boat(int x1, int y1) { //future implementation has int x2, int y2 too
//   new Boat("", x1, y1, 1);
// }
  
