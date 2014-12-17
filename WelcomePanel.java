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
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class WelcomePanel extends JPanel  {
  
  private JLabel title, instructions, authors, east, west;
  
  public WelcomePanel() {
    setBackground(Color.black);
    setLayout (new BorderLayout());
    
    title = new JLabel ("Battleship");
    File i = new File("intro.png");
    try {
      Image intro = ImageIO.read(i);
      intro = getScaledImage(intro, 1000, 700);
      this.add(new JLabel(new ImageIcon(intro)));
    } catch (IOException e) {
    }
  } 
  
  private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
  }
 
}//ends WelcomePanel