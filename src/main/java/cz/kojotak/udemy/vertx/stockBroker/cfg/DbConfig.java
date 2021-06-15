package cz.kojotak.udemy.vertx.stockBroker.cfg;

public class DbConfig {

	String url;
	String user;
	String pass;

	public DbConfig() {}
	
	public DbConfig(String url, String user, String pass) {
		super();
		this.url = url;
		this.user = user;
		this.pass = pass;
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

	@Override
	public String toString() {
		return "DbConfig [url=" + url + ", user=" + user + ", pass=***]";
	}
	
}
