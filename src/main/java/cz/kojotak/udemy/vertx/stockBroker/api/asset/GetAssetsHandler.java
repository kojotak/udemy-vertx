package cz.kojotak.udemy.vertx.stockBroker.api.asset;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.Asset;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

public class GetAssetsHandler implements Handler<RoutingContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetAssetsHandler.class);
	private final List<String> assets;

	public GetAssetsHandler(List<String> assets) {
		super();
		this.assets = assets;
	}

	@Override
	public void handle(RoutingContext ctx) {
		JsonArray response = new JsonArray();
		assets.stream().map(Asset::new).forEach(response::add);
		LOG.info("path {} responds with {}", ctx.pathParams(), response.encode());
		ctx.response()
		.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
		.putHeader("my-header", "my-value")
		.end(response.toBuffer());
	}

}
