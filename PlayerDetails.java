package com.dto;

import com.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Store details about player
 */
public class PlayerDetails {
	private List<Integer> currentCards; // current cards (Ids) from hand for one player
	private List<Integer> playerTable; // goods ids from player table
	private PlayerBag playerBag; // bag with cuurent goods for inspection and bribe (if exists)
	private boolean isSheriff; // is true if player is sherrif for the current subround and false else
	private int numberOfCoins; // default is 80
	private boolean declaredApple;
	private int totalPoints;
	private int defaultPlayerPosition;
	private String playerStrategy;

	public PlayerDetails() {
		currentCards = new ArrayList<>();
		playerTable = new ArrayList<>();
		playerBag = new PlayerBag();
		isSheriff = false;
		numberOfCoins = Constants.DEFAULT_NUMBER_OF_COINS;
		declaredApple = false;
		totalPoints = 0;
		defaultPlayerPosition = -1;
		playerStrategy = "";
	}

	@Override public String toString() {
		return "PlayerDetails{" +
				"mCurrentCards=" + currentCards + "\n" +
				", mPlayerTable=" + playerTable + "\n" +
				", mPlayerBag=" + playerBag + "\n" +
				", isSheriff=" + isSheriff + "\n" +
				", mNumberOfCoins=" + numberOfCoins + "\n" +
				", declaredApple=" + declaredApple + "\n" +
				", totalPoints=" + totalPoints + "\n" +
				", defaultPlayerPosition=" + defaultPlayerPosition + "\n" +
				", playerStrategy='" + playerStrategy + '\'' +
				'}' + "\n\n";
	}

	public List<Integer> getCurrentCards() {
		return currentCards;
	}

	public void setCurrentCards(List<Integer> currentCards) {
		this.currentCards = currentCards;
	}

	public List<Integer> getPlayerTable() {
		return playerTable;
	}

	public void setPlayerTable(List<Integer> playerTable) {
		this.playerTable = playerTable;
	}

	public PlayerBag getPlayerBag() {
		return playerBag;
	}

	public void setPlayerBag(PlayerBag playerBag) {
		this.playerBag = playerBag;
	}

	public boolean isSheriff() {
		return isSheriff;
	}

	public void setSheriff(boolean sheriff) {
		isSheriff = sheriff;
	}

	public int getNumberOfCoins() {
		return numberOfCoins;
	}

	public void setNumberOfCoins(int numberOfCoins) {
		this.numberOfCoins = numberOfCoins;
	}

	public boolean isDeclaredApple() {
		return declaredApple;
	}

	public void setDeclaredApple(boolean declaredApple) {
		this.declaredApple = declaredApple;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getDefaultPlayerPosition() {
		return defaultPlayerPosition;
	}

	public void setDefaultPlayerPosition(int defaultPlayerPosition) {
		this.defaultPlayerPosition = defaultPlayerPosition;
	}

	public String getPlayerStrategy() {
		return playerStrategy;
	}

	public void setPlayerStrategy(String playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

}
