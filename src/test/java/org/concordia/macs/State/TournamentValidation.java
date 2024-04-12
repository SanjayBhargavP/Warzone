package org.concordia.macs.State;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Utilities.Connectivity;

/**
 * TournamentValidation is used for successfully validating the given tournament scenario
 */
public class TournamentValidation {

    /**
     * l_connectivity refers to connectivity object
     * l_mapList refers to list of maps specified in the userCommand
     * l_behaviourList refers to list of behaviors specified in the userCommand
     * l_gameCount refers to number of games specified in the userCommand
     * l_roundCount refers to the number of rounds specified in the userCommand
     * gameEngine is an instance of GameEngine class
     * d_gameResult stores the results of all the games in the tournament
     * d_gameplayerCommand stores the setPlayer/gameplayer command
     */
    Connectivity l_connectivity=new Connectivity();
    ArrayList<String> l_mapList = new ArrayList<>();
    HashSet<String> l_behaviourList = new HashSet<String>();
    int l_gameCount = 0;
    int l_roundCount = 0;
    GameEngine gameEngine = new GameEngine();
    ArrayList<String> d_gameResult = new ArrayList<>();
    String d_gameplayerCommand = "";

    /**
     * testTournament is used to test the tournament scenario for the defined userCommand
     */
    @Test
    public void testTournament() {
        l_connectivity.setD_continentsList(new ArrayList<Continent>());
        l_connectivity.setD_countriesList(new ArrayList<Country>());
        String l_userCommand = "tournament -M VeryBasic canada -P Random Aggressive Cheater Benevolent -G 3 -D 10";
        gameEngine.setPhase(new PlayGame(gameEngine));
        gameEngine.getPhase().enableTournament(l_userCommand);
    }
}

