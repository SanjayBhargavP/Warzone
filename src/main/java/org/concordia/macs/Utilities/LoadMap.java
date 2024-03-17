package org.concordia.macs.Utilities;
import java.util.*;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import java.io.*;

import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Map;

/**
 * This class, LoadMap, is responsible for loading the map chosen by the user.
 * @author Sanjay Bhargav Pabbu
 */

public class LoadMap {

	/**
	 *
	 * This method is used to load the Map selected by the user whether pre-defined or user made and returns 0 in-case of successful loading of map; 1 in-case unsuccessful loading of map
	 * @param p_connectivityData to transfer of data from skeleton to classes where the map objects are used.
	 * @param p_mapFileName refers to the Name of the map being loaded.
	 *
	 */

	public static int loadMap(Connectivity p_connectivityData, String p_mapFileName)
	{
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		Scanner l_input = new Scanner(System.in);
		String l_fileName = p_mapFileName;
		String l_mapname=p_mapFileName;
		String l_copyFileName=l_fileName;
		ArrayList<Continent> l_continentList=new ArrayList<Continent>();
		ArrayList<Country> l_countryList=new ArrayList<Country>();
		Map l_map=new Map();
		try
		{
			File l_file = new File("");
			String absolute = l_file.getAbsolutePath();
            p_connectivityData.setD_pathName(absolute+ File.separator+"src/main/resources");
            l_fileName = absolute + File.separator + "src/main/resources" + File.separator + l_fileName+".map";
            p_connectivityData.setD_FilePathName(l_fileName);
			p_connectivityData.setD_nameOfMap(l_mapname);
            l_map.setD_mapName(l_mapname);
        }
        catch (Exception e)
		{
            System.err.println(e.getMessage());
        }

     if(MapCheck.checkMap(l_copyFileName,p_connectivityData.getD_pathName()))
     {
    	 try
    	 {
    	   		l_input= new Scanner(new File(l_fileName));
    	   		ArrayList<String> l_fileContent=new ArrayList<String>();
    	   		while(l_input.hasNextLine())
    	   		{
    	   			l_fileContent.add(l_input.nextLine());
    	   		}
    	   		int l_continentLength = 0;
    	   		int l_countryLength=0;
    	   		int l_borderLength=0;
    	   		for(int i=0;i<l_fileContent.size();i++)
    	   		{
    	   			String l_fileContentString=l_fileContent.get(i);
    	   			if(l_fileContentString.equals("[continents]")) l_continentLength=i;	
    	   			if(l_fileContentString.equals("[countries]")) l_countryLength=i;
    	   			if(l_fileContentString.equals("[borders]")) l_borderLength=i;
    	   			
    	   		}
    	   		HashMap<Integer,ArrayList<Integer>> l_neighbouringCountries=new HashMap<Integer,ArrayList<Integer>>(); 
    	        for(int i=l_borderLength+1; i<l_fileContent.size(); i++) 
    	        {
    	        	String l_fileContentInput = l_fileContent.get(i);
    	        	String[] l_aArr=l_fileContentInput.split(",");
    	        	String l_borderString=l_aArr[0];
    	        	String[] l_borderStringArr=l_borderString.split(" ");
    	        	ArrayList<Integer> l_neighbours=new ArrayList<Integer>();
    	        	for(int j=1;j<l_borderStringArr.length;j++) l_neighbours.add(Integer.parseInt(l_borderStringArr[j]));
    	        	l_neighbouringCountries.put(Integer.parseInt(l_borderStringArr[0]),l_neighbours );
    	        }
    	   		for(int i=l_countryLength+1;i<l_borderLength-1;i++)
    	   		{
    	   			String l_fileContentInput=l_fileContent.get(i);
    	  		    String[] l_arr=l_fileContentInput.split(" ");
    	  		    Country l_countryObj=new Country();
    	  		    l_countryObj.setD_countryId(Integer.parseInt(l_arr[0]));
    	  		    l_countryObj.setD_countryName(l_arr[1]);
    	  		    l_countryObj.setD_continentId(Integer.parseInt(l_arr[2]));
    	  		    l_countryObj.setD_neighbours(Integer.parseInt(l_arr[0]), l_neighbouringCountries);
    	     		l_countryList.add(l_countryObj);	
    	   		}
    	   		int l_continentId=1;
    	   		for(int i=l_continentLength+1;i<l_countryLength-1;i++)
    	   		{
    	   			String l_fileContentInput=l_fileContent.get(i);
    	   			String[] l_arr=l_fileContentInput.split(" ");
    	   			Continent l_continentObj=new Continent();
    	   			l_continentObj.setD_continentId(l_continentId);
    	   			l_continentObj.setD_continentName(l_arr[0]);
    	   			l_continentObj.setD_continentArmyBonus(Integer.parseInt(l_arr[1]));
    	   			l_continentObj.setD_countries(l_continentObj.d_getCountryFromContinentId(l_continentId, l_countryList));
    	   			l_continentId++;
					l_continentList.add(l_continentObj);
    	   		}
    	   		p_connectivityData.setD_continentsList(l_continentList);
    	        p_connectivityData.setD_countriesList(l_countryList);
    	        }
    	      catch(Exception e)
    	       {
    	    	  System.err.println(e.getMessage());
    	       }
    	      try
    	       {
				   d_logEntryBuffer.log("Map "+p_mapFileName+".map"+" loaded successfully.....");
    	    	  System.out.println(ColorCoding.ANSI_GREEN+"Map "+p_mapFileName+".map"+" loaded successfully....."+ColorCoding.ANSI_RESET);
    	       } 
    	       catch (Exception e)
    	       {
    	    	   System.out.println("Map could not be loaded properly");
    	    	   return -1;
    	       }
			  return 0;
     }
     else
     {
    	 p_connectivityData.setD_continentsList(new ArrayList<Continent>());
    	 p_connectivityData.setD_countriesList(new ArrayList<Country>());
    	 System.out.println(ColorCoding.ANSI_RESET+"The map does not exist. Creating a map....."+ColorCoding.ANSI_RESET);
    	 MapCreator.generateMapFile(l_copyFileName,p_connectivityData.getD_pathName());
    	 SaveMap.saveMap(p_connectivityData, l_copyFileName);
		 return 1;
  	 }

	}
}

