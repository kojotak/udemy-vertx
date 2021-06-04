package cz.kojotak.udemy.vertx.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PointToPointExample {

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new Sender());
		vertx.deployVerticle(new Receiver());
	}
	
	static class Sender extends AbstractVerticle {

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.setPeriodic(1000, id->{
				//send every second...
				vertx.eventBus().send(Sender.class.getName(),"sending message...");
			});
		}
		
	}
	
	static class Receiver extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);
	
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.eventBus().consumer(Sender.class.getName(),msg->{
				LOG.debug("received {}", msg.body());
			});
		}
		
	}
}
