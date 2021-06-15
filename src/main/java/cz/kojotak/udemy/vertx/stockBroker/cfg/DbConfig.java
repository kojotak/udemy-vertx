package cz.kojotak.udemy.vertx.stockBroker.cfg;

public class DbConfig {

	String url;
	String user;
	String pass;
	String schema;

	public DbConfig() {	}
	
	public DbConfig(String url, String user, String pass, String schema) {
		super();
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.schema = schema;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	//ommit password from toString
	@Override
	public String toString() {
		return "DbConfig [url=" + url + ", user=" + user + ", schema=" + schema + "]";
	}
}
