package spark.controller.service.monitoring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;

import com.google.gson.Gson;

import spark.model.bean.Notification;

public class WebSocketIndexingProgressMonitor implements MassIndexerProgressMonitor {
	
	private String indexName;
	private Session session;
	private Map<Object, Object> response = new HashMap<Object, Object>();
	
	public WebSocketIndexingProgressMonitor(String indexName, Session session) {
		this.indexName = indexName;
		this.session = session;
	}

	@Override
	public void documentsAdded(long increment) {
		response.put("operation", "documentsAdded");
		response.put("increment", increment);
		sendMessage();
	}

	@Override
	public void addToTotalCount(long count) {
		response.put("operation", "addToTotalCount");
		response.put("count", count);
		sendMessage();
	}

	@Override
	public void documentsBuilt(int number) {
		response.put("operation", "documentsBuilt");
		response.put("number", number);
		sendMessage();
	}

	@Override
	public void entitiesLoaded(int size) {
		response.put("operation", "documentsAdded");
		response.put("size", size);
		sendMessage();
	}

	@Override
	public void indexingCompleted() {
		response.put("operation", "indexingCompleted");
		response.put("notifications", new Notification("success", "Index "+ indexName +" has been rebuilded."));
		sendMessage();
	}
	
	private void sendMessage() {
		try {
			session.getBasicRemote().sendText(new Gson().toJson(response));
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
		finally {
			response.clear();
		}
	}

}
