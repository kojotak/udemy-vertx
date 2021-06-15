package cz.kojotak.udemy.vertx.stockBroker.cfg;

import java.util.Objects;

import io.vertx.core.json.JsonObject;

public class BrokerConfig {
	
	private int serverPort;
	private String version;
	private DbConfig dbConfig;
	
	public static BrokerConfig from (JsonObject json) {
		Integer port = json.getInteger(ConfigLoader.SERVER_PORT);
		if(port==null) {
			throw new RuntimeException("missing configuration for " + ConfigLoader.SERVER_PORT);
		}
		String version = json.getString("version");
		if(Objects.isNull(version)) {
			throw new RuntimeException("missing version configuration from file" );
		}
		BrokerConfig cfg = new BrokerConfig();
		cfg.setServerPort(port);
		cfg.setVersion(version);
		cfg.setDbConfig(parseDbConfig(json));
		return cfg;
	}

	private static DbConfig parseDbConfig(JsonObject json) {
		DbConfig db = new DbConfig();
		db.setUrl(json.getString(ConfigLoader.DB_URL));
		db.setUser(json.getString(ConfigLoader.DB_USER));
		db.setPass(json.getString(ConfigLoader.DB_PASS));
		db.setSchema(json.getString(ConfigLoader.DB_SCHEMA));
		return db;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BrokerConfig() {}

	@Override
	public String toString() {
		return "BrokerConfig [serverPort=" + serverPort + ", version=" + version + "]";
	}

	public DbConfig getDbConfig() {
		return dbConfig;
	}
	public void setDbConfig(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}	
}
