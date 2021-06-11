package cz.kojotak.udemy.vertx.stockBroker;

import org.junit.jupiter.api.BeforeEach;

import cz.kojotak.udemy.vertx.stockBroker.cfg.ConfigLoader;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;

public class AbstractRestApiTest {
	protected static int TEST_PORT = 9999;

	@BeforeEach
	void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
		System.setProperty(ConfigLoader.SERVER_PORT, "" + TEST_PORT);
		vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
	}
}
