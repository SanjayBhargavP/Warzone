package org.concordia.macs.Strategy;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;

/**
 * @author Susmitha Mamula
 * This class represents an abstract class to apply player strategy.
 */
public abstract class PlayerStrategy {

    Connectivity d_connectivity;
    Player d_player;

    PlayerStrategy(Player p_player, Connectivity p_connectivity){
        d_player = p_player;
        d_connectivity = p_connectivity;
    }

    /**
     * Represents abstract class to create order
     */
    public abstract Order createOrder();

    /**
     * Represents abstract class to attack to a country.
     */
    protected abstract Country toAttack();

    /**
     * Represents abstract class to receive country to which attack should take place.
     */
    protected abstract Country toAttackFrom();

    /**
     * Represents abstract class to move armies from given country
     */
    protected abstract Country toMoveFrom();

    /**
     * Represents abstract class to defend the country
     */
    protected abstract Country toDefend();

}
