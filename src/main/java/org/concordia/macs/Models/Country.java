
package org.concordia.macs.Models;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Country class defines Country Id, Country Name
 * Continent Id - to which the country belong to
 * Army count - no of armies deployed in the country
 * @author - Blesslin Jeba Shiny
 */

public class Country
{
    private int d_countryId;
    private String d_countryName;
    private int d_armyCount=0;
    private int d_continentId;
    private ArrayList<Integer> d_neighbours = new ArrayList<>();

    /**
     * Default constructor for Country
     */
    public Country()
    {

    }

    /**
     * Parametrized Constructor for Country
     * Initalises a new Country with
     * @param p_countryId - CountryId,
     * @param p_countryName - CountryName,
     * @param p_continentId - ContinentId and
     * @param p_neighbours - neighbours
     */
    public Country(int p_countryId,String p_countryName,int p_continentId,ArrayList<Integer> p_neighbours)
    {
        this.d_countryId=p_countryId;
        this.d_countryName=p_countryName;
        this.d_armyCount=0;
        this.d_continentId=p_continentId;
        this.d_neighbours=p_neighbours;
    }

    /**
     * Get CountryId
     * @return Country ID
     */
    public int getD_countryId()
    {
        return d_countryId;
    }

    /**
     * Set CountryId
     * @param p_countryId - new country Id
     */
    public void setD_countryId(int p_countryId)
    {
        this.d_countryId = p_countryId;
    }

    /**
     * Get CountryName
     * @return Country Name
     */
    public String getD_countryName()
    {
        return d_countryName;
    }

    /**
     * Set CountryName
     * @param d_countryName - new country Name
     */
     public void setD_countryName(String d_countryName)
     {
        this.d_countryName = d_countryName;
     }

    /**
     * Get Army Count
     * @return army count
     */
    public int getD_armyCount()
    {
        return d_armyCount;
    }

    /**
     * Set Army Count
     * @param p_armyCount - new Army Count
     */
    public void setD_armyCount(int p_armyCount)
    {
        this.d_armyCount = p_armyCount;
    }

    /**
     * Get ContinentId
     * @return ContinentId
     */
    public int getD_continentId() {
        return d_continentId;
    }

    /**
     * Set Continent Id
     * @param p_continentId - new Id
     */
    public void setD_continentId(int p_continentId)
    {
        this.d_continentId = p_continentId;
    }

    /**
     * Get Neighbours
     * @return neighbours
     */
    public ArrayList<Integer> getD_neighbours()
    {
        return d_neighbours;
    }

    /**
     * Set Neighbours
     * @param p_countryId refers to the country ID.
     * @param p_neighbouringCountries refers to the neighbouring countries.
     */

    public void setD_neighbours(int p_countryId,HashMap<Integer,ArrayList<Integer>> p_neighbouringCountries)
    {
        this.d_neighbours=p_neighbouringCountries.get(p_countryId);
    }

    /**
     * This method returns the country name
     * @param p_countryList - list of countries
     * @param p_countryId - country id
     * @return - Country Name
     */
    public static String get_nameFromId(ArrayList<Country> p_countryList,int p_countryId)
    {
        for(Country i:p_countryList)
        {
            if(i.getD_countryId()==p_countryId)
            {
                return i.getD_countryName();
            }

        }
        return " ";

    }

    /**
     * This method returns the country by its name
     * @param p_countryList - list of countries
     * @param p_countryName - country name
     * @return country name if found, null if country not found
     */
    public Country getCountryFromName(ArrayList<Country> p_countryList,String p_countryName)
    {
        for(Country c :p_countryList)
        {
            if(c.getD_countryName().equals(p_countryName))
                return c;
        }
        return null;
    }

    /**
     * This method returns the country by its id
     * @param p_countryList - list of countries
     * @param p_countryId - country id
     * @return country name if found, null if country not found
     */

    public static Country getCountryFromId(ArrayList<Country> p_countryList,int p_countryId)
    {
        for(Country c:p_countryList)
        {
            if(c.getD_countryId()==p_countryId)
            {
                return c;
            }

        }
        return null;

    }

}
