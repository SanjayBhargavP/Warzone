package org.concordia.macs.Controllers;

import java.io.FileNotFoundException;

/**
 *
 *  @author Mahfuzzur Rahman
 * Main class for initiating the game engine.
 */
public class GameDriver 
{

	/**
	 * Initializes and starts the game engine.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String args[]) throws FileNotFoundException 
	{
		GameEngine gameEngine = new GameEngine();
		gameEngine.startGame();
	}
}
