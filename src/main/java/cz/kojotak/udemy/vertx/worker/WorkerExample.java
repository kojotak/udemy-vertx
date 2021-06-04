package cz.kojotak.udemy.vertx.worker;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class WorkerExample extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(
				new VertxOptions()
				);
		vertx.deployVerticle(WorkerExample.class.getName(),
				new DeploymentOptions().setInstances(4));
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		LOG.debug("start {}", getClass().getName());
		
		vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions()
				.setWorker(true)
				.setWorkerPoolSize(1)
				.setWorkerPoolName("my-worker-pool"));
		
		startPromise.complete();
		vertx.executeBlocking(event->{
			LOG.debug("executing blocking code");
			try {
				Thread.sleep(2000);
				if(new Random().nextBoolean()) {
					event.complete();
				} else {
					event.fail("bad luck");
				}
			} catch (InterruptedException ex) {
				LOG.error("failed to sleep", ex);
				event.fail(ex);
			}
			
		}, result->{
			if(result.succeeded()) {
				LOG.info("blocking code finished");
			}else {
				LOG.warn("blocking code failed to:" + result.cause());
			}
		});
	}
	
}
