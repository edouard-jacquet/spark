package spark.model.bean;

import java.util.List;

public class JsonResponse {

	private boolean warning = false;
	private boolean error = false;
	private List<Notification> notifications = null;

	
	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
}
