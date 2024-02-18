package org.concordia.macs.Utilities;

import java.util.HashMap;
import java.util.ArrayList;

public class PlayersGameplay {

    public static int assignCountries(HashMap<Integer, Player> playersMap, HashMap<Integer, Country> countryMap, HashMap<Integer, Continent> continentMap) {
        if (playersMap.isEmpty()) {
            System.out.println(ColorCoding.red + "Error: Insufficient Players to assign countries" + ColorCoding.blank);
            return 1;
        }

        if (countryMap.size() < playersMap.size()) {
            System.out.println(ColorCoding.red + "Error: Insufficient country to assign to all players" + ColorCoding.blank);
            return 1;
        }

        int range = countryMap.size();

        for (Player player : playersMap.values()) {
            ArrayList<Integer> removeCountryIds = new ArrayList<>();
            int sum = 0;
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

            for (Continent continent : continentMap.values()) {
                if (player.getCountries().containsAll(continent.getCountries())) {
                    player.addContinent(continent);
                }
            }
        }
        return 0;
    }

    public static void assignArmiesToPlayers(HashMap<Integer, Player> playersMap) {
        for (Player player : playersMap.values()) {
            int armyCount = Math.max(3, player.getCountries().size() / 3);
            for (Continent continent : player.getContinents()) {
                armyCount += continent.getContinentArmyValue();
            }
            player.setArmyCount(armyCount);
        }
    }

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

    public static boolean checkArmyAvailable(int army, Player player) {
        return player.getArmyCount() >= army;
    }
}
