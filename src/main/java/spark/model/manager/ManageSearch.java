package spark.model.manager;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import spark.Constant;
import spark.exception.SuggestionException;
import spark.model.bean.Suggestion;
import spark.model.dao.SuggestionDAO;

public class ManageSearch extends Manager {
	
	public List<Suggestion> suggest(HttpServletRequest request) throws SuggestionException {
		List<Suggestion> suggestions = new LinkedList<Suggestion>();
		String query = request.getParameter("query");
		
		if(query != null && !query.equals("") && query.length() >= Constant.SUGGESTION_QUERY_MINSIZE) {
			SuggestionDAO suggestionDAO = new SuggestionDAO();
			suggestions = suggestionDAO.getByQueryOrderByScoring(query);
		}
		
		return suggestions;
	}

}
