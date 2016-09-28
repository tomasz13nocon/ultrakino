package pl.ultrakino.exceptions;

public class NoFilmwebIdException extends Exception {

	public NoFilmwebIdException() {
		super();
	}

	public NoFilmwebIdException(String message) {
		super(message);
	}

	public NoFilmwebIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFilmwebIdException(Throwable cause) {
		super(cause);
	}
}
