package org.concordia.macs.Utilities;

import java.util.ArrayList;
import java.util.Random;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;
import org.concordia.macs.State.Play;

/**
 * PlayersGameplay class manages the player perspective.
 */
public class PlayersGameplay {
    private static ArrayList<Integer> armyCounts = new ArrayList<>();
    private static ArrayList<Country> neutralCountries = new ArrayList<>();
    private static Player winner;

    /**
     * Assigns countries randomly to players at the beginning of the game.
     *
     * @param playersArray   the list of players in the game
     * @param countryList    the list of countries on the map
     * @param continentList  the list of continents present in the map
     * @return               0 if successful, 1 for errors
     */
    public static int assignCountries(ArrayList<Player> playersArray, ArrayList<Country> countryList, ArrayList<Continent> continentList) {
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        if (playersArray.isEmpty()) {
            logEntryBuffer.log("Error: Insufficient Players to assign countries");
            System.out.println(ColorCoding.ANSI_RED + "Error: Insufficient Players to assign countries" + ColorCoding.ANSI_RESET);
            return 1;
        }

        if (countryList.size() < playersArray.size()) {
            logEntryBuffer.log("Error: Insufficient country to assign to all players");
            System.out.println(ColorCoding.ANSI_RED + "Error: Insufficient country to assign to all players" + ColorCoding.ANSI_RESET);
            return 1;
        }

        int[] playerCountArray = new int[playersArray.size()];
        int range = countryList.size();
        int sum;

        do {
            sum = 0;
            for (int i = 0; i < playersArray.size(); i++) {
                int randomResult = (int) (Math.random() * range) + 1;
                sum += randomResult;
                playerCountArray[i] = randomResult;
            }
        } while (sum > countryList.size());

        int index = 0;
        for (int i = 0; i < playersArray.size(); i++) {
            ArrayList<Country> removeCountry = new ArrayList<>();
            for (int j = 0; j < playerCountArray[i]; j++) {
                Country country = countryList.get(index++);
                removeCountry.add(country);
                playersArray.get(i).addCountry(country);
            }
        }

        for (int i = index; i < countryList.size(); i++) {
            neutralCountries.add(countryList.get(i));
        }

        updateContinent(playersArray, continentList);
        return 0;
    }

    /**
     * Assigns armies to players at the beginning of the game.
     *
     * @param playersList  the list of players
     */
    public static void assignArmiesToPlayers(ArrayList<Player> playersList) {
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        for (Player player : playersList) {
            int countryListSize = player.getD_country().size() / 3;
            int armyCount = Math.max(3, countryListSize);
            int tempContinentCount = 0;
            if (!player.getD_continent().isEmpty()) {
                for (Continent continent : player.getD_continent())
                    tempContinentCount += continent.getD_continentArmyBonus();
            }
            armyCount += tempContinentCount;
            player.setD_armyNumber(armyCount);
            logEntryBuffer.log("Player " + player.getD_playerName() + " has been allotted " + player.getD_armyNumber() + " armies for this round");
            System.out.println(ColorCoding.ANSI_GREEN + "Player " + player.getD_playerName() + " has been allotted " + player.getD_armyNumber() + " armies for this round" + ColorCoding.ANSI_RESET);
        }
    }

    /**
     * Displays the countries owned by a particular player.
     *
     * @param player         the individual player
     * @param displayFlag    the flag indicating whether to display or not
     * @return               list of countries
     */
    public static ArrayList<String> showPlayersCountry(Player player, int displayFlag) {
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        ArrayList<String> countryList = new ArrayList<>();
        if (displayFlag == 1) {
            logEntryBuffer.log("Player:" + player.getD_playerName() + " has following countries assigned");
            System.out.println("\nPlayer:" + player.getD_playerName() + " has following countries assigned");
        }
        ArrayList<Country> countries = (ArrayList<Country>) player.getD_country();
        for (Country country : countries) {
            if (displayFlag == 1) {
                logEntryBuffer.log(country.getD_countryName());
                System.out.println(country.getD_countryName());
            }
            countryList.add(country.getD_countryName());
        }
        return countryList;
    }

