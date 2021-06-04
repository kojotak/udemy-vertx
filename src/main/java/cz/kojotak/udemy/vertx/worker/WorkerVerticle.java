package cz.kojotak.udemy.vertx.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class WorkerVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		LOG.debug("start {}", getClass().getName());
		startPromise.complete();
		Thread.sleep(2000);
	}
	
}
