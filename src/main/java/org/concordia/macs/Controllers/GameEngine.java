package org.concordia.macs.Controllers;

import java.io.IOException;

import java.util.*;


import org.concordia.macs.Models.*;

import org.concordia.macs.Utilities.MapEditor;
import org.concordia.macs.Utilities.LoadMap;
import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.SaveMap;
import org.concordia.macs.Utilities.PlayersGameplay;


import org.concordia.macs.View.ShowMap;

/**
 * @author Mahfuzzur Rahman
 

* The GameEngine class serves as the entry point for the Warzone game, managing player commands and game phases.
*
*/

public class GameEngine {
	
	private static ArrayList<Player> l_playersArray = new ArrayList<Player>();
	
	
	/**
	* Retrieves the list of players.
	* @return The list of players.
	**/
	public static ArrayList<Player> getL_playersArray()
	{
		return l_playersArray;
	}
	
	
	

	/**
	* Starts the game and handles user input.
	* @param args the command line arguments.
	* @throws IOException when there is an error reading a file.
	**/
	public static void main(String[] args) throws IOException {

		Scanner l_scanner = new Scanner(System.in);
		Connectivity l_mainConnectivity=new Connectivity();
		
		l_mainConnectivity.setD_continentList(new ArrayList<Continent>());
		l_mainConnectivity.setD_countryList(new ArrayList<Country>());
		
		String l_gamePhase="startup";
		
		System.out.println("\n\n-------- Welcome to Warzone --------\n");
		System.out.println("Enter 'start' to begin or 'exit' to quit.");
		
		String l_option = "";
		int l_flag = 0;
		
		do
		{	
			if(l_flag==0)
			{
				
				l_option = l_scanner.next();
				l_scanner.nextLine();
				l_flag =1;
				
				if(l_option.equals("exit")) {
					break;
				}
				
			}
			
			else if(l_flag == 1 && l_option.equals("start") && l_gamePhase.equals("startup"))
			{
				System.out.println("Enter Warzone commands:\n"
						+ "1. editcontinent\n"
						+ "2. editcountry\n"
						+ "3. editneighbor\n"
						+ "4. showmap\n"
						+ "5. savemap\n"
						+ "6. validatemap\n"
						+ "7. loadmap");
				
				
				String l_str = l_scanner.nextLine();
				String[] l_commands = l_str.split(" "); 
				
				
				if(l_commands[0]!= null)
				{
					switch(l_commands[0])
					{
					
					
					case "exit":
						l_option = "exit";
						break;
					
						
					case "editcontinent":
						l_gamePhase="startup";
						
						for(int i=1; i<l_commands.length;)
						{
							
							if(l_commands[i].equals("-add"))
							{
								System.out.println("Adding continent...");
									MapEditor.addContinent(l_commands[i+1],Integer.parseInt(l_commands[i+2]), l_mainConnectivity);
									i=i+3;
							}
							
							else if(l_commands[i].equals("-remove") )
							{
								System.out.println("Removing continent...");
									MapEditor.removeContinent(l_commands[i+1], l_mainConnectivity);
									i=i+2;
							}
							
							else
							{
								System.out.println("Invalid command.");
								break;
							}
						}
						
						break;
					
						
					case "editcountry":
						l_gamePhase="startup";
						
						for(int i=1; i<l_commands.length;)
						{
							if(l_commands[i].equals("-add"))
							{
								System.out.println("Adding country...");
								MapEditor.addCountry(l_commands[i+1], l_commands[i+2], l_mainConnectivity);
								i = i+3;
							}
							
							else if(l_commands[i].equals("-remove"))
							{
								System.out.println("Removing country...");
								MapEditor.removeCountry(l_commands[i+1], l_mainConnectivity);
								i=i+2;
							}
							
							else
							{
								System.out.println(ColorCoding.red+"ERROR: Invalid Command"+ColorCoding.blank);
							}
						}
						
						break;
					
						
					case "editneighbor":
						l_gamePhase="startup";
						
						
						for(int i=1; i<l_commands.length;)
						{
							if(l_commands[i].equals("-add"))
							{
								System.out.println("Adding neighbour...");
								MapEditor.addNeighbour(Integer.parseInt(l_commands[i+1]), Integer.parseInt(l_commands[i+2]), l_mainConnectivity);
								i=i+3;
							}
							
							else if(l_commands[i].equals("-remove"))
							{
								System.out.println("Removing neighbour...");
								MapEditor.removeNeighbour(Integer.parseInt(l_commands[i+1]), Integer.parseInt(l_commands[i+2]), l_mainConnectivity,1);
								i=i+3;
							}
							
							else
							{
								System.out.println(ColorCoding.red+"ERROR: Invalid Command"+ColorCoding.blank);
							}
						}
						
						break;
					
						
					case "showmap":
						l_gamePhase="startup";
						ShowMap.showMap(l_mainConnectivity.getD_continentList(), l_mainConnectivity.getD_countryList());
						break;
					
						
					case "savemap":
						l_gamePhase="startup";
						SaveMap.saveMap(l_mainConnectivity);
						break;
					
						
					case "validatemap":
						l_gamePhase="startup";
						break;
					
						
					case "loadmap":
						l_gamePhase="startup";
						
						if(l_commands.length == 2)
						{
							LoadMap.loadMap(l_mainConnectivity,l_commands[1]);
						} 
						
						else {
							System.out.println(ColorCoding.red+"No map entered. Kindly enter exact name of map to be loaded"+ColorCoding.blank);
						}
						
						break;
						
					case "gameplayer":
						
						for(int i=1;i<l_commands.length;)
						{
							
							if(l_commands[i].equals("-add"))
							{
								Player l_player = new Player();
								l_player.setD_playerName(l_commands[i+1]);
								l_playersArray.add(l_player);
								System.out.println(ColorCoding.green+l_player.getD_playerName()+" added successfully"+ColorCoding.blank);
								i=i+2;
								
							}
							
							else if(l_commands[i].equals("-remove"))
							{
								for(int j=0;j<l_playersArray.size();j++)
								{
									if(l_commands[i+1].equals(l_playersArray.get(j).getD_playerName()))
									{
										System.out.println(ColorCoding.green+l_playersArray.get(j).getD_playerName()+" removed successfully"+ColorCoding.blank);
										l_playersArray.remove(j);
										i=i+2;
										break;
									}
								}
							}
						}
						
						break;
					
						
					case "assigncountries":
						l_gamePhase="startup";
						
						if(l_playersArray.size()>0)
						{
							if(PlayersGameplay.assigncountries(l_playersArray,l_mainConnectivity.getD_countryList(),l_mainConnectivity.getD_continentList())==0)
							{
								System.out.println(ColorCoding.green+"Countries assigned to the players successfully"+ColorCoding.blank+"\n");
								l_gamePhase="mainGameLoop";
							}
						}
						
						else
						{
							System.out.println(ColorCoding.red+"ERROR: No players to assign Countries"+ColorCoding.blank);
						}
						
						break;
					
						
						
					case "deploy":
						
						break;
					
						
					case "playersCountry":
						
						for(Player p:l_playersArray)
						{
							PlayersGameplay.showPlayersCountry(p,1);
						}
						
						break;
						
						
					default:
						System.out.println(ColorCoding.red+"Invalid Command"+ColorCoding.blank);
							
					}	
				}
				
			}
			else
			{
				if(l_gamePhase.equals("startup"))
				{
					System.out.println(ColorCoding.red+"ERROR: Invalid Command"+ColorCoding.blank);
					l_flag = 0;
				}
			}
			
			
			if(l_gamePhase.equals("mainGameLoop"))
			{
				PlayersGameplay.assignArmiesToPlayers(l_playersArray);
				System.out.println("The Warzone Game Begins!!\n -------------------------------------------");
				
				for(int i=0;i<l_playersArray.size();i++)
				{
					System.out.println("Player "+ Integer.sum(i,1) +"("+l_playersArray.get(i).getD_playerName()+") has Army Count: "+l_playersArray.get(i).getD_armyCount());
					PlayersGameplay.showPlayersCountry(l_playersArray.get(i),1);
					System.out.println();
				}
				
				int l_temp =1;
				int flag=0;
				
				while(l_temp>0) {
					
					for(int i=0;i<l_playersArray.size();i++)
					{
						String l_userOrder="";
						Order l_order=new Order();
						
						if(l_playersArray.get(i).getD_armyCount()!=0)
						{
							System.out.println("Player "+l_playersArray.get(i).getD_playerName()+" deploy your troops:");
							l_userOrder=l_scanner.nextLine();
							
							String[] l_tempOrderListArray=l_userOrder.split(" ");
							
							for(int j=0;j<l_playersArray.get(i).getD_Country().size();j++)
							{
								if(Integer.parseInt(l_tempOrderListArray[1])==(l_playersArray.get(i).getD_Country().get(j).getD_countryId()))
								{
									l_order.setD_fromCountry(l_playersArray.get(i).getD_Country().get(j));
								}
							}	
							
							if(PlayersGameplay.checkArmyAvailable(Integer.parseInt(l_tempOrderListArray[2]),l_playersArray.get(i)))
							{
								l_order.setD_numberOfArmies(Integer.parseInt(l_tempOrderListArray[2]));
								l_playersArray.get(i).setD_Order(l_order);
								l_playersArray.get(i).issue_order();
							}
							
							else
							{
								System.out.println(ColorCoding.red+"Error: Please enter valid number of troops"+ColorCoding.blank);
								i--;
							}
							
							if(l_playersArray.get(i).getD_armyCount()==0) flag++;	
						}
						
						if(flag==l_playersArray.size())
						{
							l_temp=0;
							break;
						}
					}
				}
				
				PlayersGameplay.assignArmiesToPlayers(l_playersArray);
				
				l_temp=1;
				flag=0;
				
				while(l_temp>0) 
				{
					for(int i=0;i<l_playersArray.size();i++)
					{
						if(l_playersArray.get(i).getD_armyCount()!=0)
						{
							l_playersArray.get(i).getD_Order().execute(l_playersArray.get(i), l_playersArray.get(i).next_order());
							if(l_playersArray.get(i).getD_armyCount()==0)
							{
								flag+=1;
							}
						}
						
						if(flag==l_playersArray.size())
						{
							l_temp=0;
							break;
						}
						
					}
					
					
				}
				
				l_gamePhase = "execute";
				l_flag=0;
				
				System.out.println(ColorCoding.green+"All Armies have been successfully deployed. Enter command to proceed "+ColorCoding.blank);
			}
			
		}while(l_option !="exit");
		
		System.out.println("Thank you for Playing the Game");
		
		l_scanner.close();
		System.exit(0);
		
	}

}
