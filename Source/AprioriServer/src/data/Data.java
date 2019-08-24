package data;

import java.util.List;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import database.TableData.TupleData;

import java.sql.SQLException;
import java.util.LinkedList;
/**
* <p>Title: Data</p>
* <p>Description: Database table representation.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class represents the database table with a particular structure composed by a matrix and some attributes.</p>
* @author Pier
* @version 1.0
*/
public class Data {
	 private Object data [][];
	 private int numberOfExamples;
	 private List<Attribute> attributeSet=new LinkedList<Attribute>();
	 /**
	  * This constructor method executes some queries on the database to build the representation of the database table. 
	  * @param tableName Name of the database table to query.
	  */
	 public Data(String tableName){
			try{
				try{
					TableData tableData=new TableData();
					DbAccess db=new DbAccess();
					try{
						db.initConnection();
					}catch(DatabaseConnectionException e){
						System.out.println(e.getMessage());
					}
					tableData.setDbAccess(db);
					LinkedList<TupleData> examplesList=(LinkedList<TupleData>)tableData.getTransazioni(tableName);
					numberOfExamples=examplesList.size();
					TableSchema tableSchema=new TableSchema(db,tableName);
					int numberOfAttributes=tableSchema.getNumberOfAttributes();
					for(int i=0;i<numberOfAttributes;i++){
						if(!tableSchema.getColumn(i).isNumber()){
							String columnName=tableSchema.getColumn(i).getColumnName();
							LinkedList<Object> columnValues=(LinkedList<Object>)tableData.getDistinctColumnValues(tableName,tableSchema.getColumn(i));
							String values[]=new String[columnValues.size()];
							for(int j=0;j<columnValues.size();j++){
								values[j]=(String)columnValues.get(j).toString();
							}
							attributeSet.add(new DiscreteAttribute(columnName,i,values));
						}else{
							String columnName=tableSchema.getColumn(i).getColumnName();
							QUERY_TYPE queryType=QUERY_TYPE.MIN;
							float min=(float)tableData.getAggregateColumnValue(tableName,tableSchema.getColumn(i),queryType);
							queryType=QUERY_TYPE.MAX;
							float max=(float)tableData.getAggregateColumnValue(tableName,tableSchema.getColumn(i),queryType);
							attributeSet.add(new ContinuousAttribute(columnName,i,min,max));
						}
					}
					data=new Object[numberOfExamples][attributeSet.size()];
					for(int i=0;i<numberOfExamples;i++){
						for(int j=0;j<numberOfAttributes;j++){
							data[i][j]=examplesList.get(i).tuple.get(j);
						}
					}
					db.closeConnection();
				}catch(NoValueException e){
					System.out.println(e.getMessage());
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	/**
	 * This method returns the number of rows (number of tuples as well) in the database table.
	 * @return Number of rows in the database table (number of tuples).
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	/**
	 * This method returns the number of columns (number of attributes as well) in the database table.
	 * @return Number of columns in the database table (number of attributes).
	 */
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}
	/**
	 * This method returns the value located at "attributeIndex" column and "exampleIndex" row.
	 * @param exampleIndex Row of the table.
	 * @param attributeIndex Column of the table.
	 * @return The value located at "attributeIndex" column and "exampleIndex" row.
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	/**
	 * This method returns the attribute at column "index".
	 * @param index Column of the table.
	 * @return Attribute located at the "index" column in the table.
	 */
	public Attribute getAttribute(int index){
		return attributeSet.get(index);
	}
	/**
	 * This method overrides the generic toString method to represent the database table using a String.
	 */
	public String toString(){
		String result="";
		for(int i=0;i<getNumberOfExamples();i++) {
			result=result+(i+1)+":"+getAttributeValue(i,0).toString()+","+getAttributeValue(i,1).toString()+","+getAttributeValue(i,2).toString()+","+getAttributeValue(i,3).toString()+","+getAttributeValue(i,4).toString()+",\n";
		}
		return result;
	}
}