    /**
     * Checks whether the player has enough armies available.
     *
     * @param army    the number of armies left with the player
     * @param player  the player object
     * @return        true if army is available, false otherwise
     */
    public static boolean checkArmyAvailable(int army, Player player) {
        if(player.getD_armyNumber() >= army){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Advances troops to a country belonging to the same player or attacks if the country doesn't belong to the player.
     *
     * @param p_player          the individual player
     * @param p_playersArray    the list of players
     * @param p_fromCountry     the country from which troops are advanced or attacked
     * @param toCountry       the country to which troops are sent or attack happens
     * @param p_troops          the number of troops to be sent or used for attacking
     * @param p_continent       the list of continents
     * @param p_connectivity    the map connectivity
     * @param p_fortifyFlag     the flag indicating the fortify phase
     * @return                0 for successful advance/attack and 1 for failed advance/attack
     */
    public static int advance(Player p_player, ArrayList<Player> p_playersArray, Country p_fromCountry, Country toCountry, int p_troops, ArrayList<Continent> p_continent, Connectivity p_connectivity, int p_fortifyFlag) {
        LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
        if (p_connectivity.getD_countriesList().contains(p_fromCountry) && p_connectivity.getD_countriesList().contains(toCountry)) {
            if (p_player.getD_country().contains(p_fromCountry)) {
                if (p_player.getD_country().contains(toCountry)) {
                    if (p_fromCountry.getD_neighbours().contains(toCountry.getD_countryId())) {
                        if (p_troops >= 0) {
                            if (p_troops <= p_fromCountry.getD_armyCount()) {
                                logEntryBuffer.log("Calling Advance");
                                System.out.println("Calling Advance");
                                int troopsAddition = toCountry.getD_armyCount() + p_troops;
                                toCountry.setD_armyCount(troopsAddition);
                                int troopsDeduction = p_fromCountry.getD_armyCount() - p_troops;
                                p_fromCountry.setD_armyCount(troopsDeduction);
                                logEntryBuffer.log(p_troops + " Troops advanced from " + p_fromCountry.getD_countryName() + " to " + toCountry.getD_countryName());
                                System.out.println(ColorCoding.ANSI_GREEN + p_troops + " Troops advanced from " + p_fromCountry.getD_countryName() + " to " + toCountry.getD_countryName() + ColorCoding.ANSI_RESET);
                                logEntryBuffer.log("After change " + p_fromCountry.getD_countryName() + " has " + p_fromCountry.getD_armyCount() + " troops");
                                System.out.println("After change " + p_fromCountry.getD_countryName() + " has " + p_fromCountry.getD_armyCount() + " troops");
                                logEntryBuffer.log("After change " + toCountry.getD_countryName() + " has " + toCountry.getD_armyCount() + " troops");
                                System.out.println("After change " + toCountry.getD_countryName() + " has " + toCountry.getD_armyCount() + " troops");
                                return 0;
                            } else {
                                logEntryBuffer.log("Error: Can't move more armies than the armies in the country");
                                System.out.println(ColorCoding.ANSI_RED + "Error: Can't move more armies than the armies in the country" + ColorCoding.ANSI_RESET);
                                return 1;
                            }
                        } else {
                            logEntryBuffer.log("Error: Can't move negative armies");
                            System.out.println(ColorCoding.ANSI_RED + "Error: Can't move negative armies" + ColorCoding.ANSI_RESET);
                            return 1;
                        }
                    } else {
                        logEntryBuffer.log("Error: " + p_fromCountry.getD_countryName() + " is not the neighbour of " + toCountry.getD_countryName() + ". Troops can't be advanced or country can't be attacked");
                        System.out.println(ColorCoding.ANSI_RED + "Error: " + p_fromCountry.getD_countryName() + " is not the neighbour of " + toCountry.getD_countryName() + ". Troops can't be advanced or country can't be attacked" + ColorCoding.ANSI_RESET);
                        return 1;
                    }
                } else {
                    if (p_fortifyFlag == 0) {
                        logEntryBuffer.log("Inside fortify");
                        System.out.println("Inside fortify");
                        Player topLayer = findPlayerWithCountry(p_playersArray, toCountry);

                        if (topLayer != null) {
                            System.out.println(p_player.getDiplomacyWith());
                            if (!p_player.getDiplomacyWith().contains(topLayer.getD_playerId())) {
                                attack(p_player, p_playersArray, p_fromCountry, toCountry, p_troops, p_continent, p_connectivity);
                                return 0;
                            } else {
                                logEntryBuffer.log("Attack is not possible between " + p_fromCountry.getD_countryName() + " and " + toCountry.getD_countryName() + " because of diplomacy");
                                System.out.println(ColorCoding.ANSI_RED + "Attack is not possible between " + p_fromCountry.getD_countryName() + " and " + toCountry.getD_countryName() + " because of diplomacy" + ColorCoding.ANSI_RESET);
                                return 1;
                            }
                        } else {
                            attack(p_player, p_playersArray, p_fromCountry, toCountry, p_troops, p_continent, p_connectivity);
                            return 0;
                        }
                    } else {
                        logEntryBuffer.log("Attack cannot be done on fortify phase");
                        System.out.println("Attack cannot be done on fortify phase");
                        return 1;
                    }
                }
            } else {
                logEntryBuffer.log("Error: " + p_fromCountry.getD_countryName() + " doesn't belong to player from where they want to advance the troops");
                System.out.println(ColorCoding.ANSI_RED + "Error: " + p_fromCountry.getD_countryName() + " doesn't belong to player from where they want to advance the troops" + ColorCoding.ANSI_RESET);
                return 1;
            }
        } else {
            if (!p_connectivity.getD_countriesList().contains(p_fromCountry)) {
                logEntryBuffer.log("Error: Country " + p_fromCountry.getD_countryName() + " doesn't belong to the Map");
                System.out.println(ColorCoding.ANSI_RED + "Error: Country " + p_fromCountry.getD_countryName() + " doesn't belong to the Map" + ColorCoding.ANSI_RESET);
                return 1;
            } else if (!p_connectivity.getD_countriesList().contains(toCountry)) {
                logEntryBuffer.log("Error: Country " + toCountry.getD_countryName() + " doesn't belong to the Map");
                System.out.println(ColorCoding.ANSI_RED + "Error: Country " + toCountry.getD_countryName() + " doesn't belong to the Map" + ColorCoding.ANSI_RESET);
                return 1;
            }
            return 1;
        }
    }



private static ArrayList<Country> l_neutralCountry = new ArrayList<>();

/**
 * This method finds the player who owns the given country.
 * @param p_playersArray The list of players.
 * @param p_Country The country to find.
 * @return The player who owns the country, or null if no player owns it.
 */
private static Player findPlayerWithCountry(ArrayList<Player> p_playersArray, Country p_Country) {
    for(Player p: p_playersArray) {
        if(p.getD_country().contains(p_Country))
            return p;
    }
    return null;
}

/**
 * This method handles an attack on another country.
 * @param p_player The attacking player.
 * @param p_playersArray The list of players.
 * @param p_fromCountry The country from which the attack is launched.
 * @param p_toCountry The country being attacked.
 * @param p_troops The number of troops used in the attack.
 * @param p_continent The list of continents.
 * @param p_connectivity The map connectivity.
 * @return 0 for a successful attack, 1 for a failed attack.
 */
public static int attack(Player p_player,ArrayList<Player> p_playersArray,Country p_fromCountry,
                         Country p_toCountry,int p_troops,ArrayList<Continent> p_continent,Connectivity p_connectivity) {
    if(p_troops<0) {
        System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack with negative number of troops"+ColorCoding.ANSI_RESET);
        return 1;
    }

    try {
        if(!p_connectivity.getD_countriesList().contains(p_fromCountry) || !p_connectivity.getD_countriesList().contains(p_toCountry)) {
            if(!p_connectivity.getD_countriesList().contains(p_fromCountry)) {
                System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_fromCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
            }
            if(!p_connectivity.getD_countriesList().contains(p_toCountry)) {
                System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_toCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
            }
            return 1;
        }

        if(!p_player.getD_country().contains(p_fromCountry)) {
            System.out.println(ColorCoding.ANSI_RED+"Error: "+p_fromCountry.getD_countryName()+" doesn't belong to player from where he wants to attack"+ColorCoding.ANSI_RESET);
            return 1;
        }

        if(p_player.getD_country().contains(p_toCountry)) {
            System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack Own country"+ColorCoding.ANSI_RESET);
            return 1;
        }

        if(!p_fromCountry.getD_neighbours().contains(p_toCountry.getD_countryId())) {
            System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack the country "+p_toCountry.getD_countryName()+
                    " as it is not a neighbour of "+p_fromCountry.getD_countryName()+ColorCoding.ANSI_RESET);
            return 1;
        }

        if(p_troops>p_fromCountry.getD_armyCount()) {
            System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack with more armies than the armies in the country"+ColorCoding.ANSI_RESET);
            return 1;
        }

        int l_troopsSource = p_troops;
        int l_troopsDestination = p_toCountry.getD_armyCount();

        if(l_troopsDestination>0 && l_troopsDestination> 0) {
            int attackRange =(60 - 0)+1;
            int attackRandom = (int)(Math.random() * attackRange) + 0;
            System.out.println("attackRandom = "+attackRandom);
            l_troopsSource = Math.round(l_troopsSource*((float)attackRandom/100));
            System.out.println("source Troops = "+l_troopsSource);

            int defendRange =(70 - 0)+1;
            int defendRandom = (int)(Math.random() * defendRange) + 0;
            System.out.println("defendRandom = "+defendRandom);
            l_troopsDestination = Math.round (l_troopsDestination*((float)defendRandom/100));
            System.out.println("Destination Troops = "+l_troopsDestination);
        }

        if(l_troopsSource>l_troopsDestination) {
            removeCountry(p_playersArray,p_toCountry,p_continent);
            p_player.getD_country().add(p_toCountry);
            System.out.println(ColorCoding.ANSI_GREEN+"Attack Successfull!!"+ColorCoding.ANSI_RESET);
            p_player.getCards().add(generateCard());
            updateContinent(p_playersArray, p_continent);

            int l_troopsLeft = l_troopsSource - l_troopsDestination;
            p_toCountry.setD_armyCount(l_troopsLeft);
            p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);

            System.out.println("Attack on "+p_toCountry.getD_countryName()+ " from "+p_fromCountry.getD_countryName()+" was successful.");
            System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
            System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
        } else if(l_troopsSource<l_troopsDestination) {
            int l_troopsLeft = l_troopsDestination - l_troopsSource;
            p_toCountry.setD_armyCount(l_troopsLeft);
            p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);
            System.out.println(p_toCountry.getD_countryName()+" defended itself successfully from "+p_fromCountry.getD_countryName());
            System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
            System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
        } else {
            p_toCountry.setD_armyCount(0);
            p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);
            System.out.println(p_toCountry.getD_countryName()+" defended itself successfully from "+p_fromCountry.getD_countryName());
            System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
            System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
        }

