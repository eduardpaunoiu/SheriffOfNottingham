package com.game;

import com.common.utils.Utils;
import com.dto.PlayerDetails;
import com.goods.GoodsFactory;
import com.goods.GoodsType;

import java.util.*;

public class BasicStrategy {

	/**
	 * @param player
	 * @return PlayerDetails
	 * Apply the basic strategy for a player
	 */
	public PlayerDetails applyBasicStartegy(PlayerDetails player) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		List<Integer> playerGoods = new ArrayList<>();
		playerGoods = Utils.getGoodsIds(player.getCurrentCards(), GoodsType.Legal);

		if (playerGoods.size() == 0) {
			//player has only illegal goods
			playerGoods = Utils.getGoodsIds(player.getCurrentCards(), GoodsType.Illegal);
			if (playerGoods.size() > 0) {
				int illegalGoodIdWithMaxProfit = Utils.getIllegalGoodsWithMaxProfit(playerGoods);
				// update playerBag
				player.getPlayerBag().getmCurrentGoodsFromBag().add(illegalGoodIdWithMaxProfit);
				player.setDeclaredApple(true);
			}
			return player;
		}

		Map<Integer, Integer> map = Utils.countFrecvency(playerGoods);
		Map<Integer, Integer> originalFrecvencyMap = new HashMap<>(map);

		int goodId = -1;
		int goodIDFrecvency = -1;
		if (map.size() > 1) {
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				Integer profit = goodsFactory.getGoodsById(entry.getKey()).getProfit();
				map.replace(entry.getKey(), entry.getValue() * profit);
			}
			map = Utils.extractKeyWithMaxValueFromMap(map);
			if (map.size() > 1) {
				goodId = (Collections.max(map.keySet()));
			} else {
				goodId = (int) map.keySet().toArray()[0];
			}
		} else {
			goodId = (int) map.keySet().toArray()[0];
		}
		// update playerBag
		goodIDFrecvency = originalFrecvencyMap.get(goodId);
		for (int i = 0; i < goodIDFrecvency; i++) {
			player.getPlayerBag().getmCurrentGoodsFromBag().add(goodId);
		}
		return player;
	}
}
