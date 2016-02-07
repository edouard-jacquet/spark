package spark.controller.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
	
	@Override
	public void modifyHandshake(ServerEndpointConfig serverEndPointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {
		/*HttpSession httpSession = (HttpSession) handshakeRequest.getHttpSession();
		serverEndPointConfig.getUserProperties().put(HttpSession.class.getName(),  httpSession);*/
	}
	
}
