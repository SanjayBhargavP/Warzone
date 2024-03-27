package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Connectivity manages the transfer of data from skeleton to other
 * classes where the map objects are used.
 * @author Poojitha
 * @author Susmitha Mamula
 */
public class Connectivity {

    private List<Continent> d_continentsList = new ArrayList<Continent>();
    private List<Country> d_countriesList = new ArrayList<Country>();

    private String d_pathName;
    private String d_filePathName;

    private String d_nameOfMap;

    /**
     * Function is used to get the name of current map
     * @return name of map
     */
    public String getD_nameOfMap() {
        return d_nameOfMap;
    }

    /**
     * Used to set the name of current map
     * @param d_nameOfMap name of map to be set
     */
    public void setD_nameOfMap(String d_nameOfMap) {
        this.d_nameOfMap = d_nameOfMap;
    }

    /**
     * Gets the File path address.
     *
     * @return file path
     */
    public String getD_filePathName() {
        return d_filePathName;
    }

    /**
     * Sets the File path address
     *
     * @param p_FilePathName refers to the address of the file.
     */
    public void setD_FilePathName(String p_FilePathName) {
        this.d_filePathName = p_FilePathName;
    }

    /**
     * Gets the path name.
     *
     * @return path name
     */
    public String getD_pathName() {
        return d_pathName;
    }

    /**
     * Sets the path name
     *
     * @param p_pathName refers to the file name.
     */
    public void setD_pathName(String p_pathName) {
        this.d_pathName = p_pathName;
    }

    /**
     * Gets the continents List.
     *
     * @return the continentsList
     */
    public ArrayList<Continent> getD_continentsList() {
        return (ArrayList<Continent>) d_continentsList;
    }

    /**
     * Sets the continent map.
     *
     * @param p_continentsList refers to the map of continents.
     */
    public void setD_continentsList(List<Continent> p_continentsList) {
        this.d_continentsList = p_continentsList;
    }

    /**
     * Gets the countries List.
     *
     * @return the countries List
     */
    public ArrayList<Country> getD_countriesList() {
        return (ArrayList<Country>) d_countriesList;
    }

    /**
     * Sets the countries List.
     *
     * @param p_countriesList refers to the list of countries.
     */
    public void setD_countriesList(List<Country> p_countriesList) {
        this.d_countriesList = p_countriesList;
    }

    /**
     * Gets the continent object from continent ID.
     * @param p_continentId refers to the continent ID.
     * @return continent object if found
     *
     */
    public Continent getContinentFromContinentId(int p_continentId)
    {

        for(Continent continent:d_continentsList)
        {
            if(continent.getD_continentId() == p_continentId)
               return continent;
        }
        return null;
    }

    /**
     * Gets the Country object from country ID.
     * @param p_countryId refers to the country ID.
     * @return Country object if found else returns null
     *
     */
    public Country getCountryFromCountryId(int p_countryId)
    {

        for(Country country:d_countriesList)
        {
            if(country.getD_countryId() == p_countryId)
                return country;
        }
        return null;
    }

    /**
     * Deletes the country from countries list.
     * @param p_country refers to the country object
     *
     */
    public void removeCountry(Country p_country)
    {
        if(p_country != null){
            d_countriesList.remove(p_country);
        }
    }

    /**
     * Deletes the continent from continents list.
     * @param p_continent refers to the continent object
     *
     */
    public void removeContinent(Continent p_continent)
    {
        if(p_continent != null){
            d_continentsList.remove(p_continent);
        }
    }

}