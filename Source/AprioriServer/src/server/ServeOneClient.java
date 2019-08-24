package server;


import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mining.AssociationRule;
import mining.AssociationRuleMiner;
import mining.AssociationRuleArchive;
import mining.FrequentPattern;
import mining.FrequentPatternMiner;
import mining.OneLevelPatternException;

import data.Data;
import data.EmptySetException;
/**
 * <p>Title: ServeOneClient</p>
 * <p>Description: Single client server.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class manages the requests of a single client.</p>
 * @author Pier
 * @version 1.0
 */
class ServeOneClient extends Thread {
	private Socket socket;
	private ObjectInputStream in; // stream con richieste del client
	private ObjectOutputStream out;
	private AssociationRuleArchive archive;
	/**
	 * This constructor method sets the socket of the connection between client and server and the out and in streams.
	 * @param s Socket of the connection.
	 * @throws IOException Throws an IOException in case an i/o error occurs.
	 */
	ServeOneClient(Socket s) throws IOException {
		socket=s;
		out=new ObjectOutputStream(socket.getOutputStream());
		in=new ObjectInputStream(socket.getInputStream());
		start();
	}
	/**
	 * Overload of the run method from Thread class to manage requests from the client.
	 */
	public void run() {
		String nomeFile;
		try{
			while (true) {
				int command=0;
				command = ((Integer)(in.readObject())).intValue();
				switch(command)
				{
				case 1: // Learning a new archive from DB
					try{
						archive=new AssociationRuleArchive();
						String tableName=(String)in.readObject();
						Data trainingSet=new Data(tableName);
						Float minSup=(Float)in.readObject();
						Float minConf=(Float)in.readObject();
						try{
							LinkedList<FrequentPattern> outputFP=FrequentPatternMiner.frequentPatternDiscovery(trainingSet,minSup);
							Iterator<FrequentPattern> it=outputFP.iterator();
							while(it.hasNext()){
								FrequentPattern FP=it.next();
								archive.put(FP);
								LinkedList<AssociationRule> outputAR=null;
								try{
									outputAR = AssociationRuleMiner.confidentAssociationRuleDiscovery(trainingSet,FP,minConf);
									Iterator<AssociationRule> itRule=outputAR.iterator();
									while(itRule.hasNext()){
										archive.put(FP,itRule.next());
									}
								}
								catch(OneLevelPatternException e){}
							}
						}catch(EmptySetException e){
							System.out.println(e.getMessage());
						}
					} 
					finally{
						out.writeObject(archive.toString());
					}
					break;
				case 2: // SAVING ON File
					nomeFile=(String)in.readObject();
					try{
						archive.salva(nomeFile);
					}catch(FileNotFoundException e){
						e.printStackTrace();
						System.out.println(e.getMessage());
					}catch(IOException e){
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					break;
				case 3: // STORING PATTERNS and RULES stored from a FILE
					nomeFile=(String)in.readObject();
					try{
						archive=AssociationRuleArchive.carica(nomeFile);
						out.writeObject(archive.toString());
					}catch(IOException | ClassNotFoundException e){
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					break;
				default:
					try {
						out.writeObject("COMANDO INESISTENTE");
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					break;			
				}// END SWITCH
			}
		}
		catch(SocketException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		 } 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("Socket non chiuso!");
			}
		}	
	}
}



