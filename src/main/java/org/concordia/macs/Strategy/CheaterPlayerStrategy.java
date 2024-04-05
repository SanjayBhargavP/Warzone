package org.concordia.macs.Strategy;

import java.util.ArrayList;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.LoadMap;
import org.concordia.macs.Utilities.PlayersGameplay;
import org.concordia.macs.View.ShowMap;
import org.concordia.macs.State.Phase;
import org.concordia.macs.State.Play;

/**
 * The CheaterPlayerStrategy class defines a strategy where the issueOrder() method conquers all immediate 
 * neighboring enemy countries, and then doubles the number of armies on its countries that have enemy neighbors.
 */

public class CheaterPlayerStrategy extends PlayerStrategy
{
    /**
	 * This constructor refers to the cheater player strategy.
	 * @param p_player refers to the object of the player class
	 * @param p_connectivity refers to connectivity object
	 */
	public CheaterPlayerStrategy(Player p_player, Connectivity p_connectivity)
    {
		super(p_player, p_connectivity);
	}

     /**
     * Override method for attacking.
     * @return null for cheater player.
     */
    @Override
    protected Country toAttack()
    {
        return null;
    }

     /**
     * Override method to retrieve the country on which an attack is initiated.
     * @return null for cheater player.
     */
    @Override
    protected Country toAttackFrom()
    {
        return null;
    }

     /**
     * Override method to retrieve the country from which a move is initiated.
     * @return null for cheater player.
     */
    @Override
    protected Country toMoveFrom()
    {    
        return null;
    }

    /**
     * Override method for defending a country.
     * @return null for cheater player.
     */
    @Override
    protected Country toDefend()
    {    
        return null;
    }

    /**
     * Override method to execute the cheater player strategy during the main gameplay.
     * @return null.
     */
    @Override
    public Order createOrder()
    {
        //conquers all the immediate neighboring enemy countries
		ArrayList<Country> l_playerCountry = (ArrayList<Country>) d_player.getD_country();
		ArrayList<Country> l_countriesToAdd = new ArrayList<Country>();

         for (Country l_country : l_playerCountry)
         {
            for (int l_neighborID : l_country.getD_neighbours())
            {
                Country l_neighborCountry = l_country.getCountryFromId(d_connectivity.getD_countriesList(), l_neighborID);

                if (!PlayersGameplay.l_neutralCountry.contains(l_neighborCountry) && !l_countriesToAdd.contains(l_neighborCountry)) {
                    Player l_enemyPlayer = PlayersGameplay.findPlayerWithCountry(Play.getL_playersArray(), l_neighborCountry);

                    if (l_enemyPlayer != null && !l_playerCountry.contains(l_neighborCountry)) {
                        l_countriesToAdd.add(l_neighborCountry);
                        l_enemyPlayer.getD_country().remove(l_neighborCountry);
                   
                    }
                }
            }
        }

        //doubles the number of armies on its countries that have enemy neighbors
		for(Country l_country:l_countriesToAdd)
        {
            if (!d_player.getD_country().contains(l_country))
            {
                l_country.setD_armyCount(l_country.getD_armyCount() * 2);
            }
        }

        //add all countries to players
        for(Country l_country:l_countriesToAdd)
        {
            if(!d_player.getD_country().contains(l_country))
				d_player.getD_country().add(l_country);
        }

        ShowMap.showMap(d_connectivity.getD_continentsList(), d_connectivity.getD_countriesList(), Play.getL_playersArray());
		return null;
	}



    
}
