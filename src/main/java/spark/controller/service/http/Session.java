package spark.controller.service.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Session {
	
	public static Object get(HttpSession session, String name) {
		return (Object) session.getAttribute(name);
	}
	
	public static Object get(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(true);
		return (Object) get(session, name);
	}
	
	public static void set(HttpSession session, String name, Object value) {
		session.setAttribute(name, value);
	}
	
	public static void set(HttpServletRequest request, String name, Object value) {
		HttpSession session = request.getSession(true);
		set(session, name, value);
	}
	
	public static void invalidate(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
	}

}
