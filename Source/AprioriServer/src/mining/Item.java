package mining;

import data.Attribute;
import java.io.Serializable;
/**
 * <p>Title: Item</p>
 * <p>Description: Generic item.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents an item associated to a generic attribute.</p>
 * @author Pier
 * @version 1.0
 */
abstract class Item implements Serializable {
	private static final long serialVersionUID = 8094067048870643465L;
	private Attribute attribute;
	private Object value;
	/**
	 * This constructor sets the attribute associated to the item and the value of the item.
	 * @param attribute Attribute associated to the item.
	 * @param value Value of the item.
	 */
	Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	/**
	 * This method returns the attribute associated to the item.
	 * @return Attribute associated to the item.
	 */
	Attribute getAttribute() {
		return attribute;
	}
	/**
	 * This method returns the value of the item.
	 * @return Value of the item.
	 */
	Object getValue() {
		return value;
	}
	/**
	 * This abstract method checks the item condition.
	 * @param value Value used to check its condition.
	 * @return Boolean representing the result of the check.
	 */
	abstract boolean checkItemCondition(Object value);
	/**
	 * Overload of the generic toString method to represent the item with a String.
	 */
	public String toString(){
		return "("+attribute.toString()+"="+value.toString()+")";
	}
}
