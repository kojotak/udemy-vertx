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

import cz.kojotak.udemy.vertx.stockBroker.cfg.BrokerConfig;
import cz.kojotak.udemy.vertx.stockBroker.cfg.ConfigLoader;


@ExtendWith(VertxExtension.class)
public class TestQuotesRestApi extends AbstractRestApiTest {
	
  @Test
  void returns_quote_for_asset(Vertx vertx, VertxTestContext testContext) throws Throwable {
	  var client = WebClient.create(vertx, new WebClientOptions()
			  .setDefaultPort(TEST_PORT));
	  client.get("/quotes/AMZN").send().onComplete(testContext.succeeding(res->{
		var json = res.bodyAsJsonObject();
		System.out.println("response " + json);
		assertEquals(200, res.statusCode());
		assertTrue(json.encode().contains("AMZN"));
		testContext.completeNow();
	  }));
  }
  
  @Test
  void returns_not_found_for_unknown_asset(Vertx vertx, VertxTestContext testContext) throws Throwable {
	  var client = WebClient.create(vertx, new WebClientOptions()
			  .setDefaultPort(TEST_PORT));
	  client.get("/quotes/UNKNOWN").send().onComplete(testContext.succeeding(res->{
		var json = res.bodyAsJsonObject();
		System.out.println("response " + json);
		assertEquals(404, res.statusCode());
		testContext.completeNow();
	  }));
  }
}
