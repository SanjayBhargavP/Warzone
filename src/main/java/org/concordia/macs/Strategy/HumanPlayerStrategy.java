package org.concordia.macs.Strategy;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Models.Order;

import java.util.Scanner;

/**
 * The HumanPlayerStrategy class represents a player strategy that involves user interaction for decision-making.
 */

public class HumanPlayerStrategy extends PlayerStrategy{

    /**
     * Initializes a new instance of the HumanPlayerStrategy class.
     * @param p_player The player object.
     * @param p_connectivity The connectivity object.
     */
    public HumanPlayerStrategy(Player p_player, Connectivity p_connectivity){
        super(p_player, p_connectivity);
        // TODO Auto-generated constructor stub
    }

    /**
     * Overrides the method to create orders based on human player interaction during gameplay.
     * @return The created order.
     */
    @Override
    public Order createOrder() {
        String l_givenOrder="";
        Order l_order=new Order();
        Scanner l_sc = new Scanner(System.in);
        l_givenOrder=l_sc.nextLine();
        l_order.setOrderContent(l_givenOrder);
        return l_order;
    }

    /**
     * Overrides the method for attacking, returns null for human player.
     * @return Null since human player does not perform attacks.
     */
    @Override
    protected Country toAttack() {
        return null;
    }

    /**
     * Overrides the method to get the country from which the attack originates, returns null for human player.
     * @return Null since human player does not perform attacks.
     */
    @Override
    protected Country toAttackFrom() {
        return null;
    }

    /**
     * Overrides the method to get the country from which to move armies, returns null for human player.
     * @return Null since human player does not move armies.
     */
    @Override
    protected Country toMoveFrom() {
        return null;
    }

    /**
     * Overrides the method to get the country to defend, returns null for human player.
     * @return Null since human player does not perform defensive actions.
     */
    @Override
    protected Country toDefend() {
        return null;
    }


}

