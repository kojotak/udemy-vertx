package cz.kojotak.udemy.vertx.starter.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		LOG.debug("start " + getClass().getName());
		vertx.deployVerticle(new VerticleAA(), whenDeployed->{
			LOG.debug("deployed AA");
			vertx.undeploy(whenDeployed.result());
		});
		vertx.deployVerticle(new VerticleAB(), whenDeployed->{
			LOG.debug("deployed AB");
			vertx.undeploy(whenDeployed.result());
		});
		promise.complete();
	}

}
