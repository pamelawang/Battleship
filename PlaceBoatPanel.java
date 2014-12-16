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
import javafoundations.*;

public class PlaceBoatPanel extends JPanel {
  
  private Player humanPlayer;
  private JLabel banner, instructions, currentBoatInfo;
  private int boatNum;
  private int gridSize;
  private int undoStartIndex, undoEndIndex; //may not need undoEndIndex..have a MAX_UNDO
  private boolean isStartCoord;
  private GridButton currentStart, currentEnd;
  private JPanel grid;
  private JButton undoButton;
  private ArrayStack<Boat> boatStack;
  private final Color SEA = new Color (0, 0, 128);
  private final Color BOAT = new Color (96, 96, 96);
  private final int MAX_UNDO = 3;
  
  public PlaceBoatPanel(Player human) {
    humanPlayer = human;
    boatNum = 0;
    gridSize = humanPlayer.getGridDimensions();
    banner = new JLabel("Welcome to Battleship! Time to place boat " + (boatNum+1));
    instructions = new JLabel("Choose a starting coordinate for boat " + (boatNum+1));
    currentBoatInfo = new JLabel("Boat " + (boatNum+1) + " has a length of " + 
                                 humanPlayer.getBoatAt(boatNum).getLength() + ".");
    grid = createGrid(); 
    isStartCoord = true;
    boatStack = new ArrayStack<Boat>();
    undoStartIndex = 0;
    undoButton = new JButton("Undo");
    undoButton.addActionListener(new GridButtonListener());
    
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    add(banner, JComponent.CENTER_ALIGNMENT);
    add(instructions, JComponent.CENTER_ALIGNMENT);
    add(currentBoatInfo);
    add(undoButton);
    add(grid);
  }
  
  public JPanel createGrid() {
    grid = new JPanel(new GridLayout(gridSize, gridSize));
    grid.setBackground(Color.black);
    
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        GridButton justMade = new GridButton((i+1), (j+1));
//        System.out.println("createGrid(): " + (i+1) + ", " + (j+1));
//        justMade.setBackground(SEA);
        justMade.addActionListener(new GridButtonListener());
        grid.add(justMade);
      }
    }
    return grid;
  }
  
  private class GridButtonListener implements ActionListener {
    
    public void actionPerformed (ActionEvent e) {
      //if source = undo
      if (boatNum < humanPlayer.getNumBoats()) {
        System.out.println("Just hit a button!");
        if (isStartCoord) {
        currentStart = (GridButton) e.getSource();
        System.out.println("[PlaceBoat] actionPerformed(): start = (" + 
                           currentStart.getXCoord() + ", " + currentStart.getYCoord() + ").");
        instructions.setText("Choose an ending coordinate for boat " + (boatNum+1));
        isStartCoord = false;
        } else {
          currentEnd = (GridButton) e.getSource(); 
          System.out.println("[PlaceBoat] actionPerformed(): end = (" + 
                             currentEnd.getXCoord() + ", " + currentEnd.getYCoord() + ").");
          try {
          humanPlayer.placeBoat(boatNum, currentStart.getXCoord(), currentStart.getYCoord(),
                                currentEnd.getXCoord(), currentEnd.getYCoord());
          isStartCoord = true;
          boatNum++;
          if (boatNum < humanPlayer.getNumBoats()) {
             banner.setText("Welcome to Battleship! Time to place boat " + (boatNum+1));
             instructions.setText("Choose a starting coordinate for boat " + (boatNum+1));
             currentBoatInfo.setText("Boat " + (boatNum+1) + " has a length of " + 
                                 humanPlayer.getBoatAt(boatNum).getLength() + ".");
          } else {
            banner.setText("All your boats have been placed. Good luck!");
            instructions.setText("");
            currentBoatInfo.setText("");
          }
          } catch (BoatOverlapException dumb) {
            banner.setText("INVALID! Please place boat " + (boatNum+1) + " again.");
            instructions.setText("Choose a starting coordinate for boat " + (boatNum+1));
            currentBoatInfo.setText("Boat " + (boatNum+1) + " has a length of " + 
                                 humanPlayer.getBoatAt(boatNum).getLength() + ".");
            isStartCoord = true;
          }
        }
          
          /*
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
      } */

    }
  }
  }
}