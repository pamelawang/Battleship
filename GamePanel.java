/* NAME: GamePanel.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 12 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: 
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel {
  
  private JPanel centerPiece; //will contain banner, humanGrid, and shootAtGrid
  private JLabel banner;
  private JPanel humanGrid;
  private JPanel shootAtGrid;
  private JPanel boatsLeft; //goes on the left of the center panel
  private int numBoatsLeftComp;
  private int numBoatsLeftHuman;
  private int numBombsLeftComp;
  private int numBombsLeftHuman;
  private JPanel bombsLeft; //goes on right of center panel
  private int gridSize;
  private Game currentGame;
  
  public GamePanel(Game battleship) {
    
    setLayout(new BorderLayout());
    setBackground(Color.black);
    
    banner =  new JLabel("Welcome to Battleship!");
    humanGrid = new JPanel();
    shootAtGrid = new JPanel();
    gridSize = battleship.getGridSize();
    numBoatsLeftComp = battleship.getCompPlayer().getNumBoats();
    numBoatsLeftHuman = battleship.getHumanPlayer().getNumBoats();
//    numBombsLeftComp = battleship.getCompPlayer().getNumBombs();
//    numBombsLeftHuman = battleship.getHumanPlayer().getNumBombs();
    numBombsLeftComp = 0;
    numBombsLeftHuman = 0;
    currentGame = battleship;
    
    boatsLeft = createBoatsLeft();
    bombsLeft = createBombsLeft();
    centerPiece = createCenterPiece();
    
    add(boatsLeft, BorderLayout.WEST);
    add(centerPiece, BorderLayout.CENTER);
    add(bombsLeft, BorderLayout.EAST);
    
  }
  
  public JPanel createCenterPiece() {
    JPanel center = new JPanel();
    center.setLayout(new GridLayout(4, 1));
    center.setBackground(Color.black);
    
    JPanel emptySpace = new JPanel();
    emptySpace.setBackground(Color.black);
    shootAtGrid = createShootAtGrid();
    humanGrid = createHumanGrid();
    
    center.add(banner);
    center.add(shootAtGrid);
    center.add(emptySpace);
    center.add(humanGrid);
    
    return center;
  }
  
  public JPanel createBoatsLeft() {
    JPanel boats = new JPanel();
    boats.setLayout(new GridLayout(5, 1));
    boats.setBackground(Color.gray);
    JLabel title = new JLabel("     Boats still afloat:          ");
    JLabel emptySpace = new JLabel("");
    JLabel comp = new JLabel("     Computer: " + numBoatsLeftComp);
    JLabel human = new JLabel("     Human: " + numBoatsLeftHuman);
    
    boats.add(title);
    boats.add(emptySpace);
    boats.add(comp);
    boats.add(emptySpace);
    boats.add(human);
    
    return boats;
  }
  
  public JPanel createBombsLeft() {
    JPanel bombs = new JPanel();
    bombs.setLayout(new GridLayout(5, 1));
    bombs.setBackground(Color.blue);
    JLabel title = new JLabel("     Bombs left:     ");
    JLabel comp = new JLabel("     Computer: \n" + numBoatsLeftComp);
    JLabel human = new JLabel("     Human: \n" + numBoatsLeftHuman);
    JLabel emptySpace = new JLabel("");
    
    bombs.add(title);
    bombs.add(emptySpace);
    bombs.add(comp);
    bombs.add(emptySpace);
    bombs.add(human);
    return bombs;
  }
  
  public JPanel createShootAtGrid() {
    JPanel grid = new JPanel();
    //grid.setPreferredSize(new Dimension(screenHeight/3, screenlength/2))
    grid.setLayout(new GridLayout(gridSize, gridSize));
    grid.setBackground(Color.black);
    
    for (int i = 1; i <= gridSize; i++) {
      for (int j = 1; j <= gridSize; j++) {
        GridButton temp = new GridButton(i, j);
        temp.addActionListener(new GridButtonListener());
        grid.add(temp);
        
      }      
    }
    
    return grid;
  }
  
  public JPanel createHumanGrid() {
    JPanel grid = new JPanel();
    grid.setLayout(new GridLayout(10, 10));
    //grid.setPreferredSize(new Dimension(screenHeight/3, screenlength/2))
    grid.setLayout(new GridLayout(gridSize, gridSize));
    grid.setBackground(Color.black);
    
    for (int i = 1; i <= gridSize; i++) {
      for (int j = 1; j <= gridSize; j++) {
        GridButton temp = new GridButton(i, j);
        temp.addActionListener(new GridButtonListener());
        grid.add(temp);        
      }      
    }
    
    return grid;
  }
  
  private class GridButtonListener implements ActionListener {
    
    private GridButton nextShot;
    
    public void actionPerformed (ActionEvent e) {
      System.out.println("help");
      while (currentGame.getGameOver() < 0) {
        nextShot = (GridButton) e.getSource();
        try {
          currentGame.turn(nextShot.getXCoord(), nextShot.getYCoord());
          System.out.println("Took a shot! (" + nextShot.getXCoord()
                               + ", " + nextShot.getYCoord() + ")");
        } catch (InvalidShotException oops) {
          //do nothing
        }
      }
    }
  }
  
}
