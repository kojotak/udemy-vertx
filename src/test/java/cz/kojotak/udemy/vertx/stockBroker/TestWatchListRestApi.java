package cz.kojotak.udemy.vertx.stockBroker;

import io.vertx.core.Future;
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
	  var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
	  var accountId = UUID.randomUUID();
	  var watchList = new WatchList(new Asset("AMZN"), new Asset("AAPL"));
	  client.put("/account/watchlist/"+accountId.toString())
	  	.sendJsonObject(watchList.toJsonObject())
	  	.onComplete(testContext.succeeding(res->{
	  		System.out.println(res.bodyAsString());
			var json = res.bodyAsJsonObject();
			assertEquals(200, res.statusCode());
	  })).compose(next->{
		  client.get("/account/watchlist/"+accountId.toString())
		  	.send().onComplete(testContext.succeeding(res->{
		  		assertEquals(200, res.statusCode());
		  		testContext.completeNow();
		  	}));
		  return Future.succeededFuture();
	  });
  }
  
  @Test
  void adds_and_deletes_watchlist_for_account(Vertx vertx, VertxTestContext testContext) throws Throwable {
	  var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
	  var accountId = UUID.randomUUID();
	  var watchList = new WatchList(new Asset("AMZN"), new Asset("AAPL"));
	  client.put("/account/watchlist/"+accountId.toString())
	  	.sendJsonObject(watchList.toJsonObject())
	  	.onComplete(testContext.succeeding(res->{
	  		System.out.println(res.bodyAsString());
			var json = res.bodyAsJsonObject();
			assertEquals(200, res.statusCode());
	  })).compose(next->{
		  client.delete("/account/watchlist/"+accountId.toString())
		  	.send().onComplete(testContext.succeeding(res->{
		  		assertEquals(200, res.statusCode());
		  		System.out.println("object was deleted");
		  		testContext.completeNow();
		  	}));
		  return Future.succeededFuture();
	  });
  }
}