        return 0;
    } catch(Exception e) {
        System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_toCountry+" Does not exist"+ColorCoding.ANSI_RESET);
        return 1;
    }
}

/**
 * This method removes a country from a player or from the neutral country list.
 * @param p_playersArray The list of players.
 * @param p_country The country to remove.
 * @param p_continent The list of continents.
 * @return 0 for successful removal, 1 if failed.
 */
public static int removeCountry(ArrayList<Player> p_playersArray,Country p_country,ArrayList<Continent> p_continent) {
    for(Player p:p_playersArray) {
        if(p.getD_country().contains(p_country)) {
            p.getD_country().remove(p_country);
            updateContinent(p_playersArray, p_continent);
            return 0;
        }
    }
    if(l_neutralCountry.contains(p_country)) {
        l_neutralCountry.remove(p_country);
        updateContinent(p_playersArray, p_continent);
        return 0;
    } else {
        System.out.println(ColorCoding.ANSI_RED+"Error: "+p_country.getD_countryName()+" does not exist."+ColorCoding.ANSI_RESET);
    }
    return 1;
}

/**
 * This method is used to bomb one country when the other player uses the bomb card.
 * @param p_player The player performing the bomb action.
 * @param p_playersArray The list of players.
 * @param p_toCountry The country being bombed.
 * @param p_continent The list of continents.
 * @return The number of armies left on the target country after the bombing.
 */
