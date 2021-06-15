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
		System.setProperty(ConfigLoader.DB_URL, "jdbc:h2:./target/h2/stockBroker");
		System.setProperty(ConfigLoader.DB_USER, "sa");
		System.setProperty(ConfigLoader.DB_PASS, "sa");
		vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
	}
}
