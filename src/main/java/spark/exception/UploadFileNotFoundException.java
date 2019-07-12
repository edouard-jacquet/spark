package spark.exception;

@SuppressWarnings("serial")
public class UploadFileNotFoundException extends Exception {
	
	public UploadFileNotFoundException() {
		super();
	}
	
	public UploadFileNotFoundException(String message) {
		super(message);
	}
	
}
