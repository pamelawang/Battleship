/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * StatsPanel.java
 * Dec 12th, 2014
 * 
 * Purpose: Creates a visual representation of AllStats.java (the user's stats)
 * 
 * @author Pamela Wang
 */

import java.awt.*;
import javax.swing.*;
import javafoundations.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

  public class StatsPanel extends JPanel {
  //extends JPanel instead of GridPanel because of tabbed Pane layout
  
  private JLabel question, percent, five, average;
  private TextField messageField;
  private JButton ok;
  private AllStats all;
  private ButtonListener buttonAction;
  
  public StatsPanel() {
    setLayout (new GridLayout(2, 3));
    setBackground(Color.white);
    question = new JLabel ("What is your username?");
    messageField = new TextField(5);
    add(question);
    add(messageField);
    
    ok = new JButton("OK");
    buttonAction = new ButtonListener();
    ok.addActionListener(buttonAction);
    add(ok);
    
    percent = new JLabel("% games won: ");
    add(percent);
    
    five = new JLabel ("Top 5 \nScores");
    add(five);
    
    average = new JLabel ("Average Score");
    add(average);
    
  }
  
  public void addToLabel(JLabel j, String s) {
    j.setText(j.getText() + s);
  }
  
 private class ButtonListener implements ActionListener {
    
    public void actionPerformed (ActionEvent e) {
      String uName = messageField.getText();
      try {
      all.loadFile("allstats.txt");
      Stats current = all.getPlayer(uName);
      addToLabel(percent, current.percentGamesWon() + "%");
      int[] best = current.getBestScores();
      for (int i = 0; i < 5; i++) {
        addToLabel(five, " " + best[i]);
      }
      addToLabel(average, Integer.toString(current.getAverage()));
      } catch (Exception ex) {
        System.out.println("File unsuccessfully opened.");
      }
    } //closes actionPerformed
  } //closes ButtonListener
}//ends StatsPanel