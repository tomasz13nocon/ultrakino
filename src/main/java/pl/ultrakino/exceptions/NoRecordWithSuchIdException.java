package pl.ultrakino.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRecordWithSuchIdException extends Exception {

	public NoRecordWithSuchIdException() {
		super();
	}

	public NoRecordWithSuchIdException(String message) {
		super(message);
	}

	public NoRecordWithSuchIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRecordWithSuchIdException(Throwable cause) {
		super(cause);
	}

}
