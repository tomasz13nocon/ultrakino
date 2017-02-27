package pl.ultrakino.exceptions;

public class ControllerInputException extends Exception {

	public ControllerInputException() {
		super();
	}

	public ControllerInputException(String s) {
		super(s);
	}

	public ControllerInputException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ControllerInputException(Throwable throwable) {
		super(throwable);
	}

}
