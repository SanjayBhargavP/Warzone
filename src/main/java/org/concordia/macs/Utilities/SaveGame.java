package org.concordia.macs.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.concordia.macs.Controllers.GameEngine;
import org.concordia.macs.Models.Continent;
import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Map;
import org.concordia.macs.Models.Player;
import org.concordia.macs.Strategy.AggressivePlayerStrategy;
import org.concordia.macs.Strategy.BenevolentPlayerStrategy;
import org.concordia.macs.Strategy.CheaterPlayerStrategy;
import org.concordia.macs.Strategy.HumanPlayerStrategy;
import org.concordia.macs.Strategy.RandomPlayerStrategy;
import org.concordia.macs.State.Phase;
import org.concordia.macs.State.Play;
import org.concordia.macs.State.PlaySetup;

/**
 * The SaveGame class has the functionality to save the current game state to a
 * file.
 */
public class SaveGame {
    /**
     * Saves the current game state to a file.
     *
     * @param gamePhase    The phase of the game currently in progress.
     * @param connectivity Connectivity object
     * @param p_file_name  The name of the file to save the state
     * @throws IOException To handle I/O error
     */
    public boolean saveGame(Phase gamePhase, Connectivity connectivity, String p_file_name) throws IOException {
        ArrayList<String> l_mapData = new ArrayList<String>();
        GameEngine ge = new GameEngine();
        l_mapData.add("[map]" + "\n");
        String mapName = connectivity.getD_mapName();
        l_mapData.add(mapName + "\n");
        l_mapData.add("\n");
        l_mapData.add("[Continents]" + "\n");
        for (Continent l_continent : connectivity.getD_continentList()) {
            l_mapData.add(l_continent.getD_continentId() + " " + l_continent.getD_continentName() + "\n");
        }
        l_mapData.add("\n");
        l_mapData.add("[Countries]" + "\n");
        for (Country l_country : connectivity.getD_countryList()) {
            l_mapData.add(l_country.getD_countryName() + " " + l_country.getD_countryId() + " "
                    + l_country.getD_continentId() + " " + l_country.getD_armyDeployedOnCountry() + "\n");
        }
        l_mapData.add("\n");
        l_mapData.add("[Players]" + "\n");
        for (Player l_player : Play.getL_playersArray()) {
            l_mapData.add(l_player.getD_playerId() + " " + l_player.getD_playerName() + "\n");
            l_mapData.add("Player Countries:\n");
            for (Country l_playerCountry : l_player.getD_Country()) {
                l_mapData.add(l_playerCountry.getD_countryName() + " ");
            }
            l_mapData.add("\n");
            l_mapData.add("Cards: " + l_player.getCards() + "\n");
            l_mapData.add("Armies: " + l_player.getD_armyCount() + "\n");

            if (l_player.getStrategy() instanceof HumanPlayerStrategy) {
                l_mapData.add("Behaviour: Human" + "\n");
            } else if (l_player.getStrategy() instanceof CheaterPlayerStrategy) {
                l_mapData.add("Behaviour: Cheater" + "\n");
            } else if (l_player.getStrategy() instanceof BenevolentPlayerStrategy) {
                l_mapData.add("Behaviour: Benevolent" + "\n");
            } else if (l_player.getStrategy() instanceof AggressivePlayerStrategy) {
                l_mapData.add("Behaviour: Aggressive" + "\n");
            } else if (l_player.getStrategy() instanceof RandomPlayerStrategy) {
                l_mapData.add("Behaviour: Random" + "\n");
            }
            l_mapData.add("\n");
        }

        l_mapData.add("[phase]" + "\n");
        String phaseName = gamePhase.getClass().getSimpleName();
        l_mapData.add(phaseName + "\n");
        l_mapData.add("\n");

        System.out.println(connectivity.getD_FilepathName());
        File file = new File("");
        String absolute = file.getAbsolutePath();
        connectivity.setD_pathName(absolute + File.separator + "src/main/resources");
        String l_fileName = absolute + File.separator + "src/main/resources" + File.separator + p_file_name + ".game";
        FileWriter l_input = new FileWriter(l_fileName);
        for (String lines : l_mapData) {
            l_input.write(lines);
        }
        l_input.close();
        System.out.println("Game file has been successfully saved");
        return true;

    }
}
