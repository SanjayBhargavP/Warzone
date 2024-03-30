package org.concordia.macs.Strategy;

import org.concordia.macs.Controllers.GameEngine;

import org.concordia.macs.Models.Country;
import org.concordia.macs.Models.Order;
import org.concordia.macs.Models.Player;

import org.concordia.macs.Utilities.ColorCoding;
import org.concordia.macs.Utilities.Connectivity;

import org.concordia.macs.View.ShowMap;

import org.concordia.macs.State.Play;

import java.util.ArrayList;

/**
 *
 * @author Mahfuzzur Rahman
 * The AggressivePlayerStrategy class represents the player strategy that focuses on centralization of forces and then attack.
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

	/**
	 * This constructor refers to the aggressive player strategy.
	 *
	 * @param p_player       refers to the object of the player class
	 * @param p_connectivity refers to connectivity object
	 */
	public AggressivePlayerStrategy(Player p_player, Connectivity p_connectivity) {
		super(p_player, p_connectivity);
	}

	/**
	 * Override method to attack
	 *
	 * @return null in case of aggressive player
	 */
	@Override
	protected Country toAttack() {
		return null;
	}

	/**
	 * Override method to attack from method to get country to which attack should happen
	 *
	 * @return opponent country to attack
	 */
	@Override
	protected Country toAttackFrom() {
		Country d_StrongestCountry = toMoveFrom();
		Country l_neighborCountry = null;
		if (d_StrongestCountry != null) {
			int l_neighborCount = d_StrongestCountry.getD_neighbours().size();
			for (int i = 0; i < l_neighborCount - 1; i++) {
				if (!d_player.getD_Country().contains(l_neighborCountry)) {
					Country d_OpponentCountry = l_neighborCountry;
					return d_OpponentCountry;
				}
			}
		}
		System.out.println(ColorCoding.ANSI_RED + "No neighbours present for attack!" + ColorCoding.ANSI_RESET);
		return null;

	}

	/**
	 * Override method to get the strongest country from which armies should be used for attack
	 *
	 * @return country with highest army count i.e. strongest country
	 */
	@Override
	protected Country toMoveFrom() {
		int l_maxArmies = 0;
		Country d_StrongestCountry = null;

		for (Country l_country : d_player.getD_Country()) {
			int l_numArmies = l_country.getD_armyDeployedOnCountry();
			if (l_numArmies > l_maxArmies) {
				l_maxArmies = l_numArmies;
				d_StrongestCountry = l_country;
			}
		}

		if (d_player.getD_Country().size() == 0)
			return null;
		if (l_maxArmies == 0) {
			d_StrongestCountry = d_player.getD_Country().get(0);
		}

		return d_StrongestCountry;
	}

	/**
	 * Override method to defend the country
	 *
	 * @return null in case of aggressive player
	 */
	@Override
	protected Country toDefend() {
		return null;
	}

	/**
	 * Override method to apply aggressive player strategy during main game play by creating order
	 *
	 * @return order
	 */
	@Override
	public Order createOrder() {
		Order o = new Order();
		String str;

		if (toMoveFrom() == null)
			return null;
		if (GameEngine.getPhaseName().equals("Reinforcement")) {
			str = "deploy ";
			str = str + toMoveFrom().getD_countryId() + " " + d_player.getD_armyCount();
			System.out.println(str);
			o.setOrderContent(str);
			return o;
		} else if (GameEngine.getPhaseName().equals("Attack")) {
			str = "advance ";
			Country[] c = level(toMoveFrom().getD_countryId());

			if (c != null) {
				str = str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyDeployedOnCountry();
				System.out.println(str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyDeployedOnCountry());
				o.setOrderContent(str);
				ShowMap.showMap(d_connectivity.getD_continentList(), d_connectivity.getD_countryList(), Play.getL_playersArray());
				return o;
			} else {
				return null;
			}

		} else if (GameEngine.getPhaseName().equals("Fortify")) {
			str = "advance ";
			Country[] c = level1(toMoveFrom().getD_countryId());

			if (c != null) {
				str = str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyDeployedOnCountry();
				System.out.println(str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyDeployedOnCountry());
				o.setOrderContent(str);
				ShowMap.showMap(d_connectivity.getD_continentList(), d_connectivity.getD_countryList(), Play.getL_playersArray());
				return o;
			} else {
				return null;
			}

		}
		return o;
	}

	/**
	 * Method to get countries for advance and attack accordingly
	 *
	 * @param countryID refers to the country ID of the strongest country, around which advance and attack will happen
	 * @return countries
	 */
	public Country[] level1(int countryID) {
		Country[] c = new Country[2];
		ArrayList<Integer> neighbourCountryID = d_connectivity.getCountryByID(countryID).getD_neighbours();

		for (int i : neighbourCountryID) {

			//if a neighboring country belongs to same player and has troops>0 then advance to strongest country.
			if (d_player.getD_Country().contains(d_connectivity.getCountryByID(i))) {
				if (d_connectivity.getCountryByID(i).getD_armyDeployedOnCountry() > 0) {
					c[0] = d_connectivity.getCountryByID(i); //from Country
					c[1] = d_connectivity.getCountryByID(countryID); // to country
					return c;
				}
			}

		}

		//if All neighboring country belongs to player but all have 0 troops to advance
		//then this loop goes to 2nd level of neighbors.
		for (int i : neighbourCountryID) {

			neighbourCountryID = d_connectivity.getCountryByID(i).getD_neighbours();
			for (int j : neighbourCountryID) {
				if (d_player.getD_Country().contains(d_connectivity.getCountryByID(j))) {
					if (d_connectivity.getCountryByID(j).getD_armyDeployedOnCountry() > 0) {
						c[0] = d_connectivity.getCountryByID(j); //from country
						c[1] = d_connectivity.getCountryByID(i);// to country
						return c;
					}
				}

			}
		}

		//3rd Neighbour
		for (int i : neighbourCountryID) {

			ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryByID(i).getD_neighbours();
			for (int j : neighbourCountryID2) {
				ArrayList<Integer> neighbourCountryID3 = d_connectivity.getCountryByID(j).getD_neighbours();
				for (int k : neighbourCountryID3) {
					if (d_player.getD_Country().contains(d_connectivity.getCountryByID(k))) {
						if (d_connectivity.getCountryByID(k).getD_armyDeployedOnCountry() > 0) {
							c[0] = d_connectivity.getCountryByID(k); //from country
							c[1] = d_connectivity.getCountryByID(i);// to country
							return c;
						}
					}
				}

			}
		}
		return null;
	}

	public Country[] level(int countryID) {
		Country[] c = new Country[2];
		ArrayList<Integer> neighbourCountryID = d_connectivity.getCountryByID(countryID).getD_neighbours();

		for (int i : neighbourCountryID) {

			//if a neighboring country belongs to same player and has troops>0 then advance to strongest country.
			if (d_player.getD_Country().contains(d_connectivity.getCountryByID(i))) {
				if (d_connectivity.getCountryByID(i).getD_armyDeployedOnCountry() > 0) {
					c[0] = d_connectivity.getCountryByID(i); //from Country
					c[1] = d_connectivity.getCountryByID(countryID); // to country
					return c;
				}
			}
			//if neighboring country belongs to different player then attack it with the strongest country.
			else if (!d_player.getD_Country().contains(d_connectivity.getCountryByID(i))) {
				c[0] = d_connectivity.getCountryByID(countryID); //from Country
				c[1] = d_connectivity.getCountryByID(i); // to country
				return c;
			}

		}

		//if All neighboring country belongs to player but all have 0 troops to advance
		//then this loop goes to 2nd level of neighbors.
		for (int i : neighbourCountryID) {

			neighbourCountryID = d_connectivity.getCountryByID(i).getD_neighbours();
			for (int j : neighbourCountryID) {
				if (d_player.getD_Country().contains(d_connectivity.getCountryByID(j))) {
					if (d_connectivity.getCountryByID(j).getD_armyDeployedOnCountry() > 0) {
						c[0] = d_connectivity.getCountryByID(j); //from country
						c[1] = d_connectivity.getCountryByID(i);// to country
						return c;
					}
				}
				//if neighboring country belongs to different player then attack it with the strongest country.
				else if (!d_player.getD_Country().contains(d_connectivity.getCountryByID(j))) {
					c[0] = d_connectivity.getCountryByID(i); //from Country
					c[1] = d_connectivity.getCountryByID(j); // to country
					return c;
				}

			}
		}

		//3rd Neighbour
		for (int i : neighbourCountryID) {

			ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryByID(i).getD_neighbours();
			for (int j : neighbourCountryID2) {
				ArrayList<Integer> neighbourCountryID3 = d_connectivity.getCountryByID(j).getD_neighbours();
				for (int k : neighbourCountryID3) {
					if (d_player.getD_Country().contains(d_connectivity.getCountryByID(k))) {
						if (d_connectivity.getCountryByID(k).getD_armyDeployedOnCountry() > 0) {
							c[0] = d_connectivity.getCountryByID(k); //from country
							c[1] = d_connectivity.getCountryByID(i);// to country
							return c;
						} else if (!d_player.getD_Country().contains(d_connectivity.getCountryByID(k))) {
							c[0] = d_connectivity.getCountryByID(i); //from Country
							c[1] = d_connectivity.getCountryByID(k); // to country
							return c;
						}
					}
				}

			}
		}
		return null;
	}

}
