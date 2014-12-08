/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * Stats.java
 * Dec 7th, 2014
 * 
 * Purpose: To create a LinkedList that stores a user's stats.
 */

public class Stats {
  
  //Instance variables:
  private final String userName;
  private int gamesPlayed, gamesWon, averageScore;
  private int[] highScores; //smallest to largest (less shots means better score)
  private final int NUM_HIGHSCORES = 5;
  
  //Constructor:
  public Stats(String username) {
    userName = username; 
    gamesPlayed = 0;
    gamesWon = 0;
    averageScore = 0;
    highScores = new int[NUM_HIGHSCORES];
  }
  
  //Methods:
  public String getUserName() {
    return userName;
  }
  
  public int percentGamesWon() {
    return gamesWon*100/gamesPlayed;
  }
  
  public int getAverage() {
    return averageScore;
  }
  
  public int [] getHighScores() {
    return highScores;
  }
  
  public void gameCompleted(int score) {
    gamesPlayed++;
    if (score > 0) {
      gamesWon++;
      addHighScore(score);
      calculateAverage(score); //average score only changes if the user won
    }
  }
  
  private void addHighScore(int score) {
    System.out.println("addHighScore(): gamesWon: " + gamesWon);
    int whereToPlace = 0;
    if (gamesWon <= NUM_HIGHSCORES) {
      if (gamesWon == 1) {
        highScores[0] = score;
        System.out.println("addHighScore(): Just put " + score + " at position 0.");
      } else { //0 < gamesWon < NUM_HIGHSCORES
        System.out.println("0 < gamesWon < NUM_HIGHSCORES");
        for (int i = 0; i < gamesWon-1; i++) {
          if (score > highScores[i]) {
            whereToPlace = i+1;
            System.out.println("addHighScore(): " + score+ "'s whereToPlace is " + whereToPlace);
          } else {
            break;
          }
        }
        System.out.println("Final whereToPlace for " + score + " is " + whereToPlace);
        adjustScores(whereToPlace);
        highScores[whereToPlace] = score;
      }
    } else { 
      System.out.println("addHighScore(): gamesWon > NUM_HIGHSCORES");
      whereToPlace = highScoreLocation(score);
      System.out.println("Final whereToPlace for " + score + " is " + whereToPlace);
      if (whereToPlace < NUM_HIGHSCORES) { //if not, then the score isn't high enough
        adjustScores(whereToPlace); //will move the numbers down 
        highScores[whereToPlace] = score;
      }
    }
  }
  
  private int highScoreLocation(int score) {
    int whereToPlace = NUM_HIGHSCORES;
    for (int i = NUM_HIGHSCORES - 1; i >= 0; i--) {
      if (score < highScores[i]) {
        whereToPlace = i;
      } else { 
        break;
      }
    }
    return whereToPlace;
  }
  
  private void adjustScores(int moveFrom) {
    for (int i = NUM_HIGHSCORES - 1; i >= moveFrom; i--) {
      if (i != 0) {
        highScores[i] = highScores[i-1];
      }
    }
  }
  
   private String printHighScores() {
    String s = "High scores: ";
    for (int i = 0; i < NUM_HIGHSCORES; i++) {
      s += highScores[i] + " ";
    }
    return s;
  }
  
  private void calculateAverage(int newScore) {
    int tempAve = averageScore * (gamesWon-1); 
//(gamesWon-1) because assumes that gamesWon has been updated before method is called
    averageScore = (tempAve + newScore)/gamesWon;
  }
  
  public String toString() {
    String s = userName + "'s stats are:\n";
    s += "Percentage of games won: " + percentGamesWon() +
      "% (won: " + gamesWon + " out of " + gamesPlayed + ")\n";
    s += "Average Score: " + getAverage() + "\n";
    s += "High Scores: ";
    for (int i = 0; i < NUM_HIGHSCORES; i++) {
      s += highScores[i] + " ";
    }
    return s;
  }
  
  public static void main (String[] args) {
    Stats mhejmadi = new Stats("mhejmadi");
    mhejmadi.gameCompleted(0);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 0");
    System.out.println(mhejmadi.printHighScores());
    mhejmadi.gameCompleted(24);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 24");
    System.out.println(mhejmadi.printHighScores());
    mhejmadi.gameCompleted(26);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 26");
    System.out.println(mhejmadi.printHighScores());
    mhejmadi.gameCompleted(32);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 32");
    System.out.println(mhejmadi.printHighScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(18);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 18");
    System.out.println(mhejmadi.printHighScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(45);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 45");
    System.out.println(mhejmadi.printHighScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(50);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 50");
    System.out.println(mhejmadi.printHighScores());
    mhejmadi.gameCompleted(5);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 5");
    System.out.println(mhejmadi.printHighScores());
//    System.out.println(mhejmadi);
  }
}