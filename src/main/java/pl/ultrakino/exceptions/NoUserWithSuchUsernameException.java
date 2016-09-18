package pl.ultrakino.exceptions;

public class NoUserWithSuchUsernameException extends Exception {

	public NoUserWithSuchUsernameException() {
		super();
	}

	public NoUserWithSuchUsernameException(String message) {
		super(message);
	}

	public NoUserWithSuchUsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoUserWithSuchUsernameException(Throwable cause) {
		super(cause);
	}
}
