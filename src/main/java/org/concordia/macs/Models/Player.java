package org.concordia.macs.Models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class with the details of the player
 *
 * @author Susmitha Mamula
 */

public class Player {
    private String d_playerName;
    private int d_playerId;
    private int d_armyNumber;

    private Order d_order;
    private HashMap<String, Country> d_country = new HashMap<>();
    private ArrayList<Order> d_playerOrder = new ArrayList<>();
    private HashMap<String, Continent> d_continent = new HashMap<>();

    /**
     *  issues the orders given by the player
     */
    public void issue_order(){
        this.d_playerOrder.add(d_order);
    }

    /**
     * @return once an order is issued and executed then it gets removed from the list
     */
    public Order next_order(){
        Order l_order = d_playerOrder.remove(0);
        return l_order;
    }

    /**
     * gets the name of the player
     * @return name of the player
     */
    public String getD_playerName() {

        return d_playerName;
    }

    /**
     * gets the Id of the player
     * @return Id of the player
     */
    public int getD_playerId() {

        return d_playerId;
    }

    /**
     * gets the orders given by the player
     * @return orders given by the player
     */
    public Order getD_order() {

        return d_order;
    }

    public HashMap<String, Country> getD_country() {

        return d_country;
    }

    /**
     * sets the player's name
     * @param p_playerName refers to the name of the player
     */
    public void setD_playerName(String p_playerName) {

        this.d_playerName = p_playerName;
    }

    /**
     * sets the player's Id
     * @param p_playerId refers to the Id of the player
     */
    public void setD_playerId(int p_playerId) {

        d_playerId = p_playerId;
    }


    /**
     * gets the army count
     * @return the army count
     */
    public int getD_armyNumber() {

        return d_armyNumber;
    }

    /**
     * sets the army count
     * @param d_armyNumber refers to the army count
     */
    public void setD_armyNumber(int d_armyNumber) {

        this.d_armyNumber = d_armyNumber;
    }

    /**
     * adds countries to the list of countries owned by the player
     * @param p_country refers to the list of countries
     */
    public void addCountry(Country p_country) {

        d_country.put(p_country.getD_countryName(), p_country);
    }

    /**
     * sets the orders given by the player
     * @param p_order refers to the order object
     */
    public void setD_order(Order p_order) {
        d_order = p_order;
        this.d_armyNumber = this.d_armyNumber - p_order.getD_armyNumber();
        System.out.println("Number of army has changed to "+ d_armyNumber);

    }

    public HashMap<String, Continent> getD_continent() {

        return d_continent;
    }

    /**
     * sets the player continent
     * @param d_continent refers to the list of continents
     */
    public void setD_continent(HashMap<String, Continent> d_continent) {

        this.d_continent = d_continent;
    }

    /**
     * This is a default constructor
     */

    public Player(){

    }

    /**
     *
     * @param l_player denotes the object of player class
     */

    public Player (Player l_player){
        this.d_playerName = l_player.getD_playerName();
        this.d_playerId = l_player.getD_playerId();
        this.d_country = new HashMap<>(l_player.getD_country());
        this.d_playerOrder= new ArrayList<>(l_player.getD_playerOrder());
        this.d_order = l_player.getD_order();
        this.d_armyNumber = l_player.getD_armyNumber();
        this.d_continent = new HashMap<>(l_player.getD_continent());
    }

    /**
     *
     * @param p_playerName refers to the name of the player
     * @param p_playerId refers to the ID
     * @param p_playerCountry refers to the country owned by the player
     * @param p_playerOrder refers to the list of orders of player
     */

    public Player(String p_playerName, int p_playerId, HashMap<String, Country> p_playerCountry, ArrayList<Order> p_playerOrder){
        super();
        this.d_playerName = p_playerName;
        this.d_playerId = p_playerId;
        this.d_order = p_playerOrder;
        this.d_country = p_playerCountry;
    }

    /**
     * Receives the player orders
     * @return player orders
     */

    public ArrayList<Order> getD_playerOrder(){
        return d_playerOrder;
    }



}
