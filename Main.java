package com.main;

import com.game.PlayGame;

import java.util.List;

public class Main {
	public static List<Integer> gameAssetIds;

	public static void main(final String[] args) {
		GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);

		//String testInputFile = "c:/tests/base1test.in";
		//String testOutputFile = "c:/tests/base1testOut.out";
		//GameInputLoader gameInputLoader = new GameInputLoader(testInputFile, testOutputFile);
		GameInput gameInput = gameInputLoader.load();
		gameAssetIds = gameInput.getAssetIds();
		PlayGame game = new PlayGame();
		game.startGame(gameInput);
	}
}
