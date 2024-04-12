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
				if (!d_player.getD_country().contains(l_neighborCountry)) {
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

		for (Country l_country : d_player.getD_country()) {
			int l_numArmies = l_country.getD_armyCount();
			if (l_numArmies > l_maxArmies) {
				l_maxArmies = l_numArmies;
				d_StrongestCountry = l_country;
			}
		}

		if (d_player.getD_country().isEmpty())
			return null;
		if (l_maxArmies == 0) {
			d_StrongestCountry = d_player.getD_country().get(0);
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
			str = str + toMoveFrom().getD_countryId() + " " + d_player.getD_armyNumber();
			System.out.println(str);
			o.setOrderContent(str);
			return o;
		} else if (GameEngine.getPhaseName().equals("Attack")) {
			str = "advance ";
			Country[] c = level(toMoveFrom().getD_countryId());

			if (c != null) {
				str = str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyCount();
				System.out.println(str);
				o.setOrderContent(str);
				ShowMap.showMap(d_connectivity.getD_continentsList(), d_connectivity.getD_countriesList(), Play.getL_playersArray());
				return o;
			} else {
				return null;
			}

		} else if (GameEngine.getPhaseName().equals("Fortify")) {
			str = "advance ";
			Country[] c = level1(toMoveFrom().getD_countryId());

			if (c != null) {
				str = str + c[0].getD_countryName() + " " + c[1].getD_countryName() + " " + c[0].getD_armyCount();
				System.out.println(str);
				o.setOrderContent(str);
				ShowMap.showMap(d_connectivity.getD_continentsList(), d_connectivity.getD_countriesList(), Play.getL_playersArray());
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
		ArrayList<Integer> neighbourCountryID = d_connectivity.getCountryFromCountryId(countryID).getD_neighbours();

		for (int i : neighbourCountryID) {

			//if a neighboring country belongs to same player and has troops>0 then advance to strongest country.
			if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(i))) {
				if (d_connectivity.getCountryFromCountryId(i).getD_armyCount() > 0) {
					c[0] = d_connectivity.getCountryFromCountryId(i); //from Country
					c[1] = d_connectivity.getCountryFromCountryId(countryID); // to country
					return c;
				}
			}

		}

		//if All neighboring country belongs to player but all have 0 troops to advance
		//then this loop goes to 2nd level of neighbors.
		for (int i : neighbourCountryID) {

			neighbourCountryID = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
			for (int j : neighbourCountryID) {
				if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(j))) {
					if (d_connectivity.getCountryFromCountryId(j).getD_armyCount() > 0) {
						c[0] = d_connectivity.getCountryFromCountryId(j); //from country
						c[1] = d_connectivity.getCountryFromCountryId(i);// to country
						return c;
					}
				}

			}
		}

		//3rd Neighbour
		for (int i : neighbourCountryID) {

			ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
			for (int j : neighbourCountryID2) {
				ArrayList<Integer> neighbourCountryID3 = d_connectivity.getCountryFromCountryId(j).getD_neighbours();
				for (int k : neighbourCountryID3) {
					if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(k))) {
						if (d_connectivity.getCountryFromCountryId(k).getD_armyCount() > 0) {
							c[0] = d_connectivity.getCountryFromCountryId(k); //from country
							c[1] = d_connectivity.getCountryFromCountryId(i);// to country
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
		ArrayList<Integer> neighbourCountryID = d_connectivity.getCountryFromCountryId(countryID).getD_neighbours();

		for (int i : neighbourCountryID) {

			//if a neighboring country belongs to same player and has troops>0 then advance to strongest country.
			if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(i))) {
				if (d_connectivity.getCountryFromCountryId(i).getD_armyCount() > 0) {
					c[0] = d_connectivity.getCountryFromCountryId(i); //from Country
					c[1] = d_connectivity.getCountryFromCountryId(countryID); // to country
					return c;
				}
			}
			//if neighboring country belongs to different player then attack it with the strongest country.
			else if (!d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(i))) {
				c[0] = d_connectivity.getCountryFromCountryId(countryID); //from Country
				c[1] = d_connectivity.getCountryFromCountryId(i); // to country
				return c;
			}

		}

		//if All neighboring country belongs to player but all have 0 troops to advance
		//then this loop goes to 2nd level of neighbors.
		for (int i : neighbourCountryID) {

			neighbourCountryID = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
			for (int j : neighbourCountryID) {
				if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(j))) {
					if (d_connectivity.getCountryFromCountryId(j).getD_armyCount() > 0) {
						c[0] = d_connectivity.getCountryFromCountryId(j); //from country
						c[1] = d_connectivity.getCountryFromCountryId(i);// to country
						return c;
					}
				}
				//if neighboring country belongs to different player then attack it with the strongest country.
				else if (!d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(j))) {
					c[0] = d_connectivity.getCountryFromCountryId(i); //from Country
					c[1] = d_connectivity.getCountryFromCountryId(j); // to country
					return c;
				}

			}
		}

		//3rd Neighbour
		for (int i : neighbourCountryID) {

			ArrayList<Integer> neighbourCountryID2 = d_connectivity.getCountryFromCountryId(i).getD_neighbours();
			for (int j : neighbourCountryID2) {
				ArrayList<Integer> neighbourCountryID3 = d_connectivity.getCountryFromCountryId(j).getD_neighbours();
				for (int k : neighbourCountryID3) {
					if (d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(k))) {
						if (d_connectivity.getCountryFromCountryId(k).getD_armyCount() > 0) {
							c[0] = d_connectivity.getCountryFromCountryId(k); //from country
							c[1] = d_connectivity.getCountryFromCountryId(i);// to country
							return c;
						} else if (!d_player.getD_country().contains(d_connectivity.getCountryFromCountryId(k))) {
							c[0] = d_connectivity.getCountryFromCountryId(i); //from Country
							c[1] = d_connectivity.getCountryFromCountryId(k); // to country
							return c;
						}
					}
				}

			}
		}
		return null;
	}

}
