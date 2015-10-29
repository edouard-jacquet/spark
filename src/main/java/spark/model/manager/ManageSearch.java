package spark.model.manager;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import spark.exception.SuggestionException;
import spark.model.bean.Suggestion;

public class ManageSearch extends Manager {
	
	public List<Suggestion> suggest(HttpServletRequest request) throws SuggestionException {
		List<Suggestion> suggestions = new LinkedList<Suggestion>();
		
		String query = request.getParameter("query");
		
		return suggestions;
	}

}
