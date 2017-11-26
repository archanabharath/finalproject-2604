package com.itm.food.dao.operation;

import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.Payment;
import com.itm.food.model.PaymentDB;

public class PaymentOperations {

	private static final Logger log = Logger.getLogger(PaymentOperations.class);

	PaymentDB paymentDB = new PaymentDB();

	public String addPaymentInfo(Payment newCard) {
		log.info("payment operations started");
		String cardId = null;
		try {
			cardId = paymentDB.add(newCard);
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

	public List<Payment> getCards(String customerId) throws Exception {
		return paymentDB.getAllCardsFromDB(customerId);

	}

}
