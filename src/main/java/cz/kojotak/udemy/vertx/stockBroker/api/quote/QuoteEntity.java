package cz.kojotak.udemy.vertx.stockBroker.api.quote;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import cz.kojotak.udemy.vertx.stockBroker.dto.Asset;
import io.vertx.core.json.JsonObject;

//DB entity
public class QuoteEntity {
	
	@JsonProperty("ASSET")
	private Asset asset;
	
	@JsonProperty("BID")
	private BigDecimal bid;
	
	@JsonProperty("ASK")
	private BigDecimal ask;
	
	@JsonProperty("LAST_PRICE")
	private BigDecimal lastPrice;
	
	@JsonProperty("VOLUME")
	private BigDecimal volume;

	public QuoteEntity() {}

	public JsonObject toJsonObject() {
		return JsonObject.mapFrom(this);
	}
	
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
	
}
