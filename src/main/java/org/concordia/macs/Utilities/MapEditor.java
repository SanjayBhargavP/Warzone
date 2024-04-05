package org.concordia.macs.Utilities;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import java.util.ArrayList;
import java.util.List;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;

/**
 * The class MapEditor will make changes to the existing map according to the user.
 *
 */

public class MapEditor {
	
	/**
	 *
	 * This method is used to add continent to the user defined maps.
	 * @param p_continentId refers to the ID of the continent.
	 * @param p_continentvalue refers to the control value of the continent.
	 * @param p_connectivity refers object of the connectivity class.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
		
	public static int addContinent(String p_continentId,int p_continentvalue,Connectivity p_connectivity) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
	    Continent l_continent=new Continent();
	    for(int i=0; i<p_connectivity.getD_continentsList().size(); i++)
	    {
	    	if(p_continentId.equalsIgnoreCase(p_connectivity.getD_continentsList().get(i).getD_continentName()))
	    	{
	    		d_logEntryBuffer.log("Continent Already Exists");
	    		System.out.println("Continent already Exists.");
	    		return 1;
	    	}
	    }
	    List<Country> l_countries = new ArrayList<Country>();
		l_continent.setD_continentId(p_connectivity.getD_continentsList().size()+1);
		l_continent.setD_continentName(p_continentId);
		l_continent.setD_continentArmyBonus(p_continentvalue);
		l_continent.setD_countries(l_countries);
		p_connectivity.getD_continentsList().add(l_continent);
		d_logEntryBuffer.log("Continent Added Successfully");
		System.out.println("\u001B[32m"+"Continent Added Successfully"+"\u001B[0m");
		return 0;
	}
	
	/**
	 *
	 * This method is used to add country to the user defined maps.
	 * @param p_countryId refers to the ID of the country.
	 * @param p_continentId refers to the ID of the continent.
	 * @param p_connectivity refers to the object of the connectivity class.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
	
	public static int addCountry(String p_countryId,String p_continentId,Connectivity p_connectivity) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
		Country l_country=new Country();
		int flag = 0;
		if(p_connectivity.getD_continentsList().size()==0)
		{
			d_logEntryBuffer.log("ERROR: Enter the values of continents first..");
			System.out.println("\u001B[31m"+"ERROR: Enter the values of continents first.."+"\u001B[0m");
			return 1;
		}
		for(int i=0; i<p_connectivity.getD_continentsList().size(); i++)
		{
			if(Integer.parseInt(p_continentId) == p_connectivity.getD_continentsList().get(i).getD_continentId())
			{
				flag = 1;
			}
		}
		if(flag == 0)
		{
			d_logEntryBuffer.log("Continent "+p_continentId+" does not exist");
			System.out.println("Continent "+p_continentId+" does not exist");
			return 1;
		}
		for(int i=0; i<p_connectivity.getD_continentsList().size(); i++)
		{
			for(int j=0; j<p_connectivity.getD_continentsList().get(i).getD_countries().size(); j++)
			{
				if(p_countryId.equalsIgnoreCase(p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryName()))
				{
					d_logEntryBuffer.log("Country "+p_countryId+" already exists under this continent");
					System.out.println("Country "+p_countryId+" already exists under this continent");
					return 1;
				}
			}
		}
		
		l_country.setD_countryId(p_connectivity.getD_countriesList().size()+1);
		l_country.setD_countryName(p_countryId);
		l_country.setD_continentId(Integer.parseInt(p_continentId));
		p_connectivity.getD_countriesList().add(l_country);
		for(int i=0; i<p_connectivity.getD_continentsList().size(); i++) {
			if(p_connectivity.getD_continentsList().get(i).getD_continentId() == Integer.parseInt(p_continentId)) {
				d_logEntryBuffer.log("Macthed with continentID "+p_continentId);
				System.out.println("Macthed with continentID "+p_continentId);
				p_connectivity.getD_continentsList().get(i).getD_countries().add(l_country);
			}
		}
		
		d_logEntryBuffer.log("Country Added Successfully");
		System.out.println("\u001B[32m"+"Country Added Successfully"+"\u001B[0m");
		return 0;	
	}
	
	/**
	 *
	 * This method is used to add neighbouring countries to the owned countries to the user defined maps.
	 * @param p_countryId refers to the ID of the country.
	 * @param p_neighbourcountryId refers to the ID of the neighbouring country.
	 * @param p_connectivity refers to the object of the connectivity class.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
	
	public static int addNeighbour(int p_countryId,int p_neighbourcountryId,Connectivity p_connectivity) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
		Country l_country = new Country();
		if(p_connectivity.getD_continentsList().size()==0)
		{
			d_logEntryBuffer.log("ERROR: Enter the values of continents first..");
			System.out.println("ERROR: Enter the values of continents first..");
			return 1;
		}
		if(p_connectivity.getD_countriesList().size()==0)
		{
			d_logEntryBuffer.log("ERROR: Enter the values of countries first..");
			System.out.println("ERROR: Enter the values of countries first..");
			return 1;
		}

		ArrayList<String> find = new ArrayList<String>();
		for(int i=0; i<p_connectivity.getD_countriesList().size(); i++)
		{
			if(p_countryId == p_connectivity.getD_countriesList().get(i).getD_countryId())
			{
				find.add("Foundp_countryId");
			}
			if(p_neighbourcountryId == p_connectivity.getD_countriesList().get(i).getD_countryId())
			{
				find.add("Foundp_neighbourcountryId");
			}
		}
		if(find.size() == 2)
		{
			for(int i=0;i<p_connectivity.getD_countriesList().size();i++)
			{
				if(p_connectivity.getD_countriesList().get(i).getD_countryId()==p_countryId)
					{
					p_connectivity.getD_countriesList().get(i).getD_neighbours().add(p_neighbourcountryId);
					d_logEntryBuffer.log("Neighbor "+l_country.get_nameFromId(p_connectivity.getD_countriesList(),p_neighbourcountryId)+ "("+p_neighbourcountryId +") added successfully to "+l_country.get_nameFromId(p_connectivity.getD_countriesList(),p_countryId)+"("+p_countryId+")");
					System.out.println("Neighbor "+l_country.get_nameFromId(p_connectivity.getD_countriesList(),p_neighbourcountryId)+ "("+p_neighbourcountryId +") added successfully to "+l_country.get_nameFromId(p_connectivity.getD_countriesList(),p_countryId)+"("+p_countryId+")");
					}
			}	
			return 0;	
		} else
		{
			if(find.size() == 0)
			{
				d_logEntryBuffer.log("NeighbourCountryID "+p_neighbourcountryId+" and "+"CountryID "+p_countryId+" don't exist");
				System.out.println("NeighbourCountryID "+p_neighbourcountryId+" and "+"CountryID "+p_countryId+" don't exist");
			}
			else
			{
				if(find.get(0) == "Foundp_countryId")
				{
					d_logEntryBuffer.log("NeighbourCountryID "+p_neighbourcountryId+" does not exist");
					System.out.println("NeighbourCountryID "+p_neighbourcountryId+" does not exist");
				}
				else
				{
					d_logEntryBuffer.log("CountryID "+p_countryId+" does not exist");
					System.out.println("CountryID "+p_countryId+" does not exist");
				}				
			}
		
			return 1;
		}

	}
	
	/**
	 *
	 * This method is used to remove neighbouring countries to the owned countries to the user defined maps.
	 * @param p_countryId refers to the ID of the country.
	 * @param p_neighbourcountryId refers to the ID of the neighbouring country.
	 * @param p_connectivity refers to the object of the connectivity class.
	 * @param p_displayMessage refers to the message being displayed.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
	
	public static int removeNeighbour(int p_countryId,int p_neighbourcountryId, Connectivity p_connectivity, int p_displayMessage) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
		ArrayList<String> find = new ArrayList<String>();
		for(int i=0; i<p_connectivity.getD_countriesList().size(); i++) {
			if(p_countryId == p_connectivity.getD_countriesList().get(i).getD_countryId())  {
				find.add("Foundp_countryId");
			if(p_connectivity.getD_countriesList().get(i).getD_neighbours().contains(p_neighbourcountryId)) {
					find.add("Foundp_neighbourcountryId");
					break;
				}
			}
		}
		if(find.size() == 2) {
			for(int i=0;i<p_connectivity.getD_countriesList().size();i++) {
				if(p_connectivity.getD_countriesList().get(i).getD_countryId()==p_countryId) {
					for(int j=0;j<p_connectivity.getD_countriesList().get(i).getD_neighbours().size();j++) {
						if(p_neighbourcountryId==p_connectivity.getD_countriesList().get(i).getD_neighbours().get(j)) {
							p_connectivity.getD_countriesList().get(i).getD_neighbours().remove(j);
							d_logEntryBuffer.log("Removed Neighbour Successfully");
							System.out.println(ColorCoding.ANSI_GREEN+"Removed neighbor succesfully"+ColorCoding.ANSI_RESET);
							return 0;
						}
					}
				}
			}
		}
		if(p_displayMessage !=0) {
			if(find.size() == 0) {
				d_logEntryBuffer.log("CountryID "+p_countryId+" don't exist");
				System.out.println("CountryID "+p_countryId+" don't exist");
			}
			else if(find.size()==1) {
				d_logEntryBuffer.log("NeighbourCountryID "+p_neighbourcountryId+" does not exist");
				System.out.println("NeighbourCountryID "+p_neighbourcountryId+" does not exist");	
			}
		}

		return 1;
	}
	
	/**
	 *
	 * This method is used to remove country to the user defined maps.
	 * @param p_countryId refers to the ID of the country.
	 * @param p_connectivity refers to the object of the connectivity class.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
	
	public static int removeCountry(String p_countryId,Connectivity p_connectivity) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
		int l_requiredCountryId=0, flag=1, l_continentID=0, l_countryPresent=0;
		for(int i=0;i<p_connectivity.getD_countriesList().size();i++)
		{	
			if(p_countryId.equalsIgnoreCase(p_connectivity.getD_countriesList().get(i).getD_countryName()))
			{
				l_countryPresent =1;
				if(p_connectivity.getD_countriesList().get(i).getD_neighbours().size() == 0)
				{
					flag =0;
				}
				l_continentID = p_connectivity.getD_countriesList().get(i).getD_continentId();
				l_requiredCountryId=p_connectivity.getD_countriesList().get(i).getD_countryId();
				p_connectivity.getD_countriesList().remove(i);
			}

		}
		
		for(int i=0; i<p_connectivity.getD_continentsList().size(); i++)
		{
			if(l_continentID == p_connectivity.getD_continentsList().get(i).getD_continentId())
			{
				for(int j=0;j<p_connectivity.getD_continentsList().get(i).getD_countries().size();j++)
				{
					if(l_requiredCountryId == p_connectivity.getD_continentsList().get(i).getD_countries().get(j).getD_countryId())
					{
						p_connectivity.getD_continentsList().get(i).getD_countries().remove(j);
					}
				}
			}
		}
		
		if(l_countryPresent==0) {
			d_logEntryBuffer.log("Country "+p_countryId+" does not exist");
			System.out.println("Country "+p_countryId+" does not exist");
			return 1;
		}
		
		if(flag == 1) {
			for(int i=0;i<p_connectivity.getD_countriesList().size();i++)
			{
				removeNeighbour(p_connectivity.getD_countriesList().get(i).getD_countryId(),l_requiredCountryId,p_connectivity,0);
				
			}
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	/**
	 *
	 * This method is used to remove continent to the user defined maps.
	 * @param p_continentId refers to the ID of the continent.
	 * @param p_connectivity refers to the object of the connectivity class.
	 * 
	 * @return 0 is continent adds successfully or 1 if it fails.
	 *
	 */
	
