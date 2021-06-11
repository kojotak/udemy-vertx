package cz.kojotak.udemy.vertx.stockBroker;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class WatchListRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(WatchListRestApi.class);

	public static void attach(Router parent) {
		HashMap<UUID, WatchList> watchListPerAccount = new HashMap<>();
		parent.get("/account/watchlist").handler(ctx->{
			String accountId = ctx.pathParam("accountId");
			LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
			var watchList = watchListPerAccount.get(UUID.fromString(accountId));
			if(watchList==null) {
				ctx.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject()
						.put("message", "account " + accountId + " not available")
						.put("path", ctx.normalisedPath())
						.toBuffer());
				return;
			} 
			ctx.response().end(watchList.toJsonObject().toBuffer());
		});
		parent.put("/account/watchlist/:accountId").handler(ctx->{
			var accountId = ctx.pathParam("accountId");
			LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
			var json = ctx.getBodyAsJson();
			var watchList = json.mapTo(WatchList.class);
			watchListPerAccount.put(UUID.fromString(accountId), watchList);
			ctx.response().end(json.toBuffer());
		});
		parent.delete("/account/watchlist/:accountId").handler(ctx->{
			
		});
	}
}
