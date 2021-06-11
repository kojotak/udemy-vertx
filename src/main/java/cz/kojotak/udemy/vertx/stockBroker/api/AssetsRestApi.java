package cz.kojotak.udemy.vertx.stockBroker.api;

import java.util.Arrays;
import java.util.List;

import cz.kojotak.udemy.vertx.stockBroker.api.asset.GetAssetsHandler;
import io.vertx.ext.web.Router;

public class AssetsRestApi {
	
	static final List<String> ASSETS = Arrays.asList("AAPL","AMZN","GOOG","MSFT","NFLX");

	public static void attach(Router router) {
		router.get("/assets").handler( new GetAssetsHandler(ASSETS));
	}
}
