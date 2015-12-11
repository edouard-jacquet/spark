package spark.controller.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

import spark.controller.service.monitoring.WebSocketIndexingProgressMonitor;
import spark.model.indexer.DocumentIndexer;
import spark.model.indexer.SuggestionIndexer;
import spark.model.manager.ManageUser;

@ServerEndpoint(value = "/administration/indexer", configurator = WebSocketConfigurator.class)
public class AdministrationIndexerWebSocket extends WebSocket {

	public AdministrationIndexerWebSocket() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	public void open(Session session) {
		logger.debug("Connection is opened by session "+ session.getId());
	}
	
	@OnOpen
	public void open(Session session, EndpointConfig endPointConfig) {
		this.httpSession = (HttpSession) endPointConfig.getUserProperties().get(HttpSession.class.getName());
		logger.debug("Connection is opened by session "+ httpSession.getId());
	}
	
	@OnClose
	public void close(Session session) {
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
			if("rebuild".equals(request.get("action"))) {
				switch(request.get("index")) {
					case "document":
						DocumentIndexer.getInstance().rebuild(new WebSocketIndexingProgressMonitor("document", session));
						break;
					case "suggestion":
						SuggestionIndexer.getInstance().rebuild(new WebSocketIndexingProgressMonitor("suggestion", session));
						break;
				}
			}
		}
	}
	
}
