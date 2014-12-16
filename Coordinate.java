/* NAME: Coordinate.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 16 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Returns a pair of x and y values (used in ComputerPlayer only)
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

public class Coordinate {
  private int x;
  private int y;
  
  public Coordinate (int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
}