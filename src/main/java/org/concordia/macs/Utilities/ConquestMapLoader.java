package org.concordia.macs.Utilities;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Map;

import java.io.*;
import java.util.*;

/**
 *
 * @author Mahfuzzur Rahman
 * The ConquestMapLoader class is responsible for loading maps in the Conquest game.
 */
public class ConquestMapLoader {

	/**
	 * Loads the map selected by the user, whether predefined or user-made.
	 *
	 * @param p_connectivity the Connectivity object
	 * @param p_mapName      the name of the map being loaded
	 * @return 0 in case of successful loading of the map; 1 in case of unsuccessful loading of the map
	 */
	public static int loadMap(Connectivity p_connectivity, String p_mapName) {

		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		Scanner l_input = new Scanner(System.in);

		String l_fileName = p_mapName;
		String l_copyFileName = l_fileName;

		ArrayList<Continent> continentList = new ArrayList<Continent>();
		ArrayList<Country> l_countryList = new ArrayList<Country>();

		Map l_map = new Map();

		try {
			File f = new File("");
			p_connectivity.setD_mapName(p_mapName);
			String absolute = f.getAbsolutePath();
			p_connectivity.setD_pathName(absolute + File.separator + "src/main/resources");
			l_fileName = absolute + File.separator + "src/main/resources" + File.separator + l_fileName + ".map";
			p_connectivity.setD_FilepathName(l_fileName);
			l_map.setD_mapFileName(l_fileName);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (MapCheck.validateMap(l_copyFileName, p_connectivity.getD_pathName())) {
			try {
				l_input = new Scanner(new File(l_fileName));
				ArrayList<String> l_fileContent = new ArrayList<String>();

				while (l_input.hasNextLine()) {
					l_fileContent.add(l_input.nextLine());
				}

				int l_continentLength = 0;
				int l_territoryLength = 0;

				for (int i = 0; i < l_fileContent.size(); i++) {
					String a = l_fileContent.get(i);
					if (a.equals("[Continents]")) l_continentLength = i;
					if (a.equals("[Territories]")) l_territoryLength = i;
				}

				HashMap<Integer, ArrayList<Integer>> l_neighbouringCountries = new HashMap<Integer, ArrayList<Integer>>();

				int l_countryID = 1;
				int l_continentID = 1;

				// Parsing continents
				for (int i = l_continentLength + 1; i < l_territoryLength - 1; i++) {
					String a = l_fileContent.get(i);
					String[] aArr = a.split("=");
					Continent l_continentObj = new Continent();

					l_continentObj.setD_continentId(l_continentID);
					l_continentObj.setD_continentName(aArr[0]);
					l_continentObj.setD_continentBonusValue(Integer.parseInt(aArr[1]));
					l_continentID++;

					continentList.add(l_continentObj);
				}

				// Parsing territories
				for (int i = l_territoryLength + 1; i < l_fileContent.size(); i++) {
					Country l_currentCountry = new Country();
					String a = l_fileContent.get(i);
					String[] aArr = a.split(",");

					// Checking if aArr[0] country already exists or not, if it does, set it to l_currentCountry
					// else create a new country object and assign it to l_currentCountry

					if (checkIfcountryExists(l_countryList, aArr[0])) {
						for (int j = 0; j < l_countryList.size(); j++)
							if (l_countryList.get(j).getD_countryName().equals(aArr[0])) {
								l_currentCountry = l_countryList.get(j);
								l_currentCountry.setD_continentId(Continent.getIdFromName(aArr[3], continentList));
							}
					} else {
						l_currentCountry.setD_countryId(l_countryID);
						l_currentCountry.setD_countryName(aArr[0]);
						l_currentCountry.setD_continentId(Continent.getIdFromName(aArr[3], continentList));
						l_countryList.add(l_currentCountry);
						l_countryID++;
					}

					// Adding currentCountry to continentList
					for (int j = 0; j < continentList.size(); j++) {
						if (continentList.get(j).getD_continentName().equals(aArr[3]))
							continentList.get(j).getD_countries().add(l_currentCountry);
					}

					// Setting neighbors for l_currentCountry according to loaded map
					ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
					for (int j = 4; j < aArr.length; j++) {
						Country l_neighborCountry = new Country();
						if (checkIfcountryExists(l_countryList, aArr[j])) {
							for (int k = 0; k < l_countryList.size(); k++)
								if (l_countryList.get(k).getD_countryName().equals(aArr[j]))
									l_neighborCountry = l_countryList.get(k);
						} else {
							l_neighborCountry.setD_countryId(l_countryID);
							l_neighborCountry.setD_countryName(aArr[j]);
							l_countryList.add(l_neighborCountry);
							l_countryID++;
						}
						l_neighbours.add(l_neighborCountry.getD_countryId());
					}
					l_neighbouringCountries.put(l_currentCountry.getD_countryId(), l_neighbours);
					l_currentCountry.setD_neighbours(l_currentCountry.getD_countryId(), l_neighbouringCountries);

				}
				p_connectivity.setD_continentList(continentList);
				p_connectivity.setD_countryList(l_countryList);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			try {
				d_logEntryBuffer.log("Map " + p_mapName + ".map" + " has been successfully loaded...");
				System.out.println(ColorCoding.ANSI_GREEN + "Map " + p_mapName + ".map" + " has been successfully loaded..." + ColorCoding.ANSI_RESET);
			} catch (Exception e) {
				System.out.println("Map could not be loaded properly");
				return -1;
			}
			return 0;

		} else {
			p_connectivity.setD_continentList(new ArrayList<Continent>());
			p_connectivity.setD_countryList(new ArrayList<Country>());
			d_logEntryBuffer.log("Map does not exist. Creating a map...");
			System.out.println(ColorCoding.ANSI_GREEN + "Map does not exist. Creating a map..." + ColorCoding.ANSI_RESET);
			MapCreater.createMap(l_copyFileName, p_connectivity.getD_pathName());
			SaveMap.saveMap(p_connectivity, l_copyFileName);
			return 1;
		}

	}

	/**
	 * Checks if a country exists in the given list of countries.
	 *
	 * @param p_countryList the list of countries
	 * @param p_country     the country to check for existence
	 * @return true if the country exists in the list, false otherwise
	 */
	public static boolean checkIfcountryExists(ArrayList<Country> p_countryList, String p_country) {
		boolean l_countryExists = false;
		for (int i = 0; i < p_countryList.size(); i++) {
			if (p_countryList.get(i).getD_countryName().equals(p_country))
				l_countryExists = true;
		}
		return l_countryExists;
	}
}
