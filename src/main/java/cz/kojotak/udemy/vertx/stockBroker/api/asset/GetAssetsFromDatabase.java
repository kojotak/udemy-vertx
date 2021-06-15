package cz.kojotak.udemy.vertx.stockBroker.api.asset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.Asset;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Query;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

public class GetAssetsFromDatabase implements Handler<RoutingContext> {

	private static final Logger LOG = LoggerFactory.getLogger(GetAssetsFromDatabase.class);
	private final JDBCPool dbPool;
	
	public GetAssetsFromDatabase(JDBCPool dbPool) {
		this.dbPool = dbPool;
	}

	@Override
	public void handle(RoutingContext ctx) {
		dbPool
			.query("SELECT a.value from broker.assets a")
			.execute()
			.onFailure( err->{
				LOG.error("failed! ", err);
				ctx.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject()
						.put("message", "failed to get assets from db ")
						.put("path", ctx.normalisedPath())
						.toBuffer());
				
			})
			.onSuccess(res->{
				var response = new JsonArray();
				res.forEach(row->{
					response.add(row.getValue("value"));
				});
				LOG.info("path {} responds with {}", ctx.pathParams(), response.encode());
				ctx.response()
				.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
				.end(response.toBuffer());
			});

	}

}
