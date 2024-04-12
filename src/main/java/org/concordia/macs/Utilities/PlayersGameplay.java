package org.concordia.macs.Utilities;

import java.util.ArrayList;
import java.util.Random;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;
import org.concordia.macs.State.Play;

/**
 * The class PlayerGameplay manages the player perspective.
 *
 */

public class PlayersGameplay {
	
	public static ArrayList<Integer> l_armiesOfPlayers=new ArrayList<>();
	public static ArrayList<Country> l_neutralCountry = new ArrayList<>();
	private static Player l_winner;
	
	/**
	 *
	 * This method is used to randomly assign the countries to the user once the game begins.
	 * @param p_playersArray refers to the number of players playing the game.
	 * @param p_countryList refers to the list of countries in the Map.
	 * @param p_continentList refers to all the continents present in the Map.
	 * 
	 * @return error types
	 *
	 */
	
	public static int assigncountries(ArrayList<Player> p_playersArray,ArrayList<Country> p_countryList,ArrayList<Continent> p_continentList) {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		if (p_playersArray.size() == 0) {
			d_logEntryBuffer.log("Error: Insufficient Players to assign countries");
			System.out.println(ColorCoding.ANSI_RED + "Error: Insufficient Players to assign countries" + ColorCoding.ANSI_RESET);
			return 1;
		}

		if (p_countryList.size() < p_playersArray.size()) {
			d_logEntryBuffer.log("Error: Insufficient country to assign to all players");
			System.out.println(ColorCoding.ANSI_RED + "Error: Insufficient country to assign to all players" + ColorCoding.ANSI_RESET);
			return 1;
		}
		
		int[] l_playerCountArray = new int[p_playersArray.size()];
		int sum = 0;
		int range = (p_countryList.size() - 1) + 1;
		
		do
		{
			sum=0;
			for(int i=0; i<p_playersArray.size(); i++) {
				int randomResult = (int) ((Math.random()*range) + 1);
				sum=sum+randomResult;
				l_playerCountArray[i]=randomResult;
			}
		}	while(sum>p_countryList.size());
		
		int index = 0;
		for(int i=0; i<p_playersArray.size();i++) {
			ArrayList<Country> l_removeCountry=new ArrayList<>();
			for(int j=0; j<l_playerCountArray[i];j++) {
				Country l_country = p_countryList.get(index);
				index++;
				l_removeCountry.add(l_country);
				p_playersArray.get(i).addCountry(l_country);
			}
		}
		
		for(int i=index; i<p_countryList.size(); i++)
		{
			l_neutralCountry.add(p_countryList.get(i));
		}
		
		updateContinent(p_playersArray,p_continentList);	
		
		return 0;
	}
	/**
	 *
	 * This method is used to assign armies to the players once the game begins.
	 * @param p_playersList refers to the list of players.
	 *
	 */
	 
