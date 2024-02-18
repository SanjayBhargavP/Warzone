package org.concordia.macs.View;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import dnl.utils.text.table.TextTable;
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
    public static void showMap(ArrayList<Continent> p_continentList, ArrayList<Country> p_countryList)
    {
        if(p_continentList.size()==0)
        {
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
        String[][] l_map = new String[noOfLines][4];

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
                        l_map[lineIterator][3] = String.valueOf(continent.getD_continentArmyCount());
                        lineIterator++;
                        countryIndex++;
                }
            continentIndex++;
        }

       System.out.println("\nThe Map Details : \n");
       String[] l_mapColumnNames={"Continent","Country","Neighbours","Total Army"};
       TextTable l_mapAsTable = new TextTable(l_mapColumnNames, l_map);
       l_mapAsTable.printTable();
       System.out.println();
    }
}