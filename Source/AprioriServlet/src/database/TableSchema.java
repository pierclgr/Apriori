package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * <p>Title: TableSchema</p>
 * <p>Description: Database table structure.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents the structure of a single database table.</p>
 * @author Pier
 * @version 1.0
 */
public class TableSchema {
	/**
	* <p>Title: Column</p>
	* <p>Description: Structure of a table's column.</p>
	* <p>Copyright: Copyright (c) 2017</p>
	* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
	* <p>Class description: This class represents the structure of a generic column from the table.</p>
	* @author Pier
	* @version 1.0
	*/
	public class Column{
		private String name;
		private String type;
		/**
		 * This constructor method sets the name of the column (also name of the attribute) and the type of the attribute (String, Integer etc.).
		 * @param name Name of the column (attribute).
		 * @param type Type of the column (attribute).
		 */
		private Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * This method returns the attribute name.
		 * @return Name of the attribute
		 */
		public String getColumnName(){
			return name;
		}
		/**
		 * This method controls if the current attribute is a numeric attribute or not.
		 * @return A boolean value representing the result of the checking operation.
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		/**
		 * Overload of the generic toString method to represent the column using a String.
		 */
		public String toString(){
			return name+":"+type;
		}
	}
	private List<Column> tableSchema=new ArrayList<Column>();
	/**
	 * This constructor method sets the database used to extract the structure of the table "tableName" and extracts the structure of itself.
	 * @param db Database used to extract the structure.
	 * @param tableName Name of the table in the database from which extract the structure.
	 * @throws SQLException Throws a SQLException in case a SQL error occurs.
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		 Connection con=db.getConnection();
		 DatabaseMetaData meta = con.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	
	         
	         
	      }
	      res.close();
	
	
	    
	    }
		/**
		 * This method returns the number of the attributes in the table.
		 * @return Number of the attributes in the table.
		 */
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
		/**
		 * This method returns the attribute at column "index".
		 * @param index Index of the column in the database table.
		 * @return Attribute located at "index" column in the database table.
		 */
		public Column getColumn(int index){
			return tableSchema.get(index);
		}
}

