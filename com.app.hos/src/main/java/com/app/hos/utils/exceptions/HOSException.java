package com.app.hos.utils.exceptions;

public class HOSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7123058116221698850L;

	public HOSException() {
		super();
	}

	/**
	 * Instantiates a new HOS exception.
	 *
	 * @param message
	 *            the message
	 */
	public HOSException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new HOS exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HOSException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new HOS exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public HOSException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new HOS exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public HOSException(final Throwable cause) {
		super(cause);
}

}
