package cz.kojotak.udemy.vertx.stockBroker.dto;

import java.math.BigDecimal;

import io.vertx.core.json.JsonObject;

public class Quote {
	private Asset asset;
	private BigDecimal bid;
	private BigDecimal ask;
	private BigDecimal lastPrice;
	private BigDecimal volume;

	public Quote() {}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
	public BigDecimal getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public JsonObject toJsonObject() {
		return JsonObject.mapFrom(this);
	}
	
}
