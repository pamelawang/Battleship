/* NAME: Game.java
 * Created by: Meera Hejmadi and Pamela Wang
 * Date created: 10 Dec 2014
 * CS 230 Project: Battleship
 * 
 * Purpose: Creates a single game instance, with two players.
 * 
 * NOTES:
 * 1. We don't use a "turn" method because the way that the GUI is 
 * structured means that each player takes a turn separately, and we can't
 * combine the two into a single turn method for GUI-purposes.
 * However, we've just commented out the turn method below, because we used 
 * it for non-GUI testing.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */
import java.io.*;
import java.util.*;

public class Game {
  
  //instance variables:
  private String username; //for the Stats class to use
  private Player human;
  private ComputerPlayer computer; 
  private boolean isHumanTurn;
  private int score;
  private int gameOver; // -1 if not true, 0 for comp, 1 for human
  private static int NOT_OVER = -1;
  private static int COMP_WIN = 0;
  private static int HUMAN_WIN = 1;
  
  /***********************************************************************
    * Constructor: Creates a game. Each game has two players - human and 
    * computer - and a score.
    ***********************************************************************/
  public Game (String name) {
    username = name;
    human = new Player();
    computer = new ComputerPlayer();
    isHumanTurn = true; //human starts.
    score = 0;
    gameOver = NOT_OVER;
    computer.placeBoats();
  }
  
  /*****************************************************************
    * Primary method in game. Creates a round to be played for both Computer
    * and the user, since the Computer would always go after the user's turn.
    * 
    * @param   x   x-coordinate indicating where 'this' Player is shooting at the other PLayer
    * @param   y   y-coordinate indicating where 'this' Player is shooting at the other Player
    *****************************************************************/
 /* public void turn(int x, int y) { //throws InvalidShotException {
    //HUMAN TURN
    System.out.println("turn(): HUMAN");
    try {
    computer.gotShot(x, y); //Computer is the one being shot at
    score++; //user has taken another shot
    if (computer.didILose()) { //checking if game is over
      gameOver = HUMAN_WIN;
      System.out.println("Game over. Human won.");
    }     
    //COMPUTER'S TURN
    System.out.println("turn(): COMPUTER");
    computer.shoot(human);
    if (human.didILose()) { //checking if the game is over
      gameOver = COMP_WIN;
      System.out.println("Game over. Computer won.");
    }
    } catch (InvalidShotException oops) {
      //restart that turn
    }
      
  } */ //This was used only for non-GUI playing of the game.
  
  /*****************************************************************
    * Returns the user's account (Player) along with their fleet and grid.
    * 
    * @return  Player   the user's account
    *****************************************************************/
  public Player getHumanPlayer() {
    return human;
  }
  
  /*****************************************************************
    * Returns the Computer's account (ComputerPlayer) along with its
    * fleet and grid.
    * 
    * @return  ComputerPlayer   the Computer's account
    *****************************************************************/
  public ComputerPlayer getCompPlayer() {
    return computer;
  }
  
  /*****************************************************************
    * Returns the user's grid size (extension of the Player class'
    * getGridDimensions() method). Assume that the Computer has the 
    * same size grid as the user.
    * 
    * @return  int   size of the user's grid
    *****************************************************************/
  public int getGridSize() {
    return getHumanPlayer().getGridDimensions();
  }
  
  /*****************************************************************
    * Returns the user's number of boats (extension of the Player class'
    * getNumBoats() method). Assume that the Computer has the 
    * same number of boats as the user.
    * 
    * @return  int   number of boats
    *****************************************************************/
  public int getNumBoats() {
    return getHumanPlayer().getNumBoats();
  }
  
  /*****************************************************************
    * Returns the user's username.
    * 
    * @return  String   current player's username
    *****************************************************************/
  public String getUsername() {
    return username;
  }  
  
  /*****************************************************************
    * Returns whose turn it is; if true, then human's turn.
    * 
    * @return  boolean   whether it is the human player's turn to guess
    *****************************************************************/
  public boolean getIsHumanTurn() {
    return isHumanTurn;
  }  
  
   /*****************************************************************
    * Sets whose turn it is; if true, then human's turn.
    * 
    * @param  turn   whether it is the human player's turn to guess
    *****************************************************************/
  public void setIsHumanTurn(boolean turn) {
    isHumanTurn = turn;
  }  
  
  
  /*****************************************************************
    * Returns whether the game isn't over (-1), the Computer won (0) or
    * the user won (1) in int form.
    * 
    * @return  int   if the game isn't over (-1), the Computer won (0) or the user won (1)
    *****************************************************************/
  public int getGameOver() {
    return gameOver;
  }
  
  /*****************************************************************
    * Returns the user's score if they won, or 0 if the user lost.
    * (Score is only important if the user won.)
    * 
    * @return  int   user's score (0 if the user lost)
    *****************************************************************/
  public int getScore() {
    return (gameOver != COMP_WIN) ? score : 0;
  }
  
  /*****************************************************************
    * Returns a String representation of Game.java, with the user's username.
    * their current score and who's turn it is.
    * 
    * @return  int   user's score (0 if the user lost)
    *****************************************************************/
  public String toString() {
    String s = "This game is " + username + " vs. Computer.";
    s += "\nThe current score is " + getScore() + ",";
    return s;
  }
  
  //testing main:
/*  public static void main (String[] args) {
    Game bringIt = new Game("meera");
    Scanner scan = new Scanner(System.in);
    int currentX, currentY;
    System.out.println("Welcome to Battleship! Please enter coordinates in the form" 
                         + "\"x y\". Ex. (2,3) should be entered as \"2 3\".\nRemember"
                         + "that your grid is " + bringIt.getGridSize() + "square units.");
    System.out.println("Time to place your boats.");
    int boatX, boatY;
    System.out.println("Human:");
    for (int i = 0; i < bringIt.getHumanPlayer().getNumBoats(); i++) {
      System.out.println("Where do you want to put boat " + (i+1) + "?");
      boatX = scan.nextInt();
      boatY = scan.nextInt();
      //FIX bringIt.getHumanPlayer().placeBoat(i, boatX, boatY);
    }
    System.out.println("Computer:");
     for (int i = 0; i < bringIt.getCompPlayer().getNumBoats(); i++) {
      System.out.println("Where do you want to put boat " + (i+1) + "?");
      boatX = scan.nextInt();
      boatY = scan.nextInt();
      //FIX bringIt.getCompPlayer().placeBoat(i, boatX, boatY);
    }
    while (bringIt.getGameOver() == NOT_OVER) {
//      try {
        System.out.println("It's your turn! Please enter a set of coordinates you'd"
                             + "like to shoot at.");
        currentX = scan.nextInt();
        currentY = scan.nextInt();
        bringIt.turn(currentX, currentY);
//      } catch (InvalidShotException oops) {
//        //prompts user for a different set of coordinates by going back through loop
//      }
    }
  } */
  
}