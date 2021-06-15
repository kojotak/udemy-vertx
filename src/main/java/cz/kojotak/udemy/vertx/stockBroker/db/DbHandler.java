package cz.kojotak.udemy.vertx.stockBroker.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.api.asset.GetAssetsFromDatabase;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DbHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(DbHandler.class);

	public static Handler<Throwable> error(RoutingContext ctx, String msg) {
		return err->{
			LOG.error("failed! ", err);
			ctx.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject()
					.put("message", msg)
					.put("path", ctx.normalisedPath())
					.toBuffer());
		};
	}
	
	public static void notFound(RoutingContext ctx, String msg) {
		ctx.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject()
				.put("message", msg)
				.put("path", ctx.normalisedPath())
				.toBuffer());
	}
}
