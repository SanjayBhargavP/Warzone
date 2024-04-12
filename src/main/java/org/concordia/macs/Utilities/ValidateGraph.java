package org.concordia.macs.Utilities;

import org.concordia.macs.Models.LogEntryBuffer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ValidateGraph validates the map represented as a graph
 * @author - Blesslin Jeba Shiny
 */
public class ValidateGraph {
    public int d_noOfCountries;
    public LinkedList[] d_adjCountriesList;

    public int isConnected(ValidateGraph graph){
        LogEntryBuffer d_logEntryBuffer=new LogEntryBuffer();

        int vertices=graph.d_noOfCountries+1;
        LinkedList<Integer> adjacencyList []=graph.d_adjCountriesList;
        boolean[] visited=new boolean[vertices];

        DFS(1,adjacencyList,visited);
        boolean connected = true;

        for(int i=1;i< visited.length;i++)
        {
            if(!visited[i]) {
                connected = false;
                break;
            }
        }

        if(connected)
        {
            d_logEntryBuffer.log("Graph is connected");
            System.out.println(ColorCoding.getGreen()+"Graph is Connected"+ColorCoding.getReset());
            d_logEntryBuffer.log("\n----- Loaded Map is Valid -----\n"+ColorCoding.getReset());
            System.out.println(ColorCoding.getGreen()+"\n----- Loaded Map is a Valid Map-----\n"+ColorCoding.getReset());
            return 0;
        }
        else {
            d_logEntryBuffer.log("Graph is not connected");
            System.out.println(ColorCoding.getRed() +"Graph is not Connected"+ColorCoding.getReset());
            return 1;
        }
    }

    /**
     * Performs depth first search
     * @param source - source country from where we traverse
     * @param adjacencyList - list of neighbour countries
     * @param visited - list of visited countries
     */
    public void DFS(int source,LinkedList<Integer> adjacencyList [],boolean[] visited)
    {
        try {

            visited[source] = true;
            for (int i = 0; i < adjacencyList[source].size(); i++) {
                int neighbour = adjacencyList[source].get(i);
                if (visited[neighbour] == false)
                    DFS(neighbour, adjacencyList, visited);
            }
        }
        catch (Exception e){

        }
    }

    /**
     *Checks if continents are connected
     * @param p_connectivity - connectivity object
     * @param graph - map
     * @return true if continents are connected , else false.
     */
    public boolean continentConnection(Connectivity p_connectivity,ValidateGraph graph)
    {
        LogEntryBuffer d_logEntryBuffer=new LogEntryBuffer();
        ArrayList<String> l_countries = new ArrayList<>();
        ArrayList<String> l_continents=new ArrayList<>();

        for(int i=0;i<p_connectivity.getD_continentsList().size();i++)
        {
            if(p_connectivity.getD_continentsList().get(i).getD_countries().size()==0)
            {
                d_logEntryBuffer.log("Graph is disconnected as "+p_connectivity.getD_continentsList().get(i).getD_continentName()+" has no countries.");
                System.out.println(ColorCoding.getRed()+"Graph is disconnected as "+p_connectivity.getD_continentsList().get(i).getD_continentName()+" has no countries."+ColorCoding.getReset());
                return false;
            }
            if(l_continents.contains(p_connectivity.getD_continentsList().get(i).getD_continentName()))
            {
                d_logEntryBuffer.log("Map is invalid because "+p_connectivity.getD_continentsList().get(i).getD_continentName()+" is duplicated.");
                System.out.println(ColorCoding.getRed()+"Map is invalid because "+p_connectivity.getD_continentsList().get(i).getD_continentName()+" is duplicated."+ColorCoding.getReset());
                return false;
            }
            else l_continents.add(p_connectivity.getD_continentsList().get(i).getD_continentName());

            for(int j=0;j<p_connectivity.getD_continentsList().get(i).getD_countries().size();j++)
            {
                if(l_countries.contains(p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryName()))
                {
                    d_logEntryBuffer.log("Map is invalid because "+p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryName()+" is duplicated.");
                    System.out.println(ColorCoding.getRed()+"Map is invalid because "+p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryName()+" is duplicated."+ColorCoding.getReset());
                    return false;
                }
                else l_countries.add(p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryName());
            }

        }
        return true;
    }

}
