package com.cqupt.setting;

public final class HttpSettings {

	public enum REQUST_TYPE {
		LOGIN, SEND_TABLE, GET_ITEM
	}

	public static String LOCAL_NAME = "http://www.cqupttest";

	public static String LOGIN = "/login";
	public static String SEND_TABLE = "/send_table";
	public static String GET_ITEM = "/get_item";

	public static String getURLByType(REQUST_TYPE type) {

		switch (type) {
		case LOGIN:
			return LOCAL_NAME + LOGIN;
		case SEND_TABLE:
			return LOCAL_NAME + SEND_TABLE;
		case GET_ITEM:
			return LOCAL_NAME + GET_ITEM;
		default:
			return "";
		}

	}
}
