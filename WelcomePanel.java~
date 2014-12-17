/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * WelcomePanel.java
 * Dec 12th, 2014
 * 
 * Purpose: Creates a panel detailing the rules of Battleship and authors
 * 
 * @author Pamela Wang
 */

import java.awt.*;
import javax.swing.*;

public class WelcomePanel extends JPanel  {
  
  private JLabel title, instructions, authors, east, west;
  
  public WelcomePanel() {
    
    setBackground(Color.blue);
    setLayout (new BorderLayout());
    
    title = new JLabel ("Battleship");
    title.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(title, BorderLayout.NORTH);
    
    east = new JLabel(" "); //blank to use formatting rules of BorderLayout
    this.add(east, BorderLayout.EAST);
    
    west = new JLabel(" "); //blank to use formatting rules of BorderLayout
    this.add(west, BorderLayout.WEST); 
    
    instructions = new JLabel ("How to play");
    instructions.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(instructions, BorderLayout.CENTER);
    
    authors = new JLabel ("\u00A9 2014 Meera Hejmadi and Pamela Wang");
    authors.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(authors, BorderLayout.SOUTH);
    
    //this.add(new JLabel(new ImageIcon("IMAGE NAME HERE"));
  } 
 
}//ends WelcomePanel