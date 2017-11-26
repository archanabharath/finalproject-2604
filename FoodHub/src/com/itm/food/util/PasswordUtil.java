package com.itm.food.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class PasswordUtil {
	
	private static final Logger log = Logger.getLogger(PasswordUtil.class);

	public static final String AES = "AES";
	private static final String KEY = "FF1264A8F59BD8B0A331FF8A9287E26A"; // DO NOT CHANGE THIS KEY

	public static String encryptPassword(String password)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		log.trace("Encrypting Password...");
		byte[] bytekey = hexStringToByteArray(PasswordUtil.KEY);
		SecretKeySpec sks = new SecretKeySpec(bytekey, PasswordUtil.AES);
		Cipher cipher = Cipher.getInstance(PasswordUtil.AES);
		cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
		byte[] encrypted = cipher.doFinal(password.getBytes());
		return byteArrayToHexString(encrypted);
	}

	public static String decryptPassword(String encryptedPassword)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		log.trace("Decrypting Password...");
		byte[] bytekey = hexStringToByteArray(PasswordUtil.KEY);
		SecretKeySpec sks = new SecretKeySpec(bytekey, PasswordUtil.AES);
		Cipher cipher = Cipher.getInstance(PasswordUtil.AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(encryptedPassword));
		return new String(decrypted);
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
}
