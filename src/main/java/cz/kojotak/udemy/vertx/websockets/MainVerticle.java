package cz.kojotak.udemy.vertx.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public static void main(String... args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

	public void start(Promise<Void> startPromise) throws Exception {
		vertx.createHttpServer()
			 .webSocketHandler(new WebSocketHandler())
			 .listen(8900, http -> {
				if (http.succeeded()) {
					startPromise.complete();
					System.out.println("HTTP server started on port 8900");
				} else {
					startPromise.fail(http.cause());
				}
		});
	}
}
