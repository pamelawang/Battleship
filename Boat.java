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
 //private int numHits; //will count how many shots have been taken
 private boolean sunk;
 
 public Boat(String name, int x1, int y1, int length) {
   boatType = name;
   startX = x1;
   startY = y1;
   this.length = length;
   sunk = false;
   //endX = x2, endY = y2, hits = 0;
 }
   
 public Boat(int x1, int y1) { //future implementation has int x2, int y2 too
   new Boat(x1, y1, 1);
 }
 
 //METHODS
 
 //the following 4 methods are for interaction between the coordinate and 
 public int getStartX(){
  return startX; 
 }
 
 public int getStartY(){
  return startY; 
 }

// public int getEndX() {
//   return endX;
// }
// 
// public int getEndY() {
//   return endY;
// }
 
 //no setters for start/end coordinates because, once created, cannot be changed. (future stuff?)
 
 public int getLength() {
   return length;
 }
 //no setter
 
 public int getNumHits() {
   return numHits;
 }
 
 //when the boat has been hit
 public boolean mayday() {
   numHits++;
   if (numHits == length) {
     sunk = true;
   } else { 
     //do nothing
   }
   return sunk;
 }
 
}
