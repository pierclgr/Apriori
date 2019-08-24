package data;
/**
* <p>Title: DiscreteAttribute</p>
* <p>Description: A discrete table attribute.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class extends the Attribute class and represents a particular attribute that can assume only specified values (String values).</p>
* @author Pier
* @version 1.0
*/
public class DiscreteAttribute extends Attribute {
	private static final long serialVersionUID = -3009307099947940836L;
	private String values[];
	/**
	 * This constructor sets the attribute name, index and values that the attribute can assume.
	 * @param name Name of the attribute.
	 * @param index Index of the attribute.
	 * @param values Values assumed by the attribute.
	 */
	DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);
		this.values = values;
	}
	/**
	 * This method returns the number of values that the attribute can assume.
	 * @return number of values assumed by the attribute
	 */
	public int getNumberOfDistinctValues(){
		return values.length;
	}
	/**
	 * This method returns one of the values assumed by the attribute, precisely the value at "index" position.
	 * @param index Index of the value in the values array.
	 * @return The value in position "index" of the values array.
	 */
	public String getValue(int index){
		return values[index];
	}
}
