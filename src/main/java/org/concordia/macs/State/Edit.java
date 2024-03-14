package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;

/**
 *
 * @author Mahfuzzur Rahman
 * This abstract sub class Edit contains states for Preload and Postload states.
 */

public abstract class Edit extends Phase 
{

    /**
     * Constructor for the Edit class.
     *
     * @param p_ge The associated GameEngine object.
     */

    Edit(GameEngine p_ge) 
    {
        super(p_ge);
    }

    /**
     * Loads a map. This method is expected to be overridden by subclasses.
     */

    public void loadMap() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Displays the map.
     */

    public void showMap() 
    {
        System.out.println("Map is being displayed");
    }

    /**
     * Edits a country. This method is expected to be overridden by subclasses.
     */

    public void editCountry() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Edits a continent. This method is expected to be overridden by subclasses.
     */

    public void editContinent() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Edits a neighbor. This method is expected to be overridden by subclasses.
     */

    public void editNeighbor() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Validates the map. This method is expected to be overridden by subclasses.
     */
    public void validateMap() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Saves the map. This method is expected to be overridden by subclasses.
     */

    public void saveMap() 
    {
        printInvalidCommandMessage();
    }

    /**
     * Ends the game and sets the phase to End.
     */

    public void endGame() 
    {
        ge.setPhase(new End(ge));
    }
}