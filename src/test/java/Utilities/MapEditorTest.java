package org.concordia.macs.Utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * The MapEditorTest class tests various functionalities of the MapEditor class
 */

public class MapEditorTest {

    Connectivity d_connectvty = new Connectivity();

    /**
     * Test adding a continent to the map
     */

    @Test
    void testAddContinent() {
        String continentName = "Asia";
        int continentSize = 8;

        int result = MapEditor.addContinent(continentName, continentSize, d_connectvty);

        assertEquals(0, result);
    }

    /**
     * Test adding a country with a continent
     */

    @Test
    void testAddCountryWithContinent() {
        MapEditor.addContinent("Europe", 5, d_connectvty);
        String countryName = "Germany";
        String continentId = "EU";

        int result = MapEditor.addCountry(countryName, continentId, d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test adding a country without a continent.
     */

    @Test
    void testAddCountryWithoutContinent() {
        String countryName = "USA";
        String continentId = "";

        int result = MapEditor.addCountry(countryName, continentId, d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test adding neighbouring countries without specifying country or continent
     */

    @Test
    void testAddNeighbourWithoutContinentAndCountry() {
        int result = MapEditor.addNeighbour(1, 2, d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test adding neighbouring countries with a continent but without specifying
     * country
     */

    @Test
    void testAddNeighbourWithContinentWithoutCountry() {
        MapEditor.addContinent("Asia", 10, d_connectvty);
        int result = MapEditor.addNeighbour(1, 2, d_connectvty);
        assertEquals(1, result);
    }

    /**
     * Test adding neighbouring countries with continent and country
     */

    @Test
    void testAddNeighbourWithContinentAndCountry() {
        MapEditor.addContinent("Asia", 10, d_connectvty);
        MapEditor.addCountry("1", "", d_connectvty);

        int result = MapEditor.addNeighbour(1, 1, d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test removing neighbouring countries without specifying country and
     * neighbours
     */

    @Test
    void testRemoveNeighbourWithoutCountryAndNeighbours() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeNeighbour(30, 31, d_connectvty, 1);

        assertEquals(1, result);
    }

    /**
     * Test removing a neighbour with a specified country but without neighbours
     */

    @Test
    void testRemoveNeighbourWithCountryWithoutNeighbours() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeNeighbour(1, 31, d_connectvty, 1);

        assertEquals(1, result);
    }

    /**
     * Test removing a neighbour with a wrong country neighbour
     */

    @Test
    void testRemoveNeighbourWrongCountryNeighbour() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeNeighbour(1, 6, d_connectvty, 1);

        assertEquals(1, result);
    }

    /**
     * Test removing a neighbor with the correct country and neighbor
     */

    @Test
    void testRemoveNeighbourWithCorrectCountryAndNeighbour() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeNeighbour(1, 2, d_connectvty, 1);

        assertEquals(1, result);
    }

    /**
     * Test removing a country with a wrong country name
     */

    @Test
    void testRemoveCountryWrongCountry() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeCountry("abc", d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test removing a country with the correct country name
     */

    @Test
    void testRemoveCountryCorrectCountry() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeCountry("Canada", d_connectvty);

        assertEquals(0, result);
    }

    /**
     * Test removing a continent with a wrong continent name
     */

    @Test
    void testRemoveContinentWrongContinent() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeContinent("India", d_connectvty);

        assertEquals(1, result);
    }

    /**
     * Test removing a continent with the correct continent name
     */

    @Test
    void testRemoveContinentCorrectContinent() {
        LoadMap.loadMap(d_connectvty, "testMap");

        int result = MapEditor.removeContinent("Africa", d_connectvty);

        assertEquals(0, result);
    }
}