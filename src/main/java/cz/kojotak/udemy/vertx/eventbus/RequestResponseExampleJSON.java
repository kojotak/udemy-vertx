package cz.kojotak.udemy.vertx.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class RequestResponseExampleJSON {

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new RequestVerticle());
		vertx.deployVerticle(new ResponseVerticle());
	}
	
	static class RequestVerticle extends AbstractVerticle {

		static final String ADDRESS = "my.request.address";
		private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);

		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			var eventBus = vertx.eventBus();
			var msg = new JsonObject()
					.put("msg","Hello world")
					.put("version", 1)
					;
			LOG.debug("sending {}", msg);
			eventBus.request(ADDRESS, //advice: use id of the sender
					msg, //what to send
					reply -> {
						LOG.debug("Response {}", reply.result().body());
					});
		}
		
	}
	
	static class ResponseVerticle extends AbstractVerticle {
		
		private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);
		
		@Override
		public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
			vertx.eventBus().consumer(RequestVerticle.ADDRESS, msg->{
				LOG.debug("received {}", msg.body());
				msg.reply(new JsonArray().add("one").add("two").add("three"));
			});
		}
	}
}