	public static void assignArmiesToPlayers(ArrayList<Player> p_playersList) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		for(int i=0;i<p_playersList.size();i++)
		{
			int l_countryListSize=p_playersList.get(i).getD_country().size()/3;
			int l_armyCount=Math.max(3, l_countryListSize);
			int l_tempContinentCount=0;
			if(!p_playersList.get(i).getD_continent().isEmpty())
			{
				for(int j=0;j<p_playersList.get(i).getD_continent().size();j++)
					l_tempContinentCount=l_tempContinentCount+ p_playersList.get(i).getD_continent().get(j).getD_continentArmyBonus();
			}
			l_armyCount+=l_tempContinentCount;
			p_playersList.get(i).setD_armyNumber(l_armyCount);
			d_logEntryBuffer.log("Player "+p_playersList.get(i).getD_playerName()+" has been alloted "+p_playersList.get(i).getD_armyNumber()+" amries for this round");
			System.out.println(ColorCoding.ANSI_GREEN+"Player "+p_playersList.get(i).getD_playerName()+" has been alloted "+p_playersList.get(i).getD_armyNumber()+" amries for this round"+ColorCoding.ANSI_RESET);
		}
	}
	
	/**
	 *
	 * This method is used to show the countries owned by a particular player.
	 * @param p_player refers to the individual player.
	 * @param p_Displayflag refers to flag being displayed.
	 * 
	 * @return list of countries
	 *
	 */
	 
	public static ArrayList<String> showPlayersCountry(Player p_player,int p_Displayflag) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		ArrayList<Country> l_country = new ArrayList<>();
		if(p_Displayflag==1) {
			d_logEntryBuffer.log("Player:" + p_player.getD_playerName()+ " has following countries assigned");
			System.out.println("\nPlayer:" + p_player.getD_playerName()+ " has following countries assigned");
		}
		l_country= (ArrayList<Country>) p_player.getD_country();
		ArrayList<String> l_countryList=new ArrayList<>();
		for(Country c:l_country)
		{
			if(p_Displayflag==1) {
				d_logEntryBuffer.log(c.getD_countryName());
				System.out.println(c.getD_countryName());
			}
			l_countryList.add(c.getD_countryName());
		}
		return l_countryList;
	}
	
	/**
	 *
	 * This method is used to check whether the army is available or not.
	 * @param p_army refers to the army left with player.
	 * @param p_player refers to the player object
	 * 
	 * @return boolean type
	 *
	 */
	
	public static boolean checkArmyAvailable(int p_army,Player p_player) {
		if(p_player.getD_armyNumber() >= p_army)
			return true;
		else 
			return false;			
	}
	
	/**
	 *
	 * This method is used to advance troops to country belonging to same player or to attack if country doesn't belong to player
	 * @param p_player refers to the individual player.
	 * @param p_playersArray refers to the list of players.
	 * @param p_fromCountry refers to the country from which troops are advanced or attacked to other country 
	 * @param p_toCountry refers to the country to which troops are sent or attack happens
	 * @param p_troops refers to number of troops to be sent or used for attacking 
	 * @param p_continent refers to list of continents.
	 * @param p_connectivity refers to map and used to access countries and continents in the map 
	 * @param fortify_flag refers to the fortify phase 
	 * 
	 * @return 0 for successful advance/attack and 1 for failed advance/attack
	 *
	 */
	public static int advance(Player p_player,ArrayList<Player> p_playersArray,Country p_fromCountry,Country p_toCountry,int p_troops,ArrayList<Continent> p_continent,Connectivity p_connectivity, int fortify_flag) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		if(p_connectivity.getD_countriesList().contains(p_fromCountry) && p_connectivity.getD_countriesList().contains(p_toCountry))
		{
			if(p_player.getD_country().contains(p_fromCountry))
			{
				if(p_player.getD_country().contains(p_toCountry))
				{
					if(p_fromCountry.getD_neighbours().contains(p_toCountry.getD_countryId()))
					{
						if(p_troops>=0)
						{
							if(p_troops<=p_fromCountry.getD_armyCount())
							{

								int l_troopsaddition = p_toCountry.getD_armyCount() + p_troops;
								p_toCountry.setD_armyCount(l_troopsaddition);
								int l_troopsDeduction = p_fromCountry.getD_armyCount() - p_troops;
								p_fromCountry.setD_armyCount(l_troopsDeduction);
								d_logEntryBuffer.log(p_troops+" Troops advanced from "+p_fromCountry.getD_countryName()+" to "+p_toCountry.getD_countryName());
								System.out.println(ColorCoding.ANSI_GREEN+p_troops+" Troops advanced from "+p_fromCountry.getD_countryName()+" to "+p_toCountry.getD_countryName()+ColorCoding.ANSI_RESET);
								d_logEntryBuffer.log("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
								System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
								d_logEntryBuffer.log("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops");
								System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops");
								return 0;
							}
							else
							{
								d_logEntryBuffer.log("Error: Can't move more armies than the armies in the country");
								System.out.println(ColorCoding.ANSI_RED+"Error: Can't move more armies than the armies in the country"+ColorCoding.ANSI_RESET);
								return 1;
							}
						}
						else
						{
							d_logEntryBuffer.log("Error: Can't move negative armies");
							System.out.println(ColorCoding.ANSI_RED+"Error: Can't move negative armies"+ColorCoding.ANSI_RESET);
							return 1;
						}
					}
					else
					{
						d_logEntryBuffer.log("Error: "+p_fromCountry.getD_countryName()+ " is not the neighbour of "+ p_toCountry.getD_countryName()+" troops can't be advanced or country can't be attacked");
						System.out.println(ColorCoding.ANSI_RED+"Error: "+p_fromCountry.getD_countryName()+ " is not the neighbour of "+ p_toCountry.getD_countryName()+" troops can't be advanced or country can't be attacked"+ColorCoding.ANSI_RESET);
						return 1;
					}	
				}
				else
				{
					if(fortify_flag==0)
					{
						Player l_toplayer = findPlayerWithCountry(p_playersArray,p_toCountry);
					
					if(l_toplayer != null)
					{
						if(!p_player.getDiplomacyWith().contains(l_toplayer.getD_playerId()))
						{							
							attack(p_player,p_playersArray,p_fromCountry,p_toCountry,p_troops,p_continent,p_connectivity);
							return 0;
						}
						else
						{
							d_logEntryBuffer.log("Attack is not possible between "+p_fromCountry.getD_countryName()+" and "+p_toCountry.getD_countryName()+" because of diplomacy");
							System.out.println(ColorCoding.ANSI_RED+"Attack is not possible between "+p_fromCountry.getD_countryName()+" and "+p_toCountry.getD_countryName()+" because of diplomacy"+ColorCoding.ANSI_RESET);
							return 1;
						}	
					}
					else
					{
						attack(p_player,p_playersArray,p_fromCountry,p_toCountry,p_troops,p_continent,p_connectivity);
						return 0;
					}		
					}
					else
					{
						d_logEntryBuffer.log("Attack cannot be done on fortify phase");
						System.out.println(ColorCoding.ANSI_RED+"Attack cannot be done on fortify phase"+ColorCoding.ANSI_RESET);
						return 1;
					}
				}
			}
			else
			{
				d_logEntryBuffer.log("Error: "+p_fromCountry.getD_countryName()+" doesn't belongs to player from where he wants to advance the troops");
				System.out.println(ColorCoding.ANSI_RED+"Error: "+p_fromCountry.getD_countryName()+" doesn't belongs to player from where he wants to advance the troops"+ColorCoding.ANSI_RESET);
				return 1;
			}
		}
		else
		{
			if(!p_connectivity.getD_countriesList().contains(p_fromCountry))
			{
				d_logEntryBuffer.log("Error: Country "+p_fromCountry.getD_countryName()+" doesn't belong to Map");
				System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_fromCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
				return 1;
			}
			else if(p_toCountry!=null && !p_connectivity.getD_countriesList().contains(p_toCountry))
			{
				d_logEntryBuffer.log("Error: Country "+p_toCountry.getD_countryName()+" doesn't belong to Map");
				System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_toCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
				return 1;
			}
			return 1;
		}
	}


	/**
	 *
	 * This method is used to find player with given country 
	 * @param p_playersArray refers to the list of players. 
	 * @param p_Country refers to the country 
	 * 
	 * @return player to which the country belongs or null if country dosn't belongs to anyone 
	 *
	 */
	public static Player findPlayerWithCountry(ArrayList<Player> p_playersArray, Country p_Country) 
	{
		for(Player p: p_playersArray)
		{
			if(p.getD_country().contains(p_Country))
				return p;
		}
		return null;
	}
	
	/**
	 *
	 * This method is used to attack on other country 
	 * @param p_player refers to the individual player.
	 * @param p_playersArray refers to the list of players.
	 * @param p_fromCountry refers to the country from which troops are advanced or attacked to other country 
	 * @param p_toCountry refers to the country to which troops are sent or attack happens
	 * @param p_troops refers to number of troops to be sent or used for attacking 
	 * @param p_continent refers to list of continents.
	 * @param p_connectivity refers to map and used to access countries and continents in the map 
	 * 
	 * @return 0 for successful attack and 1 for failed attack
	 *
	 */
	public static int attack(Player p_player,ArrayList<Player> p_playersArray,Country p_fromCountry,Country p_toCountry,int p_troops,ArrayList<Continent> p_continent,Connectivity p_connectivity)
	{	
		System.out.println("Calling Attack");
		if(p_troops>=0)
		{
			try{
				if(p_connectivity.getD_countriesList().contains(p_fromCountry) && p_connectivity.getD_countriesList().contains(p_toCountry))
				{
					if(p_player.getD_country().contains(p_fromCountry))
					{
						if(!p_player.getD_country().contains(p_toCountry))
						{
							if(p_fromCountry.getD_neighbours().contains(p_toCountry.getD_countryId()))
							{
								if(p_troops<=p_fromCountry.getD_armyCount())
								{
									int l_troopsSource = p_troops;
									int l_troopsDestination = p_toCountry.getD_armyCount();
									
									if(l_troopsDestination>0 && l_troopsDestination> 0)
									{
										int attackRange =(60 - 0)+1; 
										int attackRandom = (int)(Math.random() * attackRange) + 0;
										l_troopsSource = Math.round(l_troopsSource*((float)attackRandom/100));
										
										int defendRange =(70 - 0)+1; 
										int defendRandom = (int)(Math.random() * defendRange) + 0;
										l_troopsDestination = Math.round (l_troopsDestination*((float)defendRandom/100));

									}
									if(l_troopsSource>l_troopsDestination)
									{
										removeCountry(p_playersArray,p_toCountry,p_continent);	
										p_player.getD_country().add(p_toCountry);
										System.out.println(ColorCoding.ANSI_GREEN+"Attack Successfull!!"+ColorCoding.ANSI_RESET);
										p_player.getCards().add(generateCard());
										updateContinent(p_playersArray, p_continent);

										int l_troopsLeft = l_troopsSource - l_troopsDestination; 
										p_toCountry.setD_armyCount(l_troopsLeft);
										p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);

										System.out.println("Attack on "+p_toCountry.getD_countryName()+ " from "+p_fromCountry.getD_countryName()+" was successful.");
										System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
										System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
									}
									else if(l_troopsSource<l_troopsDestination)
									{
										int l_troopsLeft = l_troopsDestination - l_troopsSource;
										p_toCountry.setD_armyCount(l_troopsLeft);
										p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);
										System.out.println(p_toCountry.getD_countryName()+" defended itself successuflly from "+p_fromCountry.getD_countryName());
										System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
										System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
									}
									else
									{
										p_toCountry.setD_armyCount(0);
										p_fromCountry.setD_armyCount(p_fromCountry.getD_armyCount()-p_troops);
										System.out.println(p_toCountry.getD_countryName()+" defended itself successuflly from "+p_fromCountry.getD_countryName());
										System.out.println("After change "+p_fromCountry.getD_countryName()+" has "+p_fromCountry.getD_armyCount()+" troops");
										System.out.println("After change "+p_toCountry.getD_countryName()+" has "+p_toCountry.getD_armyCount()+" troops" );
									}

									return 0;
								}
								else
								{
									System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack with more armies then the armies in the country"+ColorCoding.ANSI_RESET);
									return 1;
								}

							}
							else
							{
								System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack the country "+p_toCountry.getD_countryName()+" as it is not a neighbour of "+p_fromCountry.getD_countryName()+ColorCoding.ANSI_RESET);
								return 1;
							}
						}
						else
						{
							System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack Own country"+ColorCoding.ANSI_RESET);
							return 1;
						}		
					}
					else
					{
						System.out.println(ColorCoding.ANSI_RED+"Error: "+p_fromCountry.getD_countryName()+" doesn't belongs to player from where he wants to attack"+ColorCoding.ANSI_RESET);
						return 1;
					}
				}
				else
				{
					if(!p_connectivity.getD_countriesList().contains(p_fromCountry))
					{
						System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_fromCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
						return 1;
					}
					else if(!p_connectivity.getD_countriesList().contains(p_toCountry))
					{
						System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_toCountry.getD_countryName()+" doesn't belong to Map"+ColorCoding.ANSI_RESET);
						return 1;
					}
					return 1;
				}			
			}catch(Exception e)
			{
				System.out.println(ColorCoding.ANSI_RED+"Error: Country "+p_toCountry+" Does not exist"+ColorCoding.ANSI_RESET);
				return 1;
			}
		
		}
		else
		{
			System.out.println(ColorCoding.ANSI_RED+"Error: Can't attack with negative number of troops"+ColorCoding.ANSI_RESET);
			return 1;
		}
	}

	/**
	 *
	 * This method is used to remove country form players if it is with any players and update continent list with players 
	 * or remove it form neutral country list. 
	 * @param p_playersArray refers to the players Array.
	 * @param p_country refers to the country.
	 * @param p_continent refers to list of continent.
	 * 
	 * @return 0 for successful removal of country and 1 if failed.
	 *
	 */	
	public static int removeCountry(ArrayList<Player> p_playersArray,Country p_country,ArrayList<Continent> p_continent) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		for(Player p:p_playersArray)
		{
			if(p.getD_country().contains(p_country))
			{
			    p.getD_country().remove(p_country);
			    updateContinent(p_playersArray, p_continent);
				return 0;
			}
		}
		if(l_neutralCountry.contains(p_country))
		{
			l_neutralCountry.remove(p_country);
			updateContinent(p_playersArray, p_continent);
			return 0;
		}
		else {
			d_logEntryBuffer.log("Error: "+p_country.getD_countryName()+" doesnot exist.");
			System.out.println(ColorCoding.ANSI_RED+"Error: "+p_country.getD_countryName()+" doesnot exist."+ColorCoding.ANSI_RESET);
		}
		return 1;
	}
	
	/**
	 * 
	 * This method is used to bomb one country when the other player uses the bomb card.
	 * @param p_player represents the player
	 * @param p_playersArray refers to the list of players.
	 * @param p_toCountry refers to the country to which attack is made.
	 * @param p_continent refers to list of continents.
	 * @return l_targetArmies refers to the number of armies left on that country after bomb card is used.
	 */
	public static int bomb(Player p_player, ArrayList<Player> p_playersArray,Country p_toCountry,ArrayList<Continent> p_continent) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		
		int l_targetArmies=0;
		int l_countryFoundFlag=0;
		for(Country c:p_player.getD_country())
		{
			if(c.getD_neighbours().contains(p_toCountry.getD_countryId()))
				l_countryFoundFlag++;
		}
		if(l_countryFoundFlag!=0) {
			l_targetArmies=(int) Math.floor(p_toCountry.getD_armyCount()/2);
			p_toCountry.setD_armyCount(l_targetArmies);
			if(p_toCountry.getD_armyCount()==0) {
				removeCountry(p_playersArray, p_toCountry, p_continent);
				updateContinent(p_playersArray, p_continent);
				d_logEntryBuffer.log("The Country "+ p_toCountry + " is a now a neutral Country");
				System.out.println("The Country "+ p_toCountry + " is a now a neutral Country");
			}
			else {
				d_logEntryBuffer.log("The country "+ p_toCountry + "now has " + l_targetArmies+ " armies");
				System.out.println("The country "+ p_toCountry + "now has " + l_targetArmies+ " armies");
			}
		} else {
			System.out.println(p_toCountry + " is not neighbour to any of the existing lands.");
		}
		return l_targetArmies;
	}
	
	/**
	 *
	 * This method is used to update continent list of players if country gets added or removed. 
	 * @param p_playersArray refers to the players Array.
	 * @param p_continentList refers to list of continent.
	 * 
	 * @return 0 after successful update
	 *
	 */	
	public static int updateContinent(ArrayList<Player> p_playersArray, ArrayList<Continent> p_continentList) {
		
		for(Player p:p_playersArray)
		{
			p.setD_continent(new ArrayList<Continent>());
		}
		
		for(int i=0;i<p_playersArray.size();i++) {
			ArrayList<String> l_tempCountry=new ArrayList<>();
			ArrayList<String> tempCountryInContinent=new ArrayList<>();
			ArrayList<Continent> l_continentsOwned=new ArrayList<>();
			for(int j=0;j<p_playersArray.get(i).getD_country().size();j++)
				l_tempCountry.add(p_playersArray.get(i).getD_country().get(j).getD_countryName());
			for(int j=0;j<p_continentList.size();j++)
			{
				tempCountryInContinent=new ArrayList<>();
				for(int k=0;k<p_continentList.get(j).getD_countries().size();k++) tempCountryInContinent.add(p_continentList.get(j).getD_countries().get(k).getD_countryName());
				if(l_tempCountry.containsAll(tempCountryInContinent))
				{
					l_continentsOwned.add(p_continentList.get(j));
					p_playersArray.get(i).setD_continent(l_continentsOwned);
				}
			}				
		}
		return 0;
	}
	
	/**
	 *
	 * This method is used to airlift armies from one country to another once the player uses airlift card.
	 * @param p_sourceCountryObj refers to the source country for airlift.
	 * @param p_targetCountryObj refers to the target country for airlift.
	 * @param p_armiesToAirlift refers to the number of armies player wants to airlift.
	 * @param p_player refers to the player who used airlift card.
	 * 
	 * @return result
	 *
	 */
	public static boolean AirliftDeploy(String p_sourceCountryObj, String p_targetCountryObj, int p_armiesToAirlift, Player p_player) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		
		String l_sourceCountryVar = p_sourceCountryObj;
	    String l_targetCountryVar = p_targetCountryObj;
	    
	    int l_armiesToAirlift = p_armiesToAirlift;
	    
	    Player l_player = p_player;
	    
	    
	    int flag =0;
	    int flag1 =0;

	    ArrayList<Country> l_country = new ArrayList<>();
	    l_country= (ArrayList<Country>) p_player.getD_country();
	    Country l_sourceCountry=new Country();
	    Country l_targetCountry=new Country();
	    
	    
		for(Country c:l_country)
		{
			if(c.getD_countryName().equals(l_sourceCountryVar)) {
				
				flag=1;
				l_sourceCountry=c;
			}
		}
	    if (flag==0) {
	    	d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the source country.");
            System.out.println(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the source country.");
            return false;
	    }
	    
	    for(Country c:l_country)
		{
			if(c.getD_countryName().equals(l_targetCountryVar)) {
				flag1=1;
				l_targetCountry=c;
			}
		}
	    if (flag1==0) {
	    	d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the target country.");
            System.out.println(l_player.getD_playerName() + " can not use Airlift card as it doesn't own the target country.");
            return false;
	    }
	    
	    if(l_sourceCountry.getD_armyCount()<l_armiesToAirlift) {
	    	d_logEntryBuffer.log(l_player.getD_playerName() + " doesn't have enough army on this country to airlift.");
	    	System.out.println(l_player.getD_playerName() + " doesn't have enough army on this country to airlift.");
            return false;
	    }
	  
	    int x = l_sourceCountry.getD_armyCount();
	    x = x - l_armiesToAirlift;
	    
	    int y  = l_targetCountry.getD_armyCount();
	    y = y + l_armiesToAirlift;
	    
	    l_sourceCountry.setD_armyCount(x);
	    l_targetCountry.setD_armyCount(y);
	    
		return true;
	}
	
	/**
	 *
	 * This method is used to make a country neutral and increase its army count once the player uses blockade card.
	 * @param p_sourceCountryObj refers to the country for blockade.
	 * @param p_player refers to the player who used blockade card.
	 * @param p_playersArray refers to the list of players.
	 * @param p_continent refers to the list of continents.
	 * 
	 * @return result
	 *
	 */
	public static boolean Blockade(String p_sourceCountryObj,Player p_player,ArrayList<Player> p_playersArray,ArrayList<Continent> p_continent) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		
		String l_sourceCountry = p_sourceCountryObj;
	    Player l_player = p_player;
	    int flag =0;
	    ArrayList<Country> l_country = new ArrayList<>();
	    l_country= (ArrayList<Country>) p_player.getD_country();
	    Country l_sourceCountrynew = new Country();
	    
		for(Country c:l_country) {
			if(c.getD_countryName().equals(l_sourceCountry)) {
				
				flag=1;
				l_sourceCountrynew=c;
			}
		}
	    if (flag==0) {
	    	d_logEntryBuffer.log(l_player.getD_playerName() + " can not use Blockade card as it doesn't own "+ l_sourceCountrynew.getD_countryName());
            System.out.println(l_player.getD_playerName() + " can not use Blockade card as it doesn't own "+ l_sourceCountrynew.getD_countryName());
            return false;
	    }
	    
	    if(l_sourceCountrynew.getD_armyCount()<0) {
	    	d_logEntryBuffer.log(l_player.getD_playerName() + " doesn't have enough army on this country to blockade.");
	    	System.out.println(l_player.getD_playerName() + " doesn't have enough army on this country to blockade.");
            return false;
	    }
	    
	    int x = l_sourceCountrynew.getD_armyCount();
	    x = x*3;
	    
	    l_sourceCountrynew.setD_armyCount(x);
	    removeCountry(p_playersArray, l_sourceCountrynew, p_continent);
	    l_neutralCountry.add(l_sourceCountrynew);
	    
		updateContinent(p_playersArray, p_continent);
		d_logEntryBuffer.log("The Country "+ p_sourceCountryObj + " is a now a neutral Country with army count "+l_sourceCountrynew.getD_armyCount());
		System.out.println("The Country "+ p_sourceCountryObj + " is a now a neutral Country with army count "+l_sourceCountrynew.getD_armyCount());

	    return true;
	}
	
	/**
	 *
	 * This method will generate random cards and adds to players if player capture the new territory.
	 * @return returns generated card
	 */	
	public static String generateCard() {
		String[] l_cards = {"bomb","blockade","airlift","diplomacy"};
		Random ran = new Random();
		 
		return l_cards[ran.nextInt(l_cards.length)];
		
	}
	/**
	 * This function decides the winner of tha game
	 * @param p_players array of players
	 * @param p_connectivity map content
	 * @return returns the winner of the game
	 */
	
	public static Player winnerPlayer(ArrayList<Player> p_players,Connectivity p_connectivity) {
		Player l_winner = new Player();
		for(Player p:p_players)
		{
			if(p.getD_country().size()==p_connectivity.getD_countriesList().size())
			{
				l_winner.setD_playerId(p.getD_playerId());
				l_winner.setD_playerName(p.getD_playerName());
				l_winner.setD_armyNumber(p.getD_armyNumber());
				return l_winner;
			}
		}
		return null;
	}
	
	/**
	 *
	 * This method adds diplomacy between two players if any of the used diplomacy card
	 * @param p_player refers to the player.
	 * @param p_playersArray refers to the players Array.
	 * @param p_toPlayerID refers to other player ID with whom diplomacy is set 
	 * 
	 */	
	public static void negotiate(Player p_player,ArrayList<Player> p_playersArray, String p_toPlayerID) {
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		
		p_player.addDiplomacyWith(Integer.parseInt(p_toPlayerID));
		
		for(Player p: p_playersArray) {
			if(p.getD_playerId() == Integer.parseInt(p_toPlayerID)) {
				p.addDiplomacyWith(p_player.getD_playerId());
			}
			d_logEntryBuffer.log(p.getD_playerName()+" = "+p.getDiplomacyWith());
			System.out.println(p.getD_playerName()+" = "+p.getDiplomacyWith());
		}
		
		
	}
	
	/**
	 *
	 * This method will remove diplomacy between players after each turn
	 * @param p_playersArray refers to the players Array.
	 * 
	 */	
	public static void resetDiplomacy(ArrayList<Player> p_playersArray) {

		for(Player p:p_playersArray)
		{
			p.clearDiplomacyWith();
		}
		
	}
	
	/**
	 *
	 * This method will add country to neutral country list.
	 * @param c refers to the country.
	 * 
	 */	
	public static void addNutrealCountry(Country c)
	{
		l_neutralCountry.add(c);
	}
	
	/**
	 *
	 * This method will return neutral country list.
	 *
	 *@return neutral country list
	 */	
	public static ArrayList<Country> getNutrealCountry()
	{
		return l_neutralCountry;
	}
	
	
	/**
	 *
	 * This method will clear the neutral country list.
	 *
	 */	
	public static void clearNutrealCountry()
	{
		l_neutralCountry.clear();
	}
}
