package data;
/**
* <p>Title: EmptySetException</p>
* <p>Description: Empty set exception.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This exception class represents the case where the database table has no rows (no tuples).</p>
* @author Pier
* @version 1.0
*/
public class EmptySetException extends Throwable{
	private static final long serialVersionUID = -3210179017764935055L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	public EmptySetException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}
