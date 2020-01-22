package com.game;

import com.common.Constants;
import com.common.utils.Utils;
import com.dto.PlayerDetails;
import com.goods.Goods;
import com.goods.GoodsType;

import java.util.ArrayList;
import java.util.List;

public class BribeStrategy {
	/**
	 * @param player
	 * @return PlayerDetails
	 * Apply the Bribe strategy for a player
	 */
	public PlayerDetails applyBribeStartegy(PlayerDetails player) {
		// get illegalLList sorted descending
		List<Integer> bag = new ArrayList<>();
		int numberOfGoodsInBag = 0;
		int bribe = 0;
		List<Goods> listOfGoodsSorted;
		List<Integer> goodsIdList = Utils.getGoodsIds(player.getCurrentCards(), GoodsType.Illegal);
		int totalCoins = player.getNumberOfCoins();
		if (goodsIdList.size() > 0) {
			listOfGoodsSorted = Utils.getAssetsIdOrderedDescByProfit(goodsIdList);
			for (Goods good : listOfGoodsSorted) {
				if (numberOfGoodsInBag < Constants.MAX_NUMBER_OF_SELCTED_CARDS) {
					if (totalCoins - good.getPenalty() > 0) {
						if ((numberOfGoodsInBag < 2 && totalCoins - Constants.BRIBE_FOR_ILLEGAL_CARDS_LESS_THAN_TWO > 0) ||
								(numberOfGoodsInBag >= 2 && totalCoins - Constants.BRIBE_FOR_ILLEGAL_CARDS_MORE_THAN_THREE > 0)) {
							numberOfGoodsInBag++;
							totalCoins = totalCoins - good.getPenalty();
							bag.add(good.getId());
						}
					}
				}
			}
			// endFor
			// try to add legal Goods
			if (numberOfGoodsInBag < Constants.MAX_NUMBER_OF_SELCTED_CARDS) {
				goodsIdList = Utils.getGoodsIds(player.getCurrentCards(), GoodsType.Legal);
				if (goodsIdList.size() > 0) {
					listOfGoodsSorted = Utils.getAssetsIdOrderedDescByProfit(goodsIdList);
					for (Goods good : listOfGoodsSorted) {
						if (numberOfGoodsInBag < Constants.MAX_NUMBER_OF_SELCTED_CARDS) {
							if (totalCoins - good.getPenalty() > 0) {
								totalCoins = totalCoins - good.getPenalty();
								numberOfGoodsInBag++;
								bag.add(good.getId());
							}

						}
					}
				}
			}
			// count illegal goods from bag and update bribe from bag
			int numberOfIllegalGoods = Utils.getGoodsIds(bag, GoodsType.Illegal).size();
			if (numberOfIllegalGoods == 1 || numberOfIllegalGoods == 2) {
				bribe = Constants.BRIBE_FOR_ILLEGAL_CARDS_LESS_THAN_TWO;
			} else if (numberOfIllegalGoods > 2) {
				bribe = Constants.BRIBE_FOR_ILLEGAL_CARDS_MORE_THAN_THREE;
			}

		} // end try to put in bag illegal and legal goods
		else { // we don't have illegal goods so we used basic strategy
			PlayerDetails playerWithBasicStrategy = new BasicStrategy().applyBasicStartegy(player);
			return playerWithBasicStrategy;
		}

		if (numberOfGoodsInBag == 0) {
			PlayerDetails playerWithBasicStrategy = new BasicStrategy().applyBasicStartegy(player);
			return playerWithBasicStrategy;
		}
		player.getPlayerBag().setmCurrentGoodsFromBag(bag);
		player.getPlayerBag().setBribeValue(bribe);
		player.setNumberOfCoins(player.getNumberOfCoins() - bribe);
		player.setDeclaredApple(true);
		return player;

	}

}
