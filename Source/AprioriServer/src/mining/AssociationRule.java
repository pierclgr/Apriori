package mining;

import java.io.Serializable;
/**
 * <p>Title: AssociationRule</p>
 * <p>Description: Association rule.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents an association rule.</p>
 * @author Pier
 * @version 1.0
 */
public class AssociationRule implements Comparable<AssociationRule>,Serializable {
	private static final long serialVersionUID = -6299920630993912551L;
	private Item antecedent[]=new Item[0];
	private Item consequent[]=new Item[0];
	private float support;
	private float confidence;
	/**
	 * This constructor method sets the support of the rule.
	 * @param support Support of the rule.
	 */
	AssociationRule(float support) {
		this.support = support;
	}
	/**
	 * This method returns the support of the rule.
	 * @return Support of the rule.
	 */
	float getSupport() {
		return support;
	}
	/**
	 * This method returns the confidence of the rule.
	 * @return Confidence of the rule.
	 */
	float getConfidence() {
		return confidence;
	}
	/**
	 * This method returns the length of the antecedent.
	 * @return Length of the antecedent.
	 */
	int getAntecedentLength() {
		return antecedent.length;
	}
	/**
	 * This method returns the length of the consequent.
	 * @return Length of the consequent.
	 */
	int getConsequentLength() {
		return consequent.length;
	}
	/**
	 * This method adds an item to the antecedent.
	 * @param item Item to add to the antecedent.
	 */
	void addAntecedentItem(Item item) {
		int length =antecedent.length;
		Item temp []=new Item[length+1];
		System.arraycopy(antecedent,0,temp,0,length);
		temp[length]=item;
		antecedent=temp;
	}
	/**
	 * This method adds an item to the consequent.
	 * @param item Item to add to the consequent.
	 */
	void addConsequentItem(Item item) {
		int length =consequent.length;
		Item temp []=new Item[length+1];
		System.arraycopy(consequent,0,temp,0,length);
		temp[length]=item;
		consequent=temp;
	}
	/**
	 * This method returns the item in position "index" in the antecedent list.
	 * @param index Index of the item to return.
	 * @return Item of the antecedent in position "index".
	 */
	Item getAntecedentItem(int index){
		return antecedent[index];
	}
	/**
	 * This method returns the item in position "index" in the consequent list.
	 * @param index Index of the item to return.
	 * @return Item of the consequent in position "index".
	 */
	Item getConsequentItem(int index){
		return consequent[index];
	}
	/**
	 * This method sets the confidence of the rule.
	 * @param confidence Confidence of the rule.
	 */
	void setConfidence(float confidence){
		this.confidence=confidence;
	}
	/**
	 * Overload of the generic toString method to represent the association rule with a String.
	 */
	public String toString(){
		String output="";
		for(int i=0;i<antecedent.length;i++){
			output+=antecedent[i].toString();
			if(i!=antecedent.length-1){
				output+=" AND ";
			}
		}
		output+="==>";
		for(int i=0;i<consequent.length;i++){
			output+=consequent[i].toString();
			if(i!=consequent.length-1){
				output+=" AND ";
			}
		}
		output+="["+support+","+confidence+"]"+"\n";
		return output;
	}
	/**
	 * Implements the generic compareTo method of Comparable to compare the confidence of two rules.
	 */
	public int compareTo(AssociationRule AR){
		if(confidence==AR.getConfidence()){
			return 0;
		}else if(confidence>AR.getConfidence()){
			return 1;
		}else{
			return -1;
		}
	}
}	

