package cz.kojotak.udemy.vertx.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;

public class WebSocketHandler implements Handler<ServerWebSocket> {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);

	@Override
	public void handle(ServerWebSocket ws) {
		LOG.info("openning web socket connection {},{}", ws.path(), ws.textHandlerID());
		ws.accept();
		ws.endHandler(on -> LOG.error("closed {}", ws.textHandlerID()));
		ws.exceptionHandler(err->LOG.error("failed",err));
		ws.writeTextMessage("Connected");
	}

}
