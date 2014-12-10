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
  
  //WHAT ARE OUR METHODS?!
  
  //I don't know what to do once the game is over...
  public void turn(int x, int y) {
    if (humanTurn) {
      computer.gotShot(x, y);
      humanTurn = false;
      if (computer.didILose()) {
        gameOver = HUMAN_WIN;
        System.out.println("Game over. Computer lost.");
      }
    } else {
      human.gotShot(x,y);
      humanTurn = true;
      if (human.didILose()) {
        gameOver = COMP_WIN;
        System.out.println("Game over. Computer lost.");
      }
    }
  }
      
      
  
  
  
  
}