/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * Stats.java
 * Dec 7th, 2014
 * 
 * Purpose: To create a LinkedList that stores a user's stats.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

public class Stats {
  
  //Instance variables:
  private final String userName;
  private int gamesPlayed, gamesWon, averageScore;
  private int[] bestScores; //smallest to largest (less shots means better score)
  private final int NUM_HIGHSCORES = 5;
  
  /******************************************************************
    * Constructor: Creates a Stats object for a single user.
    * 
    * @param    username    username to be stored in this instance
    *****************************************************************/
  public Stats(String username) {
    userName = username; 
    gamesPlayed = 0;
    gamesWon = 0;
    averageScore = 0;
    bestScores = new int[NUM_HIGHSCORES];
  }
  
  //Methods:
  /*******************************************************************
    * Returns the username.
    * 
    * @return   String   object's username
    *******************************************************************/
  public String getUserName() {
    return userName;
  }
  
  /*******************************************************************
    * Returns an integer value of the percentage of games won by the user.
    * 
    * @return   int   percent of games won
    *******************************************************************/
  public int percentGamesWon() {
    if (gamesWon == 0) {
      return 0;
    } else {
      return gamesWon*100/gamesPlayed;
    }
  }
  
  /******************************************************************
    * Sets games played. Only for use in AllStats' loadFile()
    * 
    * @param  games     number of games player
    *****************************************************************/
  public void setGamesPlayed(int games) { //for AllStats only
    gamesPlayed = games;
  }
  
  /******************************************************************
    * Sets number of games won. Only for use in AllStats' loadFile()
    * 
    * @param  games    number of games won
    *****************************************************************/
  public void setGamesWon(int games) { //for AllStats only
    gamesWon = games;
  }
  
  /*******************************************************************
    * Returns the user's average score. Average score is calculated by
    * adding all the scores and dividing by the number of games won.
    * 
    * @return   int   user's average score
    *******************************************************************/
  public int getAverage() {
    return averageScore;
  }
  
  /******************************************************************
    * Sets average score. Only for use in AllStats' loadFile()
    * 
    * @param  av     average score
    *****************************************************************/
  public void setAverage (int av) { //for use with .txt file in AllStats
    averageScore = av;
  }
  
  /*******************************************************************
    * Returns an int array of the user's top 5 scores.
    * 
    * @return   int[]   user's 5 high scores
    *******************************************************************/
  public int[] getBestScores() {
    return bestScores;
  }
  
  /******************************************************************
    * Sets best scores. Only for use in AllStats' loadFile()
    * 
    * @param  best     int[] of bet scores
    *****************************************************************/
  public void setBestScores (int[] best) {
    //sorting algorithm would be here
    bestScores = best;
  }
  
  /*******************************************************************
    * Updates a user's statistics after they have completed a game. Takes in
    * the score of the game that has just been played and makes necessary 
    * changes to user's stats.
    * 
    * @param   score   user's score from newly-completed game
    *******************************************************************/
  public void gameCompleted(int score) {
    gamesPlayed++;
    if (score > 0) {
      gamesWon++;
      addHighScore(score);
      calculateAverage(score); //average score only changes if the user won
    }
  }
 
  /*******************************************************************
    * Private, used in gameCompleted(). Takes in a score and decides whether
    * it should be added to bestScores[]. Figures out where to put the high
    * score. Places it in the correct position in the array.
    * 
    * @param   score   user's score from newly-completed game
    *******************************************************************/
  private void addHighScore(int score) {
    System.out.println("addHighScore(): gamesWon: " + gamesWon);
    if (gamesWon == 1) {
      bestScores[0] = score;
      System.out.println("addHighScore(): Just put " + score + " at position 0.");
    } else { //user has won multiple games
      System.out.println("addHighScore(): gamesWon > NUM_HIGHSCORES");
      int scorePosition = scoreLocation(score); //returns where "score" goes if placed into the bestScores[]
      System.out.println("Final whereToPlace for " + score + " is " + scorePosition);
      //decides whether score should be inserted into array or not
      if (scorePosition < NUM_HIGHSCORES) { 
        //moves numbers down so bestScores remains accurate
        adjustScores(scorePosition); 
        bestScores[scorePosition] = score;
      }
    }
  }
  
