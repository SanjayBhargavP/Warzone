package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.SaveMap;

/**
 * Concrete state representing the phase after loading a map.
 * Allows the user to save the map and proceed to game play phases.
 */
public class PostLoad extends Edit {

    /**
     * Constructor for PostLoad phase.
     *
     * @param p_gameEngine The GameEngine object associated with this phase.
     */
    public PostLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Shows the map (invalid command in this state).
     */
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * Loads a map (invalid command in this state).
     */
    public void loadMap() {
        printInvalidCommandMessage();
    }

    /**
     * Edits a country (invalid command in this state).
     */
    public void editCountry() {
        printInvalidCommandMessage();
    }

    /**
     * Saves the map.
     *
     * @param p_connectivity The connectivity object containing map data.
     * @param p_mapName      The name of the map to save.
     */
    public void saveMap(Connectivity p_connectivity, String p_mapName) {
        int saveMapResult = SaveMap.saveMap(p_connectivity, p_mapName);
        if (saveMapResult == 0) {
            ge.setPhase(new PlaySetup(ge));
        }
    }

    /**
     * To move to the next phase.
     */
    public void next() {
        System.out.println("Must save the map");
    }

    // Override methods from superclass

    @Override
    public void editCountry(String[] commands, Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent(String[] commands, Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbor(String[] commands, Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap(Connectivity connectivity, String[] commands) {
        printInvalidCommandMessage();
    }

    @Override
    public void setPlayers(String[] commands) {
        printInvalidCommandMessage();
    }

    @Override
    public boolean assignCountries(Connectivity connectivity) {
        printInvalidCommandMessage();
        return false;
    }

    @Override
    public void reinforce(Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void attack(Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void fortify(Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap(Connectivity connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        System.out.println("Thank you for playing the game");
        System.exit(0);
    }
}
