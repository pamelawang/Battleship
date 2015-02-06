# Battleship
CS 230 Data Structures Final Project

By Meera Hejmadi and Pamela Wang

17 December 2014
***************************************


VERSION 1.0: Instructions and basic gameplay (when printed out in console) works properly. Version submitted as a final.

Bugs:

-shoot(p, x, y) in ComputerPlayer.java isn't fully functional: gets stuck on the case where Computer 'aims' at a wall and still trying to use the try-catch statement to loop back/get new coordinates again.

-turn() isn't used in Game.java - was used in our Stage 1, when boats were length of 1 Cell.

-Buttons in GamePanel don't change colour when pressed (miss, hit). Can still play but lacking visual indicator.

-ComputerPlayer.java doesn't use/overwrites any shooting-related methods from Player because it handles it differently.

-StatsPanel.java doesn't work; idea was that by entering in a username and pressing the button the programme would open the .txt file with all the stats information and display that specific user.

********************

Future implementations:

-2 human users playing the game

-have difficulty levels (different sized grids, boats)

-have a special shoot (bomb) - we have written the code for this in Player.java through gotBombed(), but haven't implemented it into gameplay

-have a more intelligient shooting algorithm
  
  e.g. of what it currently does
       
        - - H H B B - - (SEA SEA hit hit boat boat SEA SEA)
   
   next turn:
        
        - M H H B B - - (SEA miss hit hit boat boat SEA SEA)
   
   next turn (doesn't follow the boat anymore, does randomiser))

-exportFile() in AllStats - be able to export list of users' scores in a .txt file

-StatsPanel being able to use a .txt document to display searchable user information

-fixing buttons in GamePanel to change colour
