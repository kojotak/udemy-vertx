package cz.kojotak.udemy.vertx.stockBroker;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.internal.ThreadLocalRandom;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class QuotesRestApi {

	private static final Logger LOG = LoggerFactory.getLogger(QuotesRestApi.class);

	public static void attach(Router parent) {
		parent.get("/quotes/:asset").handler(ctx->{
			var assetParam = ctx.pathParam("asset");
			JsonObject response = randomQuote(assetParam).toJsonObject();
			LOG.debug("asset parameter {} and response {}", assetParam, response);
			ctx.response().end(response.toBuffer());
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
