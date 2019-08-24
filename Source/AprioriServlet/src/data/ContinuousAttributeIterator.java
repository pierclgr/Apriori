package data;

import java.util.Iterator;
/**
* <p>Title: ContinuousAttributeIterator</p>
* <p>Description: Iterator for a continuous attribute.</p>
* <p>Copyright: Copyright (c) 2017</p>
* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
* <p>Class description: This class represents an iterator to loop on a continuous attribute interval.</p>
* @author Pier
* @version 1.0
*/
class ContinuousAttributeIterator implements Iterator<Float> {
	private float min;
	private float max;
	private int j=0;
	private int numValues;
	/**
	 * This constructor method sets the minimum and maximum iterable values and the number of discretization intervals.
	 * @param min Minimum iterable value.
	 * @param max Maximum iterable value.
	 * @param numValues Number of discretization intervals.
	 */
	ContinuousAttributeIterator(float min,float max,int numValues){
		this.min=min;
		this.max=max;
		this.numValues=numValues;
	}
	/**
	 * This methods implements the hasNext method of Iterator to check if the current value has a following value or not.
	 */
	@Override
	public boolean hasNext() {
		return (j<=numValues);
	}
	/**
	 * This methods implements the next method of Iterator to obtain the value following the current value.
	 */
	@Override
	public Float next() {
		j++;
		return min+((max-min)/numValues)*(j-1);
	}
}
