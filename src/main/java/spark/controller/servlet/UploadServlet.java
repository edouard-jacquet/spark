package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.model.manager.ManageUser;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		if(manageUser.isLogged(request)) {
			request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		ManageResource manageResource = new ManageResource();
		try {
			manageResource.upload(request);
		}
		catch(UploadException uploadException) {
			JsonResponse jsonResponse = new JsonResponse();
			jsonResponse.setError(true);
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("");
		}
		*/
	}
	
}
