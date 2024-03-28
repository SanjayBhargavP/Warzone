package org.concordia.macs.State;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.View.ShowMap;
/**
 * @author Susmitha Mamula
 * This class manages the different states within the State Pattern in the game.
 */

public abstract class Phase {



    GameEngine ge;

    Phase(GameEngine p_g)
    {
        ge = p_g;
    }

    /**
     * This method is responsible for loading the map during the Preload state.
     * @param p_connectivity the connectivity of the map
     * @param p_command the commands passed by the user
     */
    public void loadMap(Connectivity p_connectivity, String[] p_command)
    {
        // TODO Auto-generated method stub
    }

    /**
     * This method displays the map in a tabular format.
     * @param p_continentList the list of continents
     * @param p_countryList the list of countries
     * @param p_playerList the list of players in the game
     */
    public void showMap(ArrayList<Continent> p_continentList, ArrayList<Country> p_countryList, ArrayList<Player> p_playerList)
    {
        ShowMap.showMap(p_continentList, p_countryList, p_playerList);
    }

    /**
     * This abstract method is used to add or remove countries during the preload state
     * @param p_commands the commands passed by the user
     * @param p_connectivity the connectivity of the map
     */
    abstract public void editCountry(String[] p_commands, Connectivity p_connectivity);

    /**
     * This abstract method is used to add or remove continents
     * @param p_commands the commands passed by the user
     * @param p_connectivity the connectivity of the map
     */
    abstract public void editContinent(String[] p_commands, Connectivity p_connectivity);

    /**
     * This abstract method is used to add or remove neighbors
     * @param p_commands the commands passed by the user
     * @param p_connectivity the connectivity of the map
     */
    abstract public void editNeighbor(String[] p_commands, Connectivity p_connectivity);

    /**
     * This abstract method is used to save the map during the preload state
     * @param p_connectivity the connectivity of the map
     * @param p_mapName the name of the map to be saved
     */
    abstract public void saveMap(Connectivity p_connectivity, String p_mapName);

    /**
     * This abstract method is used to add or remove players during the PlaySetup state
     * @param p_commands the commands passed by the user
     */
    abstract public void setPlayers(String[] p_commands, Connectivity p_connectivity);

    /**
     * This abstract method is used to assign countries to the players during the PlaySetup state
     * @param p_connectivity the connectivity of the map
     */
    abstract public boolean assignCountries(Connectivity p_connectivity);

    /**
     * This abstract method is used to deploy the troops during Reinforcement state.
     * @param p_connectivity connectivity of the map
     */
    abstract public void reinforce(Connectivity p_connectivity);

    /**
     * This method is used for attacking during attack state
     * @param p_connectivity the connectivity of the map
     */
    abstract public void attack(Connectivity p_connectivity);

    /**
     * This method is used to fortify positions during Fortification State
     * @param p_connectivity the connectivity of the map
     */
    abstract public void fortify(Connectivity p_connectivity);

    /**
     * This abstract method is used to end the game.
     * @throws FileNotFoundException
     */
    public void endGame(Connectivity p_connectivity)
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Do you want to save the game?");
        String choice = sc.nextLine();
        if(choice.equalsIgnoreCase("yes"))
        {
            System.out.println("Enter the command: ");
            String[] l_command= sc.nextLine().split(" ");
            String l_filename=l_command[1];
            SaveGame sg = new SaveGame();
            try
            {
                sg.saveGame(this,p_connectivity,l_filename);
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

    abstract public void loadgame(String[] p_commands,Connectivity p_connectivity,GameEngine ge) throws FileNotFoundException;

    /**
     * This method displays the help instructions.
     */

    public void help()
    {
        System.out.println("\nloadmap filename: \n Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph\n_____________________________________________________________________\n" +
                "validatemap: \n Verification of map correctness\n___________________________________________________________\n" +
                "editmap filename: \n User-driven creation/deletion of map elements: country, continent, and connectivity between countries\n________________________________________________________\n" +
                "editcontinent -add continentID continentvalue -remove continentID\n" +
                "editcountry -add countryID continentID continentID -remove countryID" +
                "editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID\n____________________________________________________________\n"+
                "showmap: \n show all country and continents, armies on each country, ownership, and connectivity\n_______________________________________________________\n"+
                "savemap: \n save a map\n_______________________________________________________________\n"+
                "assigncountries: \n countries get randomly assigned to players\n________________________________________\n"+
                "gameplayer -add playername -remove playername : \n Command to add or remove players from the game\n_____________________________________________________\n"
                );

    }

    /**
     * This method moves the game to next phase
     */
    abstract public void next(Connectivity p_connectivity);

    /**
     * This method is used to validate the loaded map
     * @param p_connectivity the connectivity of the map
     */

    abstract public void validateMap(Connectivity p_connectivity);

    /**
     * This method prints an invalid command message.
     */
    public void printInvalidCommandMessage(){
        System.out.println("Invalid command in state" + this.getClass().getSimpleName());
    }

    public abstract void enableTournament(String mycommand);

}
