package cz.kojotak.udemy.vertx.customCodec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PingPongExample {

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(PingPongExample.class);
		var vertx = Vertx.vertx();
		Handler<AsyncResult<String>> ar = res->{
			//always check the outcome to see any errors
			log.error("asynchronous result: {}", res.cause() );
		};
		vertx.deployVerticle(new PingVerticle(), ar);
		vertx.deployVerticle(new PongVerticle(), ar);
	}
	
	static class PingVerticle extends AbstractVerticle {

		static final String ADDRESS = PingVerticle.class.getName();
		private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			vertx.eventBus().registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
			var eventBus = vertx.eventBus();
			var msg = new Ping("hello", true);
			LOG.debug("sending {}", msg);
			eventBus.request(ADDRESS, 
					msg, 
					reply -> {
						if(reply.failed()) {
							LOG.error("failed {}", reply.cause());
						} else {
							LOG.debug("Response {}", reply.result().body());
						}
					});
			startPromise.complete();
		}
		
	}
	
	static class PongVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
			vertx.eventBus().<Pong>consumer(PingVerticle.ADDRESS, msg->{
				LOG.debug("received {}", msg.body());
				msg.reply(new Pong(0));
			}).exceptionHandler(err->{
				LOG.error("error {}", err);
			});
			startPromise.complete();
		}
	}
}
