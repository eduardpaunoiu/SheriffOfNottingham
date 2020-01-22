package com.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Store details about cards from player bag and the value of the bribe, if it exists
 */
public class PlayerBag {
	private List<Integer> mCurrentGoodsFromBag; // goods from bag before inspection

	private Integer bribeValue; // can be 0

	public PlayerBag() {
		mCurrentGoodsFromBag = new ArrayList<>();
		bribeValue = 0;
	}

	@Override public String toString() {
		return "PlayerBag{" +
				"mCurrentGoodsFromBag=" + mCurrentGoodsFromBag +
				", bribeValue=" + bribeValue +
				'}';
	}

	public List<Integer> getmCurrentGoodsFromBag() {
		return mCurrentGoodsFromBag;
	}

	public void setmCurrentGoodsFromBag(List<Integer> mCurrentGoodsFromBag) {
		this.mCurrentGoodsFromBag = mCurrentGoodsFromBag;
	}

	public Integer getBribeValue() {
		return bribeValue;
	}

	public void setBribeValue(Integer bribeValue) {
		this.bribeValue = bribeValue;
	}

}
