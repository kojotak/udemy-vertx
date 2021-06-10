package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class AssetsRestApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(AssetsRestApi.class);

	static void attach(Router router) {
		router.get("/assets").handler( ctx->{
			JsonArray response = new JsonArray();
			response.add(new JsonObject().put("symbol","AAPL"));
			response.add(new JsonObject().put("symbol","TSLA"));
			response.add(new JsonObject().put("symbol","AMZN"));
			LOG.info("path {} responds with {}", ctx.pathParams(), response.encode());
			ctx.response().end(response.toBuffer());
		});
	}
}