public static int bomb(Player p_player, ArrayList<Player> p_playersArray, Country p_toCountry, ArrayList<Continent> p_continent) {
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
    int l_targetArmies = 0;
    int l_countryFoundFlag = 0;
    for (Country c : p_player.getD_country()) {
        if (c.getD_neighbours().contains(p_toCountry.getD_countryId()))
            l_countryFoundFlag++;
    }
    if (l_countryFoundFlag != 0) {
        l_targetArmies = (int) Math.floor(p_toCountry.getD_armyCount() / 2);
        p_toCountry.setD_armyCount(l_targetArmies);
        if (p_toCountry.getD_armyCount() == 0) {
            removeCountry(p_playersArray, p_toCountry, p_continent);
            updateContinent(p_playersArray, p_continent);
            d_logEntryBuffer.log("The Country " + p_toCountry + " is a now a neutral Country");
            System.out.println("The Country " + p_toCountry + " is a now a neutral Country");
        } else {
            d_logEntryBuffer.log("The country " + p_toCountry + " now has " + l_targetArmies + " armies");
            System.out.println("The country " + p_toCountry + " now has " + l_targetArmies + " armies");
        }
    } else {
        System.out.println(p_toCountry + " is not a neighbor to any of the existing lands.");
    }
    return l_targetArmies;
}

