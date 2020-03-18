package cz.mbucek.jdtp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
	public static String getRandomMess(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz"; 

		StringBuilder sb = new StringBuilder(n); 

		for (int i = 0; i < n; i++) { 

			int index = (int)(AlphaNumericString.length() * Math.random()); 

			sb.append(AlphaNumericString.charAt(index)); 
		} 

		return sb.toString(); 
	}
	
	public static String encodeMess(String mess) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest(mess.getBytes("UTF-8")));
	}
	
	public static String isCorrectMess(String mess) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((mess + "2V83AFA5-VO24-47DA-9DCA-NU6L05FT0D31").getBytes("UTF-8")));
	}
}
