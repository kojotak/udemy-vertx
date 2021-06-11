package cz.kojotak.udemy.vertx.stockBroker;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;

public class AssetsRestApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(AssetsRestApi.class);
	
	public static final List<String> ASSETS = Arrays.asList("AAPL","AMZN","GOOG","MSFT","NFLX");

	static void attach(Router router) {
		router.get("/assets").handler( ctx->{
			JsonArray response = new JsonArray();
			ASSETS.stream().map(Asset::new).forEach(response::add);
			LOG.info("path {} responds with {}", ctx.pathParams(), response.encode());
			ctx.response()
				.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
				.end(response.toBuffer());
		});
	}
}
