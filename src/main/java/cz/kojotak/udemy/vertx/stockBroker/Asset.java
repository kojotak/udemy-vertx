package cz.kojotak.udemy.vertx.stockBroker;

public class Asset {
	private final String name;

	public Asset(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
