package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import database.TableSchema.Column;
/** TODO
* <p>Title: TableData</p>
* <p>Description: Table data.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class manages the data extracted from the database table.</p>
* @author Pier
* @version 1.0
*/
public class TableData {
	private DbAccess db;
	/** TODO
	* <p>Title: TupleData</p>
	* <p>Description: Tuple data.</p>
	* <p>Copyright: Copyright (c) 2017</p>
	* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
	* <p>Class description: This class manages the data extracted from a single tuple.</p>
	* @author Pier
	* @version 1.0
	*/
	public class TupleData{
		public List<Object> tuple=new ArrayList<Object>();
		
		/**
		 * Overrides the generic toString method to represent a tuple with a String.
		 */
		public String toString(){
			String value="";
			Iterator<Object> it= tuple.iterator();
			while(it.hasNext())
				value+= (it.next().toString() +" ");
			
			return value;
		}
	}
	/**
	 * This method sets the database access.
	 * @param db Database to use for data extraction.
	 */
	public void setDbAccess(DbAccess db){
		this.db=db;
	}
	/**
	 * This method returns a list of TupleData where every element of the list is a tuple from the database.
	 * @param table Name of the table from the database used to extract data from.
	 * @return List of TupleData where every element is a tuple from the database.
	 * @throws SQLException Throws a SQLException in case a SQL error occurs.
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException{
		LinkedList<TupleData> transSet = new LinkedList<TupleData>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			TupleData currentTuple=new TupleData();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.tuple.add(rs.getFloat(i+1));
				else
					currentTuple.tuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();

		
		
		return transSet;

	}
	/**
	 * This method returns a list of all values assumed by a specific attribute (in case this attribute is discrete).
	 * @param table Name of the table from the database to query.
	 * @param column Column (attribute) of the table used to extract its distinct values.
	 * @return List of all values assumed by the attribute.
	 * @throws SQLException Throws a SQLException in case a SQL error occurs.
	 */
	public List<Object> getDistinctColumnValues (String table, Column column) throws SQLException {
		List<Object> values=new LinkedList<Object>();
		Statement statement=db.getConnection().createStatement();
		ResultSet rs=statement.executeQuery("SELECT DISTINCT "+column.getColumnName()+" FROM "+table+" ORDER BY "+column.getColumnName()+" ASC;");
		while(rs.next()){
			values.add(rs.getObject(column.getColumnName()));
		}
		rs.close();
		statement.close();
		return values;
	}
	/**
	 * This method returns the aggregate values assumed by a specific attribute (in case this attribute is continuous).
	 * @param table Name of the table from the database to query.
	 * @param column Column (attribute) of the table used to extract its aggregate values.
	 * @param aggregate Enumeration of minimum and maximum values.
	 * @return An aggregation of values.
	 * @throws SQLException Throws a SQLException in case a SQL error occurs.
	 * @throws NoValueException Throws a NoValueException in case there's a null continuous value in the column.
	 */
	public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		Object value=null;
		String aggregateOp="";
		
		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
				if(column.isNumber())
					value=rs.getFloat(1);
				else
					value=rs.getString(1);
			
		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
			
		return value;

	}

	

	

}
