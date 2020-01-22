package com.common.utils;

import com.common.GoodComparator;
import com.common.RankingComparator;
import com.dto.PlayerDetails;
import com.goods.*;

import java.util.*;

public class Utils {
	/**
	 * @param idsList
	 * @return Map
	 * count frecvency for each goodId from a goodIds list
	 */
	public static Map<Integer, Integer> countFrecvency(List<Integer> idsList) {
		Map<Integer, Integer> countGoods = new HashMap<>();
		for (Integer id : idsList) {
			countGoods.put(id, countGoods.getOrDefault(id, 0) + 1);
		}
		return extractKeyWithMaxValueFromMap(countGoods);
	}

	/**
	 * @param map
	 * @return Map
	 * extract keys with max value from a map and return a mao with these keys and values
	 */
	public static Map<Integer, Integer> extractKeyWithMaxValueFromMap(Map<Integer, Integer> map) {
		Map<Integer, Integer> mapWithMaxValues = new HashMap<>();
		int maxValue = (Collections.max(map.values()));
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() == maxValue) {
				mapWithMaxValues.put(entry.getKey(), entry.getValue());
			}
		}
		return mapWithMaxValues;
	}

	/**
	 * @param goodsIdsList
	 * @param goodsType
	 * @return List of integers
	 * return list of illegalGoods or list of legal goods from a list of goods
	 */
	public static List<Integer> getGoodsIds(List<Integer> goodsIdsList, GoodsType goodsType) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		List<Integer> idsList = new ArrayList<>();
		for (Integer id : goodsIdsList) {
			if (goodsFactory.getGoodsById(id).getType() == goodsType) {
				idsList.add(id);
			}
		}
		return idsList;
	}

	/**
	 * @param id
	 * @return int
	 * return profit + bonus for  illegal good with given by Id
	 */
	public static int calculateProfit4IllegalGoodsById(int id) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		Goods good = goodsFactory.getGoodsById(id);
		int defaultProfit = good.getProfit();
		Map<Goods, Integer> bonus = ((IllegalGoods) good).getIllegalBonus();
		int totalBonus = 0;
		for (Map.Entry<Goods, Integer> entry : bonus.entrySet()) {
			totalBonus += entry.getKey().getProfit() * entry.getValue();
		}
		totalBonus += defaultProfit;
		return totalBonus;
	}

	/**
	 * @param playerGoods
	 * @return int
	 * return goodID for illegal good with max profit form player cards list
	 */
	public static int getIllegalGoodsWithMaxProfit(List<Integer> playerGoods) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		int maxProfit = 0;
		int goodIdWithMaxProfit = -1;
		for (Integer goodId : playerGoods) {
			int profit4IllegalGood = goodsFactory.getGoodsById(goodId).getProfit();
			if (profit4IllegalGood > maxProfit) {
				maxProfit = profit4IllegalGood;
				goodIdWithMaxProfit = goodId;
			}
		}
		return goodIdWithMaxProfit;
	}

	/**
	 * @param assetsIdList
	 * @return List of goods
	 * return list o goods id ordered descending by profit
	 */
	public static List<Goods> getAssetsIdOrderedDescByProfit(List<Integer> assetsIdList) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		List<Goods> list = new ArrayList<>();
		for (Integer id : assetsIdList) {
			list.add(goodsFactory.getGoodsById(id));
		}
		GoodComparator goodComparator = new GoodComparator();
		Collections.sort(list, goodComparator);
		Collections.reverse(list);
		return list;
	}

	/**
	 * @param playersList
	 * @return int
	 * return sheriff position in list of Players
	 */
	public static int getSheriffPosition(List<PlayerDetails> playersList) {
		int sheriffPosition = -1;
		for (int i = 0; i < playersList.size(); i++) {
			if (playersList.get(i).isSheriff() == true) {
				sheriffPosition = i;
				break;
			}
		}
		return sheriffPosition;
	}

	/**
	 * @param list
	 * @return List of goodsId
	 * return list of all leggal cards for a player (including legal cards win from bonus for illegal cards)
	 */
	public static List<Integer> getTotalLegalCards(List<Integer> list) {
		List<Integer> result = new ArrayList<>();
		result.addAll(list);
		List<Integer> illegalList = getGoodsIds(list, GoodsType.Illegal);
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		for (Integer id : illegalList) {
			Goods good = goodsFactory.getGoodsById(id);
			Map<Goods, Integer> bonus = ((IllegalGoods) good).getIllegalBonus();
			for (Map.Entry<Goods, Integer> entry : bonus.entrySet()) {
				for (int i = 0; i < entry.getValue(); i++) {
					result.add(entry.getKey().getId());
				}
			}
		}
		return result;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Calculate and apply King bonus and Queen bonus for legal goods
	 */
	public static List<PlayerDetails> applyBonus4LegalGoods(List<PlayerDetails> playersList) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		Map<Integer, Goods> allGoodsMap = goodsFactory.getAllGoods();
		for (Map.Entry<Integer, Goods> entry : allGoodsMap.entrySet()) {
			if (entry.getValue().getType() == GoodsType.Legal) {
				int kingPlayerPosition = -1;
				int queenPlayerPosition = -1;
				int kingBonusValue = ((LegalGoods) entry.getValue()).getKingBonus();
				int queenBonusValue = ((LegalGoods) entry.getValue()).getQueenBonus();
				int id = entry.getKey();
				List<Integer> idFrecvencyList = new ArrayList<>();
				for (int i = 0; i < playersList.size(); i++) {
					List<Integer> totallegalCards = getTotalLegalCards(playersList.get(i).getPlayerTable());

					int frecvency = Collections.frequency(totallegalCards, id);
					idFrecvencyList.add(frecvency);
				} // endFor

				// king position
				int maxFrecvency = Collections.max(idFrecvencyList);
				if (maxFrecvency > 0) {
					for (int i = 0; i < idFrecvencyList.size(); i++) {
						if (idFrecvencyList.get(i) == maxFrecvency) {
							kingPlayerPosition = i;
							idFrecvencyList.set(i, -1);
						}
					}
				}
				// queen position
				maxFrecvency = Collections.max(idFrecvencyList);
				if (maxFrecvency > 0) {
					for (int i = 0; i < idFrecvencyList.size(); i++) {
						if (idFrecvencyList.get(i) == maxFrecvency) {
							queenPlayerPosition = i;
						}
					}
				}
				// update numberOfcoins for first two players
				if (kingPlayerPosition != -1) {
					int totalPoints = playersList.get(kingPlayerPosition).getTotalPoints();
					playersList.get(kingPlayerPosition).setTotalPoints(totalPoints + kingBonusValue);
				}
				if (queenPlayerPosition != -1) {
					int totalPoints = playersList.get(queenPlayerPosition).getTotalPoints();
					playersList.get(queenPlayerPosition).setTotalPoints(totalPoints + queenBonusValue);
				}
			}
		} // end with currentGood
		return playersList;
	}

	/**
	 * @param player
	 * @return int
	 * calculate total profit for good and illegal goods from player Table
	 */
	public static int calculatePointsFromProfit(PlayerDetails player) {
		GoodsFactory goodsFactory = GoodsFactory.getInstance();
		int totalPoints = 0;
		List<Integer> cardsList = player.getPlayerTable();
		for (Integer id : cardsList) {
			Goods good = goodsFactory.getGoodsById(id);
			if (good.getType() == GoodsType.Legal) {
				totalPoints += good.getProfit();
			} else {
				totalPoints += calculateProfit4IllegalGoodsById(good.getId());
			}
		}
		return totalPoints;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Calculates the total score of the players at the end of the game
	 */
	public static List<PlayerDetails> updatePlayersPoints(List<PlayerDetails> playersList) {
		for (int i = 0; i < playersList.size(); i++) {
			playersList.get(i)
					.setTotalPoints(playersList.get(i).getNumberOfCoins() + calculatePointsFromProfit(playersList.get(i)));
		}
		playersList = applyBonus4LegalGoods(playersList);
		return playersList;
	}

	/**
	 * @param playersList
	 * @return List of PlayerDetails
	 * Update player Table with goods from bag at the end of the subround
	 */
	public static List<PlayerDetails> updatePlayersTable(List<PlayerDetails> playersList) {
		for (int i = 0; i < playersList.size(); i++) {
			playersList.get(i).getPlayerTable().addAll(playersList.get(i).getPlayerBag().getmCurrentGoodsFromBag());
			playersList.get(i).getPlayerBag().getmCurrentGoodsFromBag()
					.removeAll(playersList.get(i).getPlayerBag().getmCurrentGoodsFromBag());
		}
		return playersList;
	}

	/**
	 * @param playersList
	 * @return String
	 * display Ranking list at the end of the game
	 */
	public static String displayRankingList(List<PlayerDetails> playersList) {
		// sort playerList by totalPoints
		RankingComparator rankingComparator = new RankingComparator();
		Collections.sort(playersList, rankingComparator);
		String podium = "";
		for (PlayerDetails player : playersList) {
			podium += player.getDefaultPlayerPosition() + " " +
					player.getPlayerStrategy().toUpperCase() + " " +
					player.getTotalPoints() + "\n";
		}
		return podium;
	}
}