package cz.kojotak.udemy.vertx.starter.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(VerticleN.class);

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		LOG.debug("start " + getClass().getName() 
				+ " in " + Thread.currentThread().getName() 
				+ " with config " + config().toString());
		promise.complete();
	}

}