/**
 * This method is used to update continent list of players if country gets added or removed.
 * @param p_playersArray The list of players.
 * @param p_continentList The list of continents.
 * @return 0 after successful update.
 */
public static int updateContinent(ArrayList<Player> p_playersArray, ArrayList<Continent> p_continentList) {
    for (Player p : p_playersArray) {
        p.setD_continent(new ArrayList<Continent>());
    }

    for (int i = 0; i < p_playersArray.size(); i++) {
        ArrayList<String> l_tempCountry = new ArrayList<>();
        ArrayList<String> tempCountryInContinent = new ArrayList<>();
        ArrayList<Continent> l_continentsOwned = new ArrayList<>();
        for (int j = 0; j < p_playersArray.get(i).getD_country().size(); j++)
            l_tempCountry.add(p_playersArray.get(i).getD_country().get(j).getD_countryName());
        for (int j = 0; j < p_continentList.size(); j++) {
            tempCountryInContinent = new ArrayList<>();
            for (int k = 0; k < p_continentList.get(j).getD_countries().size(); k++)
                tempCountryInContinent.add(p_continentList.get(j).getD_countries().get(k).getD_countryName());
            if (l_tempCountry.containsAll(tempCountryInContinent)) {
                l_continentsOwned.add(p_continentList.get(j));
                p_playersArray.get(i).setD_continent(l_continentsOwned);
            }
        }
    }
    return 0;
}

/**
 * This method is used to airlift armies from one country to another once the player uses airlift card.
 * @param p_sourceCountryObj The source country for airlifting armies.
 * @param p_targetCountryObj The target country for airlifting armies.
 * @param p_armiesToAirlift The number of armies to airlift.
 * @param p_player The player performing the airlift action.
 * @return True if the airlift action was successful, false otherwise.
 */
