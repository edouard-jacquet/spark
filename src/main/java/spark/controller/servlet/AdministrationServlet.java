package spark.controller.servlet;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.mutable.MutableLong;

import spark.Constant;
import spark.model.dao.DocumentDAO;
import spark.model.dao.SuggestionDAO;
import spark.model.dao.UserDAO;
import spark.model.manager.ManageUser;

@WebServlet("/administration")
public class AdministrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
	private Map<String, Object> statistics = new HashMap<String, Object>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ManageUser manageUser = new ManageUser();
		manageUser.hasCookie(request, response);
		if(manageUser.isLogged(request)) {
			UserDAO userDAO = new UserDAO();
			statistics.put("userCount", userDAO.getAll().size());
			DocumentDAO documentDAO = new DocumentDAO();
			statistics.put("documentCount", documentDAO.getAll().size());
			SuggestionDAO suggestionDAO = new SuggestionDAO();
			statistics.put("suggestionCount", suggestionDAO.getAll().size());
			getSizeFolder(Constant.STORAGE_ROOT_DIRECTORY, Constant.STORAGE_ROOT_FOLDER);
			getSizeFolder(Constant.STORAGE_DOCUMENT_DIRECTORY, Constant.STORAGE_DOCUMENT_FOLDER);
			getSizeFolder(Constant.STORAGE_INDEX_DIRECTORY, Constant.STORAGE_INDEX_FOLDER);
			
			request.setAttribute("statistics", statistics);
			request.getRequestDispatcher("/jsp/administration.jsp").forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void getSizeFolder(String name, String path) throws IOException {
		MutableLong bytes = new MutableLong();
		
		Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				bytes.add(attrs.size());
				return FileVisitResult.CONTINUE;
			}
		});
		
		Object[] folder = readableFileSize(bytes.longValue());
		statistics.put(name +"Space", folder[0]);
		statistics.put(name +"UnitSpace", folder[1]);
	}
	
	private Object[] readableFileSize(long size) {
		if(size <= 0) {
			return new Object[]{"0", ""};
		}
		
		int digitGroup = (int) (Math.log10(size) / Math.log10(1024));
		String unit = units[digitGroup];

		return new Object[]{size, unit};
	}
}
