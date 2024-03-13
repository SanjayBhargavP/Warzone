package org.concordia.macs.Utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.concordia.macs.Models.Continent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Player;

/**
 * The class PlayersGampePlayTest tests all the player's perspectives
 */

public class PlayersGamePlayTest {
    private static List<Player>  d_playersList = GameEngine.getL_playersArray();
    private static Connectivity d_connectivity = new Connectivity();

    /**
     * This method is used to test addition of players if not there
     */
    @BeforeEach
    public void addPlayersIfNotThereTest() {
        if (d_playersList.isEmpty()) {
            LoadMap.loadMap(d_connectivity, "testMap");

            Player l_currentPlayer = new Player();
            l_currentPlayer.setD_playerName("player1");
            d_playersList.add(l_currentPlayer);

            PlayersGameplay.assignCountries(d_playersList, d_connectivity.getD_countryList(),
                    d_connectivity.getD_continentList());

            PlayersGameplay.assignArmiesToPlayers((ArrayList<Player>) d_playersList);
        }
    }

    /**
     * The method tests if countries are assigned without players.
     */
    @Test
    void assignCountriesWithoutPlayersTest() {
        d_playersList.clear();
        assertEquals(1, PlayersGameplay.assignCountries(d_playersList, d_connectivity.getD_countryList(),
                d_connectivity.getD_continentList()));
    }

    /**
     * The method tests if players are not assigned with countries.
     */
    @Test
    void assignCountriesWithPlayersWithoutCountriesTest() {
        Player l_testPlayer = new Player();
        l_testPlayer.setD_playerName("player2");
        d_playersList.add(l_testPlayer);
        ArrayList<Country> l_countries = new ArrayList<>();

        assertEquals(1, PlayersGameplay.assignCountries(d_playersList, l_countries,
                d_connectivity.getD_continentList()));
    }

    /**
     * The method tests if countries are more than the number of players.
     */
    @Test
    void assignCountriesWithPlayersAndWithLessCountriesTest() {
        Player l_testPlayer = new Player();
        l_testPlayer.setD_playerName("player2");
        d_playersList.add(l_testPlayer);
        Country India = new Country();

        Set<Country> l_countrySet = new HashSet<>();
        l_countrySet.add(India);
    }

    /**
     * The method tests if armies are available to attack or not.
     */
    @Test
    void checkArmyAvailableSendMoreTroopsTest() {
        Player l_currentPlayer = d_playersList.get(0);
        boolean l_truthValue = PlayersGameplay.checkArmyAvailable((l_currentPlayer.getD_armyNumber() + 1),
                l_currentPlayer);
        assertEquals(false, l_truthValue);
    }

    /**
     * The method tests if assignment of armies is correct.
     */
    @Test
    void assignArmiesToPlayersManualCorrectTest() {
        Player l_currentPlayer = d_playersList.get(0);
        Player l_testPlayer = new Player(l_currentPlayer);

        List<Player> l_testPlayerList = new ArrayList<>();
        l_testPlayerList.add(l_testPlayer);

        int l_countryCount = l_testPlayerList.get(0).getD_country().size() / 3;
        int l_assignedArmies = Math.max(3, l_countryCount);
        int l_continentBonus = 0;
        if (!l_testPlayerList.get(0).getD_continent().isEmpty()) {
            for (Continent l_continent : l_testPlayerList.get(0).getD_continent())
                l_continentBonus += l_continent.getD_continentArmyCount();
        }
        l_assignedArmies += l_continentBonus;
        l_testPlayerList.get(0).setD_armyNumber(l_assignedArmies);
        System.out.println(l_assignedArmies);

        PlayersGameplay.assignArmiesToPlayers((ArrayList<Player>) d_playersList);

        assertEquals(d_playersList.get(0).getD_armyNumber(), l_testPlayerList.get(0).getD_armyNumber());
    }

    /**
     * The method tests if assignment of armies is wrong.
     */
    @Test
    void assignArmiesToPlayersManualWrongTest() {
        Player l_currentPlayer = d_playersList.get(0);
        Player l_testPlayer = new Player(l_currentPlayer);

        List<Player> l_testPlayerList = new ArrayList<>();
        l_testPlayerList.add(l_testPlayer);

        int l_countryCount = l_testPlayerList.get(0).getD_country().size() / 3;
        int l_assignedArmies = Math.max(3, l_countryCount);
        int l_continentBonus = 0;
        if (!l_testPlayerList.get(0).getD_continent().isEmpty()) {
            for (Continent l_continent : l_testPlayerList.get(0).getD_continent())
                l_continentBonus += l_continent.getD_continentArmyCount();
        }
        l_assignedArmies += l_continentBonus;

        l_testPlayerList.get(0).setD_armyNumber(l_assignedArmies + 1);
        System.out.println(l_assignedArmies);

        PlayersGameplay.assignArmiesToPlayers((ArrayList<Player>) d_playersList);

        assertNotEquals(d_playersList.get(0).getD_armyNumber(), l_testPlayerList.get(0).getD_armyNumber());
    }
}