package org.concordia.macs.Models;

/**
 *Continent class defines continentId,continentName
 */

public class Continent
{
    private int d_continentId;
    private String d_continentName;
    private List<Country> d_countries;
    private int d_continentArmyCount;

    /**
     * Default constructor for Continent
     */
    public Continent()
    {

    }

    /**
     * Parametrized Constructor for Continent
     * Initalises a new continent with Id and name
     */
    public Continent(int p_continentID, String p_continentName)
    {
        this.d_continentId=p_continentID;
        this.d_continentName=p_continentName;
    }

    /**
     * Get Continent ID
     * @return continent ID
     */
    public int getD_continentId()
    {
        return d_continentId;
    }

    /**
     * Set Continent ID
     * @param p_continentId - new continentId
     */
    public void setD_continentId(int p_continentId)
    {
        this.d_continentId = p_continentId;
    }

    /**
     * Get Continent Name
     * @return Continent Name
     */
    public String getD_continentName()
    {
        return d_continentName;
    }

    /**
     * Set Continent Name
     * @param p_continentName - new Continent Name
     */
    public void setD_continentName(String p_continentName)
    {
        this.d_continentName = p_continentName;
    }

    /**
     * Get List of countries in the continent
     * @return Country list
     */
    public List<Country> getD_countries()
    {
        return d_countries;
    }

    /**
     * Set List of countries in the continent
     * @param p_countries - new Country list
     * */
    public void setD_countries(List<Country> p_countries)
    {
        this.d_countries = p_countries;
    }

    /**
     * Get Continent Army count
     * @return Continent Army Count
     */
    public int getD_continentArmyCount()
    {
        return d_continentArmyCount;
    }

    /**
     * Set Continent Army Count
     * @param p_continentArmyCount - new Continent army count
     */
    public void setD_continentArmyCount(int p_continentArmyCount)
    {
        this.d_continentArmyCount = p_continentArmyCount;
    }


}