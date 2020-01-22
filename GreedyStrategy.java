package com.game;

import com.common.utils.Utils;
import com.dto.PlayerDetails;
import com.goods.GoodsType;

import java.util.List;

public class GreedyStrategy {

	/**
	 * @param player
	 * @return PlayerDetails
	 * Apply the Greedy strategy for a player
	 */

	public PlayerDetails applyGreedyStartegy(PlayerDetails player, int roundNumber) {
		PlayerDetails palyerAfterApplyStrategy = new BasicStrategy().applyBasicStartegy(player);
		if (roundNumber % 2 == 0 && player.getPlayerBag().getmCurrentGoodsFromBag().size() < 8) {
			// try to add an illegalGood in the bag
			List<Integer> playerIllegalGodsList = Utils.getGoodsIds(player.getCurrentCards(), GoodsType.Illegal);
			if (playerIllegalGodsList.size() > 0) {
				int illegalGoodIdWithMaxProfit = Utils.getIllegalGoodsWithMaxProfit(playerIllegalGodsList);
				palyerAfterApplyStrategy.getPlayerBag().getmCurrentGoodsFromBag().add(illegalGoodIdWithMaxProfit);
				palyerAfterApplyStrategy.setDeclaredApple(true);
			}
		}
		return palyerAfterApplyStrategy;
	}
}
