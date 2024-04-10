package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.PlayersGameplay;
import org.concordia.macs.Utilities.SaveGame;

import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;

import org.concordia.macs.Strategy.HumanPlayerStrategy;

import org.concordia.macs.View.ShowMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Mahfuzzur Rahman
 * Concrete state representing the game's reinforcement phase.
 * Allows players to deploy armies and proceed to the Attack phase.
 */
public class Reinforcement extends MainPlay 
{
	LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();

	/**
	 * Constructor for Reinforcement phase.
	 *
	 * @param p_ge The associated GameEngine object.
	 */
	public Reinforcement(GameEngine p_ge)
	{
		super(p_ge);
	}

	/**
	 * Allows players to deploy armies during the reinforcement phase.
	 * 
	 * @param p_connectivity refers to connectivity object
	 */
	public void reinforce(Connectivity p_connectivity) 
	{
		int l_temp =1;
		int flag=0;

		ArrayList<String> l_tempName=new ArrayList<>();
		ArrayList<Player> l_tempPlayerArray = new ArrayList<>();

		for(int i=0; i<l_playersArray.size(); i++)
		{
			l_tempPlayerArray.add(l_playersArray.get(i));
		}

		while(l_temp>0) 
		{
			for(int i=0;i<l_playersArray.size();i++)
			{
				String l_userOrder="";
				boolean l_outcomeIssueOrder = false;

				if(l_playersArray.get(i).getD_armyNumber()!=0 && !(l_tempName.contains(l_playersArray.get(i).getD_playerName())))
				{
					d_logEntryBuffer.log("Player "+l_playersArray.get(i).getD_playerName()+" deploy your troops:");
					System.out.println("Player "+l_playersArray.get(i).getD_playerName()+" deploy your troops:");
					l_outcomeIssueOrder =l_playersArray.get(i).issue_order();

					if(l_outcomeIssueOrder == false)
					{
						System.out.println("Player "+l_playersArray.get(i).getD_playerName()+" has decided not to deploy any troops");
						l_tempName.add(l_playersArray.get(i).getD_playerName());
						flag++;

						for(int k=0; k<l_tempPlayerArray.size(); k++)
						{
							if(l_tempPlayerArray.get(k).getD_playerName().equals(l_playersArray.get(i).getD_playerName()))
								l_tempPlayerArray.remove(k);
						}
					}
						

					for(int j=0;j<l_playersArray.size();j++)
					{
						if(l_tempName.contains(l_playersArray.get(j).getD_playerName()))
							continue;
						else
						{
						if(l_playersArray.get(j).getD_armyNumber()==0)
							{
								l_tempName.add(l_playersArray.get(j).getD_playerName());
								flag++;	
							}
						}
					}

				}

				if(flag==l_playersArray.size())
				{
					l_temp=0;
					break;
				}
			}
		}

		PlayersGameplay.assignArmiesToPlayers(l_tempPlayerArray);
		l_temp=1;
		flag=0;

		if(l_tempPlayerArray.size() != 0)
		{
			while(l_temp>0) 
			{
				for(int i=0;i<l_tempPlayerArray.size();i++)
				{
					if(l_tempPlayerArray.get(i).getD_armyNumber()!=0)
					{
						l_tempPlayerArray.get(i).getD_order().execute(l_tempPlayerArray.get(i), l_tempPlayerArray.get(i).next_order(),p_connectivity,0,0);

						if(l_tempPlayerArray.get(i).getD_armyNumber()==0)
						{
							flag+=1;
						}
					}

					if(flag==l_tempPlayerArray.size())
					{
						l_temp=0;
						break;
					}
					
				}
				
				
			}
			System.out.println(ColorCoding.ANSI_GREEN+"All Armies have been successfully deployed. Enter command to proceed"+ColorCoding.ANSI_RESET);
		}

		ShowMap.showMap(p_connectivity.getD_continentsList(), p_connectivity.getD_countriesList(), Play.getL_playersArray());
		System.out.println("reinforcement done");
		ge.setPhase(new Attack(ge));
	}

	/**
	 * Invalid command in Reinforcement phase.
	 * Players cannot attack during the Reinforcement phase.
	 *
	 * @param p_connectivity The Connectivity object.
	 */
	public void attack(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage(); 
	}

	/**
	 * Invalid command in Reinforcement phase.
	 * Players cannot fortify during the Reinforcement phase.
	 *
	 * @param p_connectivity The Connectivity object.
	 */
	public void fortify(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage(); 
	}

	/**
	 * Moves to the Attack phase.
	 */
	public void next(Connectivity p_connectivity) 
	{
		ge.setPhase(new Attack(ge));
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void loadMap(Connectivity p_connectivity, String[] p_commands) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void validateMap(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void editCountry(String[] p_commands, Connectivity p_connectivity) 
	{

		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void editContinent(String[] p_commands, Connectivity p_connectivity) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void editNeighbor(String[] p_commands, Connectivity p_connectivity) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void saveMap(Connectivity p_connectivity, String p_mapName) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	public void setPlayers(String[] p_commands) 
	{
		printInvalidCommandMessage();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean assignCountries(Connectivity p_connectivity) 
	{
		printInvalidCommandMessage();
		return false;
		
	}



	/**
	 * Sets the player commands
	 * @param p_commands refers to commands
	 * @param p_connectivity refers to connectivity object
	 */
	@Override
	public void setPlayers(String[] p_commands, Connectivity p_connectivity) {

		// Implementation not needed for Reinforcement phase
	}

	@Override
	public void enableTournament(String mycommand) {
		// Implementation not needed for Reinforcement phase
		
	}

	public void endGame()
	{
		System.out.println("Thank you for Playing the Game");
		System.exit(0);

	}

}
