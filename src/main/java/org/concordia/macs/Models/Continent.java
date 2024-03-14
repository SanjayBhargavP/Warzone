package org.concordia.macs.Models;
import java.util.ArrayList;
import java.util.List;

/**
 *Continent class defines continentId,continentName
 * @author Blesslin Jeba Shiny
 */

public class Continent
{
    private int d_continentId;
    private String d_continentName;
    private List<Country> d_countries;
    private int d_continentArmyBonus;

    /**
     * Default constructor for Continent
     */
    public Continent()
    {

    }

    /**
     * Parametrized Constructor for Continent
     * Initializes a new continent
     * @param p_continentID - new continentID
     * @param p_continentName - new continentName
     * @param p_continentArmyBonus - Army count
     * @param p_countries - list of countries
     */
    public Continent(int p_continentID, String p_continentName,int p_continentArmyBonus, List<Country> p_countries)
    {
        this.d_continentId=p_continentID;
        this.d_continentName=p_continentName;
        this.d_continentArmyBonus = p_continentArmyBonus;
        this.d_countries = p_countries;
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
     * Get Continent Army bonus
     * @return Continent Army bonus
     */
    public int getD_continentArmyBonus()
    {
        return d_continentArmyBonus;
    }

    /**
     * Set Continent Army bonus
     * @param p_continentArmyBonus - new Continent army bonus
     */
    public void setD_continentArmyBonus(int p_continentArmyBonus)
    {
        this.d_continentArmyBonus = p_continentArmyBonus;
    }

    /**
     * Gets the country name from continent ID.
     * @param p_continentId refers to the continent ID.
     * @param p_countryList refers to the list of countries present in the continent.
     * @return countries name
     *
     */

    public ArrayList<Country> d_getCountryFromContinentId(int p_continentId, ArrayList<Country> p_countryList)
    {
        ArrayList<Country> l_countries=new ArrayList<Country>();
        for(Country i:p_countryList)
        {
            if(i.getD_continentId()==p_continentId)
                l_countries.add(i);
        }
        return l_countries;
    }

}