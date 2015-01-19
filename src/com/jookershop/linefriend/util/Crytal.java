package com.jookershop.linefriend.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crytal {
	
	public static String toHash(String stringToHash) {
		MessageDigest digest;
		String rep = "";
		try {
			digest = MessageDigest.getInstance("SHA-1");
			byte[] result = digest.digest(stringToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();

			for (byte b : result)
			{
			    sb.append(String.format("%02X", b));
			}

			rep = sb.toString();				
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rep;
	}
}
