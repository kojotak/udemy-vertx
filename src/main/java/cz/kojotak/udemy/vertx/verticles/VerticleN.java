package cz.kojotak.udemy.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		System.out.println("start " + getClass().getName() 
				+ " in " + Thread.currentThread().getName() 
				+ " with config " + config().toString());
		promise.complete();
	}

}
