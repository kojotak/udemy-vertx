package cz.kojotak.udemy.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleB extends AbstractVerticle {

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		System.out.println("start " + getClass().getName());
		promise.complete();
	}

}
