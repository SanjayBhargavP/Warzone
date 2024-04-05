package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.SaveGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the end phase of the game.Handles all the action that needs to be performed in end state.
 * @author Blesslin Jeba Shiny
 */
public class End extends Phase {

    /**
     * Constructs a new end phase
     * @param p_ge - GameEngine object
     */
    End(GameEngine p_ge)
    {
        super(p_ge);
    }

    /**
     * Prints the invalid message while trying to load a map
     */
    public void loadMap()
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to show a map
     */
    public void showMap()
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to edit a country
     */
    public void editCountry()
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to save a map
     */
    public void saveMap()
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to set the players
     * @param p_commands - array of user commands
     * @param p_connectivity - connectivity object
     */
    public void setPlayers(String[] p_commands,Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying assign countries
     * @param p_connectivity - connectivity object
     * @return always returns false
     */
    public boolean assignCountries(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
        return false;
    }

    /**
     * Prints the invalid message while trying to reinforce
     * @param p_connectivity - connectivity object
     */
    public void reinforce(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to attack
     * @param p_connectivity - connectivity object
     */
    public void attack(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to attack.
     * @param p_connectivity - connectivity object
     */
    public void fortify(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * This method ends the game by thank you message.
     */
    public void endGame(Connectivity p_connectivity)
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Do you want to save the game?");
        String choice = "";
        if(ge.getCheckIfTournament() || ge.getCheckIfTest())
            choice = "no";
        else
            choice = sc.nextLine();
        if(choice.equalsIgnoreCase("yes"))
        {
            System.out.println("Enter the command: ");
            String[] l_command= sc.nextLine().split(" ");
            String l_filename=l_command[1];
            SaveGame savegame = new SaveGame();
            try
            {
                savegame.saveGame(this,p_connectivity,l_filename);
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Thank you for Playing the Game");
            System.exit(0);
        }
        else if (choice.equalsIgnoreCase("no"))
        {
            System.out.println("Thank you for Playing the Game");
        }
        return;
    }

    /**
     * Prints the invalid message while trying to issue next.
     */
    public void next(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_commands - array of user commands
     */
    @Override
    public void loadMap(Connectivity p_connectivity,String[] p_commands)
    {
        // TODO Auto-generated method stub
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_mapName - map name
     */
    @Override
    public void saveMap(Connectivity p_connectivity, String p_mapName) {
        // TODO Auto-generated method stub
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     */
    @Override
    public void validateMap(Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_commands - array of user commands
     */
    @Override
    public void editCountry(String[] p_commands,Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_commands - array of user commands
     */
    @Override
    public void editContinent(String[] p_commands,Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub
    }


    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_commands - array of user commands
     */
    @Override
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity) {

    }

    /**
     * This method is not implemented in end phase.
     * @param p_connectivity - connectivity object
     * @param p_commands - array of user commands
     * @param ge - game engine object
     */
    @Override
    public void loadgame(String[] p_commands, Connectivity p_connectivity, GameEngine ge) throws FileNotFoundException {
        // TODO Auto-generated method stub

    }

    /**
     * This method is not implemented in end phase.
     * @param mycommand - command provided in tournament
     */
    @Override
    public
    void enableTournament(String mycommand) {
        // TODO Auto-generated method stub

    }

}
