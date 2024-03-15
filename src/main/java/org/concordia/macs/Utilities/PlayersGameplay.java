package org.concordia.macs.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.concordia.macs.Models.Player;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Continent;
/**
 * The PlayersGameplay class manages gameplay related to players.
 * @author - Piyush Gupta
 */
public class PlayersGameplay {

    /**
     * Assigns countries to players randomly.
     * 
     * @param p_playersList The list of players.
     * @param p_countryList The list of countries.
     * @param p_continentList The list of continents.
     * @return An error code indicating success or failure of assignment.
     */
    public static int assignCountries(List<Player> p_playersList, List<Country> p_countryList, List<Continent> p_continentList) {
        if (p_playersList.size() == 0) {
            System.out.println(ColorCoding.ANSI_RED + "Error: Insufficient Players to assign countries" + ColorCoding.ANSI_RESET);
            return 1;
        }

        if (p_countryList.size() < p_playersList.size()) {
            System.out.println(
                    ColorCoding.ANSI_RED + "Error: Insufficient country to assign to all players" + ColorCoding.ANSI_RESET);
            return 1;
        }

        int[] l_playerCountArray = new int[p_playersList.size()];
        int sum = 0;
        int range = (p_countryList.size() - 1) + 1;

        do
        {
            sum=0;
            for(int i=0; i<p_playersList.size(); i++) {
                int randomResult = (int) ((Math.random()*range) + 1);
                sum=sum+randomResult;
                l_playerCountArray[i]=randomResult;
            }
        }	while(sum>p_countryList.size());

        for(int i=0; i<p_playersList.size();i++) {
            ArrayList<Country> l_removeCountry=new ArrayList<>();
            for(int j=0; j<l_playerCountArray[i];j++) {
                Country l_country = p_countryList.get(j);
                l_removeCountry.add(l_country);
                p_playersList.get(i).addCountry(l_country);
            }
            p_countryList.removeAll(l_removeCountry);

        }
        for(int i=0;i<p_playersList.size();i++) {
            ArrayList<String> l_tempCountry=new ArrayList<>();
            ArrayList<String> l_tempCountryInContinent=new ArrayList<>();
            ArrayList<Continent> l_continentsOwned=new ArrayList<>();
            for(int j=0;j<p_playersList.get(i).getD_country().size();j++) l_tempCountry.add(p_playersList.get(i).getD_country().get(j).getD_countryName());
            for(int j=0;j<p_continentList.size();j++)
            {
                l_tempCountryInContinent=new ArrayList<>();
                for(int k=0;k<p_continentList.get(j).getD_countries().size();k++) l_tempCountryInContinent.add(p_continentList.get(j).getD_countries().get(k).getD_countryName());
                if(l_tempCountry.containsAll(l_tempCountryInContinent))
                {
                    l_continentsOwned.add(p_continentList.get(j));
                    p_playersList.get(i).setD_continent(l_continentsOwned);
                }
            }
        }
        return 0;
    }

    /**
     * Assigns armies to players based on their territories and continents controlled.
     * 
     * @param p_playerList The list of players.
     */
    public static void assignArmiesToPlayers(ArrayList<Player> p_playerList) {
        for(Player l_player: p_playerList){
            int l_countryListSize=l_player.getD_country().size()/3;
            int l_armyCount=Math.max(3, l_countryListSize);
            int l_tempContinentCount=0;
            if(l_player.getD_continent().size()!=0)
            {
                for(Continent l_continent: l_player.getD_continent())
                    l_tempContinentCount=l_tempContinentCount+ l_continent.getD_continentArmyBonus();
            }
            l_armyCount+=l_tempContinentCount;
            l_player.setD_armyNumber(l_armyCount);
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
        if (displayFlag == 1){
            System.out.println("\nPlayer:" + player.getD_playerName() + " has following countries assigned");
        }
        for (Country country : player.getD_country()) {
            String countryName = country.getD_countryName();
            if (displayFlag == 1){
                System.out.println(countryName);
            }
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
        return player.getD_armyNumber() >= army;
    }
}

