package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.cfg.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VersionInfoVerticle extends AbstractVerticle {
	
	private static final Logger LOG = LoggerFactory.getLogger(VersionInfoVerticle.class);

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		ConfigLoader
			.load(vertx)
			.onFailure(startPromise::fail)
			.onSuccess(cfg->{
				LOG.info("retrieved version from cfg {}", cfg.getVersion());
				startPromise.complete();
			});
	}
}
