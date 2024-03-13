package state;

import Controllers.GameEngine;
import Tools.Connectivity;
import Tools.SaveMap;

/**
 * Concrete state representing the phase after loading a map.
 * Allows the user to save the map and proceed to game play phases.
 */
public class PostLoad extends Edit {

    /**
     * Constructor for PostLoad phase.
     *
     * @param gameEngine The GameEngine object associated with this phase.
     */
    public PostLoad(GameEngine gameEngine) {
        super(gameEngine);
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
     * @param connectivity The connectivity object containing map data.
     * @param mapName      The name of the map to save.
     */
    public void saveMap(Connectivity connectivity, String mapName) {
        int saveMapResult = SaveMap.saveMap(connectivity, mapName);
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

