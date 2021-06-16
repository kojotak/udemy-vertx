package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import cz.kojotak.udemy.vertx.stockBroker.dto.WatchList;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;
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
		final String accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
		
		var json = ctx.getBodyAsJson();
		var watchlist = json.mapTo(WatchList.class);
		
		List<Map<String,Object>> parameterBatch = watchlist.getAssets().stream()
			.map(asset->{
				Map<String, Object> params = new HashMap<>();
				params.put("account_id", accountId);
				params.put("asset", asset.getName());
				return params;
			}).collect(Collectors.toList());
		
		dbPool.withTransaction( client->{
			//1 - delete all for account_id
			return SqlTemplate.forUpdate(client, "delete * from watchlits w where w.account_id = #{account_id}")
				.execute(Collections.singletonMap("account_id", accountId))
				.onFailure(DbHandler.error(ctx, "failed to delete watchlist for account " + accountId))
				.compose(deletionDone->{
					//2 - add all for account_id
					return addAllForAccountId(client, ctx, parameterBatch);
				})
				.onFailure(DbHandler.error(ctx, "failed to update watchlist for account " + accountId))
				.onSuccess(result->{
					//3 - both succeeded
					ctx.response()
					.setStatusCode(HttpResponseStatus.NO_CONTENT.code())
					.end();
				});
		});
		
	}

	//have to reuse the same client for transaction to work
	private Future<SqlResult<Void>> addAllForAccountId(SqlConnection client, RoutingContext ctx, List<Map<String, Object>> parameterBatch) {
		return SqlTemplate
		.forUpdate(dbPool,"insert into watchlist values (#{account_id},#{asset})")
		.executeBatch(parameterBatch)
		.onFailure(DbHandler.error(ctx, "failed to insert into wathclist"))
		;
	}

}
