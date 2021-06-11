package cz.kojotak.udemy.vertx.stockBroker.cfg;

import io.vertx.core.json.JsonObject;

public class BrokerConfig {
	
	public static final int DEFAULT_PORT = 8888;

	int serverPort;
	
	public static BrokerConfig from (JsonObject json) {
		Integer port = json.getInteger(ConfigLoader.SERVER_PORT);
		if(port==null) {
			port = DEFAULT_PORT;
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
}
