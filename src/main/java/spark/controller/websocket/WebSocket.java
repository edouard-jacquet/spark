package spark.controller.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

public abstract class WebSocket implements IWebSocket {
	
	protected Logger logger;
	protected HttpSession httpSession;

	protected void sendMessage(String message, Session session) {
		try {
			session.getBasicRemote().sendText(message);
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> messageDecode(String message) {
		return new Gson().fromJson(message, HashMap.class);
	}
	
}
