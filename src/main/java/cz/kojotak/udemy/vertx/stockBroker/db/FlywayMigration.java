package cz.kojotak.udemy.vertx.stockBroker.db;

import java.util.Optional;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.cfg.DbConfig;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class FlywayMigration {
	
	private static final Logger LOG = LoggerFactory.getLogger(FlywayMigration.class);

	public static Future<Void> migrate(Vertx vertx, DbConfig cfg) {
		vertx.executeBlocking( promise->{
			execute(cfg);
			promise.complete();
		}).onFailure( err -> LOG.error("failed to migrate DB", err));
		return Future.succeededFuture();
	}

	private static void execute(DbConfig cfg) {
		Flyway flyway = Flyway.configure()
			.dataSource(cfg.getUrl(), cfg.getUser(), cfg.getPass())
			.schemas("broker")
			.defaultSchema("broker")
			.load();
		
		LOG.debug("going to migrate DB: " + cfg);
		
		var current = Optional.ofNullable(flyway.info().current());
		
		flyway.migrate();
		
		LOG.info("migration done, current: {}", current.orElse(null) );
	}

}
