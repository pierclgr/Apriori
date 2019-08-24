package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
* <p>Title: DbAccess</p>
* <p>Description: Access to the database.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class manages the connection to the database.
* @author Pier
* @version 1.0
*/
public class DbAccess {
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DBMS = "jdbc:mysql";
	private  final String SERVER = "localhost";
	private  int PORT = 3306;
	private  String DATABASE = "AprioriDB";
	private  String USER_ID = "AprioriUser";
	private  String PASSWORD = "apriori";
	private Connection conn;
	/**
	 * This method starts the connection to the database.
	 * @throws DatabaseConnectionException Throws a DatabaseConnectionException in case there are some errors with the connection to the database.
	 */
	public void initConnection() throws DatabaseConnectionException{
		String connectionString = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
		try {
			
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} 
		catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new DatabaseConnectionException(e.toString());
			}
		catch (InstantiationException e) {
					e.printStackTrace();
					throw new DatabaseConnectionException(e.toString());
			} 
		catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}
		
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}
	/**
	 * This method returns the connection to the database.
	 * @return Connection to the database.
	 */
	Connection getConnection(){
		return conn;
	}
	/**
	 * This method closes the connection to the database.
	 * @throws SQLException Throws a SQLException in case there are some errors during connection closing.
	 */
	public void closeConnection() throws SQLException{
		conn.close();
	}
}
