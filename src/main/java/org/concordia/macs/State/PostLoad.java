package org.concordia.macs.State;

import java.util.Scanner;
import java.io.FileNotFoundException;

import Controllers.GameEngine;
import Tools.ColorCoding;
import Tools.Connectivity;
import Tools.ConquestSaveMap;
import Tools.SaveMap;

/**
 * Concrete state representing the phase after loading a map.
 * Allows the user to save the map and proceed to game play phases.
 */
public class PostLoad extends Edit {

    /**
     * Constructor for PostLoad phase.
     *
     * @param p_ge The GameEngine object associated with this phase.
     */
    public PostLoad(GameEngine p_ge) {
        super(p_ge);
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
     * @param p_connectivity Represents the map content.
     * @param p_mapName     The name of the map to save.
     */
    public void saveMap(Connectivity p_connectivity, String p_mapName) {
        System.out.println("Enter the format for savemap (conquest/domination)");
        Scanner scanner = new Scanner(System.in);
        String mapType = "";
        if (ge.getCheckIfTournament() || ge.getCheckIfTest() || ge.getCheckIfSave())
            mapType = "domination";
        else
            mapType = scanner.nextLine();

        while (!isValidMapType(mapType)) {
            System.out.println(ColorCoding.red + "Please enter a valid map type (conquest/domination):" + ColorCoding.blank);
            mapType = scanner.nextLine();
        }

        int saveMapResult = saveMapBasedOnType(p_connectivity, p_mapName, mapType);
        if (saveMapResult == 0)
            ge.setPhase(new PlaySetup(ge));
    }

    /**
     * Validates if the provided map type is valid.
     *
     * @param mapType The map type to validate.
     * @return True if the map type is valid, false otherwise.
     */
    private boolean isValidMapType(String mapType) {
        return mapType.equals("conquest") || mapType.equals("domination");
    }

    /**
     * Saves the map based on the provided map type.
     *
     * @param p_connectivity Represents the map content.
     * @param p_mapName     The name of the map to save.
     * @param mapType       The type of the map (conquest/domination).
     * @return The result of saving the map.
     */
    private int saveMapBasedOnType(Connectivity p_connectivity, String p_mapName, String mapType) {
        int saveMapResult = 0;
        switch (mapType) {
            case "conquest":
                saveMapResult = ConquestSaveMap.conquestMapSaver(p_connectivity, p_mapName);
                break;
            case "domination":
                saveMapResult = SaveMap.saveMap(p_connectivity, p_mapName);
                break;
        }
        return saveMapResult;
    }

    /**
     * Moves to the next phase.
     */
    public void next(Connectivity p_connectivity) {
        System.out.println("You must save the map first.");
    }

    // Override other methods to print invalid command message
    @Override
    public void editCountry(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        printInvalidCommandMessage();
    }

    @Override
    public void setPlayers(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public boolean assignCountries(Connectivity p_connectivity) {
        printInvalidCommandMessage();
        return false;
    }

    @Override
    public void reinforce(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void attack(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void fortify(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void enableTournament(String mycommand) {
        // Not implemented in this phase
    }

    @Override
    public void loadgame(String[] p_commands, Connectivity p_connectivity, GameEngine ge) throws FileNotFoundException {
        // Not implemented in this phase
    }
}
