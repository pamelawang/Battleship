/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * PlaceBoatPanel.java
 * Dec 13th, 2014
 * 
 * Purpose: Panel to have user choose the location of their fleet (all their boats)
 * 
 * @author Meera Hejmadi
 */

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import javafoundations.*;

public class PlaceBoatPanel extends JPanel {
  
  private Game battle;
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
  
  public PlaceBoatPanel(Game game) {
    battle = game;
    boatNum = 0;
    gridSize = game.getHumanPlayer().getGridDimensions();
    banner = new JLabel("Welcome to Battleship! Time to place boat " + (boatNum+1));
    instructions = new JLabel("Choose a starting coordinate for boat " + (boatNum+1));
    currentBoatInfo = new JLabel("Boat " + (boatNum+1) + " has a length of " + 
                                 battle.getHumanPlayer().getBoatAt(boatNum).getLength() + ".");
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
  
  /******************************************************************
  * Creates grid for user to choose boat locations.
  * 
  * @return  JPanel  grid for user to choose boat locations
  *****************************************************************/
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
  
  /******************************************************************
  * GridButtonListener: actionPerformed() keeps the game running by
  * changing those things that need to be changed.
  *****************************************************************/
  private class GridButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent e) {
      if (e.getSource() == undoButton) {
        System.out.println("Undo! boatNum = " + boatNum + " startIndex = " + undoStartIndex);
        if (boatNum > 0 && boatStack.size() >= undoStartIndex) {
          System.out.println("Undo!");
          boatStack.pop();
          boatNum--;
          battle.getHumanPlayer().removeBoat(boatNum);
          System.out.println(battle.getHumanPlayer().printGrid());
          banner.setText("Welcome to Battleship! Time to place boat " + (boatNum+1));
          instructions.setText("Choose a starting coordinate for boat " + (boatNum+1));
          currentBoatInfo.setText("Boat " + (boatNum+1) + " has a length of " + 
                                  battle.getHumanPlayer().getBoatAt(boatNum).getLength() + ".");
          isStartCoord = true;
        }
      } else if (boatNum < battle.getHumanPlayer().getNumBoats()) {
        System.out.println("Just hit a button!");
        if (isStartCoord) {
        currentStart = (GridButton) e.getSource();
        System.out.println("[PlaceBoat] actionPerformed(): start = (" + 
                           currentStart.getXCoord() + ", " + currentStart.getYCoord() + ").");
        instructions.setText("Choose an ending coordinate for boat " + (boatNum+1));
        isStartCoord = false;
        } else { //end coordinate
          currentEnd = (GridButton) e.getSource(); 
          System.out.println("[PlaceBoat] actionPerformed(): end = (" + 
                             currentEnd.getXCoord() + ", " + currentEnd.getYCoord() + ").");
          try {
          battle.getHumanPlayer().placeBoat(boatNum, currentStart.getXCoord(), currentStart.getYCoord(),
                                currentEnd.getXCoord(), currentEnd.getYCoord());
          System.out.println("pushing boat to boatStack");
          boatStack.push(battle.getHumanPlayer().getBoatAt(boatNum));
          if (boatStack.size() >= 3) { undoStartIndex = boatStack.size()-MAX_UNDO; }            
          isStartCoord = true;
          boatNum++;          
          if (boatNum < battle.getHumanPlayer().getNumBoats()) {
             banner.setText("Welcome to Battleship! Time to place boat " + (boatNum+1));
             instructions.setText("Choose a starting coordinate for boat " + (boatNum+1));
             currentBoatInfo.setText("Boat " + (boatNum+1) + " has a length of " + 
                                 battle.getHumanPlayer().getBoatAt(boatNum).getLength() + ".");
          } else {
            banner.setText("All your boats have been placed. Good luck!");
            instructions.setText("");
            currentBoatInfo.setText("");
          }
          } catch (InvalidPlacementException dumb) {
            banner.setText("INVALID! Please place boat " + (boatNum+1) + " again.");
            instructions.setText("Choose a starting coordinate for boat " + (boatNum+1));
            currentBoatInfo.setText("Boat " + (boatNum+1) + " has a length of " + 
                                 battle.getHumanPlayer().getBoatAt(boatNum).getLength() + ".");
            isStartCoord = true;
          }
          System.out.println(battle.getHumanPlayer().findMyFleet());
          System.out.println(battle.getHumanPlayer().printGrid());
        }
     

    }
  }
  }
}