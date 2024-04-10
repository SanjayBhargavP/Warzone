package org.concordia.macs.Strategy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.MapEditor;
import org.concordia.macs.Utilities.LoadMap;
import org.concordia.macs.State.Attack;
import org.concordia.macs.State.Phase;
import org.concordia.macs.State.Reinforcement;

/**
 * This is benevolent player test
 */
public class BenevolentPlayerStrategyTest {


    static GameEngine gameEngine = new GameEngine();
    static Connectivity d_connectivity=new Connectivity();
    Phase gamePhase;

    @BeforeEach
    void BeforeEach()
    {


    }


    @Test
    void deployTest()
    {
        Player d_player1 = new Player();
        Country c1 = new Country();
        Country c2 = new Country();

        c1.setD_countryName("India");
        c2.setD_countryName("China");

        c1.setD_countryId(1);
        c2.setD_countryId(2);

        c1.setD_armyCount(5);
        c2.setD_armyCount(10);

        d_player1.addCountry(c1);
        d_player1.addCountry(c2);

        d_player1.setD_armyNumber(10);

        BenevolentPlayerStrategy B = new BenevolentPlayerStrategy(d_player1, d_connectivity);

        gameEngine.setPhase(new Reinforcement(gameEngine));
        Order o = B.createOrder();

        String str = o.getOrderContent();
        String[] strArr = str.split(" ");
        System.out.println(str);

        assertEquals(strArr[0], "deploy");
        assertEquals(strArr[1], "1");
        assertEquals(strArr[2], "10");
    }

    @Test
    void advanceTest()
    {
        LoadMap.loadMap(d_connectivity, "VeryBasic");

        Player d_player1 = new Player();
        Country c1 = d_connectivity.getD_countriesList().get(0); //canada
        Country c2 = d_connectivity.getCountryFromCountryId(c1.getD_neighbours().get(0)); //usa



        System.out.println(c1.getD_countryName()+" ???"+c2.getD_countryName());
        d_player1.addCountry(c1);
        d_player1.addCountry(c2);

        c1.setD_armyCount(5);//canada
        c2.setD_armyCount(12);//usa

        d_player1.setD_armyNumber(10);

        BenevolentPlayerStrategy B = new BenevolentPlayerStrategy(d_player1, d_connectivity);

        gameEngine.setPhase(new Attack(gameEngine));
        Order o = B.createOrder();
        String str = o.getOrderContent();
        String[] strArr = str.split(" ");
        System.out.println("___"+str);
        assertEquals(strArr[0], "advance");
        assertEquals(strArr[1], "USA");
        assertEquals(strArr[2], "Canada");
        assertEquals(strArr[3], "12");
    }

}
