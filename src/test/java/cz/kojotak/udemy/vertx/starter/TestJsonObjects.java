package cz.kojotak.udemy.vertx.starter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonObject;

public class TestJsonObjects {

	@Test
	void jsonObjectCanBeMapper() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.put("id", 42);
		jsonObject.put("name", "Alice");
		jsonObject.put("loves", true);
		String jsonString = jsonObject.encode();
		assertTrue(jsonString.contains("Alice"));
		JsonObject decoded = new JsonObject(jsonString);
		assertEquals(jsonObject, decoded);
	}
	
	@Test
	void canMapJavaObjects() {
		Person person = new Person(1, "Alice", true);
		JsonObject alice = JsonObject.mapFrom(person);
		assertEquals(person.getId(), alice.getInteger("id"));
		assertEquals(person.getName(), alice.getString("name"));
		assertEquals(person.isLoveVertx(), alice.getBoolean("loveVertx"));
		Person person2 = alice.mapTo(Person.class);
		assertEquals(person.getId(), person2.getId());
		assertEquals(person.getName(), person2.getName());
		assertEquals(person.isLoveVertx(), person2.isLoveVertx());
	}
	
	static class Person {
		private Integer id;
		private String name;
		private boolean loveVertx;
		
		public Person() {}
		public Person(Integer id, String name, boolean loveVertx) {
			super();
			this.id = id;
			this.name = name;
			this.loveVertx = loveVertx;
		}

		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isLoveVertx() {
			return loveVertx;
		}
		public void setLobeVertx(boolean loveVertx) {
			this.loveVertx = loveVertx;
		}
		
	}
}
