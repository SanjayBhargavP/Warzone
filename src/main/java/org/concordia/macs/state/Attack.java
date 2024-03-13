package org.concordia.macs.state;

import org.concordia.macs.Controllers.GameEngine;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.PlayersGameplay;

import org.concordia.macs.View.ShowMap;

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

	public void next() 
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

		while(l_winner==0) 
		{
			terminateFlag=0;
			l_flag1=0;

			ArrayList<String> playerNames=new ArrayList<>();

			if(ge.getCheckIfTest() && l_testFlag == 3)
			{
				d_logEntryBuffer.log("Winner assigned for test case");
				System.out.println("Winner assigned for test case\n");
				l_playersArray.get(0).setD_Country(p_connectivity.getD_countryList());
			}

			if(winner_check>0)
			{
				ge.setPhase(new Fortify(ge));
				break;
			}
			
		do{
			if(PlayersGameplay.winnerPlayer(l_playersArray, p_connectivity)!=null)
			{
				Player winner = PlayersGameplay.winnerPlayer(l_playersArray, p_connectivity);
				d_logEntryBuffer.log("CONGRATULATIONS!!! Our Winner is:"+winner.getD_playerName());
				System.out.println("CONGRATULATIONS!!! Our Winner is:"+winner.getD_playerName());

				l_winner++;

				ge.setPhase(new End(ge));
				ge.getPhase().endGame();	
			}

		for(int i=0;i<l_playersArray.size();i++)
		{
			 l_flag1=1;

			do 
			{
			if(playerNames.contains(l_playersArray.get(i).getD_playerName()))
				continue;

			d_logEntryBuffer.log(l_playersArray.get(i).getD_playerName()+"Asked whether he/she wants to give a command");
			System.out.println(ColorCoding.cyan+"\n"+l_playersArray.get(i).getD_playerName()+"!! Do you want to give command or pass?(Press enter to continue / pass)"+ColorCoding.blank);

			Scanner l_sc = new Scanner(System.in);
			String l_passContinue = "";

			if(ge.getCheckIfTest())
			{
				if(l_testFlag == 0)
					l_passContinue = "continue";
				else if(l_testFlag > 0)
					l_passContinue = "pass";
				l_testFlag = l_testFlag+1;
			}

			else
				l_passContinue=l_sc.nextLine();

			if(l_passContinue.equalsIgnoreCase("exit"))
			{
				System.out.println("Thank you for Playing the Game");
				System.exit(0);
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

			if(ge.getCheckIfTest())
			{
				String l_neighbor = Country.get_nameFromId(l_playersArray.get(i).getD_Country(), l_playersArray.get(i).getD_Country().get(0).getD_neighbours().get(0));
				System.out.println(l_neighbor);
				l_orderinput = "advance " + l_playersArray.get(i).getD_Country().get(0).getD_countryName() + " "+l_neighbor+ " "+l_playersArray.get(i).getD_Country().get(0).getD_armyDeployedOnCountry();
				System.out.println("For testcase, we have the following command\n"+l_orderinput);
			}
			else
				l_orderinput=l_sc.nextLine();

			if(l_orderinput.equalsIgnoreCase("exit"))
			{
				System.out.println("Thank you for Playing the Game");
				System.exit(0);
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
				System.out.println(ColorCoding.red+"Invalid Command!!"+ColorCoding.blank);
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
				l_playersArray.get(j).getD_Order().execute(l_playersArray.get(j), l_playersArray.get(j).next_order(),p_connectivity,1,0);
			}
		}
		ShowMap.showMap(p_connectivity.getD_continentList(), p_connectivity.getD_countryList(), l_playersArray);
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

	public void loadMap(Connectivity p_connectivity, String[] p_commands) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void validateMap(Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void editCountry(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void editContinent(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void editNeighbor(String[] p_commands, Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void saveMap(Connectivity p_connectivity, String p_mapName) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public void setPlayers(String[] p_commands) 
	{
		// Implementation not needed for Attack phase
		
	}

	 /**
     * {@inheritDoc}
     */

	public boolean assignCountries(Connectivity p_connectivity) 
	{
		// Implementation not needed for Attack phase
		return false;

		
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
