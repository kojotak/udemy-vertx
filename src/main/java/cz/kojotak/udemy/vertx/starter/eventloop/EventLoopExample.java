package cz.kojotak.udemy.vertx.starter.eventloop;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

// rule: never block an event loop
public class EventLoopExample extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(
				new VertxOptions()
					.setMaxEventLoopExecuteTime(500)
					.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
					.setBlockedThreadCheckInterval(1)
					.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
					.setEventLoopPoolSize(4)
				);
		vertx.deployVerticle(EventLoopExample.class.getName(),
				new DeploymentOptions().setInstances(4));
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		LOG.debug("start {}", getClass().getName());
		startPromise.complete();
		Thread.sleep(5000);
	}
	
}
