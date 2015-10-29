package spark.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import spark.exception.SuggestionException;
import spark.model.bean.JsonResponse;
import spark.model.manager.ManageSearch;

@WebServlet("/search/suggestion")
public class SuggestionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageSearch manageSearch = new ManageSearch();
		JsonResponse jsonResponse = new JsonResponse();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try {
			jsonResponse.setData(manageSearch.suggest(request));
		}
		catch(SuggestionException suggestionException) {
			jsonResponse.setError(true);
		}
		
		response.getWriter().write(new Gson().toJson(jsonResponse));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}