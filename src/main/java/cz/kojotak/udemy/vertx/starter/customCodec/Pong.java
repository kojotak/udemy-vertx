package cz.kojotak.udemy.vertx.starter.customCodec;

public class Pong {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pong(Integer id) {
		super();
		this.id = id;
	}

	public Pong() {}

	@Override
	public String toString() {
		return "Pong [id=" + id + "]";
	}
	
}
