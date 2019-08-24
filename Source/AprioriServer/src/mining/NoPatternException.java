package mining;
/**
 * <p>Title: NoPatternException</p>
 * <p>Description: No pattern exception.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This exception class represents the case where pattern has no rules.</p>
 * @author Pier
 * @version 1.0
 */
class NoPatternException extends Throwable{
	private static final long serialVersionUID = 1562197364625236227L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	NoPatternException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}
