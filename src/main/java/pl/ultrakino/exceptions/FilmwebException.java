package pl.ultrakino.exceptions;

public class FilmwebException extends Exception {

	public FilmwebException() {
		super();
	}

	public FilmwebException(String message) {
		super(message);
	}

	public FilmwebException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilmwebException(Throwable cause) {
		super(cause);
	}
}
