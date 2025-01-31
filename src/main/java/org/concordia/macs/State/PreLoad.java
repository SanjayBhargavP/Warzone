package org.concordia.macs.State;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Map;
import org.concordia.macs.Utilities.*;

/**
 * Concrete state representing the phase before loading a map.
 * Allows the user to load, edit, and validate the map before entering PostLoad phase.
 */
public class PreLoad extends Edit {
    private LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

    /**
     * Constructor for Preload phase.
     *
     * @param p_ge The GameEngine object associated with this phase.
     */
    public PreLoad(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Loads a map using the provided commands.
     *
     * @param p_connectivity Represents the map content.
     * @param p_commands     Array of user commands for loading a map.
     */
    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        int newMapCreated = 0;
        if (p_commands.length == 2) {
            newMapCreated = LoadMap.loadMap(p_connectivity, p_commands[1]);
        } else {
            System.out.println(ColorCoding.getRed() + "No map entered. Please enter the name of the map to be loaded" + ColorCoding.getReset());
        }
        if (newMapCreated == 0) {
            System.out.println(ColorCoding.getCyan() + "\n-------- Validating the loaded map --------\n" + ColorCoding.getReset());
            Graph graph = new Graph(p_connectivity.getD_countriesList().size(), p_connectivity);
            if (graph.continentConnection(p_connectivity, graph))
                graph.isConnected(graph);
        } else {
            System.out.println(ColorCoding.getGreen() + "Skipping Map Validation as it is a newly created map" + ColorCoding.getReset());
        }
        next(p_connectivity);
    }

    /**
     * Validates the loaded map.
     *
     * @param p_connectivity Represents the map content.
     */
    private void validateLoadedMap(Connectivity p_connectivity) {
        System.out.println(ColorCoding.ANSI_CYAN + "\n--------Validating the loaded map--------\n" + ColorCoding.ANSI_RESET);
        Graph graph = new Graph(p_connectivity.getD_countriesList().size(), p_connectivity);
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
                System.out.println(ColorCoding.ANSI_RED + "ERROR: Invalid Command" + ColorCoding.ANSI_RESET);
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
        try {
            for (int i = 1; i < p_commands.length; ) {
                if (p_commands[i].equals("-add")) {
                    MapEditor.addNeighbour(Integer.parseInt(p_commands[i + 1]), Integer.parseInt(p_commands[i + 2]), p_connectivity);
                    i = i + 3;
                } else if (p_commands[i].equals("-remove")) {
                    MapEditor.removeNeighbour(Integer.parseInt(p_commands[i + 1]), Integer.parseInt(p_commands[i + 2]), p_connectivity, 1);
                    i = i + 3;
                } else {
                    System.out.println(ColorCoding.getRed() + "ERROR: Invalid Command" + ColorCoding.getReset());
                }
            }
            next(p_connectivity);
        }
        catch (Exception e){
            //e.printStackTrace();
        }
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


    /**
     * Validates the loaded map.
     *
     * @param p_connectivity  represents the map content
     */
    @Override
    public void validateMap(Connectivity p_connectivity)
    {
        System.out.println(ColorCoding.ANSI_CYAN+"\n--------Validating the loaded map--------\n"+ColorCoding.ANSI_RESET);
        Graph l_graph=new Graph(p_connectivity.getD_countriesList().size(),p_connectivity);
        if(l_graph.continentConnection(p_connectivity, l_graph))
            l_graph.isConnected(l_graph);
        next(p_connectivity);
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
