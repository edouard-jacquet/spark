package spark.model.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.Constant;
import spark.controller.service.http.Cookie;
import spark.controller.service.http.Session;
import spark.controller.service.security.Random;
import spark.controller.service.time.TimeStamp;
import spark.exception.NotificationException;
import spark.exception.UserIsExistException;
import spark.exception.UserNotCreatedException;
import spark.exception.UserNotExistException;
import spark.model.bean.Notification;
import spark.model.bean.User;
import spark.model.dao.UserDAO;

public class ManageUser {

	private UserDAO userDAO = new UserDAO();
	private List<Notification> notifications = new LinkedList<Notification>();
	
	
	public void create(HttpServletRequest request, HttpServletResponse response) throws NotificationException, UserIsExistException, UserNotCreatedException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		if(login.length() == 0) {
			notifications.add(new Notification("alert", "Login is empty."));
		}
		else if(!Pattern.matches(Constant.REGEX_LOGIN, login)) {
			notifications.add(new Notification("alert", "Login contain invalid characters."));
		}
		if(password.length() == 0) {
			notifications.add(new Notification("alert", "Password is empty."));
		}
		else if(!Pattern.matches(Constant.REGEX_PASSWORD, password)) {
			notifications.add(new Notification("alert", "Password contain invalid characters."));
		}
		
		if(!notifications.isEmpty()) { throw new NotificationException(); }
		
		if(userDAO.isExist(login)) { 
			notifications.add(new Notification("alert", "User exist already."));
			throw new UserIsExistException();
		}
		
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setKey(Random.generate(160));
		if(!userDAO.add(user)) {
			notifications.add(new Notification("alert", "User hasn't been created."));
			throw new UserNotCreatedException();
		}
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws NotificationException, UserNotExistException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		if(login.length() == 0) {
			notifications.add(new Notification("alert", "Login is empty."));
		}
		else if(!Pattern.matches(Constant.REGEX_LOGIN, login)) {
			notifications.add(new Notification("alert", "Login contain invalid characters."));
		}
		if(password.length() == 0) {
			notifications.add(new Notification("alert", "Password is empty."));
		}
		else if(!Pattern.matches(Constant.REGEX_PASSWORD, password)) {
			notifications.add(new Notification("alert", "Password contain invalid characters."));
		}
		
		if(!notifications.isEmpty()) { throw new NotificationException(); }
		
		User user = userDAO.getByLoginAndPassword(login, password);
		
		if(user == null) { 
			notifications.add(new Notification("alert", "User doesn't exist."));
			throw new UserNotExistException();
		}
		
		Session.set(request, Constant.SESSION_USER_NAME, user);
		if(remember != null) {
			Cookie.set(response, Constant.COOKIE_USER_NAME, user.getId() + Constant.COOKIE_DELIMITER + user.getKey(), TimeStamp.get() + Constant.COOKIE_MAXAGE);
		}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Session.invalidate(request);
		Cookie.set(response, Constant.COOKIE_USER_NAME, "", 0);
	}
	
	public boolean isLogged(HttpServletRequest request) {
		if(Session.get(request, Constant.SESSION_USER_NAME) != null && Session.get(request, Constant.SESSION_USER_NAME) instanceof User) {
			return true;
		}	
		return false;
	}
	
	public void hasCookie(HttpServletRequest request, HttpServletResponse response) {
		if(!this.isLogged(request)) {
			if(Cookie.get(request, Constant.COOKIE_USER_NAME) != null) {
				String[] cookie = Cookie.get(request, Constant.COOKIE_USER_NAME).split(Constant.COOKIE_DELIMITER);
				long id = Long.parseLong(cookie[0]);
				String key = cookie[1];
				
				User user = userDAO.getById(id);
				if(user != null && user.getKey().equals(key)) {
					Session.set(request, Constant.SESSION_USER_NAME, user);
					Cookie.set(response, Constant.COOKIE_USER_NAME, user.getId() + Constant.COOKIE_DELIMITER + user.getKey(), TimeStamp.get() + Constant.COOKIE_MAXAGE);
				}
				else {
					Cookie.set(response, Constant.COOKIE_USER_NAME, "", 0);
				}
			}
		}
	}
	
	public List<Notification> getNotifications() {
		return notifications;
	}

}
