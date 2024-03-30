package org.concordia.macs.View;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import dnl.utils.text.table.TextTable;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;

import java.util.ArrayList;


/**
 * Class Show_map displays all continents and countries and their respective neighbors along with the army count
 * @author Blesslin Jeba Shiny
 */

public class ShowMap
{
    /**
     * This method displays all the continents , its countries ,its neighbors and its army count
     */
    public static void showMap(ArrayList<Continent> p_continentList, ArrayList<Country> p_countryList,ArrayList<Player> p_playerList)
    {
        LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
        if(p_continentList.size()==0)
        {
            d_logEntryBuffer.log("Continents or Country does not exist");
            System.out.println("Continents or Country does not exist");
            return;
        }


        // calculate total number of lines needed for l_map
        int noOfLines=0;
        for(Continent continent:p_continentList)
        {
            noOfLines+=Math.max(continent.getD_countries().size(),1);
        }

        // initialise map array
        String[][] l_map = new String[noOfLines][6];

        // construct the map for each continent and its countries
        int lineIterator=0;
        int continentIndex=0;
        while (continentIndex < p_continentList.size())
        {
            Continent continent = p_continentList.get(continentIndex);
            int countryIndex = 0;
                while (countryIndex < continent.getD_countries().size())
                {
                        Country country = continent.getD_countries().get(countryIndex);
                        l_map[lineIterator][0] = continent.getD_continentName();
                        l_map[lineIterator][1] = country.getD_countryName();

                        String tempNeighbours = "";
                        for (int neighbourID : country.getD_neighbours())
                        {
                            if (neighbourID != 0)
                            {
                                tempNeighbours += ", " + country.get_nameFromId(p_countryList, neighbourID);
                            } else
                            {
                                tempNeighbours += country.get_nameFromId(p_countryList, neighbourID);
                            }
                        }

                        l_map[lineIterator][2] = tempNeighbours.toString();
                        l_map[lineIterator][3] = String.valueOf(continent.getD_continentArmyBonus());
                        l_map[lineIterator][4] = String.valueOf(country.getD_armyCount());
                        l_map[lineIterator][5] = "Neutral";
                        int playerIndex = 0;
                            while (playerIndex < p_playerList.size()) {
                                int countryOwnedIndex = 0;
                                while (countryOwnedIndex < p_playerList.get(playerIndex).getD_country().size()) {
                                    if (p_playerList.get(playerIndex).getD_country().get(countryOwnedIndex).getD_countryId() == country.getD_countryId()) {
                                        l_map[lineIterator][5] = p_playerList.get(playerIndex).getD_playerName();
                                    }
                                    countryOwnedIndex++;
                                }
                                playerIndex++;
                             }
                        lineIterator++;
                        countryIndex++;
                }
            if (continent.getD_countries().isEmpty()) {
                l_map[lineIterator][0] = continent.getD_continentName();
                l_map[lineIterator][3] = String.valueOf(continent.getD_continentArmyBonus());
                lineIterator++;
            }
            continentIndex++;
        }

       System.out.println("\nThe Map Details : \n");
       String[] l_mapColumnNames={"Continent", "Country", "Neighbouring Country", "Continent Bonus", "Armies deployed", "Country Owner"};
       TextTable l_mapAsTable = new TextTable(l_mapColumnNames, l_map);
       l_mapAsTable.printTable();
       System.out.println();
    }
}