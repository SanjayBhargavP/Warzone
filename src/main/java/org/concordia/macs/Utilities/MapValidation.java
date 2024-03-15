package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MapValidation class validates the entire map.
 *
 */
public class MapValidation {

    /**
     * Validates the complete map.
     * @param p_connectivity for the transfer of data from skeleton to other
     * @return Boolean Value if map is valid
     */
    public static boolean validateMap(Connectivity p_connectivity)
    {
        List<Continent> l_continentsList = p_connectivity.getD_continentsList();
        List<Country> l_countryList = p_connectivity.getD_countriesList();
        if(l_continentsList==null || l_continentsList.isEmpty()){
            System.out.println(ColorCoding.ANSI_RED+"ERROR: Map must possess atleast one continent!"+ColorCoding.ANSI_RESET);
            return false;
        }
        if(l_countryList==null || l_countryList.isEmpty()){
            System.out.println(ColorCoding.ANSI_RED+"ERROR: Map must possess atleast one country!"+ColorCoding.ANSI_RESET);
            return false;
        }
        for(Country c: l_countryList){
            if(c.getD_neighbours() == null || c.getD_neighbours().isEmpty()){
                System.out.println(ColorCoding.ANSI_RED+"ERROR: "+c.getD_countryName()+" does not possess any neighbour, hence isn't reachable!"+ColorCoding.ANSI_RESET);
                return false;
            }
        }
        boolean l_connectivityFlag=true;
        for (Continent l_continent:l_continentsList){
            if (null == l_continent.getD_countries() || l_continent.getD_countries().size()<1){
                System.out.println(ColorCoding.ANSI_RED+"ERROR: "+l_continent.getD_continentName() + " has no countries, it must possess atleast 1 country"+ColorCoding.ANSI_RESET);
            }
            if(!subGraphConnectivity(l_continent, p_connectivity)){
                l_connectivityFlag=false;
            }
        }
        if(!l_connectivityFlag){
            return l_connectivityFlag;
        }

        HashMap<Integer, Boolean> d_countryReach = new HashMap<Integer, Boolean>();
        for (Country c : l_countryList) {
            d_countryReach.put(c.getD_countryId(), false);
        }
        dfsCountry(l_countryList.get(0), d_countryReach, p_connectivity);

        for (Map.Entry<Integer, Boolean> entry : d_countryReach.entrySet()) {
            if (!entry.getValue()) {
                System.out.println(ColorCoding.ANSI_RED+"ERROR: "+getCountry(entry.getKey(), p_connectivity).getD_countryName() + " country is not reachable"+ColorCoding.ANSI_RESET);
            }
        }
        return !d_countryReach.containsValue(false);
    }

    /**
     * dfsCountry iteratively applies the DFS search from the entered node.
     *
     * @param p_country Country visited first
     * @param p_countryReach Map of Countries already reached
     * @param p_connectivity for the transfer of data from skeleton to other
     */
    public static void dfsCountry(Country p_country,  HashMap<Integer, Boolean> p_countryReach, Connectivity p_connectivity) {
        p_countryReach.put(p_country.getD_countryId(), true);
        for (Country l_nextCountry : getAdjacentCountry(p_country, p_connectivity)) {
            if (!p_countryReach.get(l_nextCountry.getD_countryId())) {
                dfsCountry(l_nextCountry, p_countryReach, p_connectivity);
            }
        }
    }

    /**
     * Gets the Adjacent Country Objects.
     *
     * @param p_country the adjacent country
     * @param p_connectivity for the transfer of data from skeleton to other
     * @return list of Adjacent Country Objects
     */
    public static List<Country> getAdjacentCountry(Country p_country, Connectivity p_connectivity) {
        List<Country> l_neighbourCountries = new ArrayList<Country>();

        if (p_country.getD_neighbours().size() > 0) {
            for (int i : p_country.getD_neighbours()) {
                l_neighbourCountries.add(getCountry(i, p_connectivity));
            }
        } else {
            System.out.println(ColorCoding.ANSI_RED+"ERROR: "+p_country.getD_countryName() + " doesn't have any adjacent countries"+ColorCoding.ANSI_RESET);
        }
        return l_neighbourCountries;
    }

    /**
     * Finds the Country object from a given country ID.
     *
     * @param p_countryId ID of the country object to be found
     * @param p_connectivity for the transfer of data from skeleton to other
     * @return matching country object
     */
    public static Country getCountry(Integer p_countryId, Connectivity p_connectivity) {
        return p_connectivity.getD_countriesList().stream().filter(l_country -> (l_country.getD_countryId() == (p_countryId))).findFirst().orElse(null);
    }

    /**
     * Checks Inner Connectivity of a Continent.
     *
     * @param p_continent Continent being checked
     * @param p_connectivity for the transfer of data from skeleton to other
     * @return Bool Value if Continent is Connected
     */
    public static boolean subGraphConnectivity(Continent p_continent,Connectivity p_connectivity ){
        HashMap<Integer, Boolean> l_continentCountry = new HashMap<Integer, Boolean>();

        for (Country c : p_continent.getD_countries()) {
            l_continentCountry.put(c.getD_countryId(), false);
        }
        dfsContinent(p_continent.getD_countries().get(0), l_continentCountry, p_continent);

        for (Map.Entry<Integer, Boolean> entry : l_continentCountry.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = getCountry(entry.getKey(), p_connectivity);
                System.out.println(ColorCoding.ANSI_RED+"ERROR: "+l_country.getD_countryName() + " in Continent " + p_continent.getD_continentName() + " is not reachable"+ColorCoding.ANSI_RESET);
            }
        }
        return !l_continentCountry.containsValue(false);
    }

    /**
     * Depth First Search Applied to the Continent Subgraph.
     *
     * @param p_country country visited
     * @param p_continentCountry Hashmap of Visited Boolean Values
     * @param p_continent continent being checked for connectivity
     */
    public static void dfsContinent(Country p_country, HashMap<Integer, Boolean> p_continentCountry, Continent p_continent) {
        p_continentCountry.put(p_country.getD_countryId(), true);
        for (Country c : p_continent.getD_countries()) {
            if (p_country.getD_neighbours().contains(c.getD_countryId())) {
                if (!p_continentCountry.get(c.getD_countryId())) {
                    dfsContinent(c, p_continentCountry, p_continent);
                }
            }
        }
    }
}
