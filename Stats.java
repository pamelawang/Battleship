/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * Stats.java
 * Dec 7th, 2014
 * 
 * Purpose: To create a LinkedList that stores a user's stats.
 * 
 * Notes: 
 * 1. There are no Javadoc comments for the private methods, but there are
 * in-detail comments before those private methods. 
 * 2. There are less setter methods than getter methods, and that was a design
 * decision, because we don't want the stats to be manipulated.
 * 3. Add file - to add to for old scores
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
    return gamesWon*100/gamesPlayed;
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
  
  /*******************************************************************
    * Returns an int array of the user's top 5 scores.
    * 
    * @return   int[]   user's 5 high scores
    *******************************************************************/
  public int[] getBestScores() {
    return bestScores;
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
  
  // Private methods: no Javadocs.
  
  /* addHighScore(): This method has three functions: 
   * (i) to take in a score and decide if it should be added to the bestScores array
   * (ii) to figure out where to put the new high score
   * (iii) to place it into the correct position in the array.
   * 
   * There are two possibilities:
   * (a) the user has played only one game, in which case the score is 
   * automatically added to the 0 index of the bestScores array.
   * (b) th user has played more than one game, in which case it may or may
   * not be added to the array. A helper method is called to see what position
   * the score would get if it was added. This returned position is used to
   * decide whether to add the score or not: if position > bestScores.length -1,
   * then it isn't added. Else the array is adjusted and the score added
   * where it should be.
   */
  private void addHighScore(int score) {
    System.out.println("addHighScore(): gamesWon: " + gamesWon);
    if (gamesWon == 1) { //ask if this should be a final variable
      bestScores[0] = score; //and this too
      System.out.println("addHighScore(): Just put " + score + " at position 0.");
    } else {
      System.out.println("addHighScore(): gamesWon > NUM_HIGHSCORES");
      //helper method returns where "score" should go if placed into the bestScores array
      int scorePosition = scoreLocation(score); 
      System.out.println("Final whereToPlace for " + score + " is " + scorePosition);
      //decides whether score should be inserted into array or not
      if (scorePosition < NUM_HIGHSCORES) { 
        //moves numbers down so bestScores remains accurate
        adjustScores(scorePosition); 
        bestScores[scorePosition] = score;
      }
    }
  }
  
  /* scoreLocation(): Calculates the input score's location in the bestScores
   * array. Assumes that the score is the user's best (i.e. least number of 
   * shots to sink all the opponents boat). Then goes through bestScores array
   * from the beginning and compares "score" to the values in the array.
   * While "score" is larger (i.e. worse) than the scores in the array, 
   * "whereToPlace" increases. 
   * If the score larger than all the scores in the 
   * array, one of two things happens:
   * (a) if gamesWon <= NUM_HIGHSCORES, then whereToPlace becomes the index of 
   * the first empty slot in bestScores (which currently stores the default of 0).
   * (b) if gamesWon > NUM_HIGHSCORES, then whereToPlace beomes NUM_HIGHSCORES,
   * which means that it will not be added into the array later on.
   */
  private int scoreLocation(int score) {
    int whereToPlace = 0; //if not to be added, this will work.
    for (int i = 0; i < gamesWon-1; i++) {
      if (score > bestScores[i]) {
        whereToPlace++;
        System.out.println("addHighScore(): " + score+ "'s whereToPlace is " + whereToPlace);
      } else {
        break;
      }
    }
    return whereToPlace;
  }
  
  /* adjustScores(): shifts all the values past "moveFrom" in bestScores array 
   * to the right by one, so that the new best score can be added in the 
   * "moveFrom" slot.
   */
  private void adjustScores(int moveFrom) {
    for (int i = NUM_HIGHSCORES - 1; i >= moveFrom; i--) {
      if (i != 0) { //if i == 0, then (i-1) will throw an error
        //if true, then 0th slot is to overwritten in any case.
        bestScores[i] = bestScores[i-1];
      }
    }
  }
  
  /* calculateAverage(): takes in the user's score from the newly completed game
   * and calculates the new average; resets averageScore appropriately.
   * Not a public method  because this should be called only when a game is completed,
   * from within the completedGame method.
   * While calculating, assumes that gamesWon is up-to-date (i.e. gamesWon is updated
   * before this method is called, and hence uses (gamesWon-1) in the first part.
   */
  private void calculateAverage(int newScore) {
    int sum = averageScore * (gamesWon-1); 
    averageScore = (sum + newScore)/gamesWon;
  }
  
  /* printBestScores(): used for testing purposes. Prints bestScores array. */
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