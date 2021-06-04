package cz.kojotak.udemy.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		System.out.println("start " + getClass().getName());
		vertx.deployVerticle(new VerticleAA(), whenDeployed->{
			System.out.println("deployed AA");
			vertx.undeploy(whenDeployed.result());
		});
		vertx.deployVerticle(new VerticleAB(), whenDeployed->{
			System.out.println("deployed AB");
			vertx.undeploy(whenDeployed.result());
		});
		promise.complete();
	}

}
