package spark.model.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import spark.Constant;
import spark.exception.NotificationException;
import spark.model.bean.Document;
import spark.model.bean.Notification;
import spark.model.dao.DocumentDAO;

public class ManageDocument extends Manager {
	
	DocumentDAO documentDAO = new DocumentDAO();
	private int currentPage = 1;
	private int maxPage = 1;
	
	
	public List<Document> search(HttpServletRequest request) {
		List<Document> documents = new LinkedList<Document>();
		String query = request.getParameter("query");
		String page = request.getParameter("page");
		
		if(query != null && query.length() > 0  && query.length() >= Constant.DOCUMENT_QUERY_MINSIZE) {
			if(page != null && page.length() > 0 && Pattern.matches(Constant.REGEX_PAGE, page)) {
				currentPage = Integer.parseInt(page);
			}

			documents = documentDAO.getByQueryAndPageOrderByScoring(query, currentPage);
			
			if(documents != null && documents.size() > 0) {
				maxPage = (int) Math.ceil(((float) documents.size()) / Constant.DOCUMENT_MAXRESULT);
			}
		}
		
		return documents;
	}
	
	public Document open(HttpServletRequest request) throws NotificationException {
		String id = request.getParameter("id");
		
		if(id.length() == 0) {
			notifications.add(new Notification("alert", "Id is empty."));
		}
		else if(!Pattern.matches(Constant.REGEX_ID, id)) {
			notifications.add(new Notification("alert", "Id contain invalid characters."));
		}
		
		if(!notifications.isEmpty()) { throw new NotificationException(); }
		
		return documentDAO.getById(Long.parseLong(id));
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public int getMaxPage() {
		return maxPage;
	}

}
