package cz.kojotak.udemy.vertx.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;

public class WebSocketHandler implements Handler<ServerWebSocket> {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);
	static final String PATH = "/ws/simple";
	private final PriceBroadcast broadcast;
	
	public WebSocketHandler(Vertx vertx) {
		this.broadcast = new PriceBroadcast(vertx);
	}

	@Override
	public void handle(ServerWebSocket ws) {
		LOG.info("handling web socket connection {},{}", ws.path(), ws.textHandlerID());

		if(!PATH.equalsIgnoreCase(ws.path())) {
			ws.writeFinalTextFrame("wrong path, rejected");
			closeMe(ws);
			return;
		}

		ws.accept();
		ws.frameHandler(received-> frameHandler(ws, received) );
		ws.endHandler(on -> {
			LOG.error("closed {}", ws.textHandlerID());
			broadcast.unregister(ws);
		});
		ws.exceptionHandler(err->LOG.error("failed",err));
		ws.writeTextMessage("Connected");
		broadcast.register(ws);
	}

	private void frameHandler(ServerWebSocket ws, WebSocketFrame received) {
		String message = received.textData();
		LOG.debug("received message {} from {}", message, ws.textHandlerID());
		if("disconnect me".equals(message)) {
			LOG.info("going to disconnect");
			closeMe(ws);
			return;
		} else {
			ws.writeTextMessage("not supported message: " + message);
		}
	}

	private void closeMe(ServerWebSocket ws) {
		ws.close((short)1000, "normal closure");
	}

}
