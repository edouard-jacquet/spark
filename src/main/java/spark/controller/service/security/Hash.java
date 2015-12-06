package spark.controller.service.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	
	public static String generate(String algorithm, String text) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(text.getBytes("UTF-8"));
			byte[] digest = messageDigest.digest();
			
			StringBuffer stringBuffer = new StringBuffer();
			for(byte b : digest) {
				stringBuffer.append(String.format("%02x", b & 0xff));
			}
			return stringBuffer.toString();
		}
		catch(NoSuchAlgorithmException | UnsupportedEncodingException exception) {
			exception.printStackTrace();
			return null;
		}
	}

}
