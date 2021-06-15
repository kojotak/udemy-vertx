package cz.kojotak.udemy.vertx.stockBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kojotak.udemy.vertx.stockBroker.cfg.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public static void main(String[] args) throws Exception {
		var vertx = Vertx.vertx();
		vertx.exceptionHandler(err->{
			LOG.error("unhandled error {}", err);
		});
		vertx.deployVerticle(new MainVerticle(), ar->{
			if(ar.failed()) {
				LOG.error("failed to deploy {}",ar.cause());
			} else {
				LOG.info("sucessfully deployed {}", MainVerticle.class.getName());
			}
		});
	}

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.deployVerticle(VersionInfoVerticle.class.getName())
			.onFailure(startPromise::fail)
			.onSuccess(id->LOG.info("deployed {} with id {}", RestApiVerticle.class.getSimpleName(), id))
			.compose(next->migrateDatabase())
			.onFailure(startPromise::fail)
			.onSuccess(id-> LOG.info("migrated DB to latest version"))
			.compose(next->deployRestApiVerticle(startPromise)
			);
		
	}
	
	private Future<Void> migrateDatabase() {
		return ConfigLoader.load(vertx)
			.compose(cfg->{
				return FlywayMigration.migrate(vertx, cfg.getDbConfig());
			});
	}

	private Future<String> deployRestApiVerticle(Promise<Void> startPromise){
		return vertx.deployVerticle(RestApiVerticle.class.getName(),
				new DeploymentOptions()
				.setInstances(processors())
				)
		.onFailure(startPromise::fail)
		.onSuccess(id->{
			LOG.info("deployed {} with id {}", RestApiVerticle.class.getSimpleName(), id);
			startPromise.complete();
		});		
	}

	private int processors() {
		return Math.max(1,Runtime.getRuntime().availableProcessors());
	}

}
