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
  
  private String name;
  private int startX;
  private int startY; //start coordinates of boat
  private int endX; 
  private int endY; //end coordinates of boat (should correlate with the length of the boat)
  private int length;
  private int MAX_LENGTH = 5; //largest ship size
  private int numHits; //will count how many shots have been taken
  private boolean isSunk;
  private int GRID_DIMENSIONS = 5; //maximum length of one size of the grid; hard-coded because grid size should be present
  private int INVALID = -1;
  
/******************************************************************
   * Constructor: Creates a Boat object, taking in the name and length of 
   * the boat. Start/end x,y coordinates will be set later.
   * 
   * @param     name     name of boat
   * @param    length    length of boat (>0)
  ******************************************************************/
  public Boat (String name, int length) {
    name = name;
    numHits = 0;
    isSunk = false;
    this.length = length;
    if (length < 1 || length > MAX_LENGTH) {
      System.out.println("Boat constructor: boat length isn't between 1 and " + MAX_LENGTH + "."); //should have an exception here?????
      this.length = INVALID; //overriding this.length if length is INVALID
    }
    startX = INVALID;
    startY = INVALID;
    endX = INVALID;
    endY = INVALID;
  }
  
  /******************************************************************
   * Constructor: Creates a Boat object of type "tester" and length 
   * default. Start/end x,y coordinates will be set later. - TO DELETE????????????????
  ******************************************************************/
  public Boat() {
    this("tester", 1);
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
    * Sets the last ('ending') x-coordinate of boat.
    * 
    * @param  xEnd     last ('ending') x-coordinate of boat
    *****************************************************************/
  public void setEndX (int xEnd) {
    endX = xEnd;
  }
 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Assumes setEndX
    * has already been set since this method checks whether the coordinates entered
    * matches the length of the boat.
    * 
    * @param  yEnd     last ('ending') y-coordinate of boat
    *****************************************************************/
 public void setEndY (int yEnd) {
  /* int check = 1; //inclusive of the cell mentioned (e.g. (1,1) and (2,1) has a boat length of 2)
   int tempEndY = yEnd;
   
   if (yEnd < check || GRID_DIMENSIONS < yEnd) {
     endY = INVALID;
     throw new InvalidCoordinateException("setEndY(): y-coordinate isn't within the grid dimensions of "
                                            + GRID_DIMENSIONS + ".");
   }
   
   if (startX == endX) { //if the boat is vertical
     check += Math.abs(startY - tempEndY);
     //System.out.println("ABC 1st IF STATEMENT");
   } else if (startY == tempEndY) { //if the boat is horizontal
     check += Math.abs(startX - endX);
     //System.out.println("XYZ 2nd IF STATEMENT");
   }
   //System.out.println("check: " + check + "\tlength: " + length);
   //System.out.println("startX: " + startX + "\tstartY: " + startY + "\tendX: " + endX + "\tendY: " + endY);
   if (check != length) { //if the y-coordinates entered don't correlate to boat length
     throw new InvalidCoordinateException("setEndY(): Incorrect boat length - starting and ending y-coordinates should be reentered again.");
   }
   //System.out.println("Coordinates have been entered correctly. Length: " + length);
   endY = tempEndY;*/
   endY = yEnd;
 }
  
  /******************************************************************
    * Returns the last ('ending') x-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') x-coordinate of boat
    *****************************************************************/
 public int getEndX() {
   return endX;
 }
 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') y-coordinate of boat
    *****************************************************************/
 public int getEndY() {
   return endY;
 }
  
  /******************************************************************
    * Gets the length of the boat.
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
  
  public String getBoatName() {
    return name;
  }
  
  /******************************************************************
    * Returns a string representation of the boat.
    * 
    * @return  String     representation of the boat object
    *****************************************************************/
  public String toString() {
    String s = name + "'s current status is:\n";
    s += "Starting position: (" + (startX) + ", " + (startY) + ").\n";
    s += "Ending position: (" + (endX) + ", " + (endY) + ").\n";
    s += "Length: " + length + "\tnumHits: " + numHits + "\t\tisSunk: " + isSunk;
    return s;
  }
  
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
  
  public static void main (String[] args) {

    Boat sub = new Boat("submarine", 3);
//    sub.setStartX(2);
//    sub.setStartY(3);
    /****************TESTING WHEN BOATS WERE LENGTH OF 1 (without endX and endY)**********************
    System.out.println("Creating a new boat with 1st constructor");
    Boat sub = new Boat("submarine", 5);
    sub.setStartX(2);
    sub.setStartY(3);
    System.out.println(sub);
    
    System.out.println("Hit and checking if it's sunk");
    sub.hitAndMaybeSunk(); //hits and checks if it's sunk
    System.out.println(sub);
    
    System.out.println("Hit and checking if it's sunk");
    sub.hitAndMaybeSunk();
    System.out.println(sub);
    
    System.out.println("Hit and checking if it's sunk");
    sub.hitAndMaybeSunk();
    System.out.println(sub);
    
    System.out.println("Checking getNumHits() and getIsSunk(): " + sub.getNumHits() + ",\t" + sub.getIsSunk());
    System.out.println("getLength() " + sub.getLength());
    
    System.out.println("-------------------------");
    
    System.out.println("Creating a new boat with 2nd constructor");
    Boat boat = new Boat();
    boat.setStartX(3);
    boat.setStartY(3);
    System.out.println(boat);
    
    System.out.println("Checking getStartX() and getStartY(): " + sub.getStartX()
    + ",\t" + sub.getStartY());
    System.out.println("Checking getLength(): " + sub.getLength());
    System.out.println("-------Can't check End getters and setters--------"); */
    
    /****************TESTING WITH MULTIPLE LENGTHS**********************/
    Boat test = new Boat("WRONG", 2); //testing exceptions
    /*System.out.println("Testing incorrect coordinates (not within GRID_DIMENSIONS)");
     test.setStartX(-20);
     test.setStartY(-1);
     test.setEndX(15);
     test.setEndY(10);
     
     System.out.println("Testing incorrect end coordinates (neither endX nor endY match either startX or startY)");
     test.setStartX(1);
     test.setStartY(1);
     test.setEndX(1);
     test.setEndY(2);*/
    
    System.out.println("Testing if coordinates correlate to length (when boat is vertical/startX == endX)");
    System.out.println("startX should = 4, startY should = 3, endX should = 2, endY should = 1");
    test.setStartX(1);
    test.setStartY(1);
    test.setEndX(1);
    test.setEndY(2);
    System.out.println(test);
    
    System.out.println("\nTesting if coordinates correlate to length (when boat is horizontal/startY == endY)");
    test.setStartX(1);
    test.setStartY(1);
    test.setEndX(2);
    test.setEndY(1);
    System.out.println(test);
  }
}
