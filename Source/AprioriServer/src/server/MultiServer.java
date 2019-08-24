package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * <p>Title: MultiServer</p>
 * <p>Description: Multi server manager.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This represents a server accepting multiple client connections.</p>
 * @author Pier
 * @version 1.0
 */
class MultiServer {
	private static final int PORT = 8081;
	/**
	 * This is the main method of MultiServer.
	 * @param args Arguments representing input.
	 */
	public static void main(String[] args){
		new MultiServer();
	}
	/**
	 * This constructor method starts the server.
	 */
	private MultiServer(){
		try{
			System.out.println("Server avviato");
			run();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	/**
	 * This method loops infinitely waiting for connection requests by clients.
	 * @throws IOException Throws an IOException in case an i/o error occurs.
	 */
	private void run() throws IOException{
		ServerSocket s=new ServerSocket(PORT);
		try{
			while(true){
				Socket socket=s.accept();
				System.out.println("Connessione di " + socket);
				try{
					System.out.println("Nuovo client connesso");
					new ServeOneClient(socket);
				}catch(IOException e){
					e.printStackTrace();
					System.out.println(e.getMessage());
					socket.close();
				}
			}
		}finally{
			s.close();
		}
	}
}
