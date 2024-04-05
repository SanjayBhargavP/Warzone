package org.concordia.macs.Models;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Strategy.PlayerStrategy;
import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.PlayersGameplay;

import java.util.ArrayList;
import java.util.List;

/**
 * class with the details of the player
 *
 * @author Susmitha Mamula,Blesslin Jeba Shiny
 */

public class Player {
    private String d_playerName;
    private int d_playerId;
    private int d_armyNumber;

    private Order d_order;
    private List<Country> d_country = new ArrayList<>();
    private List<Order> d_playerOrder = new ArrayList<>();
    private  List<Continent> d_continent = new ArrayList<>();

    private List<String> d_cards = new ArrayList<>();

    private ArrayList<Integer> d_diplomacyWith = new ArrayList<>();
    private static int d_objCount = 1;
    private PlayerStrategy strategy;

    public Player(){

        this.setD_playerId(d_objCount);
        d_objCount++;
    }


    /**
     * Gets the player strategy
     * @return player strategy
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the player strategy
     * @param strategy strategy
     */
    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
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

    /**
     * gets the list of countries with the player
     * @return the list of countries with the player
     */
    public List<Country> getD_country() {

        return d_country;
    }

    /**
     * sets the list of countries with the player
     */
    public void setD_country(List<Country> d_country) {
        this.d_country = d_country;
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

        d_country.add(p_country);
    }

    /**
     * sets the orders given by the player
     * @param p_order refers to the order object
     */
    public void setD_order(Order p_order) {
        LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
        d_order = p_order;
        this.d_armyNumber = this.d_armyNumber - p_order.getD_armyNumber();
        d_logEntryBuffer.log("Army count changed to "+ d_armyNumber);
        System.out.println("Number of army has changed to "+ d_armyNumber);

    }

    public List<Continent> getD_continent() {

        return d_continent;
    }

    /**
     * sets the player continent
     * @param d_continent refers to the list of continents
     */
    public void setD_continent(ArrayList<Continent> d_continent) {

        this.d_continent = d_continent;
    }


    /**
     *
     * @param l_player denotes the object of player class
     */

    public Player (Player l_player){
        this.d_playerName = l_player.getD_playerName();
        this.d_playerId = l_player.getD_playerId();
        this.d_country = l_player.getD_country();
        this.d_playerOrder= new ArrayList<>(l_player.getD_playerOrder());
        this.d_order = l_player.getD_order();
        this.d_objCount = l_player.getD_armyNumber();
        this.d_continent = l_player.getD_continent();
    }

    /**
     *
     * @param p_playerName refers to the name of the player
     * @param p_playerId refers to the ID
     * @param p_playerCountry refers to the country owned by the player
     * @param p_playerOrder refers to the list of orders of player
     */

    public Player(String p_playerName, int p_playerId, List<Country> p_playerCountry, ArrayList<Order> p_playerOrder){
        super();
        this.d_playerName = p_playerName;
        this.d_playerId = p_playerId;
        this.d_playerOrder = p_playerOrder;
        this.d_country = p_playerCountry;
    }

    /**
     * Receives the player orders
     * @return player orders
     */

    public List<Order> getD_playerOrder(){
        return d_playerOrder;
    }
    /**
     * This function returns the cards of the given player
     * @return list of cards
     */
    public ArrayList<String> getCards() {
        return (ArrayList<String>) d_cards;
    }
    /**
     * This function sets the cards owned by the players
     * @param cards cards owned by player
     */

    public void setCards(ArrayList<String> cards)
    {
        d_cards = cards;
    }
    /**
     * This function removes the card from the list of cards owned by the player
     * @param card Card to be removed from the array list
     */
    public void removeCard(String card)
    {
        d_cards.remove(card);
    }
    /**
     * This function adds the card to the list of cards
     * @param card refers to the card to be added to list
     */
    public void addCard(String card)
    {
        d_cards.add(card);
    }
    /**
     * This function returns the countries the player set the diplomacy with
     * @return refers to the second country the player set the diplomacy with
     */
    public ArrayList<Integer> getDiplomacyWith()
    {
        return d_diplomacyWith;
    }
    /**
     * This function sets the countries the player sets the diplomacy with
     * @param diplomacyWith refers to the second country the player sets the diplomacy with
     */
    public void setDiplomacyWith(ArrayList<Integer> diplomacyWith)
    {
        d_diplomacyWith = diplomacyWith;
    }
    /**
     * This function adds the country to the diplomacy list
     * @param l_toPlayerID represents the playerId to perform diplomacy with
     */
    public void addDiplomacyWith(Integer l_toPlayerID)
    {
        d_diplomacyWith.add(l_toPlayerID);
    }
    /**
     * This function clears the diplomacy list of the player
     */
    public void clearDiplomacyWith()
    {
        d_diplomacyWith.clear();
    }
    /**
     * This function removes all the assigned continents and countries to the player
     */
    public void removeAllCountryAndContinentAssigned()
    {
        d_country.clear();
        d_continent.clear();
    }

    /**
     * This function remove the country from the player country list
     * @param c Refers to the country
     */
    public void removeCountry(Country c)
    {
        d_country.remove(c);
    }

    /**
     * Verifies the orders that are issued by the player.
     *
     */
    public boolean verifyOrder(Order p_order, Player p_player) {
        String l_userOrder = p_order.getOrderContent();
        boolean l_validUserCommand =false;
        if(l_userOrder.equalsIgnoreCase("exit"))
        {
            System.out.println("Thank you for Playing the Game");
            System.exit(0);
        }
        String[] l_tempOrderListArray=l_userOrder.split(" ");
        for(int j=0;j<p_player.getD_country().size();j++)
        {
            if(Integer.parseInt(l_tempOrderListArray[1])==(p_player.getD_country().get(j).getD_countryId()))
            {
                p_order.setD_sourceCountry(p_player.getD_country().get(j));
                l_validUserCommand= true;
            }
        }
        if(l_validUserCommand)
        {
            if(PlayersGameplay.checkArmyAvailable(Integer.parseInt(l_tempOrderListArray[2]),p_player))
            {
                p_order.setD_armyCount(Integer.parseInt(l_tempOrderListArray[2]));
                p_player.setD_order(p_order);
            }
            else
            {
                l_validUserCommand= false;
                System.out.println(ColorCoding.ANSI_RED+"Error: Please enter valid number of troops"+ColorCoding.ANSI_RESET);
            }
        }
        else
        {
            l_validUserCommand= false;
            System.out.println(ColorCoding.ANSI_RED+"INVALID Command as player "+p_player.getD_playerName()+" doesn't control country with countryID "+l_tempOrderListArray[1]+ColorCoding.ANSI_RESET);
        }
        return l_validUserCommand;
    }

    /**
     * Orders that are issued by the player.
     *
     */
    public boolean issue_order(){

        Order l_order;
        l_order = strategy.createOrder();
        if(l_order !=null)
        {
            if(GameEngine.getPhaseName().equals("Reinforcement"))
            {
                while(!verifyOrder(l_order, this))
                {
                    System.out.println("INVALID ORDER! Please enter the order again");
                    l_order = strategy.createOrder();
                }
            }
            this.d_playerOrder.add(l_order);
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Once the order is executed it gets removed from the list.
     * @return returns the order of the player
     */

    public Order next_order(){
        if(d_playerOrder.size() == 0)
        {
            System.out.println("d_playerOrder = "+d_playerOrder.size());
            return null;
        }
        else
        {
            Order l_order =d_playerOrder.remove(0);
            return l_order;
        }

    }


}
