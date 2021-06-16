package cz.kojotak.udemy.vertx.websockets;

import io.vertx.core.Vertx;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;
@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
	}

	@Timeout(value=10, timeUnit=TimeUnit.SECONDS)
//	@Test
	void can_connect_to_websocket_server(Vertx vertx, VertxTestContext testContext) throws Throwable {
		var client = vertx.createHttpClient();
		
		client.webSocket(MainVerticle.PORT, "localhost", WebSocketHandler.PATH)
			  .onFailure(testContext::failNow)
			  .onComplete(testContext.succeeding(ws->{
				 ws.handler(data->{
					 String msg = data.toString();
					 System.out.println("received: " + msg);
					 assertEquals("Connected!", msg);
					 client.close();
					 testContext.completeNow();
				 });
			  }))
			  ;
	}
}
