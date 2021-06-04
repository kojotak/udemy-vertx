package cz.kojotak.udemy.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(VerticleAB.class);

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		LOG.debug("start " + getClass().getName());
		promise.complete();
	}
	
	@Override
	public void stop(Promise<Void> stopPromise) throws Exception {
		LOG.debug("stop " + getClass().getName());
		stopPromise.complete();
	}
}
