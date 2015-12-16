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

import spark.controller.service.monitoring.RebuildIndexMonitor;
import spark.model.bean.Notification;
import spark.model.indexer.DocumentIndexer;
import spark.model.indexer.SuggestionIndexer;
import spark.model.manager.ManageUser;

@ServerEndpoint(value = "/administration/indexer", configurator = WebSocketConfigurator.class)
public class AdministrationIndexerWebSocket extends WebSocket implements Observer {
	
	private HttpSession httpSession;
	private static String rebuildIndexName = null;
	private static AtomicBoolean rebuildIndexExecuted = new AtomicBoolean(false);
	

	public AdministrationIndexerWebSocket() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
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
	}
	
	@OnError
	public void error(Throwable error) {
		
	}
	
	@OnMessage
	public void receiveMessage(String message, Session session) {
		ManageUser manageUser = new ManageUser();
		
		if(manageUser.isLogged(httpSession)) {
			Map<String, String> request = messageDecode(message);
			
			if("rebuildIndex".equals(request.get("action"))) {
				String indexName = request.get("index");
				rebuildIndex(indexName, session);
			}
		}
	}

	@Override
	public void update(Observable observable, Object message) {
		sendAllMessage(new Gson().toJson(message));
	}
	
	private void rebuildIndex(String indexName, Session session) {
		if(rebuildIndexExecuted.compareAndSet(false, true)) {
			rebuildIndexName = indexName;
			
			RebuildIndexMonitor rebuildIndexMonitor = new RebuildIndexMonitor();
			rebuildIndexMonitor.setIndexName(indexName);
			rebuildIndexMonitor.addObserver(this);
			
			Map<String, Object> message = new HashMap<String, Object>();
			message.put("type", "rebuildIndexAccept");
			message.put("accept", true);
			message.put("notifications", new Notification("info", "Index "+ rebuildIndexName +" is rebuilding."));
			sendMessage(new Gson().toJson(message), session);
			
			switch(indexName) {
				case "document":
					DocumentIndexer.getInstance().rebuild(rebuildIndexMonitor);
					break;
				case "suggestion":
					SuggestionIndexer.getInstance().rebuild(rebuildIndexMonitor);
					break;
			}
			
			rebuildIndexName = null;
			rebuildIndexExecuted.set(false);
		}
		else {
			Map<String, Object> message = new HashMap<String, Object>();
			message.put("type", "rebuildIndexAccept");
			message.put("accept", false);
			message.put("notifications", new Notification("warning", "Index "+ rebuildIndexName +" is already rebuilding."));
			sendMessage(new Gson().toJson(message), session);
		}
	}
}
