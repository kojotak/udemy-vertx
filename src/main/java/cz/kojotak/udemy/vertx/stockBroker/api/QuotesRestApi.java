package cz.kojotak.udemy.vertx.stockBroker.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.dto.Asset;
import cz.kojotak.udemy.vertx.stockBroker.dto.Quote;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.ThreadLocalRandom;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class QuotesRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(QuotesRestApi.class);

	static Map<String, Quote> cachedQuotes = new HashMap<>();
	static {
		AssetsRestApi.ASSETS.forEach( a$$ ->{
			cachedQuotes.put(a$$, randomQuote(a$$));
		});
	}
	
	public static void attach(Router parent) {
		parent.get("/quotes/:asset").handler(ctx->{
			var assetParam = ctx.pathParam("asset");
			var maybeQuote = Optional.ofNullable(cachedQuotes.get(assetParam));
			if(maybeQuote.isEmpty()) {
				ctx.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code()).end(new JsonObject()
						.put("message", "quote " + assetParam + " not available")
						.put("path", ctx.normalisedPath())
						.toBuffer());
				return;
			} 
			JsonObject response = maybeQuote.get().toJsonObject();
			ctx.response().end(response.toBuffer());
			LOG.debug("asset parameter {} and response {}", assetParam, response);
		});
	}
	private static Quote randomQuote(String name) {
		Quote quote = new Quote();
		quote.setAsset(new Asset(name));
		quote.setVolume(randomBigDecimal());
		quote.setAsk(randomBigDecimal());
		quote.setBid(randomBigDecimal());
		quote.setLastPrice(randomBigDecimal());
		return quote;
	}
	
	private static BigDecimal randomBigDecimal() {
		return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1,100));
	}
}
