package com.flight_manager.app.utils;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ParserUtils {
	public ParserUtils() {
	}

	public HashMap<String, Object> stringfiedJsonToHashMap(String strJSON) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(strJSON, HashMap.class);
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return map;
	}

	public String hashMapToStringfiedJson(HashMap<String, Object> map, Boolean pretty) {
		String strJSON = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter prettyMapper = mapper.writer().withDefaultPrettyPrinter();
			if (pretty)
				strJSON = prettyMapper.writeValueAsString(map);
			else
				strJSON = mapper.writeValueAsString(map);
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return strJSON;
	}
}
