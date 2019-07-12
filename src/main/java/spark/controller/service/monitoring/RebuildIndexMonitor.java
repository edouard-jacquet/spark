package spark.controller.service.monitoring;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;

import spark.model.bean.Notification;

public class RebuildIndexMonitor extends Observable implements MassIndexerProgressMonitor {
	
	private String indexName;
	private long documentCount = 0;
	private long documentBuilded = 0;
	private long timeOld = 0;
	private long timeEstimated = 0;
	private long timeLeft = 0;
	private float progress = 0;
	private Map<String, Object> message = new HashMap<String, Object>();

	@Override
	public void documentsAdded(long increment) {
		
	}

	@Override
	public void addToTotalCount(long count) {
		documentCount = count;
		
		timeOld = new Date().getTime();
	}

	@Override
	public void documentsBuilt(int number) {
		documentBuilded += number;
		
		long now = new Date().getTime();
		long old = timeOld;
		timeOld = now;
		long estimated = now - old;
		
		if(timeEstimated > 0) {
			timeEstimated = (long) Math.ceil(((timeEstimated + estimated) / 2));
		}
		else {
			timeEstimated = estimated;
		}
		
		timeLeft = (documentCount - documentBuilded) * timeEstimated;
		progress = ((float) Math.round(((float) documentBuilded / documentCount) * 100)) / 100;
		
		message.put("type", "rebuildIndexProgress");
		message.put("timeLeft", timeLeft);
		message.put("progress", progress);
		
		notifyChanged();
	}

	@Override
	public void entitiesLoaded(int size) {
		
	}

	@Override
	public void indexingCompleted() {
		message.put("type", "rebuildIndexCompleted");
		message.put("notifications", new Notification("success", "Index "+ indexName +" has been rebuilded."));
		
		notifyChanged();
	}
	
	private void notifyChanged() {
		message.put("indexName", indexName);
		setChanged();
		notifyObservers(message);
		message.clear();
	}
	
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

}
