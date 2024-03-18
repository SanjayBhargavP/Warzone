package org.concordia.macs.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.LoadMap;


public class CountryTest
{
    private Connectivity d_connectivity=new Connectivity();

    /**
     *
     * This method is used to loadmap.
     *
     */

    @BeforeEach
    public void addPlayersIfNotThereTest()
    {
        LoadMap.loadMap(d_connectivity, "VeryBasic");
    }

    /**
     *
     * Test if getCountryFromID returns correct country for a valid ID
     *
     */

    @Test
    void getCountryFromID_ValidID_ReturnsCorrectCountry()
    {
        Country country = Country.getCountryFromId(d_connectivity.getD_countriesList(), 5);
        assertEquals(country.getD_countryName(), "China");
    }

    /**
     *
     * Test if getCountryFromID returns null for an invalid ID
     *
     */


    @Test
    void getCountryFromID_InvalidID_ReturnsNull()
    {
        Country country = Country.getCountryFromId(d_connectivity.getD_countriesList(), 20);
        assertNull(country);
    }

    /**
     *
     * Test if getCountryFromName returns correct country for a valid name
     *
     */

    @Test
    void getCountryFromName_ValidName_ReturnsCorrectCountry()
    {
        Country country = new Country();
        Country result = country.getCountryFromName(d_connectivity.getD_countriesList(), "Germany");
        assertNotNull(result);
        assertEquals(result.getD_countryId(), 3);
    }

    /**
     *
     * Test if getCountryFromName returns null for an invalid name
     *
     */

    @Test
    void getCountryFromName_InvalidName_ReturnsNull()
    {
        Country country = new Country();
        Country result = country.getCountryFromName(d_connectivity.getD_countriesList(), "NonExistingCountry");
        assertNull(result);
    }
}
