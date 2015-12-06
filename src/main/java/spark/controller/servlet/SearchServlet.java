package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.model.manager.ManageDocument;
import spark.model.manager.ManageUser;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		
		ManageDocument manageDocument = new ManageDocument();
		request.setAttribute("query", request.getParameter("query"));
		request.setAttribute("documents", manageDocument.search(request));
		request.setAttribute("documentCount", manageDocument.getDocumentCount());
		request.setAttribute("currentPage", manageDocument.getCurrentPage());
		request.setAttribute("maxPage", manageDocument.getMaxPage());
		
		request.getRequestDispatcher("/jsp/search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}