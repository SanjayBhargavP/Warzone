package org.concordia.macs.Utilities;

import org.concordia.macs.Utilities.Connectivity;

import java.io.FileWriter;
import java.util.LinkedList;
import org.concordia.macs.Models.LogEntryBuffer;
/**
 * This class, SaveMap, manages the saving of maps after users create new ones or modify existing ones.
 *
 * @author Susmitha Mamula
 */
public class SaveMap {
    /**
     * This method facilitates the saving of maps after users create new ones or modify existing ones.
     *
     * @param p_connectivity The connectivity information for the map.
     * @return An integer indicating success or failure of the save operation
     */
    public static int saveMap(Connectivity p_connectivity) {
        LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
        LinkedList<String> l_mapInfo = new LinkedList<>();
        l_mapInfo.add("[continents]" + "\n");
        for (int i = 0; i < p_connectivity.getD_continentList().size(); i++)
            l_mapInfo.add(p_connectivity.getD_continentList().get(i).getD_continentName() + " " + p_connectivity.getD_continentList().get(i).getD_continentArmyCount() + "\n");
        l_mapInfo.add("\n");
        l_mapInfo.add("[countries]" + "\n");
        for (int i = 0; i < p_connectivity.getD_countryList().size(); i++)
            l_mapInfo.add(p_connectivity.getD_countryList().get(i).getD_countryId() + " " + p_connectivity.getD_countryList().get(i).getD_countryName() + " " + p_connectivity.getD_countryList().get(i).getD_continentId() + "\n");
        l_mapInfo.add("\n");
        l_mapInfo.add("[borders]" + "\n");
        for (int i = 0; i < p_connectivity.getD_countryList().size(); i++) {
            String l_countryData = Integer.toString(p_connectivity.getD_countryList().get(i).getD_countryId());
            for (int j = 0; j < p_connectivity.getD_countryList().get(i).getD_neighbours().size(); j++)
                l_countryData = l_countryData + " " + p_connectivity.getD_countryList().get(i).getD_neighbours().get(j);
            l_mapInfo.add(l_countryData + "\n");
        }

        try
        {
            if(p_mapName.equals(p_connectivity.getD_mapName()))
            {
                FileWriter l_input=new FileWriter(p_connectivity.getD_FilePathName());
                for(String lines:l_mapInfo)
                    l_input.write(lines);
                l_input.close();
                d_logEntryBuffer.log("Map has been successfully saved");
                System.out.println("Map has been successfully saved");
                return 0;
            }
            else
            {
                System.out.println(ColorCoding.ANSI_RED+"ERROR: Saving mapName '"+p_mapName+"' should be the same name as Loading mapName '"+p_connectivity.getD_mapName()+"'"+ColorCoding.ANSI_RESET);
                return 1;
            }

        }
        catch (Exception e)
        {
            d_logEntryBuffer.log("Map could not be saved properly");
            System.out.println("Map could not be saved properly");
            System.err.println(e.getMessage());
            return 1;
        }
    }
}
