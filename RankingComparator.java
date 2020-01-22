package com.common;

import com.dto.PlayerDetails;

import java.util.Comparator;

public class RankingComparator implements Comparator<PlayerDetails> {
	@Override
	public int compare(PlayerDetails player1, PlayerDetails player2) {
		return player2.getTotalPoints() - player1.getTotalPoints();
	}
}