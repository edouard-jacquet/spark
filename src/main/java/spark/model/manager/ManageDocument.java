package spark.model.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import spark.Constant;
import spark.controller.service.security.Hash;
import spark.exception.NotificationException;
import spark.model.bean.Author;
import spark.model.bean.Document;
import spark.model.bean.Notification;
import spark.model.dao.AuthorDAO;
import spark.model.dao.DocumentDAO;
import spark.model.dao.SourceDAO;

public class ManageDocument extends Manager {
	
	DocumentDAO documentDAO = new DocumentDAO();
	AuthorDAO authorDAO = new AuthorDAO();
	SourceDAO sourceDAO = new SourceDAO();
	private List<Document> recommendations = new LinkedList<Document>();
	private int documentCount = 0;
	private int currentPage = 1;
	private int maxPage = 1;
	
	
	public void create(String title, String summary, String publicationDate, String authors, String source, File temporaryFile) throws ParseException, IOException {
		String fileName = temporaryFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		fileName = Hash.generate("SHA-1", fileName);
		String filePath = Constant.STORAGE_DOCUMENT_FOLDER + fileName +".pdf";
		
		Document document = new Document();
		document.setTitle(title);
		document.setSummary(summary);
		document.setAttachment(fileName);
		document.setUpdateDate(new Date());
		document.setPublicationDate(new SimpleDateFormat("yyyy").parse(publicationDate));
		
		for(String name : authors.toLowerCase().split(Constant.AUTHOR_SEPARATOR)) {
			Author author = authorDAO.getByName(name);
			
			if(author == null) {
				author = new Author();
				author.setName(name);
				author = authorDAO.create(author);
			}
			
			document.getAuthors().add(author);
		}
		
		document.setSource(sourceDAO.getByName(source));
		
		Files.move(Paths.get(temporaryFile.getPath()), Paths.get(filePath));
		
		documentDAO.create(document);
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
		
		Document document = documentDAO.getById(Long.parseLong(id));
		
		if(document != null) {
			recommendations = documentDAO.getSimilarByDocumentOrderByScoring(document);
		}
		
		return document;
	}
	
	public List<Document> search(HttpServletRequest request) {
		List<Document> documents = new LinkedList<Document>();
		String query = request.getParameter("query");
		String page = request.getParameter("page");
		
		if(query != null && query.matches(Constant.REGEX_QUERY)) {
			query = query.toLowerCase();
			
			if(page != null && page.length() > 0 && Pattern.matches(Constant.REGEX_PAGE, page)) {
				currentPage = Integer.parseInt(page);
			}

			documents = documentDAO.getByQueryAndPageOrderByScoring(query, currentPage);
			documentCount = documentDAO.getResultSize();
			
			if(documentCount > 0) {
				maxPage = (int) Math.ceil(((float) documentCount) / Constant.DOCUMENT_MAXRESULT);
			}
		}
		
		return documents;
	}

	public List<Document> getRecommendations() {
		return recommendations;
	}
	
	public int getDocumentCount() {
		return documentCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

}
