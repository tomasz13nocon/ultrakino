package pl.ultrakino.exceptions;

public class FileDeletionException extends Exception {
	public FileDeletionException() {
		super();
	}

	public FileDeletionException(String s) {
		super(s);
	}

	public FileDeletionException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public FileDeletionException(Throwable throwable) {
		super(throwable);
	}
}
