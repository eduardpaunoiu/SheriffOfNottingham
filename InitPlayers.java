package com.game;

import com.common.Constants;
import com.dto.PlayerDetails;
import com.main.Main;

import java.util.ArrayList;
import java.util.List;

public class InitPlayers {
	/**
	 * @param numberOfPlayers
	 * @param playersStrategy
	 * @return List of PlayerDetails
	 * init the players list at the beginning of the game
	 */
	public static List<PlayerDetails> initPlayerList(int numberOfPlayers, List<String> playersStrategy) {
		List<PlayerDetails> playersDetailsList = new ArrayList<>();
		for (int i = 0; i < numberOfPlayers; i++) {
			PlayerDetails player = new PlayerDetails();
			player.setPlayerStrategy(playersStrategy.get(i));
			player.setDefaultPlayerPosition(i);
			playersDetailsList.add(player);
		}
		return playersDetailsList;
	}

	/**
	 * @param subroundNumber
	 * @param playersDetailsList
	 * @return List of PlayerDetails
	 * init the players list at the beginning of the specific subround
	 */
	public static List<PlayerDetails> initPlayersList4OneSpecificSubRound(int subroundNumber,
			List<PlayerDetails> playersDetailsList) {
		for (int i = 0; i < playersDetailsList.size(); i++) {
			playersDetailsList.get(i).setSheriff(false);
			if (playersDetailsList.get(i).getCurrentCards().size() > 0) {
				// remove goods for currentPlayer
				playersDetailsList.get(i).getCurrentCards().removeAll(playersDetailsList.get(i).getCurrentCards());
			}
		}
		// set sheriff
		playersDetailsList.get(subroundNumber).setSheriff(true);
		// add new ten goods for each player
		for (int i = 0; i < playersDetailsList.size(); i++) {
			if (!playersDetailsList.get(i).isSheriff()) {
				playersDetailsList.get(i).getCurrentCards().addAll(Main.gameAssetIds.subList(0, Constants.NUMBER_OF_CARDS));
				Main.gameAssetIds.subList(0, Constants.NUMBER_OF_CARDS).clear();
			}
		}
		return playersDetailsList;
	}
}
