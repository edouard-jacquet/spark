package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.exception.NotificationException;
import spark.exception.UserIsExistException;
import spark.exception.UserNotCreatedException;
import spark.model.manager.ManageUser;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		if(manageUser.isLogged(request)) {
			response.sendRedirect(request.getContextPath() + "/home");
		}
		else {
			request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		try {
			manageUser.create(request, response);
			response.sendRedirect(request.getContextPath() + "/login");
		}
		catch(NotificationException | UserIsExistException | UserNotCreatedException exception) {
			request.setAttribute("notifications", manageUser.getNotifications());
			request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
		}
	}
	
}