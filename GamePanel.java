/* NAME: GamePanel.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 12 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Gameplay panel showing two grids and 2 side sections. Main panel
 * for GUI.
 * 
 * NOTES: 
 * Have to make the buttons react after being pressed (change color)
 * 
 * @author Meera Hejmadi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GamePanel extends JPanel {
  
  private Game battle;
  private Player human;
  private ComputerPlayer computer;
  private JPanel centerPiece; //will contain banner, humanGrid, and shootAtGrid
  private JLabel banner; //instructions, hit/miss, etc
  private JPanel shootAtGrid; //grid the human player will shoot at
  private JPanel boatsLeft; //goes on the left of the center panel
  private int numBoatsLeftComp;
  private int numBoatsLeftHuman;
  private int numBombsLeftComp;
  private int numBombsLeftHuman;
  private JPanel bombsLeft; //goes on right of center panel
  private int gridSize;
  private final Color SEA = new Color (0, 0, 128);
  private Cell[][] humanRefGrid; //copy of the human Player's grid.
  private Cell[][] compRefGrid; //copy of the ComputerPlayer
  private GridButton[][] humanGrid; //2D array of gridButtons create human's GUI grid.
  private JButton letsPlay;
  private JButton readyForComp;
  
  
   /******************************************************************
    * Creates a new instance of the GamePanel. Requires a Game input, and 
    * works based off of that.
    * 
    * @param    battleship    input game that GamePanel runs off of
    *****************************************************************/
  public GamePanel(Game battleship) {
    
    battle = battleship;
    human = battle.getHumanPlayer();
    computer = battle.getCompPlayer();
    gridSize = battleship.getGridSize();
    
    setLayout(new BorderLayout());
    setBackground(Color.black);
    
    banner =  new JLabel("Welcome to Battleship! Press \"Play\" to begin!");
    banner.setForeground(Color.white);
    humanGrid = new GridButton[gridSize][gridSize];
    shootAtGrid = new JPanel();

    numBoatsLeftComp = computer.getNumBoats();
    numBoatsLeftHuman = human.getNumBoats();
//    numBombsLeftComp = battleship.getCompPlayer().getNumBombs();
//    numBombsLeftHuman = battleship.getHumanPlayer().getNumBombs();
    numBombsLeftComp = 0;
    numBombsLeftHuman = 0;
    humanRefGrid = human.getGrid();
    compRefGrid = computer.getGrid();
    letsPlay = new JButton();
    readyForComp = new JButton("Bring it on!");
    
    
    boatsLeft = createBoatsLeft();
    bombsLeft = createBombsLeft();
    centerPiece = createCenterPiece();
    
    add(boatsLeft, BorderLayout.WEST);
    add(centerPiece, BorderLayout.CENTER);
    add(bombsLeft, BorderLayout.EAST);
    
  }
  
  /******************************************************************
    * Creates centre piece of panel.
    * 
    * @return  JPanel  fully-formed centre piece (Banner and 2 grids)
    *****************************************************************/
  public JPanel createCenterPiece() {
    JPanel center = new JPanel();
    center.setLayout(new GridLayout(4, 1));
    center.setBackground(Color.black);
    
    JPanel top = new JPanel(new FlowLayout());
    top.setBackground(Color.black);
    JPanel emptySpace = new JPanel();
    emptySpace.setLayout(new GridLayout(3,6));
    emptySpace.setBackground(Color.black);
    JPanel playButton = new JPanel();
    playButton.setBackground(Color.black);
    
    letsPlay.setText(">>>>>>>>>> Play! <<<<<<<<<<");
    letsPlay.addActionListener(new GridButtonListener());
    readyForComp.addActionListener(new GridButtonListener());
    readyForComp.setVisible(false);
    
    top.add(banner);
    top.add(readyForComp);
    playButton.add(letsPlay);
    emptySpace.add(playButton);
    
    JPanel shootGrid = createShootAtGrid();
    JPanel hGrid = createHGrid();
    
    center.add(top);
    center.add(shootGrid);
    center.add(emptySpace);
    center.add(hGrid);
    
    return center;
  }
  
  /******************************************************************
    * Leftmost section that shows what boats are left.
    * 
    * @return  JPanel  section showing which boats are left for both players
    *****************************************************************/
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
  
  /******************************************************************
    * Rightmost section showing how many bombs are left.
    * 
    * @return  JPanel  section showing how many bombs are left for both players
    *****************************************************************/
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
  
  /******************************************************************
    * Creates the top grid (the grid the user is using to shoot at 
    * opponent and to keep track of previous shots.)
    * 
    * @return  JPanel  uppermost grid showing where the user has shot
    *****************************************************************/
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
  
  /******************************************************************
    * Creates the bottom grid (the grid with the user's boats and Computer's
    * previous shots)
    * 
    * @return  JPanel  bottommost grid showing where the user has shot
    *****************************************************************/
  public JPanel createHGrid() {
    JPanel hagrid = new JPanel();
    hagrid.setLayout(new GridLayout(10, 10));
    //grid.setPreferredSize(new Dimension(screenHeight/3, screenlength/2))
    hagrid.setLayout(new GridLayout(gridSize, gridSize));
    hagrid.setBackground(Color.black);
    
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        GridButton temp = new GridButton(i, j);
        temp.setBackground(decideColor(temp));
        humanGrid[i][j] = temp;
//        System.out.println("humanGrid(): " + i + ", " + j + " is " + temp.getBackground());
        hagrid.add(temp);        
      }      
    }
    
    return hagrid;
  }
  
  /******************************************************************
    * Decides the colour of the cell depending on the state of the Cell
    * (sea, boat, hit, miss)
    * 
    * @return  Color  colour of that represents the cell's current state
    *****************************************************************/
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
  
  /******************************************************************
    * String representation of current button/coordinate.
    * 
    * @return  String  String representation of current Cell/coordinate
    *****************************************************************/
  private String buttonToString(GridButton b) {
    String s = "buttonToString(): Button (" + b.getXCoord() + ", " +
      b.getYCoord() + ") is now " + b.getBackground();
    return s;
  }
  
  /******************************************************************
    * GridButtonListener
    *****************************************************************/
  private class GridButtonListener implements ActionListener {
    private GridButton nextShot;
    
    public void actionPerformed (ActionEvent e) {
      if (e.getSource() == letsPlay) {
        letsPlay.setVisible(false);
        humanRefGrid = human.getGrid();
        compRefGrid = computer.getGrid();
        banner.setText("It's your turn! Click on a square in the upper grid to shoot at the computer.");
        revalidate();
      } else {
        if (battle.getGameOver() < 0) {
          if (battle.getIsHumanTurn()) {
            nextShot = (GridButton) e.getSource();
            System.out.println("Took a shot! (" + nextShot.getXCoord()
                                 + ", " + nextShot.getYCoord() + ")");
            try {
              int result = computer.gotShot(nextShot.getXCoord(), nextShot.getYCoord());
              changeSettings(result, nextShot);
              banner.setText(postShot(result)+ " It's the computer's turn!");
              readyForComp.setVisible(true);
              System.out.println("\n" + buttonToString(nextShot));
              battle.setIsHumanTurn(false);
              System.out.println("After human's shot, computer's grid: \n" + computer.printGrid());
            } catch (InvalidShotException oops) {
              //do nothing for now - ask user to pick a diff coordinate.
            }
            
          } else {
            readyForComp.setVisible(false);
            int result = computer.takeAShot(human);
            int x = computer.getAimAtX();
            int y = computer.getAimAtY();
            banner.setText("It shot at ("+x+", "+y+")! "+ postShot(result) + " Your turn!");
            changeSettings(result, humanGrid[x][y]);
//            System.out.println("\n" + buttonToString(nextShot));
            System.out.println("After computer's shot, humans's grid: \n" + human.printGrid());
            System.out.println("Computer shoots!");
            battle.setIsHumanTurn(true);
          }
        }
      }
    }
    
    private String postShot (int resultOfShot) {
      String s = "";
      switch (resultOfShot) {
        case 1: 
          s+= "Hit!";
          break;
        case 2:
          s+= "Hit and sunk!";
          break;
        default:
          s+= "Miss!";
          break;
      }
      return s;
    }
    
    private void changeSettings (int resultOfShot, GridButton justShot) { //only when user goes
      switch (resultOfShot) {
        case 1: //hit 
          justShot.setBackground(Color.red);
          break;
        case 2:
          justShot.setBackground(Color.red);
          break;
        default:
          justShot.setBackground(Color.white);
          break;
      }
    }
  }
}
