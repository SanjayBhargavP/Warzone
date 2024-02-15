package org.concordia.macs.Models; /**
 * Country class defines Country Id, Country Name
 * Continent Id - to which the country belong to
 * Army count - no of armies deployed in the country
 */

public Class Country
{
    private int d_countryId;
    private String d_countryName;
    private int d_armyCount;
    private int d_continentId;

    /**
     * Default constructor for Country
     */
    public Country()
    {

    }

    /**
     * Parametrized Constructor for Country
     * Initalises a new Country with Id, Name, Armycount, ContinentId
     */
    public Country(int p_countryId,String p_countryName,int p_armyCount,int p_continentId)
    {
        this.d_countryId=p_countryId;
        this.d_countryName=p_countryName;
        this.d_armyCount=p_armyCount;
        this.d_continentId=p_continentId;
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
    public int getD_countryName()
    {
        return d_countryName;
    }

    /**
     * Set CountryName
     * @param p_countryName - new country Name
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


}
