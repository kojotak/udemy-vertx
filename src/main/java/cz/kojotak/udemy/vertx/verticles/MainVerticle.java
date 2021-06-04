package cz.kojotak.udemy.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

	public static void main(String[] args) {
		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		System.out.println("start " + getClass().getName());
		vertx.deployVerticle(new VerticleA());
		vertx.deployVerticle(new VerticleB());
		vertx.deployVerticle(VerticleN.class.getName(),
				new DeploymentOptions().setInstances(4)); //let vertx create all instances
		promise.complete();
	}

}
