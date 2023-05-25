package com.flightmanager.app.utils;

import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParserUtilsTest {
	private ParserUtils utils = new ParserUtils();

	@Test
	public void testStringfiedJsonToHashMap() {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("name", "Alvaro");
		obj.put("age", 20);
		LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();
		list.add(0, obj);
		list.add(0, obj);

		assertEquals(obj, this.utils.stringfiedJsonToHashMap("{\"name\":\"Alvaro\",\"age\":20}"));
		assertEquals(list, this.utils
				.stringfiedObjectArrayToHashMapList("[{\"name\":\"Alvaro\",\"age\":20},{\"name\":\"Alvaro\",\"age\":20}]"));
	}

	@Test
	public void testHashMapToStringfiedJson() {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("name", "Alvaro");
		obj.put("age", 20);
		LinkedList<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();
		list.add(0, obj);
		list.add(0, obj);

		assertEquals("{\"name\":\"Alvaro\",\"age\":20}", this.utils.hashMapToStringfiedJson(obj, false));
		assertEquals("[{\"name\":\"Alvaro\",\"age\":20},{\"name\":\"Alvaro\",\"age\":20}]", this.utils
				.hashMapToStringfiedJson(list, false));
	}
}
