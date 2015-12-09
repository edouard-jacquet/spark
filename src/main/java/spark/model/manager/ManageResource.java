package spark.model.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import spark.Constant;
import spark.exception.UploadException;
import spark.exception.UploadExtensionException;
import spark.exception.UploadFileNotFoundException;
import spark.model.bean.Notification;

public class ManageResource extends Manager {
	
	public void upload(HttpServletRequest request) throws UploadException, UploadExtensionException, UploadFileNotFoundException {
		try {
			Part filePart = request.getPart("file");
			
			if(filePart.getSize() == 0) {
				notifications.add(new Notification("alert", "File is not found."));
				throw new UploadFileNotFoundException();
			}
			
			String title = request.getParameter("title");
			String summary = request.getParameter("summary");
			String publicationDate = request.getParameter("publicationdate");
			String authors = request.getParameter("authors");
			String fileName = filePart.getSubmittedFileName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			
			if(!fileExtension.equals("pdf")) {
				notifications.add(new Notification("alert", "File extension not corresponding."));
				throw new UploadExtensionException();
			}
			
			InputStream inputStream = filePart.getInputStream();
			
			ManageDocument manageDocument = new ManageDocument();
			manageDocument.create(title, summary, publicationDate, authors, "personal", save(fileName, inputStream));
			
			notifications.add(new Notification("success", "Upload "+ fileName +" is successul."));
		}
		catch(ServletException | IOException | ParseException exception) {
			notifications.add(new Notification("alert", "Upload to encountered a problem."));
			throw new UploadException();
		}
	}
	
	private File save(String name, InputStream inputStream) throws IOException {
		OutputStream outputStream = null;
		
		try {
			String filePath = Constant.STORAGE_TEMPORARY_FOLDER + name +".pdf";
			File file = new File(filePath);
			outputStream = new FileOutputStream(file);
			
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			
			return file;
		}
		finally {
			if (inputStream != null)
				inputStream.close();

			if (outputStream != null)
				outputStream.close();
		}
	}
	
}
