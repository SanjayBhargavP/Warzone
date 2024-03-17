package org.concordia.macs.Utilities;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import java.util.ArrayList;
import java.util.List;


/**
 * The MapEditor class allows users to make changes to an existing map.
 * @author - Piyush
 * @author - Blesslin Jeba Shiny
 */
public class MapEditor {

    /**
     * Adds a new continent to the map.
     *
     * @param p_continentId      The ID of the new continent.
     * @param p_continentvalue   The control value of the new continent.
     * @param p_connectivity     The object of the Connectivity class.
     * @return                   0 if continent added successfully, 1 if continent already exists.
     */
    public static int addContinent(String p_continentId, int p_continentvalue, Connectivity p_connectivity) {

        Continent l_continent = new Continent();
        for (Continent continent : p_connectivity.getD_continentsList()) {
            if (p_continentId.equalsIgnoreCase(continent.getD_continentName())) {
                System.out.println("Continent already Exists.");
                return 1;
            }
        }
        List<Country> l_countries = new ArrayList<>();
        l_continent.setD_continentId(p_connectivity.getD_continentsList().size() + 1);
        l_continent.setD_continentName(p_continentId);
        l_continent.setD_continentArmyBonus(p_continentvalue);
        l_continent.setD_countries(l_countries);
        p_connectivity.getD_continentsList().add(l_continent);
        System.out.println("Continent Added Successfully");
        return 0;
    }

