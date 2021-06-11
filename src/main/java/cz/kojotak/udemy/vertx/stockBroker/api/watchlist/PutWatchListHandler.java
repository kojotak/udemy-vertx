package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class PutWatchListHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PutWatchListHandler.class);

	private final HashMap<UUID, WatchList> watchListPerAccount;
	
	public PutWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
		super();
		this.watchListPerAccount = watchListPerAccount;
	}

	@Override
	public void handle(RoutingContext ctx) {
		var accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
		var json = ctx.getBodyAsJson();
		var watchList = json.mapTo(WatchList.class);
		watchListPerAccount.put(UUID.fromString(accountId), watchList);
		ctx.response().end(json.toBuffer());
	}

}
