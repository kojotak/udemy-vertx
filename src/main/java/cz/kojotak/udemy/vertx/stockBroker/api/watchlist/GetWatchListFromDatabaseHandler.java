package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.templates.SqlTemplate;

public class GetWatchListFromDatabaseHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetWatchListFromDatabaseHandler.class);
	private final JDBCPool dbPool;
	
	public GetWatchListFromDatabaseHandler(JDBCPool dbPool) {
		super();
		this.dbPool = dbPool;
	}

	@Override
	public void handle(RoutingContext ctx) {
		String accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);

		SqlTemplate
			.forQuery(dbPool, "select w.asset from watchlist w where w.account_id=#{account_id}")
			.mapTo(Row::toJson)
			.execute(Collections.singletonMap("account_id", accountId))
			.onFailure(DbHandler.error(ctx, "failed to fetch watchlist for account_id="+accountId))
			.onSuccess( a$$et->{
 				if(!a$$et.iterator().hasNext()) {
					DbHandler.notFound(ctx, "not available");
					return;
				}
				var resp = new JsonArray();
				a$$et.forEach(resp::add);
				LOG.info("path {} responds with {}", ctx.pathParams(), resp.encode());
				ctx.response()
					.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
					.end(resp.toBuffer());
			});
	}

}