public static boolean AirliftDeploy(String p_sourceCountryObj, String p_targetCountryObj, int p_armiesToAirlift, Player p_player) {
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    String l_sourceCountryVar = p_sourceCountryObj;
    String l_targetCountryVar = p_targetCountryObj;

    int l_armiesToAirlift = p_armiesToAirlift;

    Player l_player = p_player;


    int flag = 0;
    int flag1 = 0;

    ArrayList<Country> l_country = new ArrayList<>();
    l_country = (ArrayList<Country>) p_player.getD_country();
    Country l_sourceCountry = new Country();
    Country l_targetCountry = new Country();


    for (Country c : l_country) {
        if (c.getD_countryName().equals(l_sourceCountryVar)) {

            flag = 1;
            l_sourceCountry = c;
        }
    }
    if (flag == 0) {
        d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the source country.");
        System.out.println(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the source country.");
        return false;
    }

    for (Country c : l_country) {
        if (c.getD_countryName().equals(l_targetCountryVar)) {
            flag1 = 1;
            l_targetCountry = c;
        }
    }
    if (flag1 == 0) {
        d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the target country.");
        System.out.println(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the target country.");
        return false;
    }

    if (l_sourceCountry.getD_armyCount() < l_armiesToAirlift) {
        d_logEntryBuffer.log(l_player.getD_playerName() + " doesn't have enough army on this country to airlift.");
        System.out.println(l_player.getD_playerName() + " doesn't have enough army on this country to airlift.");
        return false;
    }

    int x = l_sourceCountry.getD_armyCount();
    x = x - l_armiesToAirlift;

    int y = l_targetCountry.getD_armyCount();
    y = y + l_armiesToAirlift;

    l_sourceCountry.setD_armyCount(x);
    l_targetCountry.setD_armyCount(y);

    return true;
}

/**
 * This method is used to make a country neutral and increase its army count once the player uses blockade card.
 * @param p_sourceCountryObj The country to be made neutral.
 * @param p_player The player performing the blockade action.
 * @param p_playersArray The list of players.
 * @param p_continent The list of continents.
 * @return True if the blockade action was successful, false otherwise.
 */
public static boolean Blockade(String p_sourceCountryObj, Player p_player, ArrayList<Player> p_playersArray, ArrayList<Continent> p_continent) {
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    String l_sourceCountryVar = p_sourceCountryObj;

    Player l_player = p_player;

    int flag = 0;

    ArrayList<Country> l_country = new ArrayList<>();
    l_country = (ArrayList<Country>) p_player.getD_country();
    Country l_sourceCountry = new Country();

    for (Country c : l_country) {
        if (c.getD_countryName().equals(l_sourceCountryVar)) {

            flag = 1;
            l_sourceCountry = c;
        }
    }
    if (flag == 0) {
        d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Blockade card as it doesn't own the source country.");
        System.out.println(l_player.getD_playerName() + " can not use Blockade card as it doesn't own the source country.");
        return false;
    }

    if(l_sourceCountry.getD_armyCount()<0) {
        d_logEntryBuffer.log(l_player.getD_playerName() + " doesn't have enough army on this country to blockade.");
        System.out.println(l_player.getD_playerName() + " doesn't have enough army on this country to blockade.");
        return false;
    }

    int x = l_sourceCountry.getD_armyCount();
    x = x*3;

    l_sourceCountry.setD_armyCount(x);
    removeCountry(p_playersArray, l_sourceCountry, p_continent);
    l_neutralCountry.add(l_sourceCountry);

    updateContinent(p_playersArray, p_continent);
    d_logEntryBuffer.log("The country " + l_sourceCountry.getD_countryName() + " has been blockaded. It now has " + l_sourceCountry.getD_armyCount() + " armies.");
    System.out.println("The country " + l_sourceCountry.getD_countryName() + " has been blockaded. It now has " + l_sourceCountry.getD_armyCount() + " armies.");

    return true;
}

/**
 *
 * This method will generate random cards and adds to players if player capture the new territory.
 */
public static String generateCard() {
    String[] l_cards = {"bomb","blockade","airlift","diplomacy"};
    Random ran = new Random();

    return l_cards[ran.nextInt(l_cards.length)];

}
/**
 * This function decides the winner of tha game
 * @param p_players array of players
 * @param p_connectivity map content
 * @return returns the winner of the game
 */

public static Player winnerPlayer(ArrayList<Player> p_players,Connectivity p_connectivity) {
    Player l_winner = new Player();
    for(Player p:p_players)
    {
        if(p.getD_country().size()==p_connectivity.getD_countriesList().size())
        {
            l_winner.setD_playerId(p.getD_playerId());
            l_winner.setD_playerName(p.getD_playerName());
            l_winner.setD_armyNumber(p.getD_armyNumber());
            return l_winner;
        }
    }
    return null;
}

/**
 *
 * This method adds diplomacy between two players if any of the used diplomacy card
 * @param p_player refers to the player.
 * @param p_playersArray refers to the players Array.
 * @param p_toPlayerID refers to other player ID with whom diplomacy is set
 *
 */
public static void negotiate(Player p_player,ArrayList<Player> p_playersArray, String p_toPlayerID) {
    LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();

    p_player.addDiplomacyWith(Integer.parseInt(p_toPlayerID));

    for(Player p: p_playersArray) {
        if(p.getD_playerId() == Integer.parseInt(p_toPlayerID)) {
            p.addDiplomacyWith(p_player.getD_playerId());
        }
        d_logEntryBuffer.log(p.getD_playerName()+" = "+p.getDiplomacyWith());
        System.out.println(p.getD_playerName()+" = "+p.getDiplomacyWith());
    }


}

/**
 *
 * This method will remove diplomacy between players after each turn
 * @param p_playersArray refers to the players Array.
 *
 */
public static void resetDiplomacy(ArrayList<Player> p_playersArray) {

    for(Player p:p_playersArray)
    {
        p.clearDiplomacyWith();
    }

}

/**
 *
 * This method will add country to neutral country list.
 * @param c refers to the country.
 *
 */
public static void addNeutralCountry(Country c)
{
    l_neutralCountry.add(c);
}

/**
 *
 * This method will return neutral country list.
 *
 *@return neutral country list
 */
public static ArrayList<Country> getNeutralCountry()
{
    return l_neutralCountry;
}


/**
 *
 * This method will clear the neutral country list.
 *
 */
public static void clearNeutralCountry()
{
    l_neutralCountry.clear();
}
}


