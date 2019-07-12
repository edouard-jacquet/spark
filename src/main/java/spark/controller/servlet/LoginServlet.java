package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.exception.NotificationException;
import spark.exception.UserNotExistException;
import spark.model.manager.ManageUser;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		if(manageUser.isLogged(request)) {
			response.sendRedirect(request.getContextPath() + "/home");
		}
		else {
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		try {
			manageUser.login(request, response);
			response.sendRedirect(request.getContextPath() + "/home");
		}
		catch(NotificationException | UserNotExistException exception) {
			request.setAttribute("notifications", manageUser.getNotifications());
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
	}
	
}