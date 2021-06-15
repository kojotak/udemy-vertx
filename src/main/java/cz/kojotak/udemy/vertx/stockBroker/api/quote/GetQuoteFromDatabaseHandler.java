package cz.kojotak.udemy.vertx.stockBroker.api.quote;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.api.asset.GetAssetsFromDatabase;
import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.templates.SqlTemplate;

public class GetQuoteFromDatabaseHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetQuoteFromDatabaseHandler.class);
	private final JDBCPool dbPool;
	
	public GetQuoteFromDatabaseHandler(JDBCPool dbPool) {
		this.dbPool = dbPool;
	}
	@Override
	public void handle(RoutingContext ctx) {
		String assetParam = ctx.pathParam("asset");
		LOG.debug("asset parameter {}", assetParam);
		SqlTemplate
			.forQuery(dbPool, "select q.asset, q.bid, q.ask, q.last_price, q.volume from quotes q where asset=#{asset}")
			.mapTo(QuoteEntity.class)
			.execute(Collections.singletonMap("asset", assetParam))
			.onFailure(DbHandler.error(ctx, "failed to read quotes for asset "+assetParam+" from DB"))
			.onSuccess( quotes->{
				if(!quotes.iterator().hasNext()) {
					DbHandler.notFound(ctx, "no quotes found");
					return;
				}
				//assume only one
				var res = quotes.iterator().next().toJsonObject();
				ctx.response()
					.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
					.end(res.toBuffer());
				LOG.debug("asset parameter {} and response {}", assetParam, res);
			});				
	}

}
