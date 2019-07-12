package spark.controller.websocket;

import javax.websocket.Session;

public interface IWebSocket {

	public void open(Session session);
	public void close(Session session);
	public void error(Throwable error);
	public void receiveMessage(String message, Session session);
	
}
