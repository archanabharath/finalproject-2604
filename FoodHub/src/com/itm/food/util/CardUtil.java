package com.itm.food.util;

public class CardUtil {

	/**
	 * Mask card number before printing since it is sensitive data
	 * 
	 * @param cardNoForMasking
	 * @param maskPattern
	 * @return
	 */
	public static String maskCardNo(String cardNoForMasking) {
		
		String maskPattern = "XXXX-XXXX-XXXX-####";

		// format the number
		int index = 0;
		StringBuilder maskedNumber = new StringBuilder();
		for (int i = 0; i < maskPattern.length(); i++) {
			char c = maskPattern.charAt(i);
			if (c == '#') {
				maskedNumber.append(cardNoForMasking.charAt(index));
				index++;
			} else if (c == 'X') {
				maskedNumber.append(c);
				index++;
			} else {
				maskedNumber.append(c);
			}
		}

		// return the masked number
		return maskedNumber.toString();
	}

}
