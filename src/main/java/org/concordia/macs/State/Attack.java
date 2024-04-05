package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;

import org.concordia.macs.Strategy.CheaterPlayerStrategy;
import org.concordia.macs.Strategy.HumanPlayerStrategy;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.PlayersGameplay;
import org.concordia.macs.Utilities.SaveGame;

import org.concordia.macs.View.ShowMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Mahfuzzur Rahman
 * Concrete implementation of the State pattern.
 * Represents the Attack phase, defining behavior for commands like advance, attack, and card commands.
 */

public class Attack extends MainPlay 
{
	static int l_testFlag =0;

	/**
	 * Constructor for the Attack class.
	 *
	 * @param p_ge The associated GameEngine instance.
	 */
	Attack(GameEngine p_ge)
	{
		super(p_ge);
	}

	/**
	 * Transition to the next state, which is Fortify.
	 */

	public void next(Connectivity p_connectivity) 
	{
		ge.setPhase(new Fortify(ge));
	}

	/**
	 * Reinforcement is not applicable in the Attack phase. Prints an invalid command message.
	 *
	 * @param p_connectivity The Connectivity object.
	 */
	public void reinforce(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage(); 
	}

	/**
	 * Handles the logic for player commands related to attacks.
	 *
	 * @param p_connectivity The Connectivity object.
	 */
	public void attack(Connectivity p_connectivity) 
	{
		LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
		
		int terminateFlag=0;
		int winner_check=0;
		int l_winner=0;
		int l_flag1=0;
		int l_executeOrder=0;
		int l_countOrder =0;

		while(l_winner==0) 
		{
			terminateFlag=0;
			l_flag1=0;

			ArrayList<String> playerNames=new ArrayList<>();

			if(ge.getCheckIfTest() && l_testFlag == 3)
			{
				d_logEntryBuffer.log("Winner assigned for test case");
				System.out.println("Winner assigned for test case\n");
				l_playersArray.get(0).setD_country(p_connectivity.getD_countriesList());
			}

			if(winner_check>0)
			{
				ge.setPhase(new Fortify(ge));
				break;
			}
			
		do{
			Player winner = PlayersGameplay.winnerPlayer(l_playersArray, p_connectivity);

			if(winner !=null)
			{
				p_connectivity.setD_winnerPlayer(winner);
				d_logEntryBuffer.log("CONGRATULATIONS!!! Our Winner is:"+winner.getD_playerName());
				System.out.println("CONGRATULATIONS!!! Our Winner is:"+winner.getD_playerName());

				l_winner++;

				ge.setPhase(new End(ge));
				ge.getPhase().endGame(p_connectivity);
				return;
			}

		for(int i=0;i<l_playersArray.size();i++)
		{
			 l_flag1=1;

			do 
			{
			if(playerNames.contains(l_playersArray.get(i).getD_playerName()))
				continue;

			d_logEntryBuffer.log(l_playersArray.get(i).getD_playerName()+"Asked whether he/she wants to give a command");
			System.out.println(ColorCoding.ANSI_RESET+"\n"+l_playersArray.get(i).getD_playerName()+"!! Do you want to give command or pass?(Press enter to continue / pass)"+ColorCoding.ANSI_RESET);

			Scanner l_sc = new Scanner(System.in);
			String l_passContinue = "";

			if(ge.getCheckIfSave())
			{	
				l_passContinue = "exit";
			}
			else if(ge.getCheckIfTest() && !ge.getCheckIfSave())
			{
				if(l_testFlag == 0)
					l_passContinue = "continue";
				else if(l_testFlag > 0)
					l_passContinue = "pass";
				l_testFlag = l_testFlag+1;
			}
			else if(ge.getCheckIfTournament())
			{
				if(l_countOrder == l_playersArray.size())
					l_passContinue = "pass";
				else
				{
					l_passContinue = "continue";
					l_countOrder+=1;
				}
			}
			else
				l_passContinue=l_sc.nextLine();
			if(l_passContinue.equalsIgnoreCase("exit"))
			{
				Scanner sc= new Scanner(System.in);
		    	System.out.println("Do you want to save the game?");
		    	String choice = "";
		    	String l_filename="";
		    	if(ge.getCheckIfSave())
		    		choice = "yes";
		    	else
		    		choice = sc.nextLine();
		    	if(choice.equalsIgnoreCase("yes"))
		    	{
		    		System.out.println("Enter the command: ");
		    		if(!ge.getCheckIfSave())
		    		{
			    		String[] l_command= sc.nextLine().split(" ");
			    		l_filename=l_command[1];
		    		}

		    		if(ge.getCheckIfSave())
		    			l_filename = "gg";
		    		SaveGame sg = new SaveGame();
		    		try 
		    		{
						sg.saveGame(this,p_connectivity,l_filename);
					} catch (IOException e) 
		    		{

						e.printStackTrace();
					}
		            System.out.println("Thank you for Playing the Game");
		            System.exit(0);
		    	}
		    	else if (choice.equalsIgnoreCase("no"))
		    	{
		        System.out.println("Thank you for Playing the Game");
		    	}
		    	return;
			}

			if(l_passContinue.equals("pass"))
			{
				playerNames.add(l_playersArray.get(i).getD_playerName());
				terminateFlag++;
				break;
			}

			Order l_order=new Order();
			d_logEntryBuffer.log(l_playersArray.get(i).getD_playerName()+ "Asked for Command");
			System.out.println("\nEnter the Command for player: "+l_playersArray.get(i).getD_playerName());
			System.out.println("Cards available: "+l_playersArray.get(i).getCards());
			String l_orderinput = "";

			if(l_playersArray.get(i).getStrategy() instanceof HumanPlayerStrategy)
			{
				l_orderinput=l_sc.nextLine();
			}
			else
			{
				if(l_playersArray.get(i).issue_order())
				{
					Order o = l_playersArray.get(i).next_order();
					l_orderinput=o.getOrderContent();
				}
				else
					continue;
			}

			if(l_orderinput.equalsIgnoreCase("exit"))
			{
				ge.setPhase(new End(ge));
				ge.getPhase().endGame(p_connectivity);
				return;
			}
			String[] l_inputOrderArray=l_orderinput.split(" ");
			switch(l_inputOrderArray[0])
			{

			case "advance":
				l_order.setOrderContent(l_orderinput);
				l_playersArray.get(i).getD_playerOrder().add(l_order);
				l_flag1=1;
				break;

			case "bomb":
				l_order.setOrderContent(l_orderinput);
				l_playersArray.get(i).getD_playerOrder().add(l_order);
				l_flag1=1;
				break;

			case "blockade":
				l_order.setOrderContent(l_orderinput);
				l_playersArray.get(i).getD_playerOrder().add(l_order);
				l_flag1=1;
				break;

			case "airlift":
				l_order.setOrderContent(l_orderinput);
				l_playersArray.get(i).getD_playerOrder().add(l_order);
				l_flag1=1;
				break;

			case "negotiate":
				l_order.setOrderContent(l_orderinput);
				l_playersArray.get(i).getD_playerOrder().add(l_order);
				l_flag1=1;
				break;

			default:
				System.out.println(ColorCoding.ANSI_RED+"Invalid Command!!"+ColorCoding.ANSI_RESET);
				l_flag1=0;
			}

			}while(l_flag1==0);
			
		}
		
		}while(terminateFlag!=l_playersArray.size());
		
		
		 l_executeOrder=0;
		 HashSet<String> l_emptyOrders=new HashSet<>();
		
		while(l_executeOrder!=l_playersArray.size())
		{
			for(int j=0;j<l_playersArray.size();j++)
			{
				if(l_emptyOrders.contains(l_playersArray.get(j).getD_playerName()))
					continue;
				if(l_playersArray.get(j).getD_playerOrder().size()==0) 
				{
					l_emptyOrders.add(l_playersArray.get(j).getD_playerName());
					l_executeOrder++;
					continue;	
				}
				l_playersArray.get(j).getD_order().execute(l_playersArray.get(j), l_playersArray.get(j).next_order(),p_connectivity,1,0);
			}
		}
		ShowMap.showMap(p_connectivity.getD_continentsList(), p_connectivity.getD_countriesList(), l_playersArray);
		winner_check++;
		PlayersGameplay.resetDiplomacy(l_playersArray);
	}
		
		System.out.println("attack done");
		
	}

	/**
	 * Fortification is not applicable in the Attack phase. Prints an invalid command message.
	 *
	 * @param p_connectivity The Connectivity object.
	 */

	public void fortify(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage(); 
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void loadMap(Connectivity p_connectivity, String[] p_commands) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void validateMap(Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void editCountry(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void editContinent(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void editNeighbor(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void saveMap(Connectivity p_connectivity, String p_mapName) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public void setPlayers(String[] p_commands,Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */
	@Override
	public boolean assignCountries(Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		return false;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enableTournament(String mycommand) {
		// Implementation not needed for Attack phase
		
	}

	/**
	 * {@inheritDoc}
	 */

	public void endGame()
	{
		System.out.println("Thank you for Playing the Game");
		System.exit(0);
	}


}
