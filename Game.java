/* NAME: Game.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 10 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Creates a single game instance, with two players.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.io.*;
import java.util.*;

public class Game {
  
  //instance variables:
  private String username; //for the Stats class to use?
  private Player human;
  private ComputerPlayer computer; //will be changed to ComputerPlayer later
  private int score;
  private boolean humanTurn;
  private int gameOver; // -1 if not true, 0 for comp, 1 for human
  private static int NOT_OVER = -1;
  private static int COMP_WIN = 0;
  private static int HUMAN_WIN = 1;
//  private CircularArrayStack last3Human;
//  private CircularArrayStack last3Comp; //undo stuff
  
  /***********************************************************************
    * Constructor: Creates a game. Each game has two players - human and 
    * computer - and a score.
    ***********************************************************************/
  public Game (String name) {
    username = name;
    human = new Player();
    computer = new ComputerPlayer();
    score = 0;
    humanTurn = true; //human goes first
    gameOver = NOT_OVER;
  }
  
  /*****************************************************************
    * Main method in game. Creates a round to be played for both Computer
    * and the user.
    * 
    * @param   x   x-coordinate indicating where 'this' Player is shooting at the other PLayer
    * @param   y   y-coordinate indicating where 'this' Player is shooting at the other Player
    *****************************************************************/
  /* QUESTIONS FOR PROFS: So, for our player, the turn receives an (x,y) coordinate
   * when they click on a button. BUT for the computer, when it's its turn then
   * it generates the coordinates. And the turn method requires the x and y in 
   * the parameters.. So how does this work?
   */
  //TURN IS PLAYER GOES THEN COMPUTER GOES - THAT'S A TURN. NICE AND SIMPLE.
  public void turn(int x, int y) throws InvalidShotException{
    if (humanTurn) {
      System.out.println("HUMAN");
      computer.gotShot(x, y); //Computer is the one being shot at
      score++; //user has taken another shot
      humanTurn = false;
      if (computer.didILose()) { //checking if game is over
        gameOver = HUMAN_WIN;
        System.out.println("Game over. Human won.");
      }
    }
    System.out.println("COMPUTER");
    computer.shoot(human);
    humanTurn = true;
    if (human.didILose()) {
      gameOver = COMP_WIN;
      System.out.println("Game over. Computer won.");
    }
  }
  
  public String getUsername() {
    return username;
  }  
  
  public boolean getHumanTurn() {
    return humanTurn;
  }
  
  public int getGameOver() {
    return gameOver;
  }
  
  public int getScore() {
    return (gameOver != COMP_WIN) ? score : 0; 
    //because you want to be able to see the user's score during the game.
    //If they win, then their score is important. if they lose (comp wins)
    //then their score defaults back to zero.
  }
  
  public String toString() {
    String s = "This game is " + username + " vs. Computer.";
    s += "\nThe current score is " + getScore() + ", and it is ";
    s += (getHumanTurn()) ? username + "'s turn." : "the computer's turn.";
    return s;
  }
  
  
  //testing main:
  public static void main (String[] args) {
    Game bringIt = new Game("meera");
    Scanner scan = new Scanner (System.in);
    int currentX, currentY;
    System.out.println("Please enter coordinates in the form \"x y\'" 
                         + ". ex. (2,3) should be 2 3");
    while (bringIt.getGameOver() == NOT_OVER) {
      System.out.println("Your turn! Where would you like to shoot?");
      currentX = scan.nextInt();
      currentY = scan.nextInt();
      try {
        bringIt.turn(currentX, currentY);
      } catch (InvalidShotException oops) {
        //nada. loop around again.
      }
    }
  } 
  
}