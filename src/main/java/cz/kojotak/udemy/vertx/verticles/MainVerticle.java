package cz.kojotak.udemy.vertx.verticles;

import java.util.UUID;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MainVerticle extends AbstractVerticle {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public static void main(String[] args) {
		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

	@Override
	public void start(final Promise<Void> promise) throws Exception {
		LOG.debug("start " + getClass().getName());
		vertx.deployVerticle(new VerticleA());
		vertx.deployVerticle(new VerticleB());
		vertx.deployVerticle(VerticleN.class.getName(),
				new DeploymentOptions()
				.setInstances(4) //let vertx create all instances
				.setConfig(new JsonObject()
						.put("id", UUID.randomUUID().toString())
						.put("name", VerticleN.class.getSimpleName())
						)
				); 
		promise.complete();
	}

}
