package app.utils;

import java.util.HashMap;

public class ParserUtils {
	public ParserUtils() {
	}

	public HashMap<String, Object> stringToHashMap(String str) {
		String values = str.replace("{", "").replace("}", "");
		String[] pairs = values.split(", ");
		HashMap<String, Object> messageContent = new HashMap<String, Object>();

		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");
			messageContent.put(keyValue[0], keyValue[1]);
		}

		return messageContent;
	}
}
