package mining;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import mining.FrequentPattern;
import mining.AssociationRule;
/**
 * <p>Title: AssociationRuleArchive</p>
 * <p>Description: Representation of the association rule with an archive.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class is used to manage a particular data structure representing the frequent patterns and their relative association rules.</p>
 * @author Pier
 * @version 1.0
 */
public class AssociationRuleArchive implements Serializable {
	private static final long serialVersionUID = 6281688667286256563L;
	private HashMap<FrequentPattern,TreeSet<AssociationRule>> archive=new HashMap<FrequentPattern, TreeSet<AssociationRule>>();
	/**
	 * This method puts a new frequent pattern in the archive if it is not already in the archive.
	 * @param fp Frequent pattern to put in the archive.
	 */
	public void put(FrequentPattern fp){
		if(!archive.containsKey(fp)){
			archive.put(fp, new TreeSet<AssociationRule>());
		}
	}
	/**
	 * This method puts a new frequent pattern and the linked association rule in the map if it is not already in the archive, otherwise just puts the new association rule linked to the pattern.
	 * @param fp Frequent pattern to put in the archive.
	 * @param rule Association rule to put in the archive linked to "fp".
	 */
	public void put(FrequentPattern fp, AssociationRule rule){
		if(archive.containsKey(fp)){
			archive.get(fp).add(rule);
		}else{
			archive.put(fp, new TreeSet<AssociationRule>());
			archive.get(fp).add(rule);
		}
	}
	/**
	 * This method returns all the association rules linked to a specific frequent pattern.
	 * @param fp Frequent pattern used to extract its linked association rules.
	 * @return Association rules linked to the frequent pattern "fp".
	 * @throws NoPatternException Throws a NoPatternException in case the frequent pattern has no rules.
	 */
	TreeSet<AssociationRule> getRules(FrequentPattern fp) throws NoPatternException{
		if(archive.containsKey(fp)){
			return archive.get(fp);
		}else{
			throw new NoPatternException("Pattern has no rules");
		}
	}
	/**
	 * Overload of the generic toString method to represent the association rules linked to a frequent pattern with a String.
	 */
	public String toString(){
		String value="";
		Iterator<Map.Entry<FrequentPattern, TreeSet<AssociationRule>>> keyIterator=archive.entrySet().iterator();
		int i=1;
		while(keyIterator.hasNext()){
			Map.Entry<FrequentPattern, TreeSet<AssociationRule>> pair=(Map.Entry<FrequentPattern, TreeSet<AssociationRule>>) keyIterator.next();
			value+=i+": "+pair.getKey().toString()+": "+pair.getValue().toString()+"\n";
			i++;
		}
		return value;
	}
	/**
	 * This method saves the association rule archive on a file.
	 * @param nomeFile Name of the file to save on.
	 * @throws FileNotFoundException Throws a FileNotFoundException in case the file is not found.
	 * @throws IOException Throws an IOException in case an IO error occurs.
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException{
		FileOutputStream outFile=new FileOutputStream(nomeFile);
		ObjectOutputStream outStream=new ObjectOutputStream(outFile);
		outStream.writeObject(this);
		outStream.close();
		outFile.close();
	}
	/**
	 * This method loads the association rule archive from a file.
	 * @param nomeFile Name of the file to load from.
	 * @return Association rule archive loaded from the file.
	 * @throws FileNotFoundException Throws a FileNotFoundException in case the file is not found.
	 * @throws IOException Throws an IOException in case an IO error occurs
	 * @throws ClassNotFoundException Throws a ClassNotFoundException in case the class is not found.
	 */
	public static AssociationRuleArchive carica(String nomeFile) throws FileNotFoundException,IOException,ClassNotFoundException{
		FileInputStream inFile=new FileInputStream(nomeFile);
		ObjectInputStream inStream=new ObjectInputStream(inFile);
		AssociationRuleArchive myArchive=(AssociationRuleArchive)inStream.readObject();
		inStream.close();
		inFile.close();
		return myArchive;
	}
}
