package org.concordia.macs.Controllers;

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
	public static void main(String args[])
	{
		GameEngine gameEngine = new GameEngine();
		gameEngine.startGame();
	}
}
