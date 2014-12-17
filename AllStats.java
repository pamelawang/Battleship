/* Meera Hejmadi and Pamela Wang
 * Final Project - Battleship
 * AllStats.java
 * Dec 7th, 2014
 * 
 * Purpose: To create a LinkedList of all users' stats.
 * 
 * @author Meera Hejmadi
 * @author Pamela Wang
 */

import java.util.*;
import java.io.*;

public class AllStats {
 
  private LinkedList<Stats> gameStats;
  private int numUsers;
  private final int NOT_FOUND = -1;
  
  /******************************************************************
    * Constructor
    *****************************************************************/
  public AllStats() {
    gameStats = new LinkedList<Stats>();
    numUsers = 0;
  }
  
  /******************************************************************
    * Returns a specific user's index in the LinkedList.
    * 
    * @param  username     player's username
    * @return  int     user's index in the LinkedList storing all scores
    *****************************************************************/
  public int getPlayerIndex(String username) {
    LinkedList<Stats> temp = new LinkedList<Stats>();
    System.out.println("getPlayerIndex(): temp is: " + temp);
    int index = NOT_FOUND;
    boolean found = false;
    while (!gameStats.isEmpty()) {
      System.out.println("getPlayerIndex(): gameStats isn't empty.");
      temp.add(gameStats.remove());
      System.out.println("getPlayerIndex(): temp is: " + temp);
      if (temp.getFirst().getUserName().equals(username)) {
        System.out.println("getPlayerIndex(): It's a match!");
        index++; //because we start at -1
        found = true;
        break;
      } else {
        System.out.println("getPlayerIndex(): Still haven't found it.");
        index++;
      }
    }
    index = (found == true) ? index : NOT_FOUND; 
    System.out.println("getPlayerIndex(): Index: " + index);
    gameStats = temp;
    return index;
  }
  
  /******************************************************************
    * Adds a player to the file with all users' scores
    * 
    * @param  username     player's username
    *****************************************************************/
  public void addPlayer(String username) {
    System.out.println("addPlayer(): trying to add " + username);
    if (getPlayerIndex(username) == NOT_FOUND) {
      System.out.println("addPlayer(): adding " + username + " now.");
      Stats current = new Stats(username);
      this.gameStats.add(current);
      numUsers++;
      System.out.println("addPlayer(): gameStats: " + gameStats);
      System.out.println("addPlayer(): Should be " + numUsers + " entries.");
    }
  }
  
  /******************************************************************
    * Returns a specific user's stats.
    * 
    * @param  username     player's username
    * @return  Stats     one user's stats
    *****************************************************************/
  public Stats getPlayer(String username) {
    Stats s = new Stats("invalid");
    Iterator<Stats> iter = gameStats.iterator();
    while (iter.hasNext()) {
      s = iter.next();
      if (s.getUserName().equals(username)) {
        break;
      }
    }
    return s;
  }
  
  /******************************************************************
    * Reads a .txt file and adds the data into the AllStats. LinkedList
    * 
    * @param  file     file name as a String
    * @return  int     user's index in the LinkedList storing all scores
    *****************************************************************/
  /*Assumed formatting of file:
   * -username
   * -gamesPlayed
   * -gamesWon
   * -averageScore
   * -bestScores[0]
   * -bestScores[1]
   * -bestScores[2]
   * -bestScores[3]
   * -bestScpres[4]
   */
  public void loadFile(String file) throws FileNotFoundException {
    Scanner fileReader = new Scanner(new File(file));
    
    while (fileReader.hasNext()) {
      String name = fileReader.next();
      int gamesPlayed = fileReader.nextInt();
      int gamesWon = fileReader.nextInt();
      int averageScore = fileReader.nextInt();
      int best1 = fileReader.nextInt();
      int best2 = fileReader.nextInt();
      int best3 = fileReader.nextInt();
      int best4 = fileReader.nextInt();
      int best5 = fileReader.nextInt();
      
      Stats current = new Stats(name);
      current.setGamesPlayed(gamesPlayed);
      current.setGamesWon(gamesWon);
      current.setAverage(averageScore);
      current.setBestScores(new int[] {best1, best2, best3, best4, best5});
      
      gameStats.add(current);
      numUsers++;
    }
    fileReader.close();
  }
  
  /******************************************************************
    * Exports LinkedList into a .txt file. Not functional.
    * 
    * @param  file     file name as a String
    *****************************************************************/
 /* public void exportFile(String file) { //not functional
    PrintWriter writer = new PrintWriter(new File(file)); //overwrite file if it already exists
    
    while (gameStats.hasNext()) { //while there are elements
      Stats current = gameStats.next();
      
      writer.println(current.getUserName());
      writer.println(current.getGamesPlayed());
      writer.println(current.getGamesWon());
      writer.println(current.getAverage());
      
      int[] best = current.getBestScores();
      for (int i = 0; i < 5; i++) {
        writer.println(best[i]);
      }
      writer.close();
    }
  }*/
  
  /******************************************************************
    * Returns a string representation of all users and their high scores
    * 
    * @return  int     last ('ending') y-coordinate of boat
    *****************************************************************/
  public String toString() {
    LinkedList<Stats> temp = new LinkedList<Stats>();
    String s = "Users who have played so far: ";
    int i = 0;
    while (!gameStats.isEmpty()) {
      temp.add(gameStats.remove());
      System.out.println("toString(): temp is: " + temp);
      System.out.println("toString(): gameStats is: " + gameStats);
      s += temp.get(i).getUserName() + " ";
      System.out.println("toString(): Got first username.");
      i++;
    }
    gameStats = temp;
    System.out.println("toString(): Re-assigned gameStats.\tgameStats: " + gameStats);
    return s;
  }
    
  public static void main (String[] args) {
    AllStats game1 = new AllStats();
    System.out.println(game1);
    game1.addPlayer("mhejmadi");
    System.out.println(game1);
    game1.addPlayer("pwang2");
    System.out.println(game1);
    
    try {
      game1.loadFile("allstats.txt");
    } catch (FileNotFoundException e) {
    }
    //game1.exportFile(fileName);
  }
    
  
}