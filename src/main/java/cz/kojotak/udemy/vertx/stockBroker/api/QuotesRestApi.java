package cz.kojotak.udemy.vertx.stockBroker.api;

import cz.kojotak.udemy.vertx.stockBroker.api.quote.GetQuoteHandler;
import io.vertx.ext.web.Router;

public class QuotesRestApi {

	public static void attach(Router parent) {
		parent.get("/quotes/:asset").handler(new GetQuoteHandler(AssetsRestApi.ASSETS));
	}
	
}
