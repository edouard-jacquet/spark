package spark.exception;

@SuppressWarnings("serial")
public class UserNotCreatedException extends Exception {
	
	public UserNotCreatedException() {
		super();
	}
	
	public UserNotCreatedException(String message) {
		super(message);
	}
	
}
