package org.concordia.macs.Models;

import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.LoadMap;
import org.concordia.macs.Utilities.MapCheck;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class Map defines the map and its properties like mapname and mapexists ,
 * where mapname is the name of the map and
 * mapexists checks if the given map exists or not.
 * @author - Blesslin Jeba Shiny
 */

public class Map {
    private String d_mapName;
    private boolean d_mapExists;

    /**
     * Default constructor for Map Class
     */
    public Map() {
        this.d_mapExists = false;
    }

    /**
     * Check if map exists
     * @return true or false
     */
    public boolean isD_mapExists() {
        return d_mapExists;
    }

    /**
     * Set if map exists
     * @param p_mapExists - new boolean value
     */
    public void setD_mapExists(boolean p_mapExists) {
        this.d_mapExists = p_mapExists;
    }

    /**
     * Gets the file name
     * @return file name
     */
    public String getD_mapName() {
        return d_mapName;
    }

    /**
     * Set file name
     * @param p_mapName - new file name
     */
    public void setD_mapName(String p_mapName) {
        this.d_mapName = p_mapName;
    }

    public static void checkMap(Connectivity p_connectivity, String p_mapName)
    {
        String l_fileName = p_mapName;
        String l_copyFileName=l_fileName;
        Map l_map=new Map();
        File f = new File("");
        p_connectivity.setD_nameOfMap(p_mapName);
        String absolute = f.getAbsolutePath();
        p_connectivity.setD_pathName(absolute+ File.separator+"src/main/resources");
        l_fileName = absolute + File.separator + "src/main/resources" + File.separator + l_fileName+".map";
        p_connectivity.setD_FilePathName(l_fileName);
        l_map.setD_mapName(l_fileName);
        ArrayList<String> l_fileContent=new ArrayList<String>();
        if(MapCheck.checkMap(l_copyFileName, p_connectivity.getD_pathName()))
        {
            try
            {
                Scanner l_input = new Scanner(new File(l_fileName));

                while(l_input.hasNextLine())
                {
                    l_fileContent.add(l_input.nextLine());
                }
            }catch(Exception e)
            {
                System.out.println("File not found exception");
            }
        }
        if(l_fileContent.contains("[Territories]"))
            ConquestMapLoader.loadMap(p_connectivity, p_mapName);
        if(l_fileContent.contains("[countries]"))
            LoadMap.loadMap(p_connectivity, p_mapName);

    }
}