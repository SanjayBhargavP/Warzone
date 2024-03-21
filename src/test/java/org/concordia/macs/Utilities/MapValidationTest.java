package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/**
 * The MapValidationTest class tests various functionalities of the MapValidation class
 */
public class MapValidationTest {

    private Connectivity d_mockConnectivity;
    private Continent d_continent1;
    private Country d_country1, d_country2, d_country3;

    @Before
    public void setUp() {
        d_mockConnectivity = new Connectivity();

        d_continent1 = new Continent(1, "Continent1");
        d_country1 = new Country( 1, "Country1", d_continent1.getD_continentId(), new ArrayList<>(Arrays.asList(2)));
        d_country2 = new Country( 2, "Country2", d_continent1.getD_continentId(), new ArrayList<>(Arrays.asList(1, 3)));
        d_country3 = new Country( 3, "Country3", d_continent1.getD_continentId(), new ArrayList<>(Arrays.asList(3)));
        d_continent1.setD_countries(new ArrayList<Country> (Arrays.asList(d_country1, d_country2, d_country3)));
        d_continent1 = new Continent(1, "Continent1",8,new ArrayList<Country> (Arrays.asList(d_country1, d_country2, d_country3)));
        d_mockConnectivity.setD_continentsList(new ArrayList<Continent> (Arrays.asList(d_continent1)));
        d_mockConnectivity.setD_countriesList(new ArrayList<Country> (Arrays.asList(d_country1, d_country2, d_country3)));
    }

    /**
     * Test invalid map
     */
    @Test
    public void testValidMap() {
        assertTrue( MapValidation.validateMap(d_mockConnectivity));
    }

    /**
     * Test map without continents
     */
    @Test
    public void testMapWithoutContinents() {
        d_mockConnectivity.setD_continentsList(new ArrayList<Continent>());
        assertFalse(MapValidation.validateMap(d_mockConnectivity));
    }

    /**
     * Test map without countires and with continents
     */
    @Test
    public void testMapWithoutCountries() {
        d_mockConnectivity.setD_countriesList(new ArrayList<Country>());
        assertFalse(MapValidation.validateMap(d_mockConnectivity));
    }

    /**
     * Test map without neighbours
     */
    @Test
    public void testCountryWithoutNeighbours() {
        d_country1.setD_neighbours(1, new HashMap<Integer, ArrayList<Integer>>());
        assertFalse(MapValidation.validateMap(d_mockConnectivity));
    }
}
