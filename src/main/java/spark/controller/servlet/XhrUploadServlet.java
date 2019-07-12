package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import spark.exception.UploadException;
import spark.exception.UploadExtensionException;
import spark.exception.UploadFileNotFoundException;
import spark.model.bean.JsonResponse;
import spark.model.manager.ManageResource;
import spark.model.manager.ManageUser;

@WebServlet("/resource/xhr-upload")
@MultipartConfig
public class XhrUploadServlet extends HttpServlet {
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		JsonResponse jsonResponse = new JsonResponse();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(manageUser.isLogged(request)) {
			ManageResource manageResource = new ManageResource();
			try {
				manageResource.upload(request);
				jsonResponse.setNotifications(manageResource.getNotifications());
			}
			catch(UploadException | UploadExtensionException | UploadFileNotFoundException exception) {
				jsonResponse.setError(true);
				jsonResponse.setNotifications(manageResource.getNotifications());
			}
			finally {
				response.getWriter().write(new Gson().toJson(jsonResponse));
			}
		}
		else {
			jsonResponse.setError(true);
			response.getWriter().write(new Gson().toJson(jsonResponse));
		}
	}
	
}
