package org.concordia.macs.State;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameEngine;
import Tools.ColorCoding;
import Tools.Connectivity;
import Tools.MapLoader;
import Tools.SaveMap;
import Views.ViewMap;

public class saveGame {
    /**
     * GameEngine refers to static object GameEngine
     * d_connectibity refers to static Connectivity Object
     */

    static GameEngine gameEngine = new GameEngine();
    static Connectivity d_connectivity=new Connectivity();
    String[] d_commands= {"1","loadmap VeryBasic","no","savemap VeryBasic","gameplayer -add p1 -add p2","assigncountries","deploy"};

    @BeforeAll
    static void startGame()
    {
        gameEngine.setConnectivity(d_connectivity);
        gameEngine.setCheckIfTest(true);
        gameEngine.setCheckIfSave(true);
    }

    @Test
    public void testSaveGame() {
        gameEngine.setPhase(new Preload(gameEngine));
        System.out.println(gameEngine.getPhase());
        assertEquals("Preload",gameEngine.getPhaseName());
        System.out.println(ColorCoding.green+"Phase has been successfully changed to "+gameEngine.getPhaseName()+ColorCoding.blank);
        String[] mapCommand = d_commands[1].split(" ") ;

        if(d_commands[1].equals("loadmap VeryBasic"))
        {
            gameEngine.getPhase().loadMap(gameEngine.getConnectivity(), mapCommand);

        }
        assertEquals("PostLoad",gameEngine.getPhaseName());
        System.out.println(ColorCoding.green+"Phase has been successfully changed to "+gameEngine.getPhaseName()+ColorCoding.blank);
        if(d_commands[3].equals("savemap VeryBasic"))
        {
            gameEngine.getPhase().saveMap(d_connectivity, "VeryBasic");
        }
        assertEquals("PlaySetup",gameEngine.getPhaseName());
        System.out.println(ColorCoding.green+"Phase has been successfully changed to "+gameEngine.getPhaseName()+ColorCoding.blank);
        mapCommand = d_commands[4].split(" ");
        gameEngine.getPhase().setPlayers(mapCommand,gameEngine.getConnectivity());
        gameEngine.getPhase().assignCountries(gameEngine.getConnectivity());
        gameEngine.getPhase().next(d_connectivity);
        assertEquals("Reinforcement",gameEngine.getPhaseName());
        System.out.println(ColorCoding.green+"Phase has been successfully changed to "+gameEngine.getPhaseName()+ColorCoding.blank);
        gameEngine.getPhase().reinforce(d_connectivity);
        assertEquals("Attack",gameEngine.getPhaseName());
        gameEngine.getPhase().attack(d_connectivity);

    }
}
