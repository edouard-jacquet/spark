package spark.controller.websocket;

import java.util.HashMap;
import java.util.List;
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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.logging.Logger;

import com.google.gson.Gson;

import spark.model.bean.Configuration;
import spark.model.bean.Notification;
import spark.model.bean.Source;
import spark.model.dao.ConfigurationDAO;
import spark.model.dao.SourceDAO;
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
		ConfigurationDAO config_dao = new ConfigurationDAO();
		SourceDAO source_dao = new SourceDAO();
		List<Source> sources = source_dao.getAll();
		
		//if(manageUser.isLogged(httpSession)) {
			Map<String, String> request = messageDecode(message);
	
			if("launch".equals(request.get("action"))){
					Map<String, Object> user_message = new HashMap<String, Object>();
					user_message.put("type", "schedulerLauch");
					user_message.put("accept", true);
					user_message.put("notifications", new Notification("info", "Schedule is launch."));
					sendMessage(new Gson().toJson(user_message), session);
					
					UpdateLibraryJob.execute();
					
			}else if("save".equals(request.get("action"))){
				try {
					JSONObject obj = new JSONObject(message);
					Configuration ligne_config = config_dao.getByKey("schedule");
					ligne_config.setValue(obj.get("config").toString());
					config_dao.update(ligne_config);
					
					for(int i=0; i < sources.size(); i++){
						if(sources.get(i).getId() > 2){
							Source source_obj = sources.get(i);
							if(obj.get("source"+source_obj.getId()).toString() == "true")
								source_obj.setActive(1);
							else
								source_obj.setActive(0);
							source_dao.update(source_obj);
						}
					}
					
					Map<String, Object> user_message = new HashMap<String, Object>();
					user_message.put("type", "saveAccept");
					user_message.put("accept", true);
					user_message.put("notifications", new Notification("info", "Current configuration is save."));
					sendMessage(new Gson().toJson(user_message), session);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

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
