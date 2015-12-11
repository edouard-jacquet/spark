package spark.controller.servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import spark.model.bean.JsonResponse;
import spark.model.bean.Notification;
import spark.model.indexer.DocumentIndexer;
import spark.model.indexer.SuggestionIndexer;
import spark.model.manager.ManageUser;

@WebServlet("/administration/index")
public class AdministrationIndexServlet extends HttpServlet {
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		JsonResponse jsonResponse = new JsonResponse();
		String index = request.getParameter("name");
		boolean success = false;
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(manageUser.isLogged(request)) {
			switch(index) {
				case "document":
					success = DocumentIndexer.getInstance().rebuild();
					
					break;
				case "suggestion":
					success = SuggestionIndexer.getInstance().rebuild();
					break;
			}
			
			jsonResponse.setNotifications(new LinkedList<Notification>());
			if(success) {
				jsonResponse.getNotifications().add(new Notification("success", "Index "+ index +" has been rebuilded."));
			}
			else {
				jsonResponse.setError(true);
				jsonResponse.getNotifications().add(new Notification("alert", "Index "+ index +" hasn't been rebuilded"));
			}
		}
		else {
			jsonResponse.setError(true);
		}

		response.getWriter().write(new Gson().toJson(jsonResponse));
	}
	
}
