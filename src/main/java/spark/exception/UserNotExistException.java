package spark.exception;

@SuppressWarnings("serial")
public class UserNotExistException extends Exception {
	
	public UserNotExistException() {
		super();
	}
	
	public UserNotExistException(String message) {
		super(message);
	}
	
}
