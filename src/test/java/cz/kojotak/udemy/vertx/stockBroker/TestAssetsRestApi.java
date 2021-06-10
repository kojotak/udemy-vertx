package cz.kojotak.udemy.vertx.stockBroker;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(VertxExtension.class)
public class TestAssetsRestApi {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void returns_all_assets(Vertx vertx, VertxTestContext testContext) throws Throwable {
	  var client = WebClient.create(vertx, new WebClientOptions()
			  .setDefaultPort(MainVerticle.PORT));
	  client.get("/assets").send().onComplete(testContext.succeeding(res->{
		var json = res.bodyAsJsonArray();
		System.out.println("response " + json);
		assertEquals(200, res.statusCode());
		assertTrue(json.encode().contains("AAPL"));
		testContext.completed();
	  }));
  }
}
