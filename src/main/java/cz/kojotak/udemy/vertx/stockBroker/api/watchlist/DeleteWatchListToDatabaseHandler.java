package cz.kojotak.udemy.vertx.stockBroker.api.watchlist;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.templates.SqlTemplate;

public class DeleteWatchListToDatabaseHandler implements Handler<RoutingContext> {
	private static final Logger LOG = LoggerFactory.getLogger(DeleteWatchListToDatabaseHandler.class);
	private final JDBCPool dbPool;
	
	public DeleteWatchListToDatabaseHandler(JDBCPool dbPool) {
		super();
		this.dbPool = dbPool;
	}
	@Override
	public void handle(RoutingContext ctx) {
		String accountId = ctx.pathParam("accountId");
		LOG.debug("{} for account {}", ctx.normalisedPath(), accountId);
		
		SqlTemplate
			.forUpdate(dbPool, "delete from watchlist where account_id=#{account_id}")
			.execute(Collections.singletonMap("account_id", accountId))
			.onFailure(DbHandler.error(ctx, "failed to delete watchlist for account "+accountId))
			.onSuccess(result->{
				LOG.debug("deleted {} rows from account {}", result.rowCount(), accountId);
				ctx.response()
					.setStatusCode(HttpResponseStatus.NO_CONTENT.code())
					.end();
			});
		
	}

}
