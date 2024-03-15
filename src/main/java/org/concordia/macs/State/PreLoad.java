package org.concordia.macs.State;

import Controllers.GameEngine;
import Tools.ColorCoding;
import Tools.Connectivity;
import Tools.MapEditor;
import Tools.MapLoader;
import Tools.Graph;
import java.util.Scanner;

/**
 * Concrete state representing the phase before loading a map.
 * Allows the user to load, edit, and validate the map before entering PostLoad phase.
 */
public class PreLoad extends Edit {

    /**
     * Constructor for Preload phase.
     *
     * @param gameEngine The GameEngine object associated with this phase.
     */
    public Preload(GameEngine gameEngine) {
        super(gameEngine);
    }

    /**
     * Loads a map using the provided commands.
     *
     * @param connectivity The connectivity object containing map data.
     * @param commands     Array of user commands for loading a map.
     */
    public void loadMap(Connectivity connectivity, String[] commands) {
        int newMapCreated = 0;
        if (commands.length == 2) {
            newMapCreated = MapLoader.loadMap(connectivity, commands[1]);
        } else {
            System.out.println(ColorCoding.red + "No map entered. Please enter the name of the map to be loaded" + ColorCoding.blank);
        }
        if (newMapCreated == 0) {
            System.out.println(ColorCoding.cyan + "\n-------- Validating the loaded map --------\n" + ColorCoding.blank);
            Graph graph = new Graph(connectivity.getD_countryList().size(), connectivity);
            if (graph.continentConnection(connectivity, graph))
                graph.isConnected(graph);
        } else {
            System.out.println(ColorCoding.green + "Skipping Map Validation as it is a newly created map" + ColorCoding.blank);
        }
        next();
    }

    /**
     * Edits (add/remove) country information based on the provided commands.
     *
     * @param commands     Array of user commands for editing countries.
     * @param connectivity The connectivity object containing map data.
     */
    public void editCountry(String[] commands, Connectivity connectivity) {
        for (int i = 1; i < commands.length;) {
            if (commands[i].equals("-add")) {
                MapEditor.addCountry(commands[i + 1], commands[i + 2], connectivity);
                i = i + 3;
            } else if (commands[i].equals("-remove")) {
                MapEditor.removeCountry(commands[i + 1], connectivity);
                i = i + 2;
            } else {
                System.out.println(ColorCoding.red + "ERROR: Invalid Command" + ColorCoding.blank);
            }
        }
        next();
    }

    /**
     * Edits (add/remove) continent information based on the provided commands.
     *
     * @param commands     Array of user commands for editing continents.
     * @param connectivity The connectivity object containing map data.
     */
    public void editContinent(String[] commands, Connectivity connectivity) {
        for (int i = 1; i < commands.length;) {
            if (commands[i].equals("-add")) {
                MapEditor.addContinent(commands[i + 1], Integer.parseInt(commands[i + 2]), connectivity);
                i = i + 3;
            } else if (commands[i].equals("-remove")) {
                MapEditor.removeContinent(commands[i + 1], connectivity);
                i = i + 2;
            } else {
                System.out.println(ColorCoding.red + "Invalid Command" + ColorCoding.blank);
                break;
            }
        }
        next();
    }

    /**
     * Edits (add/remove) neighbor information based on the provided commands.
     *
     * @param commands     Array of user commands for editing neighbors.
     * @param connectivity The connectivity object containing map data.
     */
    public void editNeighbor(String[] commands, Connectivity connectivity) {
        for (int i = 1; i < commands.length;) {
            if (commands[i].equals("-add")) {
                MapEditor.addNeighbour(Integer.parseInt(commands[i + 1]), Integer.parseInt(commands[i + 2]), connectivity);
                i = i + 3;
            } else if (commands[i].equals("-remove")) {
                MapEditor.removeNeighbour(Integer.parseInt(commands[i + 1]), Integer.parseInt(commands[i + 2]), connectivity, 1);
                i = i + 3;
            } else {
                System.out.println(ColorCoding.red + "ERROR: Invalid Command" + ColorCoding.blank);
            }
        }
        next();
    }

    /**
     * Validates the loaded map.
     *
     * @param connectivity The connectivity object containing map data.
     */
    public void validateMap(Connectivity connectivity) {
        System.out.println(ColorCoding.cyan + "\n-------- Validating the loaded map --------\n" + ColorCoding.blank);
        Graph graph = new Graph(connectivity.getD_countryList().size(), connectivity);
        if (graph.continentConnection(connectivity, graph))
            graph.isConnected(graph);
        next();
    }

    /**
     * Saves the map (invalid command in this state).
     *
     * @param connectivity The connectivity object containing map data.
     * @param mapName      The name of the map to save.
     */
    public void saveMap(Connectivity connectivity, String mapName) {
        printInvalidCommandMessage();
    }

    /**
     * Moves to the next phase or exits the program based on user input.
     */
    public void next() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        if (ge.getCheckIfTest()) {
            userInput = "no";
        } else {
            System.out.println("Do you want to make more edits on the map (yes/no)?");
            userInput = scanner.nextLine();
        }

        if (userInput.equalsIgnoreCase("exit")) {
            System.out.println("Thank you for Playing the Game");
            System.exit(0);
        } else if (userInput.equalsIgnoreCase("no")) {
            ge.setPhase(new PostLoad(ge));
        } else {
            System.out.println("Continue editing map:");
        }
    }

    // Override methods from superclass

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry() {
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
    public void endGame() {
        System.out.println("Thank you for playing the game");
        System.exit(0);
    }
}

