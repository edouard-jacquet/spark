package spark;

import org.apache.lucene.util.Version;

public final class Constant {
	
	// root
	public static final String ROOT_FOLDER = Constant.class.getResource("/").getPath().substring(0, Constant.class.getResource("/").getPath().indexOf("WEB-INF"));
	
	// storage
	public static final String STORAGE_ROOT_FOLDER = Constant.ROOT_FOLDER +"storage/";
	public static final String STORAGE_TEMPORARY_FOLDER = Constant.STORAGE_ROOT_FOLDER +"temporary/";
	
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
