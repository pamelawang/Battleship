/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * StatsPanel.java
 * Dec 12th, 2014
 * 
 * Purpose: Creates a visual representation of AllStats.java (the user's
 */

import java.awt.*;
import javax.swing.*;

public class StatsPanel extends JPanel {
  
  private JLabel title, instructions, authors;
  
  public StatsPanel() {
    
    setBackground(Color.black);
    setLayout (new BoxLayout (this, BoxLayout.Y_AXIS)); //writing goes until end of screen
    
    title = new JLabel ("Battleship");
    title.setAlignmentX (Component.CENTER_ALIGNMENT);
    this.add(title);
    
    instructions = new JLabel ("How to play");
    instructions.setAlignmentX (Component.CENTER_ALIGNMENT);
    this.add(instructions);
    
    authors = new JLabel ("\u00A9 2014 Meera Hejmadi and Pamela Wang");
    authors.setAlignmentX (Component.CENTER_ALIGNMENT);
    this.add(authors);
    
    //this.add(new JLabel(new ImageIcon("IMAGE NAME HERE"));
  } 
 
}//ends StatsPanel