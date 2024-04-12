package org.concordia.macs.Utilities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Player;
import org.concordia.macs.State.Play;

/**
 * The class PlayerGameplay tests all the player perspective.
 *
 */

public class PlayersGamePlayTest {

    private ArrayList<Player> d_playersArray = new ArrayList<Player>();
    //Play.getL_playersArray();
    private Connectivity d_connectivity=new Connectivity();

    /**
     *
     * This method is used to test addition of players if not there.
     *
     */

    @BeforeEach
    public void addPlayersIfNotThereTest()
    {
        if(d_playersArray.size() == 0)
        {
            LoadMap.loadMap(d_connectivity, "VeryBasic");

            Player l_player1 = new Player();
            l_player1.setD_playerName("player1");

            Player l_player2 = new Player();
            l_player2.setD_playerName("player2");

            d_playersArray.add(l_player1);
            d_playersArray.add(l_player2);


            //PlayersGameplay.assigncountries(d_playersArray, d_connectivity.getD_countriesList(), d_connectivity.getD_continentsList());

            //PlayersGameplay.assignArmiesToPlayers(d_playersArray);
        }

    }
    /**
     * clears the neutral country list
     */

    @AfterEach
    public void clean()
    {
        PlayersGameplay.clearNutrealCountry();
    }

    /**
     *
     * The method tests if countries are assigned without players.
     *
     */

    @Test
    void assigncountriesWithoutPlayersTest()
    {


        d_playersArray.clear();
        assertEquals(1, PlayersGameplay.assigncountries(d_playersArray, d_connectivity.getD_countriesList(),
                d_connectivity.getD_continentsList()));
    }

    /**
     *
     * The method tests if players are not assigned with countries.
     *
     */

    @Test
    void assigncountriesWithplayersWithoutCountriesTest()
    {

        Player l_player = new Player();
        l_player.setD_playerName("player2");
        d_playersArray.add(l_player);

        ArrayList<Country> l_countryList = new ArrayList<Country>();

        assertEquals(1,
                PlayersGameplay.assigncountries(d_playersArray, l_countryList, d_connectivity.getD_continentsList()));

    }

    /**
     *
     * The method tests if countries are more than the number of players.
     *
     */

    @Test
    void assigncountriesWithPlayersAndWithLessCountriesTest()
    {

        Player l_player = new Player();
        l_player.setD_playerName("player2");
        d_playersArray.add(l_player);
        Country India = new Country();

        ArrayList<Country> l_countryList = new ArrayList<Country>();
        l_countryList.add(India);

    }

    /**
     *
     * The method tests if armies are available to attack or not.
     *
     */

    @Test
    void checkArmyAvailableSendMoreTroopsTest()
    {

        boolean l_truthValue = PlayersGameplay.checkArmyAvailable((d_playersArray.get(0).getD_armyNumber()+1), d_playersArray.get(0));
        assertEquals(false, l_truthValue);

    }

    /**
     *
     * The method tests if assignment of armies is correct.  
     *
     */

    @Test
    void assignArmiesToPlayersManualCorrectTest()
    {

        Player l_player= d_playersArray.get(0);
        Player l_playerTest = new Player(l_player);

        ArrayList<Player> l_playerArrayTest = new ArrayList<Player>();
        l_playerArrayTest.add(l_playerTest);

        int l_countryListSize=l_playerArrayTest.get(0).getD_country().size()/3;
        int l_armyCount=Math.max(3, l_countryListSize);
        int l_tempContinentCount=0;
        if(l_playerArrayTest.get(0).getD_continent().size()!=0)
        {
            for(int j=0;j<l_playerArrayTest.get(0).getD_continent().size();j++)
                l_tempContinentCount=l_tempContinentCount+ l_playerArrayTest.get(0).getD_continent().get(j).getD_continentArmyBonus();
        }
        l_armyCount+=l_tempContinentCount;
        l_playerArrayTest.get(0).setD_armyNumber(l_armyCount);
        System.out.println(l_armyCount);

        PlayersGameplay.assignArmiesToPlayers(d_playersArray);

        assertEquals(d_playersArray.get(0).getD_armyNumber(), l_playerArrayTest.get(0).getD_armyNumber());
    }

