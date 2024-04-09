package org.concordia.macs.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.LogEntryBuffer;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Utilities.Connectivity;
import org.concordia.macs.Utilities.LoadGame;
import org.concordia.macs.Utilities.MapCheck;

/**
 * @author Piyush
 * The class is used to play a tournament
 */
public class PlayGame extends Phase {

    LogEntryBuffer d_logEntryBuffer= new LogEntryBuffer();
    ArrayList<String> l_mapList = new ArrayList<>();
    HashSet<String> l_behaviourList = new HashSet<String>();
    Connectivity l_connectivity = new Connectivity();


    int l_gameCount = 0;
    int l_roundCount = 0;
    GameEngine l_gameEngine = new GameEngine();
    ArrayList<String> d_gameResult = new ArrayList<>();
    String d_gameplayerCommand = "";

    public PlayGame(GameEngine p_ge) {
        super(p_ge);
    }

    public void enableTournament(String p_usercommand) {

        String[] l_CommandList = p_usercommand.split("-");
        if (l_CommandList[0].equals("tournament ") && l_CommandList.length == 5) {
            for (int i = 1; i < l_CommandList.length; i++) {
                String[] l_param = l_CommandList[i].split(" ");
                switch (l_param[0]) {
                    case "M":
                        for (int j = 1; j < l_param.length; j++) {
                            File f = new File("");
                            String absolute = f.getAbsolutePath();
                            String l_pathName = absolute + File.separator + "src/main/resources";
                            if (!MapCheck.checkMap(l_param[j], l_pathName)) {
                                System.out.println("Map file " + l_param[j] + " does not exist");
                                return;
                            } else
                                l_mapList.add(l_param[j]);
                        }
                        break;


                    case "P":
                        d_gameplayerCommand = l_CommandList[i];
                        if (l_param.length < 3 || l_param.length > 5) {
                            System.out.println("ERROR: Invalid number of players for tournament");
                            System.out.println("Player limit allowed for the tournament: 2-4");
                            return;
                        }
                        break;


                    case "G":
                        if (l_param.length != 2) {
                            System.out.println("ERROR: Invalid number of parameters found in G");
                            return;
                        }
                        if (Integer.parseInt(l_param[1]) >= 1 && Integer.parseInt(l_param[1]) <= 5)
                            l_gameCount = Integer.parseInt(l_param[1]);
                        else {
                            System.out.println("INVALID COMMAND! Parameter G Limit: 1-5 games");
                            return;
                        }
                        break;


                    case "D":
                        if (l_param.length != 2) {
                            System.out.println("ERROR: Invalid number of parameters found in D");
                            return;
                        }
                        if (Integer.parseInt(l_param[1]) >= 10 && Integer.parseInt(l_param[1]) <= 50)
                            l_roundCount = Integer.parseInt(l_param[1]);
                        else {
                            System.out.println("INVALID COMMAND! Parameter D Limit: 10-50 turns");
                            return;
                        }
                        break;


                    default:
                        System.out.println("ERROR: Invalid Parameter " + l_param[0] + " in tournament command found");
                        return;
                }
            }
        } else {
            System.out.println("Invalid tournament command entered");
            return;
        }


        System.out.println("********************* STARTING TOURNAMENT *********************");
        for (int i = 0; i < l_mapList.size(); i++) {
            for (int j = 0; j < l_gameCount; j++) {
                System.out.println("----------------GAME " + (j + 1) + " BEGINS" + "----------------");
                startGame(l_mapList.get(i));
                String l_winner = d_gameResult.remove(d_gameResult.size() - 1);
                if (l_winner.equals("DRAW"))
                    l_winner = "Result --> " + l_winner + " for Map " + (i + 1) + " Game " + (j + 1);
                else
                    l_winner = "Winner --> " + l_winner + " for Map " + (i + 1) + " Game " + (j + 1);
                d_gameResult.add(l_winner);
            }

        }
        System.out.println();
        System.out.println("TOURNAMENT SUMMARY");
        for (int i = 0; i < d_gameResult.size(); i++)
            System.out.println(d_gameResult.get(i));
        System.out.println();

    }

