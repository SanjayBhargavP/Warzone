package org.concordia.macs.Utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.State.Preload;
import org.concordia.macs.Utilities.Graph;

import javax.swing.text.Utilities;

/**
 *
 * The class MapLoader tests if we can load the valid map or not.
 *
 */

public class MapLoaderTest
{
    private static Connectivity d_connectivity = new Connectivity();
    private static GameEngine gameEngine = new GameEngine();

    @BeforeAll
    static void startGame()
    {
        gameEngine.setConnectivity(d_connectivity);
        gameEngine.setCheckIfTest(true);
    }

    /**
     *
     * Test to check if a map without continents can be loaded.
     *
     */

    @Test
    void loadMap_NoContinents()
    {
        gameEngine.setPhase(new Preload(gameEngine));

        LoadMap.loadMap(gameEngine.getConnectivity(), "map_without_continents");

        Utilities.Graph l_graph = new Utilities.Graph(d_connectivity.getD_countryList().size(), d_connectivity);
        assertEquals(0, d_connectivity.getD_continents().size());
        assertFalse(l_graph.continentConnection(d_connectivity, l_graph));
    }

    /**
     *
     * Test to check if a map with multiple continents and countries can be loaded.
     *
     */

    @Test
    void loadMap_MultipleContinents()
    {
        gameEngine.setPhase(new Preload(gameEngine));

        LoadMap.loadMap(gameEngine.getConnectivity(), "map_with_multiple_continents");

        Utilities.Graph l_graph = new Utilities.Graph(d_connectivity.getD_countryList().size(), d_connectivity);
        assertTrue(d_connectivity.getD_continents().size() > 1);
        assertTrue(d_connectivity.getD_countryList().size() > 1);
        assertFalse(l_graph.continentConnection(d_connectivity, l_graph));
    }

    /**
     *
     * Test to check if a map with invalid connections between continents can be loaded.
     *
     */

    @Test
    void loadMap_InvalidContinentConnections()
    {
        gameEngine.setPhase(new Preload(gameEngine));

        LoadMap.loadMap(gameEngine.getConnectivity(), "map_with_invalid_continent_connections");

        Utilities.Graph l_graph = new Utilities.Graph(d_connectivity.getD_countryList().size(), d_connectivity);
        assertFalse(d_connectivity.isContinentConnected());
        assertFalse(l_graph.continentConnection(d_connectivity, l_graph));
    }
}
