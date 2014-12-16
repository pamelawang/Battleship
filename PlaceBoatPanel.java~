/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * PlaceBoatPanel.java
 * Dec 13th, 2014
 * 
 * Purpose: 
 * 
 * @author Meera Hejmadi
 */


import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class PlaceBoatPanel extends JPanel {
  
  private Player humanPlayer;
  private JLabel banner;
  private JLabel instructions;
  private int boatNum;
  private int gridSize;
  private JPanel grid;
  private final Color SEA = new Color (0, 0, 128);
  private final Color BOAT = new Color (96, 96, 96);
  
  public PlaceBoatPanel(Player human) {
    humanPlayer = human;
    boatNum = 0;
    gridSize = humanPlayer.getGridDimensions();
    banner = new JLabel("Welcome to Battleship!");
    instructions = new JLabel("Time to place boat " + (boatNum+1));
    grid = createGrid(); 
    
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    add(banner, JComponent.CENTER_ALIGNMENT);
    add(instructions, JComponent.CENTER_ALIGNMENT);
    add(grid);
  }
  
  public JPanel createGrid() {
    grid = new JPanel(new GridLayout(gridSize, gridSize));
    grid.setBackground(Color.black);
    
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        GridButton justMade = new GridButton((i+1), (j+1));
        System.out.println("createGrid(): " + (i+1) + ", " + (j+1));
//        justMade.setBackground(SEA);
        justMade.addActionListener(new GridButtonListener());
        grid.add(justMade);
      }
    }
    return grid;
  }
  
  private class GridButtonListener implements ActionListener {
    
    private GridButton nextPlace;
    
    public void actionPerformed (ActionEvent e) {
      if (boatNum < humanPlayer.getNumBoats()) {
        System.out.println("Just hit a button!");
        nextPlace = (GridButton) e.getSource();
        System.out.println("Boat placed: " + boatNum);
        System.out.println("Location: " + nextPlace.getXCoord() + ", " + nextPlace.getYCoord());
        //FIX humanPlayer.placeBoat(boatNum, nextPlace.getXCoord(), nextPlace.getYCoord());
        nextPlace.setBackground(BOAT);
        System.out.println(humanPlayer.findMyFleet());
        banner.setText("Boat " + (boatNum+1) + " is at (" + nextPlace.getXCoord() + 
                       ", " + nextPlace.getYCoord() + ").");
        boatNum++;
        if (boatNum < humanPlayer.getNumBoats()) {
          instructions.setText("Time to place boat " + (boatNum+1));
        } else { 
          instructions.setText("All your boats have been placed. Good luck!");
        }
        System.out.println("Color: " + nextPlace.getBackground());
      }

    }
  }
  
}