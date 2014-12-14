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
  private int startIndexX;
  private int startIndexY; //start coordinates of boat
  private int endIndexX; 
  private int endIndexY; //end coordinates of boat (should correlate with the length of the boat)
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
    boatType = name;
    numHits = 0;
    isSunk = false;
    this.length = length;
    if (length < 1 || length > MAX_LENGTH) {
      System.out.println("Boat constructor: boat length isn't between 1 and " + MAX_LENGTH + "."); //should have an exception here?????
      this.length = INVALID; //overriding this.length if length is INVALID
    }
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
  public void setStartIndexX (int xStart) throws InvalidCoordinateException {
    if (xStart < 1 || GRID_DIMENSIONS < xStart) {
      startIndexX = INVALID;
      throw new InvalidCoordinateException("setStartIndexX(): x-coordinate isn't within the grid dimensions of "
                                             + GRID_DIMENSIONS + ".");
    }
    startIndexX = xStart - 1; //adjusting for 0 indexing
  }
  
  /******************************************************************
    * Sets the starting y-coordinate of boat.
    * 
    * @param     yStart     starting y-coordinate of boat
    *****************************************************************/
  public void setStartIndexY (int yStart) throws InvalidCoordinateException {
    if (yStart < 1 || GRID_DIMENSIONS < yStart) {
      startIndexY = INVALID;
      throw new InvalidCoordinateException("setStartIndexY(): y-coordinate isn't within the grid dimensions of "
                                             + GRID_DIMENSIONS + ".");
    }
    startIndexY = yStart - 1; //adjusting for 0 indexing
  }
  
  /******************************************************************
    * Returns the starting x-coordinate of boat.
    * 
    * @return  int     starting x-coordinate of boat
    *****************************************************************/
  public int getStartIndexX () {
    return startIndexX; 
  }
  
  /******************************************************************
    * Returns the starting y-coordinate of boat.
    * 
    * @return  int     starting y-coordinate of boat
    *****************************************************************/
  public int getStartIndexY () {
    return startIndexY; 
  }
  
  /******************************************************************
    * Sets the last ('ending') x-coordinate of boat.
    * 
    * @param  xEnd     last ('ending') x-coordinate of boat
    *****************************************************************/
  public void setEndIndexX (int xEnd) throws InvalidCoordinateException {
    if (xEnd < 1 || GRID_DIMENSIONS < xEnd) {
      endIndexX = INVALID;
      throw new InvalidCoordinateException("setEndIndexX(): x-coordinate isn't within the grid dimensions of "
                                             + GRID_DIMENSIONS + ".");
    }
    endIndexX = xEnd - 1; //takes into account 0 indexing
  }
 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Assumes setEndIndexX
    * has already been set since this method checks whether the coordinates entered
    * matches the length of the boat.
    * 
    * @param  yEnd     last ('ending') y-coordinate of boat
    *****************************************************************/
 public void setEndIndexY (int yEnd) throws InvalidCoordinateException {
   int check = 1; //inclusive of the cell mentioned (e.g. (1,1) and (2,1) has a boat length of 2)
   int tempEndIndexY = yEnd - 1; //accounts for 0 indexing in arrays
   
   if (yEnd < 1 || GRID_DIMENSIONS < yEnd) {
     endIndexY = INVALID;
     throw new InvalidCoordinateException("setEndIndexY(): y-coordinate isn't within the grid dimensions of "
                                            + GRID_DIMENSIONS + ".");
   }
   
   if (startIndexX == endIndexX) { //if the boat is vertical
     check += Math.abs(startIndexY - tempEndIndexY);
     //System.out.println("ABC 1st IF STATEMENT");
   } else if (startIndexY == tempEndIndexY) { //if the boat is horizontal
     check += Math.abs(startIndexX - endIndexX);
     //System.out.println("XYZ 2nd IF STATEMENT");
   }
   //System.out.println("check: " + check + "\tlength: " + length);
   //System.out.println("startIndexX: " + startIndexX + "\tstartIndexY: " + startIndexY + "\tendIndexX: " + endIndexX + "\tendIndexY: " + endIndexY);
   if (check != length) { //if the y-coordinates entered don't correlate to boat length
     throw new InvalidCoordinateException("setEndIndexY(): Incorrect boat length - starting and ending y-coordinates should be reentered again.");
   }
   //System.out.println("Coordinates have been entered correctly. Length: " + length);
   endIndexY = tempEndIndexY;
 }
  
  /******************************************************************
    * Returns the last ('ending') x-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') x-coordinate of boat
    *****************************************************************/
 public int getEndIndexX() {
   return endIndexX;
 }
 
  /******************************************************************
    * Returns the last ('ending') y-coordinate of boat. Not yet functional.
    * 
    * @return  int     last ('ending') y-coordinate of boat
    *****************************************************************/
 public int getEndIndexY() {
   return endIndexY;
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
  
  /******************************************************************
    * Returns a string representation of the boat.
    * 
    * @return  String     representation of the boat object
    *****************************************************************/
  public String toString() {
    String s = boatType + "'s current status is:\n";
    s += "Starting position: (" + (startIndexX+1) + ", " + (startIndexY+1) + ").\n";
    s += "Ending position: (" + (endIndexX+1) + ", " + (endIndexY+1) + ").\n";
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
    /****************TESTING WHEN BOATS WERE LENGTH OF 1 (without endIndexX and endIndexY)**********************
    System.out.println("Creating a new boat with 1st constructor");
    Boat sub = new Boat("submarine", 5);
    sub.setStartIndexX(2);
    sub.setStartIndexY(3);
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
    boat.setStartIndexX(3);
    boat.setStartIndexY(3);
    System.out.println(boat);
    
    System.out.println("Checking getStartIndexX() and getStartIndexY(): " + sub.getStartIndexX()
                         + ",\t" + sub.getStartIndexY());
    System.out.println("Checking getLength(): " + sub.getLength());
    System.out.println("-------Can't check End getters and setters--------"); */
    
    /****************TESTING WITH MULTIPLE LENGTHS**********************/
    Boat test = new Boat("WRONG", 2); //testing exceptions
    try {
      /*System.out.println("Testing incorrect coordinates (not within GRID_DIMENSIONS)");
      test.setStartIndexX(-20);
      test.setStartIndexY(-1);
      test.setEndIndexX(15);
      test.setEndIndexY(10);
      
      System.out.println("Testing incorrect end coordinates (neither endIndexX nor endIndexY match either startIndexX or startIndexY)");
      test.setStartIndexX(1);
      test.setStartIndexY(1);
      test.setEndIndexX(1);
      test.setEndIndexY(2);*/
      
      System.out.println("Testing if coordinates correlate to length (when boat is vertical/startIndexX == endIndexX)");
      System.out.println("startIndexX should = 4, startIndexY should = 3, endIndexX should = 2, endIndexY should = 1");
      test.setStartIndexX(1);
      test.setStartIndexY(1);
      test.setEndIndexX(1);
      test.setEndIndexY(2);
      System.out.println(test);
      
      System.out.println("\nTesting if coordinates correlate to length (when boat is horizontal/startIndexY == endIndexY)");
      test.setStartIndexX(1);
      test.setStartIndexY(1);
      test.setEndIndexX(2);
      test.setEndIndexY(1);
      System.out.println(test);
    } catch (InvalidCoordinateException e) {
    }
  }
}
  
class InvalidCoordinateException extends Exception {
  public InvalidCoordinateException(String problem) {
    System.out.println(problem);
  }
}