package cz.kojotak.udemy.vertx.stockBroker.cfg;

import io.vertx.core.json.JsonObject;

public class BrokerConfig {
	
	int serverPort;
	
	public static BrokerConfig from (JsonObject json) {
		Integer port = json.getInteger(ConfigLoader.SERVER_PORT);
		if(port==null) {
			throw new RuntimeException("missing configuration for " + ConfigLoader.SERVER_PORT);
		}
		BrokerConfig cfg = new BrokerConfig();
		cfg.setServerPort(port);
		return cfg;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public BrokerConfig(int serverPort) {
		super();
		this.serverPort = serverPort;
	}
	
	public BrokerConfig() {}

	@Override
	public String toString() {
		return "BrokerConfig [serverPort=" + serverPort + "]";
	}
	
}
