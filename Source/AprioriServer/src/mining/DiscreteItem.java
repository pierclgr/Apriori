package mining;
import data.DiscreteAttribute;
/**
 * <p>Title: DiscreteItem</p>
 * <p>Description: Discrete item.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents an item associated to a discrete attribute.</p>
 * @author Pier
 * @version 1.0
 */
class DiscreteItem extends Item {
	private static final long serialVersionUID = 2256289821732148408L;
	/**
	 * This constructor method sets the discrete attribute associated to the item and the value of the item.
	 * @param attribute Attribute (discrete) associated to the item.
	 * @param value Value of the item.
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}
	/**
	 * Implementation of the checkItemCondition method of Item class to check if "value" parameter is equal to the value of the item.
	 */
	boolean checkItemCondition(Object value){
		return ((String)value).equals(this.getValue());
	}
}