    /**
     *
     * The method tests if assignment of armies is wrong.  
     *
     */

    @Test
    void assignArmiesToPlayersManualWrongTest()
    {

        Player l_player= d_playersArray.get(0);
        Player l_playerTest = new Player(l_player);


        ArrayList<Player> l_playerArrayTest = new ArrayList<Player>();
        l_playerArrayTest.add(l_playerTest);

        int l_countryListSize=l_playerArrayTest.get(0).getD_country().size()/3;
        int l_armyCount=Math.max(3, l_countryListSize);
        int l_tempContinentCount=0;
        if(l_playerArrayTest.get(0).getD_continent().size()!=0)
        {
            for(int j=0;j<l_playerArrayTest.get(0).getD_continent().size();j++)
                l_tempContinentCount=l_tempContinentCount+ l_playerArrayTest.get(0).getD_continent().get(j).getD_continentArmyBonus();
        }
        l_armyCount+=l_tempContinentCount;

        l_playerArrayTest.get(0).setD_armyNumber(l_armyCount+1);
        System.out.println(l_armyCount);

        PlayersGameplay.assignArmiesToPlayers(d_playersArray);

        assertNotEquals(d_playersArray.get(0).getD_armyNumber(), l_playerArrayTest.get(0).getD_armyNumber());
    }


