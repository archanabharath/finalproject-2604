package com.itm.food.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itm.food.dao.AbstractDomain;
import com.itm.food.dao.Payment;
import com.itm.food.model.db.MySQLQuery;


public class PaymentDB extends AbstractDB implements IDBAccess{
	
	private static final Logger log = Logger.getLogger(PaymentDB.class);

	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		Payment newCard = (Payment) object;
		log.debug("Adding new card");	
		try {
		PreparedStatement preparedstatement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_PAYMENT_INSERT);
		preparedstatement.setString(1, newCard.getCustomerid());
		preparedstatement.setString(2, newCard.getCardid());
		preparedstatement.setLong(3, newCard.getCardNo()); 
		preparedstatement.setString(4, newCard.getCardExpDate());
		preparedstatement.setString(5, newCard.getCardType());
		preparedstatement.setString(6,newCard.getNameOnCard());
		preparedstatement.executeUpdate();
		preparedstatement.close();
		
		
	}catch(SQLException e) {
		log.error(e.getMessage());
	}
		log.debug("Card added to payment table");
		return newCard.getCardid();
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends AbstractDomain> T find(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public List<Payment> getAllCardsFromDB(String customerId) throws Exception{
		ResultSet rsAllCards;
		List<Payment> cardsList = new ArrayList<Payment>();
		log.debug("Getting all cards");
		try {
			PreparedStatement preparestatement = this.getDBConnection().prepareStatement(MySQLQuery.SQL_GET_ALL_CARDS_OF_CUSTOMER);
			preparestatement.setString(1, customerId);
			rsAllCards = preparestatement.executeQuery();//NAME_ON_CARD,CARD_TYPE,EXPIRY,CARD_NO
			while(rsAllCards.next()) {
				Payment newcard = new Payment();
				newcard.setNameOnCard(rsAllCards.getString(1));
				newcard.setCardType(rsAllCards.getString(2));
				newcard.setCardExpDate(rsAllCards.getString(3));
				newcard.setCardNo(rsAllCards.getLong(4));
				newcard.setCardid(rsAllCards.getString(5));
				newcard.setCustomerid(rsAllCards.getString(6));
				
				cardsList.add(newcard);
			}
		}catch(SQLException e) {
			log.error(e.getMessage());
		}
		return cardsList;
		
	}
	

}
