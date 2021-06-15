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
		cfg.setDbConfig(new DbConfig("jdbc:h2:~/stockBroker", "sa", "sa"));
		return cfg;
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
