package cz.kojotak.udemy.vertx.stockBroker.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.vertx.core.json.JsonObject;

public class WatchList {

	private List<Asset> assets = Collections.emptyList();

	public WatchList() {}
	public WatchList(Asset ... assets) {
		this();
		setAssets(Arrays.asList(assets));
	}
	
	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
	public JsonObject toJsonObject() {
		return JsonObject.mapFrom(this);
	}
}
