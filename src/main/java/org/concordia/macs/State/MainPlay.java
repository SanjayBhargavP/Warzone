package org.concordia.macs.State;
import org.concordia.macs.Controllers.GameEngine;

/**
 *
 * @author Susmitha Mamula
 * MainPlay is a ConcreteState in the StatePattern. It encapsulates the behavior
 * for commands applicable for states like Reinforcement, Attack, Fortify
 * while indicating invalidity for others
 *
 *  This state encompasses set of states, providing shared behavior
 *  among them. All states within this set must inherit from this class
 */
public abstract class MainPlay extends Play {

    /**
     * Constructor for MainPlay phase
     * @param p_g The associated GameEngine object
     */
    MainPlay(GameEngine p_g)
        {
            super(p_g);
        }

    /**
     * Loads the map for the current phase.
     */

    public void loadMap()
        {
            this.printInvalidCommandMessage();
        }

    /**
     *Sets the players for the phase
     */

    public void setPlayers()
        {
            this.printInvalidCommandMessage();
        }

    /**
     * Assign countries to players in the phase
     */

    public void assignCountries()
        {
            this.printInvalidCommandMessage();
        }

}
