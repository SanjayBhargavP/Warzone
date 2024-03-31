package org.concordia.macs.State;
import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.PlayersGameplay;

import java.util.Scanner;

/**
 * @author Susmitha Mamula
 * Concrete states represents the setup phase of the game.
 * Phase allows players to add, remove players, assign countries and begin the game
 */
public class PlaySetup extends Play {

    LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();

    /**
     * Constructor for the PlaySetup phase
     * @param p_g The GameEngine object associated with this phase.
     */
    public PlaySetup(GameEngine p_g) {
        super(p_g);
    }

    /**
     * Sets the players based on the provided commands.
     *
     * @param p_commands Array of user commands for setting up players.
     */
    public void setPlayers(String[] p_commands,Connectivity p_connectivity)
    {
        for(int i=1;i<p_commands.length;)
        {
            if(ge.getCheckIfTest())
            {
                Player l_player = new Player();
                switch(p_commands[i])
                {
                    case "Aggressive":
                        l_player.setStrategy(new AggressivePlayerStrategy(l_player,p_connectivity));
                        break;
                    case "Human":
                        l_player.setStrategy(new HumanPlayerStrategy(l_player,p_connectivity));
                        break;
                    case "Benevolent":
                        l_player.setStrategy(new BenevolentPlayerStrategy(l_player,p_connectivity));
                        break;
                    case "Random":
                        l_player.setStrategy(new RandomPlayerStrategy(l_player,p_connectivity));
                        break;
                    case "Cheater":
                        l_player.setStrategy(new CheaterPlayerStrategy(l_player,p_connectivity));
                        break;
                    default:
                        System.out.println(ColorCoding.ANSI_RED+"Invalid strategy name"+ColorCoding.ANSI_RESET);
                }
                l_player.setD_playerName(p_commands[i]);
                l_playersArray.add(l_player);
                d_logEntryBuffer.log(l_player.getD_playerName() + "added successfully");
                System.out.println(ColorCoding.ANSI_GREEN+l_player.getD_playerName()+" added successfully"+ColorCoding.ANSI_RESET);
                i=i+1;
            }
            else
            {
                if(p_commands[i].equals("-add"))
                {
                    Player l_player = new Player();
                    l_player.setD_playerName(p_commands[i+1]);
                    int l_flag=0;
                    Scanner l_sc=new Scanner(System.in);
                    do {
                        System.out.println("Enter the behaviour of the player: "+l_player.getD_playerName() +" - Aggressive, Human, Benevolent, Random, Cheater");
                        String l_strategy = "";
                        if(ge.getCheckIfTest())
                            l_strategy = "Aggressive";
                        else
                            l_strategy=l_sc.nextLine();
                        l_flag=0;
                        switch(l_strategy)
                        {
                            case "Aggressive":
                                l_player.setStrategy(new AggressivePlayerStrategy(l_player,p_connectivity));
                                l_flag++;
                                break;
                            case "Human":
                                l_player.setStrategy(new HumanPlayerStrategy(l_player,p_connectivity));
                                l_flag++;
                                break;
                            case "Benevolent":
                                l_player.setStrategy(new BenevolentPlayerStrategy(l_player,p_connectivity));
                                l_flag++;
                                break;
                            case "Random":
                                l_player.setStrategy(new RandomPlayerStrategy(l_player,p_connectivity));
                                l_flag++;
                                break;
                            case "Cheater":
                                l_player.setStrategy(new CheaterPlayerStrategy(l_player,p_connectivity));
                                l_flag++;
                                break;
                            default:
                                System.out.println(ColorCoding.ANSI_RED+"Invalid strategy name"+ColorCoding.ANSI_RESET);
                                l_flag=0;

                        }
                    }while(l_flag==0);



                    l_player.setD_playerName(p_commands[i+1]);
                    l_playersArray.add(l_player);
                    d_logEntryBuffer.log(l_player.getD_playerName() + "added successfully");
                    System.out.println(ColorCoding.ANSI_GREEN+l_player.getD_playerName()+" added successfully"+ColorCoding.ANSI_RESET);
                    i=i+2;

                }
                else if(p_commands[i].equals("-remove"))
                {
                    for(int j=0;j<l_playersArray.size();j++)
                    {
                        if(p_commands[i+1].equals(l_playersArray.get(j).getD_playerName()))
                        {
                            d_logEntryBuffer.log(l_playersArray.get(j).getD_playerName()+ "removed successfully");
                            System.out.println(ColorCoding.ANSI_GREEN+l_playersArray.get(j).getD_playerName()+" removed successfully"+ColorCoding.ANSI_RESET);
                            l_playersArray.remove(j);
                            i=i+2;
                            break;
                        }
                    }
                }
            }

        }
        System.out.println("players have been set");
    }

