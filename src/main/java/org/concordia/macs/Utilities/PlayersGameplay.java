package org.concordia.macs.Utilities;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * The PlayersGameplay class manages gameplay related to players.
 */
public class PlayersGameplay {

    /**
     * Assigns countries to players randomly.
     * 
     * @param playersMap    The map of players.
     * @param countryMap    The map of countries.
     * @param continentMap  The map of continents.
     * @return              An error code indicating success or failure of assignment.
     */
    public static int assignCountries(HashMap<Integer, Player> playersMap, HashMap<Integer, Country> countryMap, HashMap<Integer, Continent> continentMap) {
        // Check if there are players available
        if (playersMap.isEmpty()) {
            System.out.println(ColorCoding.red + "Error: Insufficient Players to assign countries" + ColorCoding.blank);
            return 1;
        }

        // Check if there are enough countries for all players
        if (countryMap.size() < playersMap.size()) {
            System.out.println(ColorCoding.red + "Error: Insufficient country to assign to all players" + ColorCoding.blank);
            return 1;
        }

        int range = countryMap.size();

        for (Player player : playersMap.values()) {
            ArrayList<Integer> removeCountryIds = new ArrayList<>();
            int sum = 0;
            // Randomly assign countries to the player
            do {
                int randomResult = (int) (Math.random() * range) + 1;
                sum += randomResult;
                removeCountryIds.add(randomResult);
            } while (sum <= range);
            
            for (int countryId : removeCountryIds) {
                Country country = countryMap.get(countryId);
                player.addCountry(country);
                countryMap.remove(countryId);
            }

            // Check if the player controls any continent completely
            for (Continent continent : continentMap.values()) {
                if (player.getCountries().containsAll(continent.getCountries())) {
                    player.addContinent(continent);
                }
            }
        }
        return 0;
    }

    /**
     * Assigns armies to players based on their territories and continents controlled.
     * 
     * @param playersMap    The map of players.
     */
    public static void assignArmiesToPlayers(HashMap<Integer, Player> playersMap) {
        for (Player player : playersMap.values()) {
            int armyCount = Math.max(3, player.getCountries().size() / 3);
            for (Continent continent : player.getContinents()) {
                armyCount += continent.getContinentArmyValue();
            }
            player.setArmyCount(armyCount);
        }
    }

    /**
     * Displays countries owned by a player.
     * 
     * @param player        The player whose countries to display.
     * @param displayFlag   Flag indicating whether to display the countries.
     * @return              List of countries owned by the player.
     */
    public static ArrayList<String> showPlayersCountry(Player player, int displayFlag) {
        ArrayList<String> countryList = new ArrayList<>();
        if (displayFlag == 1) System.out.println("\nPlayer:" + player.getPlayerName() + " has following countries assigned");
        for (Country country : player.getCountries()) {
            String countryName = country.getCountryName();
            if (displayFlag == 1) System.out.println(countryName);
            countryList.add(countryName);
        }
        return countryList;
    }

    /**
     * Checks if a player has enough armies available.
     * 
     * @param army          The number of armies to check for.
     * @param player        The player to check.
     * @return              True if the player has enough armies, false otherwise.
     */
    public static boolean checkArmyAvailable(int army, Player player) {
        return player.getArmyCount() >= army;
    }
}

