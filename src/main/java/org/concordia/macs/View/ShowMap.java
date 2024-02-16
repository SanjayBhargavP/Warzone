package org.concordia.macs.View;

import Models.Continent;
import Models.Country;
import dnl.utils.text.table.TextTable;
/**
 * Class Show_map displays all continents and countries and their respective neighbors along with the army count
 */

Public class Show_map
{
    /**
     * This method displays all the continets , its countries ,its neighbors and its army count
     */
    public static void show_map(Continent[] continents,Country[] countries)
    {
        if(continents.length==0)
        {
            System.out.println("Continents or Country does not exist");
            return;
        }


        // calculate total number of lines needed for l_map
        int noOfLines=0;
        for(Continent continent:continents)
        {
            noOfLines+=Math.max(continent.getD_countries().length,1);
        }

        // initialise map array
        String[][] l_map = new String[noOfLines][4];

        // contruct the map for each continent and its countries
        int lineIterator=0;
        int continentIndex = 0;
        while (continentIndex < continents.length)
        {
            Continent continent = continents[continentIndex];
            int countryIndex = 0;
                while (countryIndex < continent.getD_countries().length)
                {
                        Country country = continent.getD_countries()[countryIndex];
                        l_map[lineIterator][0] = continent.getD_continentName();
                        l_map[lineIterator][1] = country.getD_countryName();

                        String tempNeighbours = "";
                        for (int neighbourID : country.getD_neighbours())
                        {
                            if (neighbourID != 0)
                            {
                                tempNeighbours += ", " + country.get_nameFromId(countries, neighbourID);
                            } else
                            {
                                tempNeighbours += country.get_nameFromId(countries, neighbourID);
                            }
                        }

                        l_map[lineIterator][2] = tempNeighbours.toString();
                        l_map[lineIterator][3] = String.valueOf(continent.getD_continentArmyValue());
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