package cz.kojotak.udemy.vertx.stockBroker;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(VertxExtension.class)
public class TestWatchListRestApi {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void adds_and_returns_watchlist_for_account(Vertx vertx, VertxTestContext testContext) throws Throwable {
	  var client = WebClient.create(vertx, new WebClientOptions()
			  .setDefaultPort(MainVerticle.PORT));
	  var accountId = UUID.randomUUID();
	  var watchList = new WatchList(new Asset("AMZN"), new Asset("AAPL"));
	  client.put("/account/watchlist/"+accountId)
	  	.sendJsonObject(watchList.toJsonObject())
	  	.onComplete(testContext.succeeding(res->{
			var json = res.bodyAsJsonObject();
			System.out.println("response " + json);
			assertEquals(200, res.statusCode());
			testContext.completeNow();
	  }));
  }
}
