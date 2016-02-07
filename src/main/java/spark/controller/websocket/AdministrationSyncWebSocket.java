package spark.controller.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

import com.google.gson.Gson;
import spark.model.manager.ManageUser;
import spark.model.manager.scheduler.job.*;

@ServerEndpoint(value = "/administration/schedule", configurator = WebSocketConfigurator.class)
public class AdministrationSyncWebSocket extends WebSocket implements Observer {
	
	private HttpSession httpSession;
	private static String rebuildIndexName = null;
	private static AtomicBoolean rebuildIndexExecuted = new AtomicBoolean(false);
	

	public AdministrationSyncWebSocket() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	/*@Override
	public void open(Session session) {
		sessions.add(session);
		logger.debug("Connection is opened by session "+ session.getId());
	}
	
	@OnOpen
	public void open(Session session, EndpointConfig endPointConfig) {
		sessions.add(session);
		httpSession = (HttpSession) endPointConfig.getUserProperties().get(HttpSession.class.getName());
		logger.debug("Connection is opened by session "+ httpSession.getId());
	}
	
	@OnClose
	public void close(Session session) {
		sessions.remove(session);
		logger.debug("Connection is closed by session "+ httpSession.getId());
	}*/
	
	@OnError
	public void error(Throwable error) {
		
	}
	
	@OnMessage
	public void receiveMessage(String message, Session session) {
		//ManageUser manageUser = new ManageUser();
		
		//if(manageUser.isLogged(httpSession)) {
			Map<String, String> request = messageDecode(message);
	
			if("launch".equals(request.get("action")))
					UpdateLibraryJob.execute();

			if("save".equals(request.get("action")))
				System.out.println("save");

		//}
	}

	@Override
	public void update(Observable observable, Object message) {
		sendAllMessage(new Gson().toJson(message));
	}

	@Override
	public void open(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(Session session) {
		// TODO Auto-generated method stub
		
	}
	
}
