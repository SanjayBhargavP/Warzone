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
    public List<Continent> getD_continentsList() {
        return d_continentsList;
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
    public List<Country> getD_countryMap() {
        return d_countriesList;
    }

    /**
     * Sets the countries List.
     *
     * @param p_countriesList refers to the list of countries.
     */
    public void setD_countryMap(List<Country> p_countriesList) {
        this.d_countriesList = p_countriesList;
    }

}