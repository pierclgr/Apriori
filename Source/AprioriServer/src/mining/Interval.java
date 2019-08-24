package mining;
import java.io.Serializable;
/**
 * <p>Title: Interval</p>
 * <p>Description: Interval of numbers.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents an interval of numbers.</p>
 * @author Pier
 * @version 1.0
 */
class Interval implements Serializable{
	private static final long serialVersionUID = -7731488452703037364L;
	private float inf;
	private float sup;
	/**
	 * This constructor method sets the inferior number and the superior number of the interval.
	 * @param inf Inferior number of the interval.
	 * @param sup Superior number of the interval.
	 */
	Interval(float inf, float sup) {
		this.inf = inf;
		this.sup = sup;
	}
	/**
	 * This method returns the inferior number of the interval.
	 * @return Inferior number of the interval.
	 */
	float getInf() {
		return inf;
	}
	/**
	 * This method sets the inferior number of the interval.
	 * @param inf Inferior number of the interval.
	 */
	void setInf(float inf) {
		this.inf = inf;
	}
	/**
	 * This method returns the superior number of the interval.
	 * @return Superior number of the interval.
	 */
	float getSup() {
		return sup;
	}
	/**
	 * This method sets the superior number of the interval.
	 * @param sup Superior number of the interval.
	 */
	void setSup(float sup) {
		this.sup = sup;
	}
	/**
	 * This method checks if the "value" parameter is included in the interval.
	 * @param value Value used to check its inclusion in the interval.
	 * @return Boolean representing the result of the check.
	 */
	boolean checkValueInclusion(float value){
		if(value>=inf&&value<sup){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Overload of the generic toString method to represent the interval with a String.
	 */
	public String toString(){
		return "["+inf+","+sup+"[";
	}
}