  /*******************************************************************
    * Private, used in addHighScores(). Calculates input score's location
    * in bestScores[].
    * 
    * Assumptions:
    * -`score` is user's best score
    * 
    * @param   score   user's score from newly-completed game
    *******************************************************************/
  private int scoreLocation(int score) {
    int whereToPlace = 0; //if not to be added, this will work.
    for (int i = 0; i < gamesWon-1; i++) { //if gamesWon == 1, is has already been set in addHighScore
      if (score > bestScores[i]) {
        whereToPlace++;
        System.out.println("addHighScore(): " + score+ "'s whereToPlace is " + whereToPlace);
      } else {
        break;
      }
    }
    return whereToPlace;
  }
  
  /*******************************************************************
    * Private, used in addHighScore(). Shifts all values past `moveFrom`
    * in bestScores[] to the right by one to slow in new best score at index
    * `moveFrom`.
    * 
    * @param   moveFrom   index location to start shifting elements over right
    *******************************************************************/
  private void adjustScores(int moveFrom) {
    for (int i = NUM_HIGHSCORES - 1; i >= moveFrom; i--) {
      if (i != 0) { //if i == 0, then (i-1) will throw an error
        //if true, then 0th slot is to overwritten in any case.
        bestScores[i] = bestScores[i-1];
      }
    }
  }
  
  /*******************************************************************
    * Private, used in gamedCompleted(). Takes in user's new score and calculates
    * the new average, resetting averageScore appropriately.
    * 
    * Assumptions:
    * -gamesWon is up to date
    * 
    * @param   newScore   user's score from newly-completed game
    *******************************************************************/
  private void calculateAverage(int newScore) {
    int sum = averageScore * (gamesWon-1); 
    averageScore = (sum + newScore)/gamesWon;
  }
  
  /* printBestScores(): used for testing purposes. Prints bestScores array. */
  /*******************************************************************
    * Private, for testing purposes only. Prints bestScores array.
    * 
    * @param   String   String representation of bestScores[]
    *******************************************************************/
  private String printBestScores() {
    String s = "High scores: ";
    for (int i = 0; i < NUM_HIGHSCORES; i++) {
      s += bestScores[i] + " ";
    }
    return s;
  }
 
  /***********************************************************************
    * Returns a string representation of the Stats object.
    * 
    * @return    string representation of user's stats.
    *********************************************************************/
  public String toString() {
    String s = userName + "'s stats are:\n";
    s += "Percentage of games won: " + percentGamesWon() +
      "% (won: " + gamesWon + " out of " + gamesPlayed + ")\n";
    s += "Average Score: " + getAverage() + "\n";
    s += "High Scores: ";
    for (int i = 0; i < NUM_HIGHSCORES; i++) {
      s += bestScores[i] + " ";
    }
    return s;
  }
  
  //Testing main.
  public static void main (String[] args) {
    Stats mhejmadi = new Stats("mhejmadi");
    mhejmadi.gameCompleted(0);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 0");
    System.out.println(mhejmadi.printBestScores());
    mhejmadi.gameCompleted(24);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 24");
    System.out.println(mhejmadi.printBestScores());
    mhejmadi.gameCompleted(26);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 26");
    System.out.println(mhejmadi.printBestScores());
    mhejmadi.gameCompleted(32);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 32");
    System.out.println(mhejmadi.printBestScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(18);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 18");
    System.out.println(mhejmadi.printBestScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(45);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 45");
    System.out.println(mhejmadi.printBestScores());
//    System.out.println(mhejmadi);
    mhejmadi.gameCompleted(50);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 50");
    System.out.println(mhejmadi.printBestScores());
    mhejmadi.gameCompleted(5);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 5");
    System.out.println(mhejmadi.printBestScores());
    mhejmadi.gameCompleted(18);
    System.out.println("gamesPlayed: " + mhejmadi.gamesPlayed + "\tScore: 18");
    System.out.println(mhejmadi.printBestScores());
//    System.out.println(mhejmadi);
  }
}