/* NAME: Boat.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 5 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: One instance of a boat on the battleship screen
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
 private boolean sunk;
 
 public Boat(String name, int x1, int y1, int length) {
   boatType = name;
   startX = x1;
   startY = y1;
   this.length = length;
   sunk = false;
   //endX = x2, endY = y2
   numHits = 0;
 }
   
 public Boat(int x1, int y1) { //future implementation has int x2, int y2 too
   new Boat("", x1, y1, 1);
 }
 
 //METHODS
 /******************************************************************
    * Returns the starting x-coordinate of boat.
    * 
    * @return  int     starting x-coordinate of boat
    *****************************************************************/
 public int getStartX(){
  return startX; 
 }
 
 /******************************************************************
    * Returns the starting y-coordinate of boat.
    * 
    * @return  int     starting y-coordinate of boat
    *****************************************************************/
 public int getStartY(){
  return startY; 
 }

 /******************************************************************
    * Returns the last ('ending') x-coordinate of boat.
    * 
    * @return  int     last ('ending') x-coordinate of boat
    *****************************************************************/
// public int getEndX() {
//   return endX;
// }
// 
 /******************************************************************
    * Returns the last ('ending') y-coordinate of boat.
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
    * Occurs when boat has been hit. Increments numHits and returns
    * the current state of boat (whether it has been sunk or not).
    * 
    * @return  boolean current state of boat (sunk or swimming)
    *****************************************************************/
 public boolean mayday() {
   numHits++;
   if (numHits == length) {
     sunk = true;
   } //do nothing in else case
   return sunk;
 }
 
 /******************************************************************
    * UNSUPPORTED OPERATIONS
    *****************************************************************/
 public void setStartX() {
   throw new UnsupportedOperationException("Boat starting x-coordinate cannot be changed.");
 }
 
 public void setStartY() {
   throw new UnsupportedOperationException("Boat starting y-coordinate cannot be changed.");
 }
 
 public void setLength() {
   throw new UnsupportedOperationException("Boat length cannot be changed.");
 }
 
 public void setNumHits() {
   throw new UnsupportedOperationException("The number of times a boat has been hit cannot be changed.");
 }
 
}
