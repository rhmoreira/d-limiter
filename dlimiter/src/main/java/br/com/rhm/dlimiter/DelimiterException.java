package br.com.rhm.dlimiter;

public class DelimiterException extends RuntimeException {

	private static final long serialVersionUID = -254792455689562682L;

	public DelimiterException() {
		super();
	}

	public DelimiterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DelimiterException(String message, Throwable cause) {
		super(message, cause);
	}

	public DelimiterException(String message) {
		super(message);
	}

	public DelimiterException(Throwable cause) {
		super(cause);
	}

}
