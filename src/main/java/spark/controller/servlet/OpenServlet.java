package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.exception.NotificationException;
import spark.model.manager.ManageDocument;

@WebServlet("/resource/open")
public class OpenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageDocument manageDocument = new ManageDocument();
		try {
			request.setAttribute("document", manageDocument.open(request));
			request.setAttribute("recommendations", manageDocument.getRecommendations());
		}
		catch(NotificationException exception) {
			request.setAttribute("notifications", manageDocument.getNotifications());
		}
		finally {
			request.getRequestDispatcher("/jsp/open.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
