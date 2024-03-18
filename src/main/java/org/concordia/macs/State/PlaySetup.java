package org.concordia.macs.State;
import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.PlayersGameplay;

/**
 * @author Susmitha Mamula
 * Concrete states represents the setup phase of the game.
 * Phase allows players to add, remove players, assign countries and begin the game
 */
public class PlaySetup extends Play {

    /**
     * Constructor for the PlaySetup phase
     * @param p_g The GameEngine object associated with this phase.
     */
    public PlaySetup(GameEngine p_g) {
        super(p_g);
    }

    /**
     * Sets the players on provided commands basis.
     * @param p_command Array of user commands for setting up the players.
     */

    public void setPlayers(String[] p_command) {
        int i = 1;
        while (i < p_command.length) {
            if (p_command[i].equals("-add")) {
                Player l_player = new Player();
                l_player.setD_playerName(p_command[i + 1]);
                l_playersArray.add(l_player);
                System.out.println(ColorCoding.ANSI_GREEN + l_player.getD_playerName() + " added successfully" + ColorCoding.ANSI_RESET);
                i = i + 2;

            } else if (p_command[i].equals(("-remove"))) {
                int j = 0;
                while (j < l_playersArray.size()) {
                    if (p_command[i + 1].equals(l_playersArray.get(j).getD_playerName())) {
                        System.out.println(ColorCoding.ANSI_GREEN + l_playersArray.get(j).getD_playerName() + " removed successfully" + ColorCoding.ANSI_RESET);
                        l_playersArray.remove(j);
                        i = i + 2;
                        break;
                    }
                    j++;
                }
            }
        }
        System.out.println("Players have been set");

    }

    /**
     *
     * Allocate countries to players
     * @param p_connectivity
     * @return True if the countries assigned are successful, false otherwise.
     */
    public boolean assignCountries(Connectivity p_connectivity) {
        if (l_playersArray.size() > 0) {

            if (PlayersGameplay.assignCountries(l_playersArray, p_connectivity.getD_countriesList(), p_connectivity.getD_continentsList()) == 0) {
                System.out.println(ColorCoding.ANSI_GREEN + "Countries assigned to players Successfully" + ColorCoding.ANSI_RESET + "\n");

            } else {
                return false;
            }
        } else {
            System.out.println(ColorCoding.ANSI_RED + "ERROR: No players to assign Countries" + ColorCoding.ANSI_RESET);
            return false;
        }
        System.out.println("countries have been assigned");
        for (Player p : l_playersArray) {
            PlayersGameplay.showPlayersCountry(p, 1);
        }
        PlayersGameplay.assignArmiesToPlayers(l_playersArray);
        System.out.println("Game Begins!!!!!!!!\n");
        int i = 0;
        while (i < l_playersArray.size()) {
            System.out.println("Player" + Integer.sum(i, 1) + "(" + l_playersArray.get(i).getD_playerName() + ") has Army count:"
                    + l_playersArray.get(i).getD_armyNumber());
            PlayersGameplay.showPlayersCountry(l_playersArray.get(i), 1);
            System.out.println();
            i++;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * @param p_connectivity
     */
    public void reinforce(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    /**
     * Attack phase is not applicable in PlaySetup and will outcome as an invalid command.
     * @param p_connectivity
     */
    public void attack(Connectivity p_connectivity){
        printInvalidCommandMessage();
    }

    /**
     *  Fortify phase is not applicable in this PlaySetup and result in an invalid command.
     * @param p_connectivity
     */
    public void fortify(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    /**
     * Game will end and program exits.
     */

    public void endGame() {
        System.out.println("Thank you for playing the Game");
        System.exit(0);
    }

    /**
     * Moves to Reinforcement phase
     */

    public void next() {
        ge.setPhase(new Reinforcement(ge));
    }

    /**
     *{@inheritDoc}
     */

    @Override
    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void validateMap(Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();

    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editCountry(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editContinent(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */
     @Override
    public void saveMap(Connectivity p_connectivity, String p_mapName){
         // TODO Auto-generated method stub
         printInvalidCommandMessage();
     }

}


