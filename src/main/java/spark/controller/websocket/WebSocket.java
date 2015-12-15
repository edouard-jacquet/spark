package spark.controller.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

public abstract class WebSocket implements IWebSocket {
	
	protected static Logger logger;
	protected static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	protected void sendMessage(String message, Session session) {
		try {
			session.getBasicRemote().sendText(message);
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
	}
	
	protected void sendAllMessage(String message) {
		synchronized(sessions) {
			for(Session session : sessions) {
				sendMessage(message, session);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> messageDecode(String message) {
		return new Gson().fromJson(message, HashMap.class);
	}
	
}
