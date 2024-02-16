package org.concordia.macs.Models;

import Utilities.ColorCoding;

/**
 * This class defines orders, including their origin and destination countries and the count of armies that is transferred
 * @author Susmitha Mamula
 */
public class Order {

    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armyCount;

    /**
     *Retrieves the country from which the order originates
     * @return the name of the origin country
     */

    public Country getD_sourceCountry(){
        return d_sourceCountry;
    }

    /**
     * Sets the country from which the order originates
     * @param p_sourceCountry The name of the origin country for the order
     */
    public void setD_sourceCountry(Country p_sourceCountry) {
        this.d_sourceCountry = p_sourceCountry;
    }

    /**
     * Retrieves the country to which the order is directed
     * @return The name of the destination country
     */
    public Country getD_targetCountry(){
        return d_targetCountry;
    }

    /**
     * Sets the country to which the order is directed
     * @param p_targetCountry The name of the destination country for the order
     */
    public void setD_targetCountry(Country p_targetCountry){
        this.d_targetCountry = p_targetCountry;
    }

    /**
     * Retrieves the number of armies to be deployed
     * @return The number of armies
     */
    public int getD_armyNumber(){
        return d_armyCount;
    }

    /**
     * Sets the number of armies that needs to be deployed
     * @param p_armyCount The number of armies to be deployed
     */
    public void setD_armyCount(int p_armyCount){
        this.d_armyCount = p_armyCount;
    }

    public void execute(Player p_gamePlayer, Order p_gameOrder){
        while(p_gamePlayer.getD_armyNumber()!=0){
            p_gamePlayer.setD_armyNumber(p_gamePlayer.getD_armyNumber()-p_gameOrder.d_armyCount);
            for(int i=0;i<p_gamePlayer.getD_country().size();i++){
                if(p_gamePlayer.getD_country().get(i).getD_countryId()==p_gameOrder.getD_sourceCountry().getD_countryId()){
                    p_gamePlayer.getD_country().get(i).setD_armyCount(p_gameOrder.d_armyCount);
                    System.out.println(ColorCoding.green+ "Player " + p_gamePlayer.getD_playerName()+": "+ p_gamePlayer.getD_country().get(i).getD_CountryName()+" is assigned with"+p_gameOrder.d_armyCount+ColorCoding.blank);
                }
            }
        }
    }

}
