package data;

import java.util.Iterator;
/**
* <p>Title: ContinuousAttribute</p>
* <p>Description: A continuous table attribute.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class extends the Attribute class and represents a particular attribute that can assume only numeric values between a minimum and a maximum value.</p>
* @author Pier
* @version 1.0
*/
public class ContinuousAttribute extends Attribute implements Iterable<Float> {
	private static final long serialVersionUID = -3818247460944913935L;
	private float min;
	private float max;
	/**
	 * This constructor method sets the attribute name, index and minimum and maximum values that the attribute can assume.
	 * @param name Name of the attribute.
	 * @param index Index of the attribute.
	 * @param min Minimum representable value.	
	 * @param max Maximum representable value.
	 */
	ContinuousAttribute(String name, int index, float min, float max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}
	/**
	 * This method returns the minimum representable value.
	 * @return Minimum representable value.
	 */
	public float getMin() {
		return min;
	}
	/**
	 * This method returns the maximum representable value.
	 * @return Maximum representable value.
	 */
	public float getMax() {
		return max;
	}
	/**
	 * This method returns an iterator to loop on the interval.
	 */
	public Iterator<Float> iterator(){
		return new ContinuousAttributeIterator(min,max,5);
	}
}
