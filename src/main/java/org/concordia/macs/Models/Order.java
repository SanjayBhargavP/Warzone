package org.concordia.macs.Models;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.PlayersGameplay;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.State.Play;
/**
 * This class defines orders, including their origin and destination countries and the count of armies that is transferred
 * @author Susmitha Mamula
 */
public class Order {

    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armyCount;

    private String orderContent;
    /**
     * @return returns the String of the order passed
     *
     */
    public String getOrderContent() {
        return orderContent;
    }

    /**
     *
     * @param orderContent sets the String passed by the user in orderContent.
     */
    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

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

    public void execute(Player p_player,Order p_order,Connectivity p_connectivity,int flag,int fortify_flag) {
        LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
        if(flag==0)
        {
            if(p_player.getD_armyNumber() !=0)
            {
                p_player.setD_armyNumber(p_player.getD_armyNumber()-p_order.d_armyCount);
                for(int i=0;i<p_player.getD_country().size();i++)
                {
                    if(p_player.getD_country().get(i).getD_countryId()==p_order.getD_sourceCountry().getD_countryId())
                    {
                        p_player.getD_country().get(i).setD_armyCount(p_order.d_armyCount+ p_player.getD_country().get(i).getD_armyCount());
                        d_logEntryBuffer.log("Player "+p_player.getD_playerName()+": " + p_player.getD_country().get(i).getD_countryName() + " has been assigned with " + p_order.d_armyCount);
                        System.out.println(ColorCoding.ANSI_RED+"Player "+p_player.getD_playerName()+": "+ p_player.getD_country().get(i).getD_countryName()+" has been assigned with "+p_order.d_armyCount+ColorCoding.ANSI_RESET);
                    }
                }
            }
        }
        else
        {
            String[] l_orderContent=p_order.getOrderContent().split(" ");
            Country l_getCountry=new Country();
            switch(l_orderContent[0])
            {
                case "advance":
                    d_logEntryBuffer.log(p_player.getD_playerName()+"is calling"+"Advance");
                    //System.out.println(p_player.getD_playerName()+"is calling"+"Advance");
                    PlayersGameplay.advance(p_player,Play.getL_playersArray(),l_getCountry.getCountryFromName(p_connectivity.getD_countriesList(), l_orderContent[1]) , l_getCountry.getCountryFromName(p_connectivity.getD_countriesList(), l_orderContent[2]), Integer.parseInt( l_orderContent[3]),p_connectivity.getD_continentsList(),p_connectivity,fortify_flag);
                    break;
                case "bomb":
                    d_logEntryBuffer.log(p_player.getD_playerName()+"is calling"+"bomb");
                    System.out.println(p_player.getD_playerName()+"is calling"+"bomb");
                    if(p_player.getCards().contains("bomb")) {
                        PlayersGameplay.bomb(p_player,Play.getL_playersArray(), Country.getCountryFromId(p_connectivity.getD_countriesList(), Integer.parseInt(l_orderContent[1])) , p_connectivity.getD_continentsList());
                        p_player.removeCard("bomb");
                    } else {
                        System.out.println(ColorCoding.ANSI_RED+p_player.getD_playerName()+" doesn't have bomb card "+ColorCoding.ANSI_RESET);
                    }
                    break;
                case "blockade":
                    d_logEntryBuffer.log(p_player.getD_playerName()+"is calling"+"blockade");
                    System.out.println(p_player.getD_playerName()+"is calling"+"blockade");
                    if(p_player.getCards().contains("blockade")){
                        PlayersGameplay.Blockade(l_getCountry.get_nameFromId(p_connectivity.getD_countriesList(), Integer.parseInt(l_orderContent[1])),p_player,Play.getL_playersArray(),p_connectivity.getD_continentsList());
                        p_player.removeCard("blockade");
                    }
                    else
                        System.out.println(ColorCoding.ANSI_RED+p_player.getD_playerName()+" doesn't have blockade card "+ColorCoding.ANSI_RESET);
                    break;
                case "airlift":
                    d_logEntryBuffer.log(p_player.getD_playerName()+"is calling"+"airlift");
                    System.out.println(p_player.getD_playerName()+"is calling"+"airlift");
                    if(p_player.getCards().contains("airlift")) {

                        PlayersGameplay.AirliftDeploy(l_getCountry.get_nameFromId(p_connectivity.getD_countriesList(), Integer.parseInt(l_orderContent[1])),l_getCountry.get_nameFromId(p_connectivity.getD_countriesList(), Integer.parseInt(l_orderContent[2])), Integer.parseInt( l_orderContent[3]), p_player);
                        p_player.removeCard("airlift");
                    }
                    else
                        System.out.println(ColorCoding.ANSI_RED+p_player.getD_playerName()+" doesn't have airlift card "+ColorCoding.ANSI_RESET);
                    break;
                case "negotiate":
                    d_logEntryBuffer.log(p_player.getD_playerName()+"is calling "+"negotiate");
                    System.out.println(p_player.getD_playerName()+"is calling "+"negotiate");

                    if(p_player.getCards().contains("diplomacy"))
                    {
                        PlayersGameplay.negotiate(p_player,Play.getL_playersArray(),l_orderContent[1]);
                        p_player.removeCard("diplomacy");
                    }


                    else {
                        d_logEntryBuffer.log(p_player.getD_playerName()+" doesn't have diplomacy card");
                        System.out.println(ColorCoding.ANSI_RED+p_player.getD_playerName()+" doesn't have diplomacy card "+ColorCoding.ANSI_RESET);
                    }
                    break;
            }

        }



    }

}
