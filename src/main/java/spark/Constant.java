package spark;

public final class Constant {
	
	/* ============================== *\
	   => LIMIT
	\* ============================== */
	// query
	public static final int QUERY_MINLENGTH = 3;
	public static final int QUERY_MAXLENGTH = 50;
	// document
	public static final int DOCUMENT_MAXRESULT = 10;
	public static final int DOCUMENT_SIMILAR_MAXRESULT = 4;
	// suggestion
	public static final int SUGGESTION_MAXRESULT = 4;
	// author
	public static final String AUTHOR_SEPARATOR = "/";
	
	
	/* ============================== *\
	   => CONTROL
	\* ============================== */
	// regex
	public static final String REGEX_LOGIN = "[a-zA-Z][a-zA-Z0-9.]+";
	public static final String REGEX_PASSWORD = "[a-zA-Z][a-zA-Z0-9.]+";
	public static final String REGEX_ID = "[1-9][0-9]*";
	public static final String REGEX_PAGE = "[1-9][0-9]*";
	public static final String REGEX_QUERY = ".{"+ Constant.QUERY_MINLENGTH +","+ Constant.QUERY_MAXLENGTH +"}";
	public static final String REGEX_YEAR = "[1-9][0-9]{3}";
	
	
	/* ============================== *\
	   => HTTP
	\* ============================== */
	// session
	public static final String SESSION_USER_NAME = "SPARKUSER";
	public static final String SESSION_SESSION_NAME = "SPARKSESSION";
	public static final String SESSION_QUERY_NAME = "SPARKQUERY";
	// cookie
	public static final String COOKIE_USER_NAME = "SPARKUSER";
	public static final String COOKIE_DELIMITER = "----";
	public static final int COOKIE_MAXAGE = 60 * 60 * 24;
	
	
	/* ============================== *\
	   => STORAGE
	\* ============================== */
	// root
	public static final String ROOT_FOLDER = "C:/Users/Sylvain/workspace-ee/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/spark/";	
	// private storage
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
	
}
