package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;

import java.io.FileWriter;
import java.util.ArrayList;

/**
 * The class MapLoader will save the map selected by the user in conquest format
 */
public class ConquestSaveMap {

    /**
     * The function is used to save the map in the conquest format
     * @param p_connectivity refers to the connectivity object
     * @param p_mapName refers to the name of map as defined in the userCommand
     * @return 0 for successful save of map; 1 for failure in saving the map
     */
    public static int conquestMapSaver(Connectivity p_connectivity, String p_mapName) {
        ArrayList<String> l_mapData=new ArrayList<String>();
        l_mapData.add("[Continents]\n");
        for(int i=0;i<p_connectivity.getD_continentsList().size();i++)
        {
            l_mapData.add(p_connectivity.getD_continentsList().get(i).getD_continentName()+"="+p_connectivity.getD_continentsList().get(i).getD_continentArmyBonus()+"\n");
        }
        l_mapData.add("\n");
        l_mapData.add("[Territories]\n");
        for(int i=0;i<p_connectivity.getD_countriesList().size();i++)
        {
            String l_countryInfo=p_connectivity.getD_countriesList().get(i).getD_countryName()+",0,"+"0,"+ Continent.getNameFromId(p_connectivity.getD_countriesList().get(i).getD_continentId(), p_connectivity.getD_continentsList());
            String l_neighbours="";
            for(int j=0;j<p_connectivity.getD_countriesList().get(i).getD_neighbours().size()-1;j++)
                l_neighbours=l_neighbours+ Country.get_nameFromId(p_connectivity.getD_countriesList(), p_connectivity.getD_countriesList().get(i).getD_neighbours().get(j))+"," ;
            l_neighbours=l_neighbours+Country.get_nameFromId(p_connectivity.getD_countriesList(), p_connectivity.getD_countriesList().get(i).getD_neighbours().get( p_connectivity.getD_countriesList().get(i).getD_neighbours().size()-1));
            l_countryInfo=l_countryInfo+","+l_neighbours;
            l_mapData.add(l_countryInfo+"\n");
        }



        try
        {
            if(p_mapName.equals(p_connectivity.getD_nameOfMap()))
            {
                FileWriter l_input=new FileWriter(p_connectivity.getD_filePathName());
                for(String lines:l_mapData)
                    l_input.write(lines);
                l_input.close();
                System.out.println("Map has been successfully saved");
                return 0;
            }
            else
            {
                System.out.println(ColorCoding.ANSI_RED+"ERROR: Saving mapName '"+p_mapName+"' should be the same name as Loading mapName '"+p_connectivity.getD_nameOfMap()+"'"+ColorCoding.ANSI_RESET);
                return 1;
            }
        }
        catch (Exception e)
        {
            System.out.println("Map could not be saved properly");
            System.err.println(e.getMessage());
            return 1;
        }

    }
}
