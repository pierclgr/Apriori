package database;
/**
* <p>Title: NoValueException</p>
* <p>Description: No value exception.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This exception class represents the case where there is a null continuous value in the table.</p>
* @author Pier
* @version 1.0
*/
public class NoValueException extends Exception{
	private static final long serialVersionUID = 3328342185370766216L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	NoValueException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}