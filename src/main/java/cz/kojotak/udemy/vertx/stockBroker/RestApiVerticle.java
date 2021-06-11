package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.api.AssetsRestApi;
import cz.kojotak.udemy.vertx.stockBroker.api.QuotesRestApi;
import cz.kojotak.udemy.vertx.stockBroker.api.WatchListRestApi;
import cz.kojotak.udemy.vertx.stockBroker.cfg.BrokerConfig;
import cz.kojotak.udemy.vertx.stockBroker.cfg.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestApiVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(RestApiVerticle.class);

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		ConfigLoader.load(vertx).onFailure(startPromise::fail).onSuccess(cfg->{
			LOG.info("retrieved cfg {}", cfg);
			startHttpServerAndAttachRoutes(startPromise, cfg);
		});
	}

	public void startHttpServerAndAttachRoutes(Promise<Void> startPromise, BrokerConfig cfg) {
		Router router = Router.router(vertx);
		router.route()
			.handler(BodyHandler.create()) //not enabled by default
			.failureHandler(failureHandler());
		AssetsRestApi.attach(router);
		QuotesRestApi.attach(router);
		WatchListRestApi.attach(router);
		
		vertx.createHttpServer()
			.requestHandler(router)
			.exceptionHandler( err-> LOG.error("http server error {}",err))
			.listen(cfg.getServerPort(), http->{
			if(http.succeeded()) {
				startPromise.complete();
				System.out.println("http started on port " + cfg.getServerPort());
			} else {
				startPromise.fail("failed to start http server");
			}
		});
	}
	
	private Handler<RoutingContext> failureHandler() {
		return h->{
			if(h.response().ended()) {
				return;
			}
			LOG.error("route error {}", h.failure());
			h.response().end(new JsonObject().put("message","Something went wrong :(").toBuffer());
		};
	}
}
