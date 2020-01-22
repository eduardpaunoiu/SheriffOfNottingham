package com.game;

import com.common.utils.Utils;
import com.dto.PlayerDetails;
import com.goods.GoodsFactory;
import com.main.Main;

import java.util.ArrayList;
import java.util.List;

public class ApplyInspection {
	int penalty4Sheriff = 0;
	int wim4Sheriff = 0;

	/**
	 * @param player
	 * @return Check a player's bag and apply the penalty or win depending on the contents of the bag
	 */
	public PlayerDetails inspectionPlayer(PlayerDetails player) {
		List<Integer> confiscatedGoodsList = new ArrayList<>();
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		penalty4Sheriff = 0;
		wim4Sheriff = 0;
		//inspection player i
		if (!player.isDeclaredApple()) {
			// is onest player -> sheriff get penalty
			if (player.getCurrentCards().size() > 0) {
				int penalty4PlayerGood = goodsFactory.getGoodsById(player.getPlayerBag().getmCurrentGoodsFromBag().get(0))
						.getPenalty();
				penalty4Sheriff = player.getPlayerBag().getmCurrentGoodsFromBag().size() * penalty4PlayerGood;
				// update player totoalNumberOfCoins
				player.setNumberOfCoins(player.getNumberOfCoins() + penalty4Sheriff);
			}
		} else {
			// isDeclaredApple() == true -> player get penalty
			if (player.getCurrentCards().size() > 0) {
				for (int id = 0; id < player.getPlayerBag().getmCurrentGoodsFromBag().size(); id++) {
					if (player.getPlayerBag().getmCurrentGoodsFromBag().get(id) != 0) {
						// 0 -> is an apple
						int penalty = goodsFactory.getGoodsById(player.getPlayerBag().getmCurrentGoodsFromBag().get(id))
								.getPenalty();
						wim4Sheriff += penalty;
						// update player totoalNumberOfCoins
						player.setNumberOfCoins(player.getNumberOfCoins() - penalty);
						// update confiscatedGoodsList
						confiscatedGoodsList.add(player.getPlayerBag().getmCurrentGoodsFromBag().get(id));
					}
				} // end for
			}
		} // endElse
		if (player.getPlayerBag().getBribeValue() > 0) {
			player.setNumberOfCoins(player.getNumberOfCoins() + player.getPlayerBag().getBribeValue());
			player.getPlayerBag().setBribeValue(0);
		}
		if (confiscatedGoodsList.size() > 0) {
			for (Integer id : confiscatedGoodsList) {
				player.getCurrentCards().remove(id);
			}
			Main.gameAssetIds.addAll(confiscatedGoodsList);
		}
		return player;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Apply inspection when the sheriff plays the basic strategy in the current subround
	 */
	public List<PlayerDetails> basic(List<PlayerDetails> playersList) {
		// sheriff is basic
		int sherifPosition = Utils.getSheriffPosition(playersList);
		for (int i = 0; i < playersList.size(); i++) {
			if (playersList.get(i).isSheriff() == false) {
				if (playersList.get(sherifPosition).getNumberOfCoins() >= 16) {
					PlayerDetails player = inspectionPlayer(playersList.get(i));
					playersList.set(i, player);
					if (penalty4Sheriff > 0) {
						playersList.get(sherifPosition)
								.setNumberOfCoins(playersList.get(sherifPosition).getNumberOfCoins() - penalty4Sheriff);
					}
					if (wim4Sheriff > 0) {
						playersList.get(sherifPosition).setNumberOfCoins(player.getNumberOfCoins() + wim4Sheriff);
					}
				} // end if player has suffcient coins
			} // end if is sherriff
		} // end for
		return playersList;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Apply inspection when the sheriff plays the greedy strategy in the current subround
	 */
	public List<PlayerDetails> greedy(List<PlayerDetails> playersList) {
		// sheriff is greedy
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		int sherifPosition = Utils.getSheriffPosition(playersList);
		int sheriffTotalBribe = 0;
		for (int i = 0; i < playersList.size(); i++) {
			if (playersList.get(i).isSheriff() == false) {
				if (playersList.get(sherifPosition).getNumberOfCoins() >= 16 && playersList.get(i).getPlayerBag()
						.getBribeValue() == 0) {
					PlayerDetails player = inspectionPlayer(playersList.get(i));
					playersList.set(i, player);
					if (penalty4Sheriff > 0) {
						playersList.get(sherifPosition).setNumberOfCoins(player.getNumberOfCoins() - penalty4Sheriff);
					}
					if (wim4Sheriff > 0) {
						playersList.get(sherifPosition).setNumberOfCoins(player.getNumberOfCoins() + wim4Sheriff);
					}
				} else {
					sheriffTotalBribe += playersList.get(i).getPlayerBag().getBribeValue();
					playersList.get(i).getPlayerBag().setBribeValue(0);
				}
			} // end if is sherriff
		} // end for
		if (sheriffTotalBribe > 0) {
			playersList.get(sherifPosition)
					.setNumberOfCoins(playersList.get(sherifPosition).getNumberOfCoins() + sheriffTotalBribe);
		}
		return playersList;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Apply inspection when the sheriff plays the bribe strategy in the current subround
	 */
	public List<PlayerDetails> bribe(List<PlayerDetails> playersList) {
		// sheriff is greedy
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		int sherifPosition = Utils.getSheriffPosition(playersList);
		int position4firstPlayerInspected = -1;
		int position4secondPlayerInspected = -1;
		if (sherifPosition == 0) {
			position4firstPlayerInspected = playersList.size() - 1;
			position4secondPlayerInspected = sherifPosition + 1;
		} else if (sherifPosition == playersList.size()) {
			position4firstPlayerInspected = sherifPosition - 1;
			position4secondPlayerInspected = 0;
		} else {
			position4firstPlayerInspected = sherifPosition - 1;
			position4secondPlayerInspected = sherifPosition + 1;
		}
		List<Integer> playersIds = new ArrayList<>();
		playersIds.add(position4firstPlayerInspected);
		playersIds.add(position4secondPlayerInspected);

		int sheriffTotalBribe = 0;
		for (int i = 0; i < playersIds.size(); i++) {
			//apply inspection players for left sheriff and right sheriff
			if (playersList.get(sherifPosition).getNumberOfCoins() >= 16) {
				PlayerDetails player = inspectionPlayer(playersList.get(playersIds.get(i)));
				playersList.set(playersIds.get(i), player);
				if (penalty4Sheriff > 0) {
					playersList.get(sherifPosition).setNumberOfCoins(player.getNumberOfCoins() - penalty4Sheriff);
				}
				if (wim4Sheriff > 0) {
					playersList.get(sherifPosition).setNumberOfCoins(player.getNumberOfCoins() + wim4Sheriff);
				}
			} else {
				sheriffTotalBribe += playersList.get(playersIds.get(i)).getPlayerBag().getBribeValue();
				playersList.get(playersIds.get(i)).getPlayerBag().setBribeValue(0);
			}
		}

		for (int i = 0; i < playersList.size(); i++) {
			if (i != sherifPosition && i != position4firstPlayerInspected && i != position4secondPlayerInspected) {
				sheriffTotalBribe += playersList.get(i).getPlayerBag().getBribeValue();
				playersList.get(i).getPlayerBag().setBribeValue(0);
			}
		}

		if (sheriffTotalBribe > 0) {
			playersList.get(sherifPosition)
					.setNumberOfCoins(playersList.get(sherifPosition).getNumberOfCoins() + sheriffTotalBribe);
		}
		return playersList;
	}

}
