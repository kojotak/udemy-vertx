package cz.kojotak.udemy.vertx.stockBroker.cfg;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ConfigLoader {
	public static String SERVER_PORT = "SERVER_PORT";
	
	static final List<String> EXPOSED_ENV_VARS=Arrays.asList(SERVER_PORT);
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

	public static Future<BrokerConfig> load(Vertx vertx){
		JsonArray exposedKeys = new JsonArray();
		EXPOSED_ENV_VARS.forEach(exposedKeys::add);
		
		LOG.debug("fetch cfg from {}", exposedKeys.encode());
		
		var envStore = new ConfigStoreOptions()
				.setType("env")
				.setConfig(new JsonObject().put("keys", exposedKeys));
		
		var propertyStore = new ConfigStoreOptions()
				.setType("sys")
				.setConfig(new JsonObject().put("cache", false));
		
		var retriever = ConfigRetriever.create(vertx,
				new ConfigRetrieverOptions()
					.addStore(envStore)
					.addStore(propertyStore));
		return retriever.getConfig().map(BrokerConfig::from);
	}
}
