package cz.kojotak.udemy.vertx.starter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
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
}
