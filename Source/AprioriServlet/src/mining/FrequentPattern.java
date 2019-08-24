package mining;

import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.io.Serializable;
/**
 * <p>Title: FrequentPattern</p>
 * <p>Description: Frequent pattern.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents a frequent pattern.</p>
 * @author Pier
 * @version 1.0
 */
public class FrequentPattern implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Item> fp= new LinkedList<Item>();;
	private float support;
	/**
	 * This constructor method does nothing.
	 */
	FrequentPattern(){
	}
	/**
	 * This method adds an item to the frequent pattern.
	 * @param item Item to add to the pattern.
	 */
	void addItem(Item item)
	{
		fp.add(item);
	}
	/**
	 * This method returns a specific item placed in "index" position.
	 * @param index Index of the item.
	 * @return Item placed in position "index".
	 */
	Item getItem(int index){
		return fp.get(index);
	}
	/**
	 * This method returns the support of the pattern.
	 * @return Support of the pattern.
	 */
	float getSupport(){
		return support;
	}
	/**
	 * This method returns the length of the pattern.
	 * @return Length of the pattern.
	 */
	int getPatternLength(){
		return fp.size();
	}
	/**
	 * This method sets the support of the pattern.
	 * @param support Support of the pattern.
	 */
	void setSupport(float support){
		this.support=support;
	}
	/**
	 * Overload of the generic toString method to represent a frequent pattern with a String.
	 */
	public String toString(){
		String value="";
		ListIterator<Item> fpIterator=fp.listIterator();
		while(fpIterator.hasNext()){
			ListIterator<Item> curr=fpIterator;
			Item i=fpIterator.next();
			if(curr.hasNext())
				value+=i.toString()+" AND ";
			else
				value+=i.toString();
		}
		if(fp.size()>0){
			value+="["+support+"]";
		}
		return value;
	}


}