	public static int removeContinent(String p_continentId,Connectivity p_connectivity) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		
		int l_requiredContinentId=0, flag =0;
	    for(int i=0; i<p_connectivity.getD_continentsList().size(); i++)
	    {
	    	if(p_continentId.equalsIgnoreCase(p_connectivity.getD_continentsList().get(i).getD_continentName()))
	    	{
	    		flag=1;
	    	}
	    }
	    if(flag==0)
	    {
	    	d_logEntryBuffer.log("Continent: "+p_continentId+" doesn't exist");
	    	System.out.println("Continent: "+p_continentId+" doesn't exist");
	    	return 1;
	    }
		for(int i=0;i<p_connectivity.getD_continentsList().size();i++)
		{
			if(p_connectivity.getD_continentsList().get(i).getD_continentName().equals(p_continentId))
			{
				l_requiredContinentId=p_connectivity.getD_continentsList().get(i).getD_continentId();
				p_connectivity.getD_continentsList().remove(i);
			}
		}
		for(int j=p_connectivity.getD_countriesList().size()-1;j>=0;j--)
		{

			if(p_connectivity.getD_countriesList().get(j).getD_continentId()==l_requiredContinentId)
			{
				removeCountry(p_connectivity.getD_countriesList().get(j).getD_countryName(),p_connectivity);
			}
		}
		
		return 0;
	}	
	
}
