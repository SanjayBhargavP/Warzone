package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * class with the details of the player
 *
 * @author Susmitha Mamula
 */

public class Player {
    private String d_playerName;
    private int d_playerId;
    private int d_army;
    private List<Country> d_country = new ArrayList<Country>();
    private List<Order> d_order = new ArrayList<>();
    private List<Continent> d_continent = new ArrayList<>();


    public String getD_playerName() {
        return d_playerName;
    }

    public void setD_playerName(String d_playerName) {
        this.d_playerName = d_playerName;
    }


    public int getD_playerId() {
        return d_playerId;
    }

    public void setD_playerId(int d_playerId) {
        this.d_playerId = d_playerId;
    }



    public int getD_army() {
        return d_army;
    }

    public void setD_army(int d_army) {
        this.d_army = d_army;
    }



    public List<Country> getD_country() {
        return d_country;
    }

    public void setD_country(List<Country> p_country) {
        d_country = p_country;
    }



    public List<Order> getD_order() {
        return d_order;
    }

    public void setD_order(List<Order> d_order) {
        this.d_order = d_order;
    }



    public List<Continent> getD_continent() {
        return d_continent;
    }

    public void setD_continent(List<Continent> d_continent) {
        this.d_continent = d_continent;
    }



    /**
     * This is a default constructor
     */

    public Player(){

    }

    public Player (Player l_player){
        this.d_playerName = l_player.getD_playerName();
        this.d_playerId = l_player.getD_playerId();
        this.d_country = l_player.getD_country();
        this.d_order = l_player.getD_order();
        this.d_army = l_player.getD_army();
        this.d_continent = l_player.getD_continent();
    }

    /**
     *
     * @param p_playerName refers to the name of the player
     * @param p_playerId refers to the ID
     * @param p_playerCountry refers to the country owned by the player
     * @param p_playerOrder refers to the list of orders of player
     */



    public Player(String p_playerName, int p_playerId, List<Country> p_playerCountry, List<Order> p_playerOrder){
        super();
        this.d_playerName = p_playerName;
        this.d_playerId = p_playerId;
        this.d_order = p_playerOrder;
        this.d_country =  p_playerCountry;
    }


    public List<String> getCountryNames(){
        List<String> countryNames = new ArrayList<>();
        for(Country country: d_country){
            countryNames.add(country.getD_country());
        }
        return countryNames;
    }

    public List<String> getContinentNames(){
        List<String> continentNames = new ArrayList<>();
        for(Continent continent: d_continent){
            continentNames.add(continent.getD_continent());
        }
        return continentNames;
    }

}
