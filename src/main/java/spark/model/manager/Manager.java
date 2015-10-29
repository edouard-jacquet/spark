package spark.model.manager;

import java.util.LinkedList;
import java.util.List;

import spark.model.bean.Notification;

public abstract class Manager implements IManager {

	protected List<Notification> notifications = new LinkedList<Notification>();
	
	
	public List<Notification> getNotifications() {
		return notifications;
	}
	
}
