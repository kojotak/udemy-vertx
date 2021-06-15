package cz.kojotak.udemy.vertx.stockBroker.api;

import cz.kojotak.udemy.vertx.stockBroker.api.quote.GetQuoteFromDatabaseHandler;
import cz.kojotak.udemy.vertx.stockBroker.api.quote.GetQuoteHandler;
import io.vertx.ext.web.Router;
import io.vertx.jdbcclient.JDBCPool;

public class QuotesRestApi {

	public static void attach(Router parent,JDBCPool dbPool) {
		parent.get("/quotes/:asset").handler(new GetQuoteHandler(AssetsRestApi.ASSETS));
		parent.get("/db/quotes/:asset").handler(new GetQuoteFromDatabaseHandler(dbPool));
	}
	
}
