package cz.kojotak.udemy.vertx.websockets;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;

public class PriceBroadcast {

	private static final Logger LOG = LoggerFactory.getLogger(PriceBroadcast.class);

	private HashMap<String,ServerWebSocket> connectedClients = new HashMap<>();

	public PriceBroadcast(Vertx vertx) {
		periodicUpdate(vertx);
	}

	private void periodicUpdate(Vertx vertx) {
		vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id->{
			connectedClients.values().forEach(ws->{
				ws.writeTextMessage(new JsonObject()
					.put("symbol", "AMZN")
					.put("value", new Random().nextInt())
					.toString());
			});
		});	
	}

	public void register(ServerWebSocket ws) {
		connectedClients.put(ws.textHandlerID(), ws);
	}

	public void unregister(ServerWebSocket ws) {
		connectedClients.remove(ws.textHandlerID());
	}
}
