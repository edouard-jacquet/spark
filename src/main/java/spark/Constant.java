package spark;

public final class Constant {
	
	// root
	public static final String ROOT_FOLDER = Constant.class.getResource("/").getPath().substring(0, Constant.class.getResource("/").getPath().indexOf("WEB-INF"));
	
	// storage
	public static final String STORAGE_ROOT_DIRECTORY = "storage";
	public static final String STORAGE_ROOT_FOLDER = Constant.ROOT_FOLDER + Constant.STORAGE_ROOT_DIRECTORY +"/";
	public static final String STORAGE_TEMPORARY_DIRECTORY = "temporary";
	public static final String STORAGE_TEMPORARY_FOLDER = Constant.STORAGE_ROOT_FOLDER + Constant.STORAGE_TEMPORARY_DIRECTORY +"/";
	public static final String STORAGE_DOCUMENT_DIRECTORY = "document";
	public static final String STORAGE_DOCUMENT_FOLDER = Constant.STORAGE_ROOT_FOLDER + Constant.STORAGE_DOCUMENT_DIRECTORY +"/";
	public static final String STORAGE_INDEX_DIRECTORY = "index";
	public static final String STORAGE_INDEX_FOLDER = Constant.STORAGE_ROOT_FOLDER + Constant.STORAGE_INDEX_DIRECTORY +"/";
	
	// public storage
	public static final String PUBLICSTORAGE_ROOT_FOLDER = Constant.STORAGE_ROOT_DIRECTORY +"/";
	public static final String PUBLICSTORAGE_DOCUMENT_FOLDER = Constant.PUBLICSTORAGE_ROOT_FOLDER + Constant.STORAGE_DOCUMENT_DIRECTORY +"/";
	
	// regex
	public static final String REGEX_LOGIN = "[a-zA-Z][a-zA-Z0-9.]+";
	public static final String REGEX_PASSWORD = "[a-zA-Z][a-zA-Z0-9.]+";
	public static final String REGEX_ID = "[1-9][0-9]*";
	public static final String REGEX_PAGE = "[1-9][0-9]*";
	public static final String REGEX_QUERY = ".{3,50}";
	
	// session
	public static final String SESSION_USER_NAME = "guest";
	
	// cookie
	public static final String COOKIE_USER_NAME = "spark_auth";
	public static final String COOKIE_DELIMITER = "----";
	public static final int COOKIE_MAXAGE = 60 * 60 * 24;
	
	// document
	public static final int DOCUMENT_MAXRESULT = 10;
	public static final int DOCUMENT_SIMILAR_MAXRESULT = 4;
	
	// suggestion
	public static final int SUGGESTION_MAXRESULT = 4;

}
