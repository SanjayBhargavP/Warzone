package org.concordia.macs.Utilities;

import java.util.LinkedList;

/**
 * Graph class is used to recreate map file in the graph format
 * @Author - Blesslin Jeba Shiny
 */
public class Graph extends ValidateGraph {

    /**
     * This method creates the graph.
     * @param  d_noOfCountries - total no of countries in the map
     * @param  p_connectivity - connectivity object
     */
    public Graph(int d_noOfCountries,Connectivity p_connectivity)
    {
        this.d_noOfCountries=d_noOfCountries;
        this.d_adjCountriesList=new LinkedList[d_noOfCountries+1];
        for(int i=1;i<=d_noOfCountries;i++)
        {
            d_adjCountriesList[i]=new LinkedList();
        }
        for(int i=0;i<p_connectivity.getD_countryList().size();i++)
        {
            for(int j=0;j<p_connectivity.getD_countryList().get(i).getD_neighbours().size();j++)
                this.addCountries(p_connectivity.getD_countryList().get(i).getD_countryId(), p_connectivity.getD_countryList().get(i).getD_neighbours().get(j));
        }
    }

    /**
     * This method add the countries
     * @param d_noOfCountries - total no of countries
     * @param p_countryId - country ID
     */
    public void addCountries(int d_noOfCountries,int p_countryId)
    {
        d_adjCountriesList[d_noOfCountries].add(p_countryId);
    }
}
