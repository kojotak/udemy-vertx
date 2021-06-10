package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

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
		vertx.createHttpServer().requestHandler(req->{
			req.response()
			   .putHeader("content-type", "text/plain")
			   .end("hello from vertx web server");
		}).listen(8888, http->{
			if(http.succeeded()) {
				startPromise.complete();
				System.out.println("http started");
			} else {
				startPromise.fail("failed to start http server");
			}
		});
	}

}
