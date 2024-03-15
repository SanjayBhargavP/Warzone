package org.concordia.macs.State;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class represents the phase where players can choose to fortify their countries.
 * @author - Blesslin Jeba Shiny
 */
public class Fortify extends MainPlay{

    /**
     * Constructs a fortify phase
     * @param p_ge - Game engine instance
     */
    Fortify(GameEngine p_ge)
    {
        super(p_ge);
    }

    /**
     * Prints the invalid message while trying to reinforce
     * @param p_connectivity - connectivity object
     */
    public void reinforce(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * Prints the invalid message while trying to attack
     * @param p_connectivity - connectivity object
     */
    public void attack(Connectivity p_connectivity)
    {
        printInvalidCommandMessage();
    }

    /**
     * This function performs the fortification phase in players turn.
     * @param p_connectivity - connectivity object
     */
    public void fortify(Connectivity p_connectivity)
    {
        Scanner sc=new Scanner(System.in);
        Country l_getCountry=new Country();
        Player l_player;
        System.out.println("Do you want to fortify ..(Yes/No");
        String user_output="";
        if(ge.getCheckIfTest())
        {
            user_output="no";
        }
        else user_output=sc.nextLine();

        if(user_output.equalsIgnoreCase("yes"))
        {
            int l_terminateFlag=0;
            int l_flag=0;
            int l_executeOrder=0;
            ArrayList<String> playerNames =new ArrayList<>();

            do
            {
                for(int i=0;i<l_playersArray.size();i++)
                {
                    l_flag=1;
                    do
                    {
                        if (playerNames.contains(l_playersArray.get(i).getD_playerName()))
                            continue;
                        System.out.println(ColorCoding.cyan+"\n"+l_playersArray.get(i).getD_playerName()+"!! Do you want to give command or pass?(Press enter to continue / pass)"+ColorCoding.blank);
                        Scanner l_sc = new Scanner(System.in);
                        String l_passContinue=l_sc.nextLine();
                        if(l_passContinue.equalsIgnoreCase("exit"))
                        {
                            System.out.println("Thank you for Playing the Game");
                            System.exit(0);
                        }
                        if(l_passContinue.equals("pass"))
                        {
                            playerNames.add(l_playersArray.get(i).getD_playerName());
                            l_terminateFlag++;
                            break;
                        }
                        Order l_order=new Order();
                        System.out.println("\nEnter the Command for player: "+l_playersArray.get(i).getD_playerName());
                        System.out.println("Cards available: "+l_playersArray.get(i).getCards());
                        String l_orderinput=l_sc.nextLine();
                        if(l_orderinput.equalsIgnoreCase("exit"))
                        {
                            System.out.println("Thank you for Playing the Game");
                            System.exit(0);
                        }
                        String[] l_inputOrderArray=l_orderinput.split(" ");
                        //make a function to validate command..
                        switch(l_inputOrderArray[0])
                        {
                            case "advance":
                                //System.out.println("Call Advance");
                                l_order.setOrderContent(l_orderinput);
                                l_playersArray.get(i).getD_playerOrder().add(l_order);
                                l_flag1=1;
                                break;
                            default:
                                //d_logEntryBuffer.log("Invalid Command!!");
                                System.out.println(ColorCoding.red+"Invalid Command!!"+ColorCoding.blank);
                                l_flag1=0;
                        }

                    }while(l_flag1==0);


                }

            }while(l_terminateFlag!=l_playersArray.size());

            l_executeOrder=0;
            HashSet<String> l_emptyOrders=new HashSet<>();


            while(l_executeOrder!=l_playersArray.size())
            {
                for(int j=0;j<l_playersArray.size();j++)
                {

                    if(l_emptyOrders.contains(l_playersArray.get(j).getD_playerName())) continue;
                    if(l_playersArray.get(j).getD_playerOrder().size()==0)
                    {
                        l_emptyOrders.add(l_playersArray.get(j).getD_playerName());
                        l_executeOrder++;
                        continue;
                    }
                    l_playersArray.get(j).getD_Order().execute(l_playersArray.get(j), l_playersArray.get(j).next_order(),p_connectivity,1,1);
                }
            }
            ViewMap.viewMap(p_connectivity.getD_continentList(), p_connectivity.getD_countryList(), l_playersArray);
            PlayersGameplay.resetDiplomacy(l_playersArray);
            System.out.println("fortification done");
            ge.setPhase(new Reinforcement(ge));
            PlayersGameplay.assignArmiesToPlayers(l_playersArray);
        }
        else if (user_output.equalsIgnoreCase("no"))
        {
            ge.setPhase(new Reinforcement(ge));
            PlayersGameplay.assignArmiesToPlayers(l_playersArray);
        }

    }

    /**
     * Moves the game to the next phase (Reinforcement) after fortification.
     */
    public void next()
    {
        ge.setPhase(new Reinforcement(ge));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadMap(Connectivity p_connectivity, String[] p_commands)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateMap(Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void editCountry(String[] p_commands, Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void editContinent(String[] p_commands, Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void editNeighbor(String[] p_commands, Connectivity p_connectivity)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMap(Connectivity p_connectivity, String p_mapName)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayers(String[] p_commands)
    {
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assignCountries(Connectivity p_connectivity)
    {
        return false;
        // TODO Auto-generated method stub

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame()
    {
        // TODO Auto-generated method stub
        System.out.println("Thank you for Playing the Game");
        System.exit(0);
    }
}
