package utility;
/**
 * <p>Title: EmptyQueueException</p>
 * <p>Description: Empty queue exception.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This exception class represents the case where a queue is empty.</p>
 * @author Pier
 * @version 1.0
 */
public class EmptyQueueException extends Throwable{
	private static final long serialVersionUID = -5649943010050781089L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	EmptyQueueException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}
