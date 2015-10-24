package spark.controller.service.security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Random {

	public static String generate(int size) {
		return new BigInteger(size, new SecureRandom()).toString(32);
	}
	
}
