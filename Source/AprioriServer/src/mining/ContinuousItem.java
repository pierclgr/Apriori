package mining;

import data.ContinuousAttribute;
/**
 * <p>Title: ContinuousItem</p>
 * <p>Description: Continuous item.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents an item (continuous) associated to a continuous attribute.</p>
 * @author Pier
 * @version 1.0
 */
class ContinuousItem extends Item {
	private static final long serialVersionUID = 5006123126759338250L;
	/**
	 * This constructor method sets the continuous attribute associated to the item and the value of the item.
	 * @param attribute Attribute (continuous) associated to the item.
	 * @param value Value of the item.
	 */
	ContinuousItem(ContinuousAttribute attribute, Interval value){
		super(attribute,value);
	}
	/**
	 * Implementation of the checkItemCondition method of Item class to check the inclusion of "value" parameter in item's value (Interval).
	 */
	boolean checkItemCondition(Object value){
		return ((Interval)this.getValue()).checkValueInclusion((Float)value);
	}
	/**
	 * Overload of the generic toString method to represent the continuous item with a String.
	 */
	public String toString(){
		return this.getAttribute().toString()+" in "+((Interval)this.getValue()).toString();
	}
}
