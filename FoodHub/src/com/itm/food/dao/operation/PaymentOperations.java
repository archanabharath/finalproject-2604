package com.itm.food.dao.operation;

import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Payment;
import com.itm.food.model.PaymentDB;

public class PaymentOperations {
	String cardId = null;
	private static final Logger log = Logger.getLogger(PaymentOperations.class);

	public String addPaymentInfo(Payment newCard) {
		log.info("payment operations started");
		PaymentDB addNewCard = new PaymentDB();
		try {
			cardId = addNewCard.add(newCard);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.debug("cardId returned to paymentOperations :" + cardId);
		return cardId;

	}

	public int updatePaymentInfo(int paymentId) {
		return 0;
	}

	public int deletePaymentInfo(int paymentId) {
		return 0;
	}

	public List<Payment> getCards(Payment getCards) throws Exception {
		PaymentDB callPaymentDBTOGetCards = new PaymentDB();
		return callPaymentDBTOGetCards.getAllCardsFromDB(getCards);

	}

}
