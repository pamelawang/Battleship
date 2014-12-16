/* NAME: GridButton.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 13 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Button representing one coordinate/Cell on a grid.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GridButton extends JButton {
  private int xCoordinate;
  private int yCoordinate;
  
  public GridButton (int x, int y) {
    xCoordinate = x;
    yCoordinate = y;
  }
  
  /******************************************************************
  * Returns x-coordinate of current Cell/coordinate/button.
  * 
  * @return  int  x-coordinate of current button
  *****************************************************************/
  public int getXCoord () { return xCoordinate; }
  
  /******************************************************************
  * Returns y-coordinate of current Cell/coordinate/button.
  * 
  * @return  int  y-coordinate of current button
  *****************************************************************/
  public int getYCoord () { return yCoordinate; }  
  
}