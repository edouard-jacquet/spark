package spark.exception;

@SuppressWarnings("serial")
public class UserIsExistException extends Exception {
	
	public UserIsExistException() {
		super();
	}
	
	public UserIsExistException(String message) {
		super(message);
	}
	
}
