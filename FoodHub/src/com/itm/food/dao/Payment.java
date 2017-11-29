package com.itm.food.dao;

import com.itm.food.util.CardUtil;

public class Payment extends AbstractDomain {

	int customerid;
	int cardid;
	long cardNo;
	String cardExpDate;
	String cardType;
	String nameOnCard;

	/**
	 * @return the customerid
	 */
	public int getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid
	 *            the customerid to set
	 */
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	/**
	 * @return the cardid
	 */
	public int getCardid() {
		return cardid;
	}

	/**
	 * @param cardid
	 *            the cardid to set
	 */
	public void setCardid(int cardid) {
		this.cardid = cardid;
	}

	/**
	 * @return the cardNo
	 */
	public long getCardNo() {
		return cardNo;
	}

	/**
	 * @param l
	 *            the cardNo to set
	 */
	public void setCardNo(long l) {
		this.cardNo = l;
	}

	/**
	 * @return the cardExpDate
	 */
	public String getCardExpDate() {
		return cardExpDate;
	}

	/**
	 * @param cardExpDate
	 *            the cardExpDate to set
	 */
	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * @param nameOnCard
	 *            the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getNameOnCard());
		builder.append("-");
		builder.append(this.getCardType());
		builder.append("-");
		builder.append(this.getCardExpDate());
		builder.append("-");
		builder.append(CardUtil.maskCardNo(String.valueOf(this.getCardNo())));
		return builder.toString();
	}

}
