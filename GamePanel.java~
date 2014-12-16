/* NAME: GamePanel.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 12 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: 
 * 
 * NOTES: 
 * Have to make the buttons react after being pressed (change color)
 * 
 * @author Meera Hejmadi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory;

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
  private final Color SEA = new Color (0, 0, 128);
//  private static Border CELL_BORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED);
  private Cell[][] humanRefGrid;
  private JButton letsPlay;
  
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
    humanRefGrid = currentGame.getHumanPlayer().getGrid();
    letsPlay = new JButton();

    
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

    emptySpace.setLayout(new GridLayout(3,6));
    emptySpace.setBackground(Color.black);
    JPanel moreEmpty = new JPanel();
    moreEmpty.setBackground(Color.black);
    JPanel playButton = new JPanel();
    letsPlay.setText("Play game!");
    letsPlay.addActionListener(new GridButtonListener());
    playButton.add(letsPlay);
    
    emptySpace.add(letsPlay);
    

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
        temp.setBackground(SEA);
        grid.add(temp);
      }      
    }
    
    return grid;
  }
  
  public JPanel createHumanGrid() {
    JPanel hGrid = new JPanel();
    hGrid.setLayout(new GridLayout(10, 10));
    //grid.setPreferredSize(new Dimension(screenHeight/3, screenlength/2))
    hGrid.setLayout(new GridLayout(gridSize, gridSize));
    hGrid.setBackground(Color.black);
    
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        GridButton temp = new GridButton(i, j);
        temp.setBackground(decideColor(temp));
//        System.out.println("humanGrid(): " + i + ", " + j + " is " + temp.getBackground());
        hGrid.add(temp);        
      }      
    }
    
    return hGrid;
  }
  
  public Color decideColor(GridButton g) {
    Color c;
    switch (humanRefGrid[g.getXCoord()][g.getYCoord()].getAllInfo()) {
          case (1):
            c = Color.black;
            break;
          case (2):
            c = Color.white;
            break;
          case (3):
            c = Color.red;
            break;
          default:
            c = SEA;
        }
    return c;
  }
  
  private String buttonToString(GridButton b) {
    String s = "buttonToString(): Button (" + b.getXCoord() + ", " +
      b.getYCoord() + ") is now " + b.getBackground();
    return s;
  }
  
  private class GridButtonListener implements ActionListener {
    
    private GridButton nextShot;
    
    public void actionPerformed (ActionEvent e) {
      if (e.getSource() == letsPlay) {
        letsPlay.setVisible(false);
        humanRefGrid = currentGame.getHumanPlayer().getGrid();
        revalidate();
      } else {
        if (currentGame.getGameOver() < 0) {
          nextShot = (GridButton) e.getSource();
          System.out.println("Took a shot! (" + nextShot.getXCoord()
                               + ", " + nextShot.getYCoord() + ")");
          currentGame.turn(nextShot.getXCoord(), nextShot.getYCoord());
          System.out.println("\n" + buttonToString(nextShot));
          
          
        }
      }
    }
  }
    
  
    
  }
