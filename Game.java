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

  public class Game {
  
  //instance variables:
  private String username; //for the Stats class to use?
  private Player human;
  private Player computer; //will be changed to ComputerPlayer later
  private int score;
  private boolean humanTurn;
  private int gameOver; // -1 if not true, 0 for comp, 1 for human
  private final int NOT_OVER = -1;
  private final int COMP_WIN = 0;
  private final int HUMAN_WIN = 1;
//  private CircularArrayStack last3Human;
//  private CircularArrayStack last3Comp; //undo stuff
  
  /***********************************************************************
   * Constructor: Creates a game. Each game has two players - human and 
   * computer - and a score.
   ***********************************************************************/
  public Game (String name) {
    username = name;
    human = new Player();
    computer = new Player();
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
  public void turn(int x, int y) {
    if (humanTurn) {
      computer.gotShot(x, y); //Computer is the one being shot at
      score++; //user has taken another shot
      humanTurn = false; //for next round
      if (computer.didILose()) { //checking if game is over
        gameOver = HUMAN_WIN;
        System.out.println("Game over. Human won.");
      }
    } else {
      human.gotShot(x,y);
      humanTurn = true;
      if (human.didILose()) {
        gameOver = COMP_WIN;
        System.out.println("Game over. Computer won.");
      }
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
    bringIt.turn(2, 2);
  } 
 
}