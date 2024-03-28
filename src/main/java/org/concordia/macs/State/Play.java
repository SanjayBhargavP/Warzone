package org.concordia.macs.State;
import java.util.ArrayList;
import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.*;

/**
 * This class represents a concrete state in the state pattern, specifically focusing on gameplay phases.
 * It defines the behavior for commands that are valid during gameplay phases and signifies invalid commands for other phases
 * Instances of this class represent a group of gameplay states and provide common behavior for all states within this group
 * All states within this group must extend this class
 */
public abstract class Play extends Phase {

    /**
     * The ArrayList containing Player objects.
     */

    public static ArrayList<Player> l_playersArray = new ArrayList<>();

    /**
     * Retrieves the ArrayList containing Player objects
     * @return an ArrayList containing Player objects.
     */

    public static ArrayList<Player> getL_playersArray()
    {
        return l_playersArray;
    }

    /**
     * Constructs a Play phase associated with the given GameEngine
     * @param p_g The GameEngine object associated with this phase
     */

    Play (GameEngine p_g)
    {
        super(p_g);

    }

    /**
     * Sets the players for the phase
     */

    public void setPlayers()
    {
        printInvalidCommandMessage();
    }

    /**
     * Assign countries to players in the phase
     */

    public void assignCountries()
    {
        printInvalidCommandMessage();
    }

    /**
     * Reinforces the players armies in the phase
     */

    public void reinforce()
    {
        printInvalidCommandMessage();
    }

    /**
     * Initiates an attack between players in the phase
     */

    public void attack()
    {
        printInvalidCommandMessage();
    }

    /**
     * Allows players to fortify their positions in the phase
     */

    public void fortify()
    {
        printInvalidCommandMessage();
    }

    public void loadgame() { printInvalidCommandMessage(); }

    /**
     * Ends the game phase.
     */
    public void endGame()
    {
        printInvalidCommandMessage();
    }

    /**
     * Shows the map for the current phase
     */

    public void showMap()
    {
        printInvalidCommandMessage();
    }


}
