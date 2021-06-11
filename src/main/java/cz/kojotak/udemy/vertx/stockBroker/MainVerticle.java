package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

	public static final int PORT = 8888;
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.exceptionHandler(err->{
			LOG.error("unhandled error {}", err);
		});
		vertx.deployVerticle(new MainVerticle(), ar->{
			if(ar.failed()) {
				LOG.error("failed to deploy {}",ar.cause());
			} else {
				LOG.info("sucessfully deployed {}", MainVerticle.class.getName());
			}
		});
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
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
			.listen(PORT, http->{
			if(http.succeeded()) {
				startPromise.complete();
				System.out.println("http started");
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
