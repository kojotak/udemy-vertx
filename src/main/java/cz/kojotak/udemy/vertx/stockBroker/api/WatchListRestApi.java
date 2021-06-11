package cz.kojotak.udemy.vertx.stockBroker.api;

import java.util.HashMap;
import java.util.UUID;

import cz.kojotak.udemy.vertx.stockBroker.api.watchlist.DeleteWatchListHandler;
import cz.kojotak.udemy.vertx.stockBroker.api.watchlist.GetWatchListHandler;
import cz.kojotak.udemy.vertx.stockBroker.api.watchlist.PutWatchListHandler;
import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.vertx.ext.web.Router;

public class WatchListRestApi {

	private static final HashMap<UUID, WatchList> watchListPerAccount = new HashMap<>();

	public static void attach(Router parent) {
		parent.get("/account/watchlist").handler(new GetWatchListHandler(watchListPerAccount));
		parent.put("/account/watchlist/:accountId").handler(new PutWatchListHandler(watchListPerAccount));
		parent.delete("/account/watchlist/:accountId").handler(new DeleteWatchListHandler(watchListPerAccount));
	}
}
