package org.concordia.macs.Models;

package Models;

import java.util.ArrayList;

public class Player {

    // Player properties
    private String d_playerName;
    private int d_playerId;
    private ArrayList<Country> d_playerCountry;
    private ArrayList<Order> d_playerOrder;
    private int d_armyCount;
    private ArrayList<Continent> d_playerContinent;
    private ArrayList<String> d_cards;
    private ArrayList<Integer> d_diplomacyWith;
    private static int d_objCount = 1;

    // Constructor to initialize a new player
    public Player() {
        this.d_playerId = d_objCount++;
        this.d_playerCountry = new ArrayList<>();
        this.d_playerOrder = new ArrayList<>();
        this.d_playerContinent = new ArrayList<>();
        this.d_cards = new ArrayList<>();
        this.d_diplomacyWith = new ArrayList<>();
    }

    // Copy constructor
    public Player(Player d_player) {
        this.d_playerName = d_player.getD_playerName();
        this.d_playerId = d_player.getD_playerId();
        this.d_playerCountry = new ArrayList<>(d_player.getD_playerCountry());
        this.d_playerOrder = new ArrayList<>(d_player.getD_playerOrder());
        this.d_armyCount = d_player.getD_armyCount();
        this.d_playerContinent = new ArrayList<>(d_player.getD_playerContinent());
        this.d_cards = new ArrayList<>(d_player.getCards());
        this.d_diplomacyWith = new ArrayList<>(d_player.getDiplomacyWith());
    }

    // Issue an order
    public void issue_order(Order d_order) {
        this.d_playerOrder.add(d_order);
    }

    // Get the next order
    public Order next_order() {
        return d_playerOrder.remove(0);
    }

    // Getters and setters for player properties
    public String getD_playerName() {
        return d_playerName;
    }

    public void setD_playerName(String d_playerName) {
        this.d_playerName = d_playerName;
    }

    public int getD_playerId() {
        return d_playerId;
    }

    public ArrayList<Order> getD_playerOrder() {
        return d_playerOrder;
    }

    public void setD_playerOrder(ArrayList<Order> d_playerOrder) {
        this.d_playerOrder = d_playerOrder;
    }

    public ArrayList<Country> getD_playerCountry() {
        return d_playerCountry;
    }

    public void setD_playerCountry(ArrayList<Country> d_playerCountry) {
        this.d_playerCountry = d_playerCountry;
    }

    public void addCountry(Country d_country) {
        this.d_playerCountry.add(d_country);
    }

    public ArrayList<Continent> getD_playerContinent() {
        return d_playerContinent;
    }

    public void setD_playerContinent(ArrayList<Continent> d_playerContinent) {
        this.d_playerContinent = d_playerContinent;
    }

    public int getD_armyCount() {
        return d_armyCount;
    }

    public void setD_armyCount(int d_armyCount) {
        this.d_armyCount = d_armyCount;
    }

    public ArrayList<String> getCards() {
        return d_cards;
    }

    public void setCards(ArrayList<String> d_cards) {
        this.d_cards = d_cards;
    }

    public void removeCard(String d_card) {
        this.d_cards.remove(d_card);
    }

    public void addCard(String d_card) {
        this.d_cards.add(d_card);
    }

    public ArrayList<Integer> getDiplomacyWith() {
        return d_diplomacyWith;
    }

    public void setDiplomacyWith(ArrayList<Integer> d_diplomacyWith) {
        this.d_diplomacyWith = d_diplomacyWith;
    }

    public void addDiplomacyWith(Integer d_toPlayerID) {
        this.d_diplomacyWith.add(d_toPlayerID);
    }

    public void clearDiplomacyWith() {
        this.d_diplomacyWith.clear();
    }

    public void removeAllCountryAndContinentAssigned() {
        this.d_playerCountry.clear();
        this.d_playerContinent.clear();
    }

    public void removeCountry(Country d_country) {
        this.d_playerCountry.remove(d_country);
    }
}