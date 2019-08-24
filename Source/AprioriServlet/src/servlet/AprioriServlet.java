package servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import data.Data;
import data.EmptySetException;
import mining.AssociationRule;
import mining.AssociationRuleArchive;
import mining.AssociationRuleMiner;
import mining.FrequentPattern;
import mining.FrequentPatternMiner;
import mining.OneLevelPatternException;

/**
 * <p>Title: AprioriServlet</p>
 * <p>Description: Apriori servlet.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents the servlet of Apriori, managing the web requests.</p>
 * @author Pier
 * @version 1.0
 */

@WebServlet("/AprioriServlet")
public class AprioriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This constructor method just calls the superclass constructor.
	 */
    public AprioriServlet() {
        super();
    }
    /**
     * This method sends an error to the web client showing the parameter "message".
     * @param request Http servlet request.
     * @param response Http servlet response.
     * @param error Message of the error.
     * @throws ServletException Throws a ServletException in case a servlet error occurs.
     * @throws IOException Throws an IOException in case an IO error occurs.
     */
    private void showError(HttpServletRequest request, HttpServletResponse response, String error)throws ServletException, IOException {
	    	RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
	    	request.setAttribute("error",error);
			rd.forward(request, response);
    }
    /**
     * This method sends a message to the web client showing the result of mining.
     * @param request Http servlet request.
     * @param response Http servlet response.
     * @param result Mining result.
     * @throws ServletException Throws a ServletException in case a servlet error occurs.
     * @throws IOException Throws an IOException in case an IO error occurs.
     */
    private void showResult(HttpServletRequest request, HttpServletResponse response, String result)throws ServletException, IOException {
    	RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
    	request.setAttribute("result",result);
		rd.forward(request, response);
    }
    /**
     * Overload of the generic method doGet to perform action in response of a get request.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("data-source");
		if(action.equals("db")) {
			String tableName=request.getParameter("data");
			if(!tableName.equals("")) {
				float minSup=0;
				if(request.getParameter("min-sup").equals("")) {
					showError(request,response,"Insert min support value");
				}else{
					minSup=Float.parseFloat(request.getParameter("min-sup"));
					if(minSup<0||minSup>1){
						showError(request,response,"Min support value is not between 0 and 1");
					}else{
						float minConf=0;
						if(request.getParameter("min-conf").equals("")) {
							showError(request,response,"Insert min confidence value");
						}else{
							minConf=Float.parseFloat(request.getParameter("min-conf"));
							if(minConf<0||minConf>1){
								showError(request,response,"Min confidence value is not between 0 and 1");
							}else{
								AssociationRuleArchive archive=new AssociationRuleArchive();
								Data trainingSet=new Data(tableName);
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
								showResult(request,response,archive.toString());
								String fileName=request.getParameter("file-name");
								if(!fileName.equals("")){
									try{
										archive.salva(request.getServletContext().getRealPath("/WEB-INF")+"/"+fileName);
									}catch(FileNotFoundException e){
										e.printStackTrace();
										System.out.println(e.getMessage());
									}catch(IOException e){
										e.printStackTrace();
										System.out.println(e.getMessage());
									}
								}
							}
						}
					}
				}
			}else{
				showError(request,response,"Insert table name");
			}
		}else if(action.equals("file")) {
			String fileName=request.getParameter("file-name");
			if(!fileName.equals("")) {
				try{
					AssociationRuleArchive archive=AssociationRuleArchive.carica(request.getServletContext().getRealPath("/WEB-INF")+"/"+fileName);
					showResult(request,response,archive.toString());
				}catch(IOException | ClassNotFoundException e){
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}else{
				showError(request,response,"Invalid file name");
			}
		}
	}
	/**
	 * Overload of the generic doPost method to perform actions in response of a post request.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
