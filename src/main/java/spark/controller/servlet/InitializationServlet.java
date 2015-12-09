package spark.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import spark.model.factory.SuggestionIndexer;

public class InitializationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	public void init() throws ServletException {
		System.out.println("----------");
		System.out.println("---------- INITIALIZATION START ----------");
		System.out.println("----------");
		
		System.out.println("---------------");
		System.out.println("--------------- BUILD SUGGESTION INDEX START ----------");
		System.out.println("---------------");
		SuggestionIndexer.getInstance().rebuildIndex();
		System.out.println("---------------");
		System.out.println("--------------- BUILD SUGGESTION INDEX END ----------");
		System.out.println("---------------");
		
		System.out.println("----------");
		System.out.println("---------- INITIALIZATION END ----------");
		System.out.println("----------");
	}
	
}
