package com.irisdock.metier;

public class InputException extends RuntimeException {
	private static final long serialVersionUID = 5120975968176225122L;

	public InputException(String message, Throwable reason) {
		super(message, reason);
	}
	
	public InputException(String message) {
		super(message);
	}
	
	public InputException(Throwable reason) {
		super(reason);
	}
}
