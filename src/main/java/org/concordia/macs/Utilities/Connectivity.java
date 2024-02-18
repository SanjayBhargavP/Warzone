package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class Connectivity manages the transfer of data from skeleton to other
 * classes where the map objects are used.
 * @author Poojitha
 * @author Susmitha Mamula
 */

public class Connectivity {

    private HashMap<String, Continent> d_continentMap = new HashMap<String, Continent>();
    private HashMap<String, Country> d_countryMap = new HashMap<String, Country>();

    private String d_pathName;
    private String D_FILE_PATH_NAME;

    /**
     * Gets the File path address.
     *
     * @return file path
     */

    public String getD_FilePathName() {
        return D_FILE_PATH_NAME;
    }

    /**
     * Sets the File path address
     *
     * @param p_FilePathName refers to the address of the file.
     */

    public void setD_FilePathName(String p_FilePathName) {
        this.D_FILE_PATH_NAME = p_FilePathName;
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
     * Gets the continent map.
     *
     * @return the continent map
     */
    public HashMap<String, Continent> getD_continentMap() {
        return d_continentMap;
    }

    /**
     * Sets the continent map.
     *
     * @param p_continentMap refers to the map of continents.
     */
    public void setD_continentMap(HashMap<String, Continent> p_continentMap) {
        this.d_continentMap = p_continentMap;
    }

    /**
     * Gets the country map.
     *
     * @return the country map
     */
    public HashMap<String, Country> getD_countryMap() {
        return d_countryMap;
    }

    /**
     * Sets the country map.
     *
     * @param p_countryMap refers to the map of countries.
     */
    public void setD_countryMap(HashMap<String, Country> p_countryMap) {
        this.d_countryMap = p_countryMap;
    }

    /**
     * converts hashmap values to arraylist
     * @return arraylist of continent
     */
    public ArrayList<Continent> getD_continentList() {
        return new ArrayList<>(d_continentMap.values());

    }

    /**
     * converts hashmap values to arraylist
     * @return arraylist of country
     */
    public ArrayList<Country> getD_countryList() {
        return new ArrayList<>(d_countryMap.values());

    }

}