    /**
     * Adds a new country to the map.
     *
     * @param p_countryId       The ID of the new country.
     * @param p_continentId     The ID of the continent to which the country belongs.
     * @param p_connectivity    The object of the Connectivity class.
     * @return                  0 if country added successfully, 1 if continent doesn't exist or country already exists.
     */
    public static int addCountry(String p_countryId, String p_continentId, Connectivity p_connectivity) {
        Country l_country = new Country();
        int flag = 0;
        if (p_connectivity.getD_continentsList().isEmpty()) {
            System.out.println("ERROR: Enter the values of continents first..");
            return 1;
        }
        boolean continentExists = false;
        for (Continent continent : p_connectivity.getD_continentsList()) {
            try {
                if (continent.getD_continentId() == Integer.parseInt(p_continentId)) {
                continentExists = true;
                break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if (!continentExists) {
            System.out.println("Continent " + p_continentId + " does not exist");
            return 1;
        }

        for (Continent continent : p_connectivity.getD_continentsList()) {
            for (Country country : continent.getD_countries()) {
                if (p_countryId.equalsIgnoreCase(country.getD_countryName())) {
                    System.out.println("Country " + p_countryId + " already exists under this continent");
                    return 1;
                }
            }
        }

        l_country.setD_countryId(p_connectivity.getD_countriesList().size() + 1);
        l_country.setD_countryName(p_countryId);
        l_country.setD_continentId(Integer.parseInt(p_continentId));
        p_connectivity.getD_countriesList().add(l_country);
        Continent continent = null;
        try {
             continent = p_connectivity.getContinentFromContinentId(Integer.parseInt(p_continentId));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(continent!= null){
            continent.getD_countries().add(l_country);
        }

        System.out.println("Country Added Successfully");
        return 0;
    }

    /**
     * Adds a neighboring country to an existing country in the map.
     *
     * @param p_countryId            The ID of the country.
     * @param p_neighbourcountryId   The ID of the neighboring country.
     * @param p_connectivity         The object of the Connectivity class.
     * @return                       0 if neighbor added successfully, 1 if country or neighbor doesn't exist.
     */
    public static int addNeighbour(int p_countryId, int p_neighbourcountryId, Connectivity p_connectivity) {
        if (p_connectivity.getD_continentsList().isEmpty()) {
            System.out.println("ERROR: Enter the values of continents first..");
            return 1;
        }
        if (p_connectivity.getD_countriesList().isEmpty()) {
            System.out.println("ERROR: Enter the values of countries first..");
            return 1;
        }

        Country l_country = p_connectivity.getCountryFromCountryId(p_countryId);
        Country l_neighbourCountry = p_connectivity.getCountryFromCountryId(p_neighbourcountryId);

        if (l_country == null || l_neighbourCountry == null) {
            System.out.println("Country IDs do not exist");
            return 1;
        }
        for(Integer l_neighborId: l_country.getD_neighbours()){
            if(l_neighborId == p_neighbourcountryId){
                System.out.println("Neighbor " + l_neighbourCountry.getD_countryName() + "(" + p_neighbourcountryId + ") is already added successfully to " + l_country.getD_countryName() + "(" + p_countryId + ")");
                return 1;
            }
        }

        l_country.getD_neighbours().add(p_neighbourcountryId);
        System.out.println("Neighbor " + l_neighbourCountry.getD_countryName() + "(" + p_neighbourcountryId + ") added successfully to " + l_country.getD_countryName() + "(" + p_countryId + ")");
        return 0;
    }

    /**
     * Removes a neighboring country from an existing country in the map.
     *
     * @param p_countryId            The ID of the country.
     * @param p_neighbourcountryId   The ID of the neighboring country.
     * @param p_connectivity         The object of the Connectivity class.
     * @param p_displayMessage       Flag to display error messages.
     * @return                       0 if neighbor removed successfully, 1 if country or neighbor doesn't exist.
     */
    public static int removeNeighbour(int p_countryId, int p_neighbourcountryId, Connectivity p_connectivity, int p_displayMessage) {
        Country l_country = p_connectivity.getCountryFromCountryId(p_countryId);
        Country l_neighbourCountry = p_connectivity.getCountryFromCountryId(p_neighbourcountryId);

        if (l_country == null || l_neighbourCountry == null) {
            if (p_displayMessage != 0) {
                if (l_country == null)
                    System.out.println("CountryID " + p_countryId + " does not exist");
                if (l_neighbourCountry == null)
                    System.out.println("NeighbourCountryID " + p_neighbourcountryId + " does not exist");
            }
            return 1;
        }

        if (l_country.getD_neighbours().remove(Integer.valueOf(p_neighbourcountryId))) {
            System.out.println("Removed neighbor successfully");
            return 0;
        } else {
            if (p_displayMessage != 0)
                System.out.println("Neighbour not found");
            return 1;
        }
    }

    /**
     * Removes a country from the map.
     *
     * @param p_countryId       The ID of the country to remove.
     * @param p_connectivity    The object of the Connectivity class.
     * @return                  0 if country removed successfully, 1 if country doesn't exist.
     */
    public static int removeCountry(String p_countryId, Connectivity p_connectivity) {
        Country l_country = null;
        try {
            l_country = p_connectivity.getCountryFromCountryId(Integer.parseInt(p_countryId));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (l_country == null) {
            System.out.println("Country " + p_countryId + " does not exist");
            return 1;
        }

        p_connectivity.removeCountry(l_country);

        for (Continent continent : p_connectivity.getD_continentsList()) {
            if (continent.getD_countries().remove(l_country)) {
                for (Country neighbour : continent.getD_countries()) {
                    removeNeighbour(neighbour.getD_countryId(), l_country.getD_countryId(), p_connectivity, 0);
                }
                System.out.println("Country " + p_countryId + " removed successfully");
                return 0;
            }
        }

        return 1;
    }

    /**
     * Removes a continent from the map.
     *
     * @param p_continentId     The ID of the continent to remove.
     * @param p_connectivity    The object of the Connectivity class.
     * @return                  0 if continent removed successfully, 1 if continent doesn't exist.
     */
    public static int removeContinent(String p_continentId, Connectivity p_connectivity) {
        Continent continent = null;
        try {
            continent = p_connectivity.getContinentFromContinentId(Integer.parseInt(p_continentId));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (continent == null) {
            System.out.println("Continent: " + p_continentId + " doesn't exist");
            return 1;
        }

        p_connectivity.removeContinent(continent);

        for (Country country : p_connectivity.getD_countriesList())
        {
            try {
                if (country.getD_continentId() == Integer.parseInt((p_continentId))) {
                    removeCountry(country.getD_countryName(), p_connectivity);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("Continent: " + p_continentId + " removed successfully");
        return 0;
    }
}
