package org.concordia.macs.Strategy;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.View.ShowMap;
import org.concordia.macs.State.Play;
import java.util.ArrayList;

/**
 * @author Susmitha Mamula
 * This class represents the player Strategy focused on safeguarding vulnerable countries
 */
public class BenevolentPlayerStrategy extends PlayerStrategy {

    public BenevolentPlayerStrategy(Player p_player, Connectivity p_connectivity){
        super(p_player, p_connectivity);
    }

    /**
     * Override method determining the country to attack
     * @return null for a benevolent player
     */

    @Override
    protected Country toAttack(){
        return null;
    }

    /**
     * Override method determining the country from which to launch an attack
     * @return null for a benevolent player
     */
    @Override
    protected Country toAttackFrom(){
        return null;
    }

    /**
     * Override method determining the country from which to move armies
     * @return null for a benevolent player
     */
    @Override
    protected  Country toMoveFrom(){
        return null;
    }

    /**
     * Override method determining the country to defend
     * @return the country with  the minimum number of troops
     */
    @Override
    protected Country toDefend()
    {
        if(d_player.getD_country().isEmpty())
            return null;
        Country output = d_player.getD_country().get(0);
        int minimum = output.getD_armyCount();
        for(Country c:d_player.getD_country())
        {
            if(c.getD_armyCount() < minimum){
                minimum = c.getD_armyCount();
                output = c;
            }
        }
        return output;
    }

    /**
     * Override method applying the benevolent player strategy during gameplay by creating orders.
     * @return The created order based on the strategy.
     */
    @Override
    public Order createOrder()
    {
        Order o =new Order();
        String str;
        if(toDefend()==null)
            return null;
        if(GameEngine.getPhaseName().equals("Reinforcement"))
        {
            str="deploy ";
            str = str+ toDefend().getD_countryId()+" " + d_player.getD_armyNumber();
            System.out.println(str);
            o.setOrderContent(str);
            return o;
        }
        else if(GameEngine.getPhaseName().equals("Attack") || GameEngine.getPhaseName().equals("Fortify") )
        {
            str="advance ";
            Country[] c = level(toDefend().getD_countryId());

            if(c!=null)
            {
                str = str+ c[0].getD_countryName() +" "+ c[1].getD_countryName()+" "+c[0].getD_armyCount();
                System.out.println("advance "+c[0].getD_countryName()+" "+c[1].getD_countryName()+" "+c[0].getD_armyCount());
                o.setOrderContent(str);
                ShowMap.showMap(d_connectivity.getD_continentsList(), d_connectivity.getD_countriesList(), Play.getL_playersArray());
                return o;
            }
            else
                return null;

        }



        return o;

    }

    /**
     * Method to retrieve neighboring countries of the country with the minimum number of troops.
     * @param countryID The ID of the country with the minimum number of troops.
     * @return An array containing two countries, the 'from' country and the 'to' country for potential advance.
     */
    public Country[] level(int countryID)
    {
        Country[] c = new Country[2];
        ArrayList<Integer> neighbourCountryID = d_connectivity.getCountryFromCountryId(countryID).getD_neighbours();

        for(int i: neighbourCountryID)
        {
            if(d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(i)))
            {
                if(d_connectivity.getCountryFromCountryId(i).getD_armyCount()>0)
                {
                    c[0] = d_connectivity.getCountryFromCountryId(i); //from Country
                    c[1] = d_connectivity.getCountryFromCountryId(countryID); // to country
                    return c;
                }
            }

        }
        //2nd Neighbour
        for(int i:neighbourCountryID)
        {

            ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
            for(int j: neighbourCountryID2)
            {
                if(d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(j)))
                {
                    if(d_connectivity.getCountryFromCountryId(j).getD_armyCount()>0)
                    {
                        c[0] = d_connectivity.getCountryFromCountryId(j); //from country
                        c[1] = d_connectivity.getCountryFromCountryId(i);// to country
                        return c;
                    }
                }

            }
        }

        //3rd Neighbour
        for(int i:neighbourCountryID)
        {

            ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
            for(int j: neighbourCountryID2)
            {
                ArrayList<Integer> neighbourCountryID3 = d_connectivity.getCountryFromCountryId(j).getD_neighbours();
                for(int k:neighbourCountryID3)
                {
                    if(d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(k)))
                    {
                        if(d_connectivity.getCountryFromCountryId(k).getD_armyCount()>0)
                        {
                            c[0] = d_connectivity.getCountryFromCountryId(k); //from country
                            c[1] = d_connectivity.getCountryFromCountryId(i);// to country
                            return c;
                        }
                    }
                }

            }
        }
        return null;
    }
}
