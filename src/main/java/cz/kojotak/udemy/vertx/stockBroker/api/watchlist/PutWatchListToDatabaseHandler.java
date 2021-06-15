package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.templates.SqlTemplate;

public class PutWatchListToDatabaseHandler implements Handler<RoutingContext> {

	private static final Logger LOG = LoggerFactory.getLogger(PutWatchListToDatabaseHandler.class);
	private final JDBCPool dbPool;
	
	public PutWatchListToDatabaseHandler(JDBCPool dbPool) {
		super();
		this.dbPool = dbPool;
	}

	@Override
	public void handle(RoutingContext ctx) {
		String accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
		
		var json = ctx.getBodyAsJson();
		var watchlist = json.mapTo(WatchList.class);
		
		watchlist.getAssets().forEach(asset->{
			var params = new HashMap<String,Object>();
			params.put("account_id", accountId);
			params.put("asset", asset.getName());
			SqlTemplate
				.forUpdate(dbPool,"insert into watchlist values (#{account_id},#{asset})")
				.execute(params)
				.onFailure(DbHandler.error(ctx, "failed to insert into wathclist"))
				.onSuccess(result->{
					if(!ctx.response().ended()) {
						ctx.response()
						.setStatusCode(HttpResponseStatus.NO_CONTENT.code())
						.end();
					}
				});
			
		});
		

	}

}
