/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * BattleshipGUI.java
 * Dec 12th, 2014
 * 
 * Purpose: Visual representation of all Battleship games
 */

import javax.swing.*;
import java.awt.*; 

public class BattleshipGUI {
  
  //instance variables
  
  public static void main (String[] args) {
    JFrame frame = new JFrame ("Battleship");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Game battle = new Game("testing");
    //AllStats scores = new AllStats()
    JTabbedPane tp = new JTabbedPane();
    
    tp.addTab("Instructions", new WelcomePanel());
    tp.addTab("Game", new PlaceBoatPanel(battle.getHumanPlayer()));
    tp.addTab("Game", new GamePanel(battle));
    tp.addTab("Top Scores", new StatsPanel());
    
    frame.getContentPane().add(tp);
    frame.setPreferredSize(new Dimension(1000, 700)); //fixed dimensions
    
    frame.pack();
    frame.setVisible(true);
  } // ends main
} //ends GUI