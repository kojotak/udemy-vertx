package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class DeleteWatchListHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteWatchListHandler.class);

	private final HashMap<UUID, WatchList> watchListPerAccount;
	
	public DeleteWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
		super();
		this.watchListPerAccount = watchListPerAccount;
	}

	@Override
	public void handle(RoutingContext ctx) {
		var accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
		WatchList deleted = watchListPerAccount.remove(UUID.fromString(accountId));
		LOG.info("deleted {}, remaining {}", accountId, watchListPerAccount.size());
		ctx.response().end(deleted.toJsonObject().toBuffer());
	}

}
