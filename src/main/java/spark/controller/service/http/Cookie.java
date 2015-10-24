package spark.controller.service.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cookie {
	
	public static String get(HttpServletRequest request, String name) {
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(javax.servlet.http.Cookie cookie : cookies) {
				if(cookie != null && name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public static void set(HttpServletResponse response, String name, String value, int maxAge) {
		javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

}