    /**
     * Assigns countries to players.
     *
     * @param p_connectivity refers to connectivity object
     * @return True if countries are successfully assigned, false otherwise.
     */
    public boolean assignCountries(Connectivity p_connectivity) {
        if(l_playersArray.size()>0)
        {
            if(PlayersGameplay.assignCountries(l_playersArray,p_connectivity.getD_countriesList(),p_connectivity.getD_continentsList())==0)
            {
                d_logEntryBuffer.log("Countries assigned to players Successfully");
                System.out.println(ColorCoding.ANSI_GREEN+"Countries assigned to players Successfully"+ColorCoding.ANSI_RESET+"\n");
            }
            else
            {
                return false;
            }
        }
        else
        {
            d_logEntryBuffer.log("ERROR: No players to assign Countries");
            System.out.println(ColorCoding.ANSI_RED+"ERROR: No players to assign Countries"+ColorCoding.ANSI_RESET);
            return false;
        }
        System.out.println("countries have been assigned");
        for(Player p:l_playersArray)
        {
            PlayersGameplay.showPlayersCountry(p,1);
        }
        PlayersGameplay.assignArmiesToPlayers(l_playersArray);
        d_logEntryBuffer.log("Game Begins");
        System.out.println("Game Begins!!!!!!!!!!!\n");
        for(int i=0;i<l_playersArray.size();i++)
        {
            d_logEntryBuffer.log("Player "+ Integer.sum(i,1) +"("+l_playersArray.get(i).getD_playerName()+") has Army Count: "+l_playersArray.get(i).getD_armyNumber());
            System.out.println("Player "+ Integer.sum(i,1) +"("+l_playersArray.get(i).getD_playerName()+") has Army Count: "+l_playersArray.get(i).getD_armyNumber());
            PlayersGameplay.showPlayersCountry(l_playersArray.get(i),1);
            System.out.println();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * @param p_connectivity
     */
    public void reinforce(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    /**
     * Attack phase is not applicable in PlaySetup and will outcome as an invalid command.
     * @param p_connectivity
     */
    public void attack(Connectivity p_connectivity){
        printInvalidCommandMessage();
    }

    /**
     *  Fortify phase is not applicable in this PlaySetup and result in an invalid command.
     * @param p_connectivity
     */
    public void fortify(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    /**
     * Game will end and program exits.
     */

    public void endGame() {
        System.out.println("Thank you for playing the Game");
        System.exit(0);
    }



    /**
     * Moves to Reinforcement phase
     */

    public void next(Connectivity p_connectivity) {
        ge.setPhase(new Reinforcement(ge));
    }

    /**
     *{@inheritDoc}
     */

    @Override
    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void validateMap(Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();

    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editCountry(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editContinent(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */

    @Override
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity){
        // TODO Auto-generated method stub
        printInvalidCommandMessage();
    }
    /**
     *{@inheritDoc}
     */
     @Override
    public void saveMap(Connectivity p_connectivity, String p_mapName){
         // TODO Auto-generated method stub
         printInvalidCommandMessage();
     }
    @Override
    public void enableTournament(String mycommand) {
        // TODO Auto-generated method stub

    }

}


