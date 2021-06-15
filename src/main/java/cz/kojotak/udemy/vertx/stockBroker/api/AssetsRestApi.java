package cz.kojotak.udemy.vertx.stockBroker.api;

import java.util.Arrays;
import java.util.List;

import cz.kojotak.udemy.vertx.stockBroker.api.asset.GetAssetsHandler;
import io.vertx.ext.web.Router;
import io.vertx.jdbcclient.JDBCPool;

public class AssetsRestApi {
	
	static final List<String> ASSETS = Arrays.asList("AAPL","AMZN","GOOG","MSFT","NFLX");

	public static void attach(Router router, JDBCPool dbPool) {
		router.get("/assets").handler( new GetAssetsHandler(ASSETS));
	}
}
