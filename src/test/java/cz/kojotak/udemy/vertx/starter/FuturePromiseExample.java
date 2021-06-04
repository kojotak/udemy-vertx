package cz.kojotak.udemy.vertx.starter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

	private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

	@Test
	void promiseSuccess(Vertx vertx, VertxTestContext testContext) throws Throwable {
		Promise<String> promise = Promise.promise();
		LOG.debug("start");
		vertx.setTimer(500, id->{
			promise.complete("success");
			LOG.debug("success");
			testContext.completeNow();
		});
		LOG.debug("end");
	}
	
	@Test
	void promiseFailure(Vertx vertx, VertxTestContext testContext) throws Throwable {
		Promise<String> promise = Promise.promise();
		LOG.debug("start");
		vertx.setTimer(500, id->{
			promise.fail(new RuntimeException("failed"));
			LOG.debug("failed");
			testContext.completeNow();
		});
		LOG.debug("end");
	}
	
	@Test
	void futureSuccess(Vertx vertx, VertxTestContext testContext) throws Throwable {
		Promise<String> promise = Promise.promise();
		LOG.debug("start");
		vertx.setTimer(500, id->{
			promise.complete("success");
			LOG.debug("Timer done");
			testContext.completeNow();
		});
		
		Future<String> future = promise.future();
		future.onSuccess(res->{
			testContext.completeNow();
			LOG.debug("end with result {}", res);
		}).onFailure(testContext::failNow);
	}
	
	@Test
	void futureFailure(Vertx vertx, VertxTestContext testContext) throws Throwable {
		Promise<String> promise = Promise.promise();
		LOG.debug("start");
		vertx.setTimer(500, id->{
			promise.fail(new RuntimeException("failed!"));
			LOG.debug("Timer done");
		});
		
		Future<String> future = promise.future();
		future.onSuccess(res->{
			testContext.failNow("should not happe");
			LOG.debug("end with result {}", res);
		}).onFailure(err->{
			LOG.debug("error result {}", err);
			testContext.completeNow();
		});
	}
	
	@Test
	void futureMap(Vertx vertx, VertxTestContext testContext) throws Throwable {
		Promise<String> promise = Promise.promise();
		LOG.debug("start");
		vertx.setTimer(500, id->{
			promise.complete("success");
			LOG.debug("Timer done");
			testContext.completeNow();
		});
		
		Future<String> future = promise.future();
		future
			.map(asString-> new JsonObject().put("key", asString))
			.map(jsonObject->new JsonArray().add(jsonObject))
			.onSuccess(res->{
				testContext.completeNow();
				LOG.debug("end with result type {}", res.getClass().getSimpleName());
				assertEquals("JsonArray", res.getClass().getSimpleName());
			})
			.onFailure(testContext::failNow);
	}
	
	@Test
	void futureCoordination(Vertx vertx, VertxTestContext testContext) throws Throwable {
		vertx.createHttpServer()
			.requestHandler( req->LOG.debug("req {}", req))
			.listen(10_000)
			.compose(server->{
				LOG.info("anoter task ");
				return Future.succeededFuture(server);
			})
			.compose(server->{
				LOG.info("even more task");
				return Future.succeededFuture(server);
			})
			.onFailure(testContext::failNow)
			.onSuccess(server->{
				LOG.debug("server started on port {}", server.actualPort());
				testContext.completeNow();
			});
	}
	
	@Test
	void futureComposition(Vertx vertx, VertxTestContext testContext) throws Throwable {
		var one = Promise.<Void>promise();
		var two = Promise.<Void>promise();
		var three = Promise.<Void>promise();
		
		var futureOne = one.future();
		var futureTwo = one.future();
		var futureThree = one.future();
		
		CompositeFuture.all(futureOne, futureTwo, futureThree)
			.onFailure(testContext::failNow)
			.onSuccess(res->{
				LOG.debug("success");
				testContext.completeNow();
			});
		
		vertx.setTimer(500, id->{
			one.complete();
			two.complete();
			three.complete();
		});
			
	}
}
