package spark;

import org.apache.lucene.util.Version;

public final class Constant {
	
	// lucene
	public static final Version LUCENE_VERSION = Version.LATEST;
	
	// regex
	public static final String REGEX_LOGIN = "[a-zA-Z][a-zA-Z0-9.]+";
	public static final String REGEX_PASSWORD = "[a-zA-Z][a-zA-Z0-9.]+";
	
	// session
	public static final String SESSION_USER_NAME = "guest";
	
	// cookie
	public static final String COOKIE_USER_NAME = "spark_auth";
	public static final String COOKIE_DELIMITER = "----";
	public static final int COOKIE_MAXAGE = 60 * 60 * 24;

}
