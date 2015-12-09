package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.exception.UploadException;
import spark.exception.UploadExtensionException;
import spark.exception.UploadFileNotFoundException;
import spark.model.manager.ManageResource;
import spark.model.manager.ManageUser;

@WebServlet("/resource/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		if(manageUser.isLogged(request)) {
			request.getRequestDispatcher("/jsp/upload.jsp").forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		
		if(manageUser.isLogged(request)) {
			ManageResource manageResource = new ManageResource();
			try {
				manageResource.upload(request);
				request.setAttribute("notifications", manageResource.getNotifications());
			}
			catch(UploadException | UploadExtensionException | UploadFileNotFoundException exception) {
				request.setAttribute("notifications", manageResource.getNotifications());
			}
			finally {
				request.getRequestDispatcher("/jsp/upload.jsp").forward(request, response);
			}
		}
	}
	
}
