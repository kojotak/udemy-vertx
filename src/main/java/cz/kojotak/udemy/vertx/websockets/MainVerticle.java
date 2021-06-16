package cz.kojotak.udemy.vertx.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

//use browser and address: https://websocketking.com/
public class MainVerticle extends AbstractVerticle {
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
	static final int PORT = 8900;

	public static void main(String... args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

	public void start(Promise<Void> startPromise) throws Exception {
		vertx.createHttpServer()
			 .webSocketHandler(new WebSocketHandler(vertx))
			 .listen(PORT, http -> {
				if (http.succeeded()) {
					startPromise.complete();
					LOG.info("HTTP server started on port " + PORT);
				} else {
					startPromise.fail(http.cause());
				}
		});
	}
}
