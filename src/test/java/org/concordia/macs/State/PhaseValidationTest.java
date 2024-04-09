package org.concordia.macs.State;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.LoadMap;
import org.concordia.macs.Utilities.SaveMap;
import org.concordia.macs.View.ShowMap;

/**
 *
 * The class PhaseValidationTest class tests if the correct phase is being executed at right time.
 *
 */
public class PhaseValidationTest {
    static GameEngine d_gameEngine = new GameEngine();
    static Connectivity d_connectvty = new Connectivity();
    String[] d_commands = {"1", "loadmap VeryBasic", "no", "savemap VeryBasic", "gameplayer -add p1 -add p2", "assigncountries", "deploy"};

    @BeforeAll
    static void startGame() {
        d_gameEngine.setConnectivity(d_connectvty);
        d_gameEngine.setCheckIfTest(true);
    }


    /**
     * This test checks if the PreLoad phase executes all methods at the right time.
     */
    @Test
    public void testPreLoadPhase() {
        // Checking if the first command is "1"
        if (d_commands[0].equals("1"))
        {
            // Setting the phase to Preload
            d_gameEngine.setPhase(new PreLoad(d_gameEngine));
            System.out.println(d_gameEngine.getPhase());

            // Checking if the phase is set to Preload
            assertEquals("PreLoad", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Splitting the second command to get map details
            String[] mapCommand = d_commands[1].split(" ");

            if (d_commands[1].equals("loadmap VeryBasic")) {
                d_gameEngine.getPhase().loadMap(d_gameEngine.getConnectivity(), mapCommand);
            }

            // Checking if the phase is set to PostLoad
            assertEquals("PostLoad", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            if (d_commands[3].equals("savemap VeryBasic")) {
                d_gameEngine.getPhase().saveMap(d_connectvty, "VeryBasic");
            }

            // Checking if the phase is set to PlaySetup
            assertEquals("PlaySetup", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            String[] playerCommands = d_commands[4].split(" ");

            // Setting up players and assigning countries
            d_gameEngine.getPhase().setPlayers(playerCommands, d_gameEngine.getConnectivity());
            d_gameEngine.getPhase().assignCountries(d_gameEngine.getConnectivity());

            // Going to the next phase
            d_gameEngine.getPhase().next(d_gameEngine.getConnectivity());

            // Checking if the phase is set to Reinforcement
            assertEquals("Reinforcement", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Performing reinforcement
            d_gameEngine.getPhase().reinforce(d_connectvty);

            // Checking if the phase is set to Attack
            assertEquals("Attack", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Performing attack
            d_gameEngine.getPhase().attack(d_connectvty);

            // Checking if the phase is set to Fortify
            assertEquals("Fortify", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Performing Fortification
            d_gameEngine.getPhase().fortify(d_connectvty);

            // Checking if the phase is set to Reinforcement again
            assertEquals("Reinforcement", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Performing Reinforcement again
            d_gameEngine.getPhase().reinforce(d_connectvty);

            // Checking if the phase is set to Attack
            assertEquals("Attack", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);

            // Performing Attack again
            d_gameEngine.getPhase().attack(d_connectvty);

            // Checking if the phase is set to End
            assertEquals("End", d_gameEngine.getPhaseName());
            System.out.println(ColorCoding.ANSI_GREEN + "Phase has been successfully changed to " + d_gameEngine.getPhaseName() + ColorCoding.ANSI_RESET);
        }
    }
}


