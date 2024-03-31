package org.concordia.macs.State;

import java.io.FileNotFoundException;
import java.util.Scanner;

import Controllers.GameEngine;
import Models.LogEntryBuffer;
import Tools.ColorCoding;
import Tools.Connectivity;
import Tools.MapEditor;
import Tools.Graph;

/**
 * Concrete state representing the phase before loading a map.
 * Allows the user to load, edit, and validate the map before entering PostLoad phase.
 */
public class Preload extends Edit {
    private LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

    /**
     * Constructor for Preload phase.
     *
     * @param p_ge The GameEngine object associated with this phase.
     */
    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Loads a map using the provided commands.
     *
     * @param p_connectivity Represents the map content.
     * @param p_commands     Array of user commands for loading a map.
     */
    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        if (p_commands.length != 2) {
            logEntryBuffer.log("No map entered. Please enter the name of the map to be loaded");
            System.out.println(ColorCoding.red + "No map entered. Please enter the name of the map to be loaded" + ColorCoding.blank);
            return;
        }

        Map.checkMap(p_connectivity, p_commands[1]);

        validateLoadedMap(p_connectivity);
        next(p_connectivity);
    }

    /**
     * Validates the loaded map.
     *
     * @param p_connectivity Represents the map content.
     */
    private void validateLoadedMap(Connectivity p_connectivity) {
        System.out.println(ColorCoding.cyan + "\n--------Validating the loaded map--------\n" + ColorCoding.blank);
        Graph graph = new Graph(p_connectivity.getD_countryList().size(), p_connectivity);
        if (graph.continentConnection(p_connectivity, graph))
            graph.isConnected(graph);
    }

    /**
     * Edits (add/remove) country information based on the provided commands.
     *
     * @param p_commands     Array of user commands for editing countries.
     * @param p_connectivity Represents the map content.
     */
    public void editCountry(String[] p_commands, Connectivity p_connectivity) {
        for (int i = 1; i < p_commands.length;) {
            if (p_commands[i].equals("-add")) {
                MapEditor.addCountry(p_commands[i + 1], p_commands[i + 2], p_connectivity);
                i = i + 3;
            } else if (p_commands[i].equals("-remove")) {
                MapEditor.removeCountry(p_commands[i + 1], p_connectivity);
                i = i + 2;
            } else {
                logEntryBuffer.log("ERROR: Invalid Command");
                System.out.println(ColorCoding.red + "ERROR: Invalid Command" + ColorCoding.blank);
            }
        }
        next(p_connectivity);
    }

    /**
     * Edits (add/remove) continent information based on the provided commands.
     *
     * @param p_commands     Array of user commands for editing continents.
     * @param p_connectivity Represents the map content.
     */
    public void editContinent(String[] p_commands, Connectivity p_connectivity) {
        for (int i = 1; i < p_commands.length;) {
            if (p_commands[i].equals("-add")) {
                logEntryBuffer.log("Added Country");
                MapEditor.addContinent(p_commands[i + 1], Integer.parseInt(p_commands[i + 2]), p_connectivity);
                i = i + 3;
            } else if (p_commands[i].equals("-remove")) {
                MapEditor.removeContinent(p_commands[i + 1], p_connectivity);
                i = i + 2;
            } else {
                logEntryBuffer.log("Invalid Command");
                System.out.println("Invalid Command");
                break;
            }
        }
        next(p_connectivity);
    }

    /**
     * Edits (add/remove) neighbor information based on the provided commands.
     *
     * @param p_commands     Array of user commands for editing neighbors.
     * @param p_connectivity Represents the map content.
     */
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity) {
        for (int i = 1; i < p_commands.length;) {
            if (p_commands[i].equals("-add")) {
                MapEditor.addNeighbour(Integer.parseInt(p_commands[i + 1]), Integer.parseInt(p_commands[i + 2]), p_connectivity);
                i = i + 3;
            } else if (p_commands[i].equals("-remove")) {
                MapEditor.removeNeighbour(Integer.parseInt(p_commands[i + 1]), Integer.parseInt(p_commands[i + 2]), p_connectivity, 1);
                i = i + 3;
            } else {
                logEntryBuffer.log("ERROR: Invalid Command");
                System.out.println(ColorCoding.red + "ERROR: Invalid Command" + ColorCoding.blank);
            }
        }
        next(p_connectivity);
    }

    /**
     * Saves the map (invalid command in this state).
     *
     * @param p_connectivity Represents the map content.
     * @param p_mapName      The name of the map to save.
     */
    public void saveMap(Connectivity p_connectivity, String p_mapName) {
        printInvalidCommandMessage();
    }

    /**
     * Moves to the next phase or exits the program based on user input.
     */
    public void next(Connectivity p_connectivity) {
        Scanner sc = new Scanner(System.in);
        String user_output;
        if (ge.getCheckIfTest()) {
            user_output = "no";
        } else if (ge.getCheckIfTournament()) {
            user_output = "no";
        } else if (ge.getCheckIfLoad()) {
            user_output = "no";
        } else if (ge.getCheckIfSave()) {
            user_output = "no";
        } else {
            System.out.println("Do you want to make more edits on the map (yes/no)?");
            user_output = sc.nextLine();
        }

        if (user_output.equalsIgnoreCase("exit")) {
            ge.setPhase(new End(ge));
            ge.getPhase().endGame(p_connectivity);
        } else if (user_output.equalsIgnoreCase("no")) {
            ge.setPhase(new PostLoad(ge));
        } else {
            System.out.println("Continue editing map:");
        }
    }

    // Override other methods to print invalid command message
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
    public void enableTournament(String mycommand) {
        // Not implemented in this phase
    }

    @Override
    public void loadgame(String[] p_commands, Connectivity p_connectivity, GameEngine ge) throws FileNotFoundException {
        // Not implemented in this phase
    }
}
