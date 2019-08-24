package database;
/**
* <p>Title: DatabaseConnectionException</p>
* <p>Description: Database connection exception.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This exception class represents the case where an error occurs during the connection to the database.</p>
* @author Pier
* @version 1.0
*/
public class DatabaseConnectionException extends Exception{
	private static final long serialVersionUID = -485446516167540745L;
	private String message;
	/**
	 * This constructor method sets the exception message shown to the user when exception is thrown.
	 * @param message Message shown to the user when exception is thrown.
	 */
	DatabaseConnectionException(String message) {
		this.message = message;
	}
	/**
	 * Overrides the default getMessage method to return the message.
	 */
	public String getMessage(){
		return message;
	}
}