    public void startGame(String p_mapName) {
        l_gameEngine.setCheckIfTournament(true);
        l_connectivity.setD_winnerPlayer(new Player());
        l_gameEngine.setConnectivity(l_connectivity);
        l_gameEngine.setPhase(new PreLoad(l_gameEngine));
        String[] l_command = ("loadmap " + p_mapName).split(" ");
        l_gameEngine.getPhase().loadMap(l_gameEngine.getConnectivity(), l_command);
        l_gameEngine.getPhase().saveMap(l_gameEngine.getConnectivity(), p_mapName);
        Play.l_playersArray.clear();
        l_gameEngine.getPhase().setPlayers(d_gameplayerCommand.split(" "), l_gameEngine.getConnectivity());
        l_gameEngine.getPhase().assignCountries(l_gameEngine.getConnectivity());
        l_gameEngine.getPhase().next(l_connectivity);
        for (int i = 0; i < l_roundCount; i++) {

            if (!checkIfWinnerExists()) {
                System.out.println("///////////////////////ROUND " + (i + 1) + " BEGINS" + "///////////////////////");
                l_gameEngine.getPhase().reinforce(l_gameEngine.getConnectivity());
                l_gameEngine.getPhase().attack(l_gameEngine.getConnectivity());
                if (checkIfWinnerExists()) {
                    recordResult();
                    return;
                } else
                    l_gameEngine.getPhase().fortify(l_gameEngine.getConnectivity());

                if (i == l_roundCount - 1) {
                    if (!checkIfWinnerExists()) {
                        System.out.println("GAME RESULT = DRAW!!!");
                        recordResult();
                        ge.setPhase(new End(ge));
                        return;
                    }

                }
            } else {
                System.out.println("WE HAVE A WINNER!!! --> " + l_gameEngine.getConnectivity().getD_winnerPlayer().getD_playerName());
                recordResult();
                l_gameEngine.getPhase().endGame(l_connectivity);
                break;
            }

        }

    }

    public boolean checkIfWinnerExists() {

        if (l_gameEngine.getConnectivity().getD_winnerPlayer().getD_playerName() == null)
            return false;
        else
            return true;
    }

    public void recordResult() {
        if (checkIfWinnerExists())
            d_gameResult.add(l_gameEngine.getConnectivity().getD_winnerPlayer().getD_playerName());
        else
            d_gameResult.add("DRAW");
    }

    public void editCountry(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    public void editContinent(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }


    public void editNeighbor(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    public void saveMap(Connectivity p_connectivity, String p_mapName) {
        printInvalidCommandMessage();
    }

    public void setPlayers(String[] p_commands, Connectivity p_connectivity) {
        printInvalidCommandMessage();

    }

    public boolean assignCountries(Connectivity p_connectivity) {
        printInvalidCommandMessage();
        return false;
    }

    public void reinforce(Connectivity p_connectivity) {

        printInvalidCommandMessage();
    }

    public void attack(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    public void fortify(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }


    public void loadMap(Connectivity p_connectivity, String[] p_commands) {
        printInvalidCommandMessage();
    }

    public void next(Connectivity p_connectivity) {

    }

    public void validateMap(Connectivity p_connectivity) {
        printInvalidCommandMessage();
    }

    @Override
    public void loadgame(String[] p_commands, Connectivity p_connectivity, GameEngine ge) throws FileNotFoundException {

        LoadGame.loadgame(p_commands[1], p_connectivity, ge);
        String l_phase_name = LoadGame.getD_GamePhase();
        String l_map_name = LoadGame.getD_MapName();
        switch (l_phase_name) {
            case "Preload":
                System.out.println("Map has not been loaded. Starting game...");
                ge.setCheckIfLoad(true);
                ge.startGame();
            case "Reinforcement":
                ge.setPhase(new Reinforcement(ge));
                PlaySetup playsetup = new PlaySetup(ge);
                playsetup.next(p_connectivity);
                break;
            case "Attack":
                ge.setPhase(new Attack(ge));
                break;
            case "PlaySetup":
                ge.setPhase(new PlaySetup(ge));
                break;
            case "PostLoad":
                ge.setCheckIfLoad(true);
                ge.setPhase(new PostLoad(ge));
                break;
            case "Fortify":
                ge.setPhase(new Fortify(ge));
                break;

        }

    }

}
