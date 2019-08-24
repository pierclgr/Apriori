package data;

import java.io.Serializable;
/**
* <p>Title: Attribute</p>
* <p>Description: Standard table attribute.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class represents a generic table attribute.</p>
* @author Pier
* @version 1.0
*/
public abstract class Attribute implements Serializable {
	private static final long serialVersionUID = -1011201825379454272L;
	private String name;
	private int index;
	/**
	 * This constructor method sets the attribute name and index.
	 * @param name Name of the attribute.
	 * @param index Index of the attribute.
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	/**
	 * This method returns the attribute name.
	 * @return Attribute name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method returns the attribute index.
	 * @return Attribute index.
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * Overrides the generic toString method to represent attribute name with a String.
	 */
	public String toString() {
		return name;
	}
}
