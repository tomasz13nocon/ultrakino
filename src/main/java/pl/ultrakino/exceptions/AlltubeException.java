package pl.ultrakino.exceptions;

public class AlltubeException extends Exception {

	public AlltubeException() {
		super();
	}

	public AlltubeException(String message) {
		super(message);
	}

	public AlltubeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlltubeException(Throwable cause) {
		super(cause);
	}

}
