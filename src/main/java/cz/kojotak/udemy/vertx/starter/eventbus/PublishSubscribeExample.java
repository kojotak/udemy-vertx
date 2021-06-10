package cz.kojotak.udemy.vertx.starter.eventbus;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PublishSubscribeExample {

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new Publish());
		vertx.deployVerticle(new Subscriber1());
		vertx.deployVerticle(new Subscriber2());
	}
	
	static class Publish extends AbstractVerticle {

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id->{
				vertx.eventBus().publish(Publish.class.getName(), "msg for everyone");
			});
		}
		
	}
	
	public static class Subscriber1 extends Subscriber{}
	public static class Subscriber2 extends Subscriber{}
	
	abstract static class Subscriber extends AbstractVerticle {
		
		private final Logger LOG = LoggerFactory.getLogger(getClass());
	
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			vertx.eventBus().<String>consumer(Publish.class.getName(), msg->{
				LOG.debug("received {}", msg.body());
			});
			startPromise.complete();
		}
		
	}
}
