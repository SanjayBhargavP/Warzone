package org.concordia.macs.Controllers;

import java.io.FileNotFoundException;
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
	private static Phase gamePhase;
	private Connectivity connectivity;

	private boolean checkIfTest = false;
	private boolean checkIfTournament = false;
	private boolean checkIfLoad = false;
	private boolean checkIfSave = false;

	/**
	 * Getter for checkIfSave flag.
	 * @return true if saving a game, false otherwise.
	 */
	public boolean getCheckIfSave() {
		return checkIfSave;
	}

	/**
	 * Setter for checkIfSave flag.
	 * @param checkIfSave true if saving a game, false otherwise.
	 */
	public void setCheckIfSave(boolean checkIfSave) {
		this.checkIfSave = checkIfSave;
	}

	/**
	 * Getter for checkIfLoad flag.
	 * @return true if loading a game, false otherwise.
	 */
	public boolean getCheckIfLoad() {
		return checkIfLoad;
	}

	/**
	 * Setter for checkIfLoad flag.
	 * @param checkIfLoad true if loading a game, false otherwise.
	 */
	public void setCheckIfLoad(boolean checkIfLoad) {
		this.checkIfLoad = checkIfLoad;
	}

	/**
	 * Getter for checkIfTournament flag.
	 * @return true if in tournament mode, false otherwise.
	 */
	public boolean getCheckIfTournament() {
		return checkIfTournament;
	}

	/**
	 * Setter for checkIfTournament flag.
	 * @param checkIfTournament true if in tournament mode, false otherwise.
	 */
	public void setCheckIfTournament(boolean checkIfTournament) {
		this.checkIfTournament = checkIfTournament;
	}

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
	public static String getPhaseName()
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
	
	public void startGame() throws FileNotFoundException {

		d_logEntryBuffer.clearFile();
		Connectivity l_connectivity=new Connectivity();
		
		l_connectivity.setD_continentsList(new ArrayList<Continent>());
		l_connectivity.setD_countriesList(new ArrayList<Country>());

		boolean l_check_if_map_loaded = false;
		Scanner keyboard = new Scanner(System.in);
		Scanner phase_command = new Scanner(System.in);
		String[] l_commands;

		do {
			if(!this.getCheckIfLoad())
			{

				d_logEntryBuffer.log("Choose to Start the Game or End it");
				System.out.println("1. Edit Map");
				System.out.println("2. Play Game");
				System.out.println("3. Quit");
				System.out.println("Where do you want to start?: ");
				startOption = keyboard.nextInt();
			}
			else if(this.getCheckIfLoad())
			{
				startOption=1;
			}
			switch (startOption) {

				case 1:
					setPhase(new PreLoad(this));
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
				System.out.println("| 1.  Any                     : showmap                                                             |");
				System.out.println("| 2.  Edit:PreLoad            : loadmap, edit country, edit continent, edit neighbor, validatemap   |");
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
				System.out.println(" =================================================");

				if(l_commands[0]!= null) {
					try {
					switch (l_commands[0]) {

							case "tournament":
								gamePhase.enableTournament(mycommand);
								System.out.println("Tournament complete! Thank you participating");
								System.exit(0);

							case "loadmap":
								gamePhase.loadMap(l_connectivity, l_commands);
								l_check_if_map_loaded = true;
								break;

							case "validatemap":
								if (l_check_if_map_loaded) gamePhase.validateMap(l_connectivity);
								else {
									d_logEntryBuffer.log("ERROR: Map cannot be validated before loading it");
									System.out.println(ColorCoding.ANSI_RED + "ERROR: Map cannot be validated before loading it" + ColorCoding.ANSI_RESET);
								}
								break;

							case "showmap":
								if (l_check_if_map_loaded)
									gamePhase.showMap(l_connectivity.getD_continentsList(), l_connectivity.getD_countriesList(), Play.getL_playersArray());
								else {
									d_logEntryBuffer.log("ERROR: Map cannot be viewed before loading it");
									System.out.println(ColorCoding.ANSI_RED + "ERROR: Map cannot be viewed before loading it" + ColorCoding.ANSI_RESET);
								}
								break;

							case "help":
								gamePhase.help();
								break;

							case "editcountry":
								gamePhase.editCountry(l_commands, l_connectivity);
								break;

							case "editcontinent":
								if (l_check_if_map_loaded) gamePhase.editContinent(l_commands, l_connectivity);
								else {
									d_logEntryBuffer.log("ERROR: Map cannot be edited before loading it");
									System.out.println(ColorCoding.ANSI_RED + "ERROR: Map cannot be edited before loading it" + ColorCoding.ANSI_RESET);
								}
								break;

							case "editneighbor":
								gamePhase.editNeighbor(l_commands, l_connectivity);
								break;

							case "savemap":
								gamePhase.saveMap(l_connectivity, l_commands[1]);
								break;

							case "gameplayer":
								gamePhase.setPlayers(l_commands, l_connectivity);
								break;

							case "assigncountries":
								if (gamePhase.assignCountries(l_connectivity)) {
									gamePhase.next(l_connectivity);
									gamePhase.reinforce(l_connectivity);
									gamePhase.attack(l_connectivity);
									if (this.getPhaseName().equals("End")) {
										return;
									}
									gamePhase.fortify(l_connectivity);
								}
								break;

							case "deploy":
								gamePhase.reinforce(l_connectivity);
								gamePhase.attack(l_connectivity);
								gamePhase.fortify(l_connectivity);
								break;

							case "attack":
								gamePhase.attack(l_connectivity);
								break;

							case "fortify":
								gamePhase.fortify(l_connectivity);
								break;

							case "loadgame":
								gamePhase.loadgame(l_commands, l_connectivity, this);
								break;

							case "exit":
								gamePhase.endGame(l_connectivity);
								break;

							default:
								d_logEntryBuffer.log("This command does not exist");
								System.out.println("This command does not exist");
						}
                    }catch (Exception e) {
                            d_logEntryBuffer.log("This command does not exist");
							System.out.println("This command does not exist");
					}
				}

			} while (!(gamePhase instanceof End));
		} while (!l_commands[0].equals("exit"));
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
