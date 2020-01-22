package com.game;

import com.common.utils.Utils;
import com.dto.PlayerDetails;
import com.main.GameInput;
import com.main.Main;

import java.util.List;

public class PlayGame {

	/**
	 * @param playersDetailsList
	 * @param roundNumber
	 * @return List of PlayerDetails
	 * Apply the strategy to each player
	 */
	List<PlayerDetails> applyStrategy(List<PlayerDetails> playersDetailsList, int roundNumber) {
		for (int i = 0; i < playersDetailsList.size(); i++) {
			if (!playersDetailsList.get(i).isSheriff()) {
				PlayerDetails player = new PlayerDetails();
				if (playersDetailsList.get(i).getPlayerStrategy().equalsIgnoreCase("BASIC")) {
					BasicStrategy comerciantStrategy = new BasicStrategy();
					player = comerciantStrategy.applyBasicStartegy(playersDetailsList.get(i));
				} else if (playersDetailsList.get(i).getPlayerStrategy().equalsIgnoreCase("GREEDY")) {
					GreedyStrategy greedyStrategy = new GreedyStrategy();
					player = greedyStrategy.applyGreedyStartegy(playersDetailsList.get(i), roundNumber);
				} else if (playersDetailsList.get(i).getPlayerStrategy().equalsIgnoreCase("BRIBED")) {
					BribeStrategy bribeStrategy = new BribeStrategy();
					player = bribeStrategy.applyBribeStartegy(playersDetailsList.get(i));
				}
				playersDetailsList.set(i, player);
			}
		}
		return playersDetailsList;
	}

	/**
	 * @param playersDetailsList
	 * @param sherifPosition
	 * @return List of PlayerDetails
	 * Performs the inspection for each player, according to the strategy of the player who is serif
	 */
	List<PlayerDetails> inspection(List<PlayerDetails> playersDetailsList, int sherifPosition) {
		PlayerDetails player = new PlayerDetails();
		ApplyInspection applyInspection = new ApplyInspection();
		if (playersDetailsList.get(sherifPosition).getPlayerStrategy().equalsIgnoreCase("BASIC")) {
			playersDetailsList = applyInspection.basic(playersDetailsList);
		} else if (playersDetailsList.get(sherifPosition).getPlayerStrategy().equalsIgnoreCase("GREEDY")) {
			playersDetailsList = applyInspection.greedy(playersDetailsList);
		} else if (playersDetailsList.get(sherifPosition).getPlayerStrategy().equalsIgnoreCase("BRIBED")) {
			playersDetailsList = applyInspection.bribe(playersDetailsList);
		}
		return playersDetailsList;
	}

	/**
	 * @param gameInput strat the game
	 *                  From this method called the other methods corresponding to the 4 phases of the game
	 */
	public void startGame(GameInput gameInput) {
		List<PlayerDetails> playersDetailsList;
		//init players
		int playerNumbers = gameInput.getPlayerNames().size();
		playersDetailsList = new InitPlayers().initPlayerList(playerNumbers, gameInput.getPlayerNames());
		Main.gameAssetIds = gameInput.getAssetIds();

		int roundNum = gameInput.getRounds();
		for (int round = 1; round <= roundNum; round++) {
			for (int subRoundNum = 0; subRoundNum < playerNumbers; subRoundNum++) {
				//init cards for subround
				playersDetailsList = new InitPlayers().initPlayersList4OneSpecificSubRound(subRoundNum, playersDetailsList);
				// Apply strategy
				playersDetailsList = applyStrategy(playersDetailsList, round);
				// Inspection
				playersDetailsList = inspection(playersDetailsList, subRoundNum);
				// update players Tables
				playersDetailsList = Utils.updatePlayersTable(playersDetailsList);
			} // end Current Subround
		} // end Current Round

		// updatePlayersPoints
		playersDetailsList = Utils.updatePlayersPoints(playersDetailsList);
		// display Podium
		System.out.println("Final Ranking");
		String finalRanking = Utils.displayRankingList(playersDetailsList);
		System.out.println(finalRanking);
	}
}
