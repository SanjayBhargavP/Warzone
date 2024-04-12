package org.concordia.macs.State;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Utilities.Connectivity;

/**
 * The SaveLoadGameTest class includes test cases for savegame and loadgame functionality.
 */
public class LoadSaveTest {

    GameEngine gameEngine = new GameEngine();
    Connectivity l_connectivity=new Connectivity();

    /**
     * Used to test loadgame
     */
    @Test
    public void testLoadGame() {
        gameEngine.setCheckIfTest(true);
        l_connectivity.setD_continentsList(new ArrayList<Continent>());
        l_connectivity.setD_countriesList(new ArrayList<Country>());
        String l_userCommand = "loadgame testLoadGame";
        String[] l_spiltCommand = l_userCommand.split(" ");
        gameEngine.setPhase(new PlayGame(gameEngine));
        try {
            gameEngine.getPhase().loadgame(l_spiltCommand, l_connectivity, gameEngine);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("Attack",gameEngine.getPhaseName());
        System.out.println("Game has been loaded to the previous saved state successfully");
        gameEngine.setPhase(new End(gameEngine));
        gameEngine.getPhase().endGame(l_connectivity);
    }
}

