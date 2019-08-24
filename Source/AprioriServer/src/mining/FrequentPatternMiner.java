package mining;
import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
import data.ContinuousAttribute;
import data.EmptySetException;
import utility.EmptyQueueException;
import utility.Queue;
import java.util.LinkedList;
import java.util.Iterator;
/**
 * <p>Title: FrequentPatternMiner</p>
 * <p>Description: Frequent pattern miner.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class is used to mine frequent patterns.</p>
 * @author Pier
 * @version 1.0
 */
public class FrequentPatternMiner {
	/**
	 * This method is used to mine frequent patterns.
	 * @param data Representation of the database table used for mine operations.
	 * @param minSup Minimum support of the frequent pattern.
	 * @return List of frequent patterns whose support is equal or greater than "minSup".
	 * @throws EmptySetException Throws an EmptySetException in case the table has no tuples.
	 */
	public static LinkedList<FrequentPattern> frequentPatternDiscovery(Data data,float minSup) throws EmptySetException{
		Queue<FrequentPattern> fpQueue=new Queue<FrequentPattern>();		
		LinkedList<FrequentPattern> outputFP=new LinkedList<FrequentPattern>();
		if(data.getNumberOfExamples()==0) throw new EmptySetException("Training set has no transactions/examples");
		for(int i=0;i<data.getNumberOfAttributes();i++)
		{
			Attribute currentAttribute=data.getAttribute(i);
			if(currentAttribute instanceof DiscreteAttribute){
				for(int j=0;j<((DiscreteAttribute)currentAttribute).getNumberOfDistinctValues();j++){
					DiscreteItem item=new DiscreteItem((DiscreteAttribute)currentAttribute,((DiscreteAttribute)currentAttribute).getValue(j));
					FrequentPattern fp=new FrequentPattern();
					fp.addItem(item);
					fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
					if(fp.getSupport()>=minSup){ // 1-FP CANDIDATE
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}
				}
			}else{
				Iterator<Float>it=((ContinuousAttribute)currentAttribute).iterator();
				if(it.hasNext()) {
					float inf=it.next();
					while(it.hasNext()){
						float sup=it.next();
						ContinuousItem item;
						if(it.hasNext())
							item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup));
						else
							item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup+0.01f*(sup-inf)));
						inf=sup;
						FrequentPattern fp=new FrequentPattern();
						fp.addItem(item);
						fp.setSupport(FrequentPatternMiner.computeSupport(data,fp));
						if(fp.getSupport()>=minSup){ // 1-FP CANDIDATE
							fpQueue.enqueue(fp);
							outputFP.add(fp);
						}
					}
				}
			}
		}
		outputFP=expandFrequentPatterns(data,minSup,fpQueue,outputFP);
		return outputFP;
	}
	/**
	 * This method adds a frequent pattern from the frequent pattern queue to the frequent pattern list.
	 * @param data Representation of the database table used for mine operations.
	 * @param minSup Minimum support of the frequent pattern.
	 * @param fpQueue Queue of the frequent patterns.
	 * @param outputFP List of the frequent patterns whose support is equal or greater than "minSup".
	 * @return List of the frequent patterns whose support is equal or greater than "minSup".
	 */
	private static LinkedList<FrequentPattern> expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue,LinkedList<FrequentPattern> outputFP){
		while(!fpQueue.isEmpty()){
			try{
				FrequentPattern toBeRefinedFP=(FrequentPattern)fpQueue.first();
				fpQueue.dequeue();
				for(int i=0;i<data.getNumberOfAttributes();i++){
					boolean found=false;
					for(int j=0;j<toBeRefinedFP.getPatternLength();j++) {
						if(toBeRefinedFP.getItem(j).getAttribute().equals(data.getAttribute(i))){
							found=true;
							break;
						}
					}
					if(!found){
						if(data.getAttribute(i) instanceof DiscreteAttribute){
							for(int k=0;k<((DiscreteAttribute)data.getAttribute(i)).getNumberOfDistinctValues();k++){
								DiscreteItem item=new DiscreteItem((DiscreteAttribute)data.getAttribute(i),((DiscreteAttribute)(data.getAttribute(i))).getValue(k));
								FrequentPattern newFP=FrequentPatternMiner.refineFrequentPattern(toBeRefinedFP,item);
								newFP.setSupport(FrequentPatternMiner.computeSupport(data,newFP));
								if(newFP.getSupport()>=minSup){
									fpQueue.enqueue(newFP);
									outputFP.add(newFP);
								}
							}
						}else{
							Iterator<Float>it=((ContinuousAttribute)data.getAttribute(i)).iterator();
							if(it.hasNext()) {
								float inf=it.next();
								while(it.hasNext()){
									float sup=it.next();
									ContinuousItem item;
									if(it.hasNext())
										item=new ContinuousItem((ContinuousAttribute)data.getAttribute(i),new Interval(inf, sup));
									else
										item=new ContinuousItem((ContinuousAttribute)data.getAttribute(i),new Interval(inf, sup+0.01f*(sup-inf)));
									inf=sup;
									FrequentPattern newFP=FrequentPatternMiner.refineFrequentPattern(toBeRefinedFP,item);
									newFP.setSupport(FrequentPatternMiner.computeSupport(data,newFP));
									if(newFP.getSupport()>=minSup){
										fpQueue.enqueue(newFP);
										outputFP.add(newFP);
									}
								}
							}
						}
					}
				}
			}catch(EmptyQueueException e){
				System.out.println(e.getMessage());
			}
		}
		return outputFP;
	}
	/**
	 * This method calculates the support of the pattern.
	 * @param data Representation of the database table used for mine operations.
	 * @param FP Frequent pattern used to calculate its support.
	 * @return Support of the frequent pattern.
	 */
	// Aggiorna il supporto
	private static float computeSupport(Data data,FrequentPattern FP){
		int suppCount=0;
		// indice esempio
		for(int i=0;i<data.getNumberOfExamples();i++){
			//indice item
			boolean isSupporting=true;
			for(int j=0;j<FP.getPatternLength();j++)
			{
				Item item;
				Attribute attribute;
				//DiscreteItem
				if(FP.getItem(j) instanceof DiscreteItem){
					item=(DiscreteItem)FP.getItem(j);
					attribute=(DiscreteAttribute)item.getAttribute();
				}else{
					item=(ContinuousItem)FP.getItem(j);
					attribute=(ContinuousAttribute)item.getAttribute();
				}
				//Extract the example value
				Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
				if(!item.checkItemCondition(valueInExample)){
					isSupporting=false;
					break; //the ith example does not satisfy fp
				}
				
			}
			if(isSupporting)
				suppCount++;
		}
		return ((float)suppCount)/(data.getNumberOfExamples());
		
	}
	/**
	 * This method is used to add an item to the frequent pattern.
	 * @param FP Frequent pattern to refine.
	 * @param item Item to add to the pattern.
	 * @return Pattern refined.
	 */
	private static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item){
		FrequentPattern refinedFP=new FrequentPattern();
		for(int i=0;i<FP.getPatternLength();i++){
			refinedFP.addItem(FP.getItem(i));
		}
		refinedFP.addItem(item);
		return refinedFP;
	}

}
	


