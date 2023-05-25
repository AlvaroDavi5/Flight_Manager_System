package com.flightmanager.app.utils;

import java.util.HashMap;
import java.util.LinkedList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ParserUtils {
	public ParserUtils() {
	}

	public HashMap<String, Object> stringfiedJsonToHashMap(String strJSON) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(strJSON,
					new TypeReference<HashMap<String, Object>>() {
					});
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return map;
	}

	public LinkedList<HashMap<String, Object>> stringfiedObjectArrayToHashMapList(String strJSON) {
		LinkedList<HashMap<String, Object>> mapList = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapList = mapper.readValue(strJSON,
					new TypeReference<LinkedList<HashMap<String, Object>>>() {
					});
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return mapList;
	}

	public String hashMapToStringfiedJson(LinkedList<HashMap<String, Object>> mapList, Boolean pretty) {
		String strJSON = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter prettyMapper = mapper.writer().withDefaultPrettyPrinter();
			if (pretty)
				strJSON = prettyMapper.writeValueAsString(mapList);
			else
				strJSON = mapper.writeValueAsString(mapList);
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return strJSON;
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
