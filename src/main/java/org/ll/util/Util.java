package org.ll.util;

import com.google.gson.Gson;

public class Util {

	private static Gson gson = new Gson();

	public static <T> Object convert(Object fromObj, Class<T> toClass) {
		return gson.fromJson(gson.toJson(fromObj), toClass);
	}

	public static String encodeToHex(String string) {
		return string;
	}
	
	
}
