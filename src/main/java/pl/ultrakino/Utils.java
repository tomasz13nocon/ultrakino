package pl.ultrakino;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class Utils {

	public static String jsonError(String msg) {
//		return JsonNodeFactory.instance.objectNode().put("error", msg);
		return "{" +
				"\"error\":\"" + msg + "\"" +
				"}";
	}
}