    /**
     *
     * The method will Advance from country that doesn't belong to players and test  
     *
     */
    @Test
    void advanceFromCountryNotBelongToPlayerTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);

        PlayersGameplay.addNutrealCountry(country1);
        PlayersGameplay.addNutrealCountry(country2);
        country1.setD_armyCount(10);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country2, country1, 4, continentList,d_connectivity,0);
        assertEquals(result, 1);

    }

    /**
     *
     * The method will Advance from country that belong to players, to country doesn't belong to player.That also means attack on neutral country and test  
     *
     */
    @Test
    void advanceToCountryNotBelongToPlayerTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);

        country1.setD_armyCount(10);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        PlayersGameplay.addNutrealCountry(country2);

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 4, continentList,d_connectivity,0);
        assertEquals(result, 0);

    }

    /**
     *
     * The method will Advance with insufficient troops and test  
     *
     */
    @Test
    void advanceWithInsufficientTroops()
    {

        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);
        country1.setD_armyCount(2);

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 4, continentList,d_connectivity,0);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will Advance negative number of troops and test  
     *
     */
    @Test
    void advanceNegativeNumberTroopsTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);

        country1.setD_armyCount(2);

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, -2, continentList,d_connectivity,0);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will Advance troops to non neighbour country and test  
     *
     */
    @Test
    void advanceToNonNeighbourTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);

        country1.setD_armyCount(5);

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 2, continentList,d_connectivity,0);
        assertEquals(result, 1);

    }

    /**
     *
     * The method will do correct advance and test  
     *
     */
    @Test
    void advanceCorrectTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);

        country1.setD_armyCount(5);

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 2, continentList,d_connectivity,0);
        assertEquals(result, 0);
        assertEquals(country1.getD_armyCount(), 3);
        assertEquals(country2.getD_armyCount(),2);
    }

    /**
     *
     * The method will attack form country that doesn't belong to player and test  
     *
     */
    @Test
    void attackFromCountryNotBelongToPlayerTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        PlayersGameplay.addNutrealCountry(country1);
        PlayersGameplay.addNutrealCountry(country2);
        country1.setD_armyCount(10);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 2, continentList,d_connectivity);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will attack to county belonging to own player and test  
     *
     */
    @Test
    void attackToOwnCountryTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);
        country1.setD_armyCount(10);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 2, continentList,d_connectivity);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will attack the country with more troops than available and test  
     *
     */
    @Test
    void attackWithMoreTroopsTest()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country1.setD_armyCount(3);
        country2.setD_armyCount(1);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 10, continentList,d_connectivity);
        assertEquals(result, 1);
    }


    /**
     *
     * The method will attack the country with negative number of troops and test  
     *
     */
    @Test
    void attackWithNegativeNumberOfTroopsTest()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country1.setD_armyCount(3);
        country2.setD_armyCount(1);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, -2, continentList,d_connectivity);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will attack the non neighbouring country and test  
     *
     */
    @Test
    void attackToNonNeighbourTest()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country1.setD_armyCount(4);
        country2.setD_armyCount(1);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 2, continentList,d_connectivity);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will do successful attack and test  
     *
     */
    @Test
    void attackSuccessfulTest()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        Country country3 = d_connectivity.getD_countriesList().get(2);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player1.addCountry(country3);
        l_player2.addCountry(country2);

        country1.setD_armyCount(4);
        country2.setD_armyCount(0);

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 3, continentList,d_connectivity);
        assertEquals(result, 0);
        assertEquals(l_player1.getCards().size(),1);
        assertEquals(l_player1.getD_country().size(),3);
        assertEquals(l_player1.getD_continent().size(), 1);
        assertEquals(l_player2.getD_country().size(),0);
    }

    /**
     *
     * The method will attack on country that is not in map and test  
     *
     */
    @Test
    void attackCountryNotInMap()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = new Country();
        country2.setD_countryName("aaa");
        l_player1.addCountry(country1);
        country1.setD_armyCount(10);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 3, continentList,d_connectivity);
        assertEquals(result, 1);
    }

    /**
     *
     * The method will attack on neutral country and test  
     *
     */
    @Test
    void attackNutrealCountry()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);

        l_player1.addCountry(country1);
        country1.setD_armyCount(10);
        PlayersGameplay.addNutrealCountry(country2);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        int result = PlayersGameplay.attack(l_player1, d_playersArray, country1, country2, 3, continentList,d_connectivity);
        assertEquals(result, 0);
        assertEquals(l_player1.getCards().size(),1);
        assertEquals(l_player1.getD_country().size(),2);
        assertEquals(PlayersGameplay.getNutrealCountry().size(),0);
    }


    /**
     *
     * The method will attack from player who has used diplomacy to another player and test  
     *
     */
    @Test
    void attackWithDeplomacySamePlayer()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country1.setD_armyCount(4);
        country2.setD_armyCount(3);

        PlayersGameplay.negotiate(l_player1, d_playersArray, Integer.toString(l_player2.getD_playerId()));

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 3, continentList,d_connectivity,0);
        l_player1.clearDiplomacyWith();
        l_player2.clearDiplomacyWith();
        assertEquals(result, 1);
    }

    /**
     *
     * The method will attack from player who has not used diplomacy but used by other player and test  
     *
     */
    @Test
    void attackWithDeplomacyDifferentPlayerTest()
    {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);
        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country1.setD_armyCount(4);
        country2.setD_armyCount(3);
        PlayersGameplay.negotiate(l_player2, d_playersArray, Integer.toString(l_player1.getD_playerId()));

        int result = PlayersGameplay.advance(l_player1, d_playersArray, country1, country2, 3, continentList,d_connectivity,0);
        l_player1.clearDiplomacyWith();
        l_player2.clearDiplomacyWith();
        assertEquals(result, 1);
    }

    /**
     *
     * This method is used to test if we can use airlift without source country.
     *
     */
    @Test
    void airliftSourceCountryNotFoundTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);

        String countryName1 = country1.getD_countryName();
        String countryName2 = country2.getD_countryName();

        l_player1.addCountry(country2);

        boolean result = PlayersGameplay.AirliftDeploy(countryName1, countryName2, 2,l_player1);
        assertEquals(result, false);
    }

    /**
     *
     * This method is used to test if we can use airlift without target country.
     *
     */
    @Test
    void airliftTargetCountryNotFoundTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);

        String countryName1 = country1.getD_countryName();
        String countryName2 = country2.getD_countryName();

        l_player1.addCountry(country1);

        boolean result = PlayersGameplay.AirliftDeploy(countryName1, countryName2, 2,l_player1);
        assertEquals(result, false);
    }

    /**
     *
     * This method is used to test if we can use airlift without enough troops.
     *
     */
    @Test
    void airliftWithNotEnoughTroopsTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);

        String countryName1 = country1.getD_countryName();
        String countryName2 = country2.getD_countryName();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);

        country1.setD_armyCount(1);

        boolean result = PlayersGameplay.AirliftDeploy(countryName1, countryName2, 2,l_player1);
        assertEquals(result, false);
    }

    /**
     *
     * This method is used to test that airlift works successfully.
     *
     */
    @Test
    void airliftSuccessfulAttackTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(1);

        String countryName1 = country1.getD_countryName();
        String countryName2 = country2.getD_countryName();

        l_player1.addCountry(country1);
        l_player1.addCountry(country2);

        country1.setD_armyCount(4);
        country2.setD_armyCount(1);

        boolean result = PlayersGameplay.AirliftDeploy(countryName1, countryName2, 2,l_player1);
        System.out.println("result"+result);
        assertEquals(result, true);
        assertEquals(country1.getD_armyCount(),2);
        assertEquals(country2.getD_armyCount(),3);

    }

    /**
     *
     * This method is used to test if we can use blockade without given country.
     *
     */
    @Test
    void blockadeCountryNotFoundTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        String countryName1 = country1.getD_countryName();

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        boolean result = PlayersGameplay.Blockade(countryName1,l_player1,d_playersArray,continentList);
        assertEquals(result, false);
    }

    /**
     *
     * This method is used to test that blockade works successfully.
     *
     */
    @Test
    void blockadeSuccessfulTest()
    {
        Player l_player1 = d_playersArray.get(0);

        Country country1 = d_connectivity.getD_countriesList().get(0);
        String countryName1 = country1.getD_countryName();

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        country1.setD_armyCount(4);

        boolean result = PlayersGameplay.Blockade(countryName1,l_player1,d_playersArray,continentList);

        assertEquals(result, true);
        assertEquals(country1.getD_armyCount(),12);
        assertEquals(PlayersGameplay.l_neutralCountry.contains(country1),true);
    }

    /**
     *
     * This method is used to test that bomb works successfully.
     *
     */
    @Test
    void bombValidTargetTest() {
        Player l_player1= d_playersArray.get(0);
        Player l_player2= d_playersArray.get(1);
        Country country1= d_connectivity.getD_countriesList().get(0);
        Country country2= d_connectivity.getD_countriesList().get(1);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country2.setD_armyCount(4);

        int result=PlayersGameplay.bomb(l_player1, d_playersArray, country2, continentList);
        assertEquals(result, 2);
    }

    /**
     *
     * This method is used to test that bomb doesn't work when countries are not neighbours..
     *
     */
    @Test
    void bombInvalidTargetTest() {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);
        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country2.setD_armyCount(4);

        int result=PlayersGameplay.bomb(l_player1, d_playersArray, country2, continentList);
        assertEquals(result, 0);
    }

    /**
     *
     * This method is used to test that bomb makes the country neutral when there is no army present.
     *
     */
    @Test
    void bombNeutralizeCOuntryTest() {
        Player l_player1 = d_playersArray.get(0);
        Player l_player2 = d_playersArray.get(1);
        Country country1 = d_connectivity.getD_countriesList().get(0);
        Country country2 = d_connectivity.getD_countriesList().get(2);

        ArrayList<Continent> continentList = d_connectivity.getD_continentsList();

        l_player1.addCountry(country1);
        l_player2.addCountry(country2);

        country2.setD_armyCount(1);

        int result=PlayersGameplay.bomb(l_player1,d_playersArray, country2, continentList);
        assertEquals(result, 0);
        assertFalse(PlayersGameplay.showPlayersCountry(l_player2, 1).contains(country2));
    }

}
