package cz.kojotak.udemy.vertx.stockBroker.api.quote;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.db.DbHandler;
import cz.kojotak.udemy.vertx.stockBroker.dto.Asset;
import cz.kojotak.udemy.vertx.stockBroker.dto.Quote;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.ThreadLocalRandom;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetQuoteHandler implements Handler<RoutingContext> {

	private static final Logger LOG = LoggerFactory.getLogger(GetQuoteHandler.class);
	
	private final Map<String, Quote> cachedQuotes = new HashMap<>();

	public GetQuoteHandler(List<String> assets) {
		super();
		assets.forEach( a$$ ->{
			cachedQuotes.put(a$$, randomQuote(a$$));
		});
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		var assetParam = ctx.pathParam("asset");
		var maybeQuote = Optional.ofNullable(cachedQuotes.get(assetParam));
		if(maybeQuote.isEmpty()) {
			DbHandler.error(ctx, "no assets found");
			return;
		} 
		JsonObject response = maybeQuote.get().toJsonObject();
		ctx.response().end(response.toBuffer());
		LOG.debug("asset parameter {} and response {}", assetParam, response);
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
