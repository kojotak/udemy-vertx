package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetWatchListHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetWatchListHandler.class);

	private final HashMap<UUID, WatchList> watchListPerAccount;
	
	public GetWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
		super();
		this.watchListPerAccount = watchListPerAccount;
	}

	@Override
	public void handle(RoutingContext ctx) {
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
	}

}
