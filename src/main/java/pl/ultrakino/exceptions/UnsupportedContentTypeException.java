package pl.ultrakino.exceptions;

public class UnsupportedContentTypeException extends RuntimeException {

	public UnsupportedContentTypeException() {
		super();
	}

	public UnsupportedContentTypeException(String message) {
		super(message);
	}

	public UnsupportedContentTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedContentTypeException(Throwable cause) {
		super(cause);
	}
}
