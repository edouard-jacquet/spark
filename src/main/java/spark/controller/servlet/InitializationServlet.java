package spark.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import spark.deployer.ApplicationDeployer;
import spark.deployer.DatabaseDeployer;

public class InitializationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	public void init() throws ServletException {
		System.out.println("----------");
		System.out.println("---------- INITIALIZATION START ----------");
		System.out.println("----------");
		ApplicationDeployer.getInstance().execute();
		DatabaseDeployer.getInstance().execute();
		System.out.println("----------");
		System.out.println("---------- INITIALIZATION END ----------");
		System.out.println("----------");
	}
	
}
