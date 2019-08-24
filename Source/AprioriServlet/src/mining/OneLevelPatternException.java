package mining;
/**
 * <p>Title: OneLevelPatternException</p>
 * <p>Description: One level pattern exception.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This exception class represents the case where a pattern has length of 1.</p>
 * @author Pier
 * @version 1.0
 */
public class OneLevelPatternException extends Throwable {
	private static final long serialVersionUID = -3769203748232077462L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	OneLevelPatternException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}
