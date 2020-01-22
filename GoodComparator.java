package com.common;

import com.goods.Goods;

import java.util.Comparator;

public class GoodComparator implements Comparator<Goods> {
	@Override
	public int compare(Goods g1, Goods g2) {
		int profit = g1.getProfit() - g2.getProfit();

		if (profit != 0) {
			return profit;
		}
		return g1.getId() - g2.getId();
	}
}
