package org.concordia.macs.Controllers;

import java.util.ArrayList;
import java.util.Scanner;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;

import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.LogEntryBuffer;

import org.concordia.macs.State.*;

/**
 *
 * @author Mahfuzzur Rahman
 * Context class implementing the State pattern.
 * It contains a State object, which in this example is the Phase class.
 */

public class GameEngine {
	
	LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

	/**
	 * State object of the GameEngine 
	 */

	private Phase gamePhase;
	private Connectivity connectivity;
	private boolean checkIfTest = false;

	/**
	 * Used for unit testing
	 * @return true if testing
	 */

	public boolean getCheckIfTest() {
		return checkIfTest;
	}

	/**
	 * Sets the checkIfTest parameter.
	 * @param checkIfTest testing parameter
	 */

	public void setCheckIfTest(boolean checkIfTest) {
		this.checkIfTest = checkIfTest;
	}

	int startOption;
	String mycommand;

	/**
	 * Allows the GameEngine object to change its state.
	 * @param p_phase new state to be set for the GameEngine object.
	 */

	public void setPhase(Phase p_phase) {
		gamePhase = p_phase;
		d_logEntryBuffer.log("new phase: " + p_phase.getClass().getSimpleName());
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}

	/**
	 * Returns the name of the game phase.
	 * @return name of the game phase
	 */

	public String getPhaseName()
	{
		return gamePhase.getClass().getSimpleName();
	}

	/**
	 * Returns the game phase.
	 * @return the game phase
	 */
	
	public Phase getPhase()
	{
		return gamePhase;
	}

	/**
	 * Starts the game by initializing map editing or gameplay.
	 */


	public void startGame() {

		d_logEntryBuffer.clearFile();
		Connectivity l_connectivity=new Connectivity();
		
		l_connectivity.setD_continentList(new ArrayList<Continent>());
		l_connectivity.setD_countryList(new ArrayList<Country>());

		boolean l_check_if_map_loaded = false;
		Scanner keyboard = new Scanner(System.in);
		Scanner phase_command = new Scanner(System.in);
		String[] l_commands;

		do {
			d_logEntryBuffer.log("Choose to Start the Game or End it");
			System.out.println("1. Edit Map");
			System.out.println("2. Play Game");
			System.out.println("3. Quit");
			System.out.println("Where do you want to start?: ");
			startOption = keyboard.nextInt();

			switch (startOption) {

			case 1:
				setPhase(new Preload(this));
				break;

			case 2:
				setPhase(new PlaySetup(this));
				break;

			case 3:
				d_logEntryBuffer.log("Bye!");
				System.out.println("Bye!");
				return;

			}
			
			do {
				System.out.println(" ====================================================================================================");
				System.out.println("| #   PHASE                   : command                                                             |"); 
				System.out.println(" ====================================================================================================");
				System.out.println("| 1.  Any                     : show map                                                            |");
				System.out.println("| 2.  Edit:PreLoad            : load map, edit country, edit continent, edit neighbor, validatemap  |");
				System.out.println("| 3.  Edit:PostLoad           : save map                                                            |");
				System.out.println("| 4.  Play:PlaySetup          : gameplayer, assigncountries                                         |");
				System.out.println("| 5.  Play:MainPlay:Reinforce : deploy                                                              |");
				System.out.println("| 6.  Play:MainPlay:Attack    : advance, bomb, airlift, blockade, negotiate                         |");
				System.out.println("| 7.  Play:MainPlay:Fortify   : fortify                                                             |");
				System.out.println("| 8.  End                     : end game                                                            |");
				System.out.println("| 9.  Any                     : next phase                                                          |");
				System.out.println(" ====================================================================================================");

				d_logEntryBuffer.log("enter a " + gamePhase.getClass().getSimpleName() + " phase command: ");
				System.out.println("enter a " + gamePhase.getClass().getSimpleName() + " phase command: ");
				mycommand = phase_command.nextLine();
				l_commands = mycommand.split(" "); 
				System.out.println(" =====================================================================================================");

				if(l_commands[0]!= null)
				{
				switch (l_commands[0]) {

				case "loadmap":					
					gamePhase.loadMap(l_connectivity,l_commands);
					l_check_if_map_loaded = true;
					break;

				case "validatemap":
					if(l_check_if_map_loaded) gamePhase.validateMap(l_connectivity);
					else {
						d_logEntryBuffer.log("ERROR: Map cannot be validated before loading it");
						System.out.println(ColorCoding.red+"ERROR: Map cannot be validated before loading it"+ColorCoding.blank);
					}
					break;

				case "showmap":
					if(l_check_if_map_loaded) gamePhase.viewMap(l_connectivity.getD_continentList(),l_connectivity.getD_countryList(),Play.getL_playersArray());
					else {
						d_logEntryBuffer.log("ERROR: Map cannot be viewed before loading it");
						System.out.println(ColorCoding.red+"ERROR: Map cannot be viewed before loading it"+ColorCoding.blank);
					}
					break;

				case "help":
					gamePhase.help();
					break;

				case "editcountry":
					gamePhase.editCountry(l_commands, l_connectivity);
					break;

				case "editcontinent":
					if(l_check_if_map_loaded) gamePhase.editContinent(l_commands, l_connectivity);
					else {
						d_logEntryBuffer.log("ERROR: Map cannot be edited before loading it");
						System.out.println(ColorCoding.red+"ERROR: Map cannot be edited before loading it"+ColorCoding.blank);
					}
					break;

				case "editneighbor":
					gamePhase.editNeighbor(l_commands, l_connectivity);
					break;

				case "savemap":
					gamePhase.saveMap(l_connectivity, l_commands[1]);
					break;

				case "gameplayer":
					gamePhase.setPlayers(l_commands);
					break;

				case "assigncountries":
					if(gamePhase.assignCountries(l_connectivity))
					{
					gamePhase.next();
					}
					break;

				case "deploy":
					gamePhase.reinforce(l_connectivity);
					break;

				case "attack":
					gamePhase.attack(l_connectivity);
					break;

				case "fortify":
					gamePhase.fortify(l_connectivity);
					break;

				case "exit":
					gamePhase.endGame();
					break;

				default: 
					d_logEntryBuffer.log("This command does not exist");
					System.out.println("This command does not exist");
				}
				}
			} while (!(gamePhase instanceof End));
		} while (l_commands[0] != "exit");
		keyboard.close();
	}

	/**
	 * @return returns the map content
	 */

	public Connectivity getConnectivity() {
		return connectivity;
	}

	/**
	 * Sets the map content.
	 * @param connectivity - holds values of the continent country neighbor 
	 */

	public void setConnectivity(Connectivity connectivity) {
		this.connectivity = connectivity;
	}
}
