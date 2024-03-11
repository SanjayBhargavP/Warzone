package org.concordia.macs.Utilities;

import org.concordia.macs.Models.LogEntryBuffer;

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
            System.out.println(ColorCoding.getGreen()+"\n-----Loaded Map is a Valid Map-----\n"+ColorCoding.getReset());
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
        visited[source]=true;
        for(int i=0;i<adjacencyList[source].size();i++)
        {
            int neighbour=adjacencyList[source].get(i);
            if(visited[neighbour]==false)
                DFS(neighbour,adjacencyList,visited);
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

    }

}
