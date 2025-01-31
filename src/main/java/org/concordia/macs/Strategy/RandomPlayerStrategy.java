package org.concordia.macs.Strategy;

import java.util.ArrayList;
import java.util.Random;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.State.Play;
import org.concordia.macs.Strategy.PlayerStrategy;

/**
 * The RandomPlayerStrategy class represents a player strategy that prioritizes
 * decentralized actions without a specific target.
 */
public class RandomPlayerStrategy extends PlayerStrategy {
    public RandomPlayerStrategy(Player p_player, Connectivity p_connectivity) {
        super(p_player, p_connectivity);
    }

    /**
     * Override method to attack
     * 
     * @return null for a random player
     */
    @Override
    protected Country toAttack() {
        return null;
    }

    /**
     * Override method to get country on which attack happens
     * 
     * @return null for a random player
     */
    @Override
    protected Country toAttackFrom() {
        return null;
    }

    /**
     * Override method to get country from which attack happens
     * 
     * @return null for a random player
     */
    @Override
    protected Country toMoveFrom() {
        return null;
    }

    /**
     * Override method to get random countries from the player's country
     * 
     * @return random countries from the player's territories.
     */
    @Override
    protected Country toDefend() {
        ArrayList<Country> l_playerCountries = (ArrayList<Country>) d_player.getD_country();
        if (l_playerCountries.isEmpty())
            return null;
        Random random = new Random();
        int randomIndex = random.nextInt(l_playerCountries.size());
        return l_playerCountries.get(randomIndex);
    }

    /**
     * Overrides the method to implement a random player strategy during main
     * gameplay by creating order, generating and deploying random troops to
     * countries,
     * and randomly attacking neighboring enemy territories.
     * 
     * @return an order generated by the strategy.
     */
    @Override
    public Order createOrder() {
        Random random = new Random();
        Order order = new Order();
        String orderContent = "";
        if (toDefend() == null)
            return null;

        // Generate a deploy order for random country with random number of troops
        // during the Reinforcement phase
        if (GameEngine.getPhaseName().equals("Reinforcement")) {
            orderContent = "deploy";
            orderContent = orderContent + toDefend().getD_countryId() + " "
                    + random.nextInt(d_player.getD_armyNumber() + 1);
            order.setOrderContent(orderContent);
            System.out.println(orderContent);
            return order;
        }

        else if (GameEngine.getPhaseName().equals("Attack")) {
            // runs until valid command is not generated
            for (; ; ) {
                String[] commands = {"advance", "bomb", "blockade", "airlift", "negotiate"};
                orderContent = commands[random.nextInt(commands.length)];
                switch (orderContent) {
                    case "advance":
                        Country l_defendingCountry = toDefend();
                        ArrayList<Integer> l_neighbour = l_defendingCountry.getD_neighbours();

                        int randomIndex = random.nextInt(l_neighbour.size());
                        Country l_targetCountry = Country.getCountryFromId(d_connectivity.getD_countriesList(),
                                l_neighbour.get(randomIndex));

                        int randomTroops = random.nextInt(l_defendingCountry.getD_armyCount() + 1);
                        orderContent = orderContent + " " + l_defendingCountry.getD_countryName() + " "
                                + l_targetCountry.getD_countryName() + " " + randomTroops;
                        order.setOrderContent(orderContent);
                        System.out.println(orderContent);
                        return order;

                    case "bomb":
                        if (d_player.getCards().contains(orderContent)) {
                            for (; ; )// runs till it finds the neighboring enemy territory
                            {
                                Country l_Country = toDefend();
                                l_neighbour = l_Country.getD_neighbours();
                                randomIndex = random.nextInt(l_neighbour.size());
                                l_targetCountry = Country.getCountryFromId(d_connectivity.getD_countriesList(),
                                        l_neighbour.get(randomIndex));

                                if (!d_player.getD_country().contains(l_targetCountry)) {
                                    orderContent = orderContent + " " + l_targetCountry.getD_countryId();
                                    order.setOrderContent(orderContent);
                                    System.out.println(orderContent);
                                    return order;
                                }
                            }
                        } else
                            break;

                    case "airlift":
                        if (d_player.getCards().contains(orderContent)) {
                            Country l_fromCountry = toDefend();
                            Country l_toCountry = toDefend();

                            if (l_fromCountry.getD_countryId() != l_toCountry.getD_countryId())
                                break;
                            else
                                l_toCountry = toDefend();

                            randomTroops = random.nextInt(l_fromCountry.getD_armyCount() + 1);
                            orderContent = orderContent + " " + l_fromCountry.getD_countryId() + " " + l_toCountry.getD_countryId() + " "
                                    + randomTroops;
                            order.setOrderContent(orderContent);
                            System.out.println(orderContent);
                            return order;
                        } else
                            break;

                    case "blockade":
                        if (d_player.getCards().contains(orderContent)) {
                            orderContent = orderContent + " " + toDefend().getD_countryId();
                            order.setOrderContent(orderContent);
                            System.out.println(orderContent);
                            return order;
                        } else
                            break;

                    case "negotiate":
                        if (d_player.getCards().contains("diplomacy")) {
                            int l_playersSize = Play.l_playersArray.size();
                            randomIndex = random.nextInt(l_playersSize);
                            Player l_otherPlayer = Play.l_playersArray.get(randomIndex);
                            if (d_player.getD_playerId() != l_otherPlayer.getD_playerId()) {
                                orderContent = orderContent + " " + d_player.getD_playerId() + " " + l_otherPlayer.getD_playerId();
                                order.setOrderContent(orderContent);
                                System.out.println(orderContent);
                                return order;
                            }
                        } else
                            break;

                }
            }
        }
            System.out.println(orderContent);
            return null;
    }
}