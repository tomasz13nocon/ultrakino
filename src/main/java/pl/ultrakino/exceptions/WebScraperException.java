package pl.ultrakino.exceptions;

public class WebScraperException extends Exception {

	public WebScraperException() {
		super();
	}

	public WebScraperException(String message) {
		super(message);
	}

	public WebScraperException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebScraperException(Throwable cause) {
		super(cause);
	}
}
