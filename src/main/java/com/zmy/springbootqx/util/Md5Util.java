package com.zmy.springbootqx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	
	public static String encoded(String rand) throws NoSuchAlgorithmException {
		String msg = rand;
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(msg.getBytes());
		byte[] digest = messageDigest.digest();
		String new_pw = byte2hex(digest);
		return new_pw.toLowerCase();
	}
	
	private static String byte2hex(byte[] b) {
		StringBuilder buf = new StringBuilder();
		for (byte c : b) {
			buf.append(String.format("%02x", c).toUpperCase());
		}
		return buf.toString();
	}
}
