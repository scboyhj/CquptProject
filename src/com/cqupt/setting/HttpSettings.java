package com.cqupt.setting;

public final class HttpSettings {

	public enum REQUST_TYPE {
		LOGIN, SEND_TABLE, GET_RECORD_ITEMS, GET_MY_INFO
	}

	public static String LOCAL_NAME = "http://www.cqupttest";

	public static String LOGIN = "/login";
	public static String SEND_TABLE = "/send_table";
	public static String GET_RECORD_ITEMS = "/get_record_items";

	public static String GET_MY_INFO = "/get_my_info";

	public static String getURLByType(REQUST_TYPE type) {

		switch (type) {
		case LOGIN:
			return LOCAL_NAME + LOGIN;
		case SEND_TABLE:
			return LOCAL_NAME + SEND_TABLE;
		case GET_RECORD_ITEMS:
			return LOCAL_NAME + GET_RECORD_ITEMS;
		case GET_MY_INFO:
			return LOCAL_NAME + GET_MY_INFO;
		default:
			return "";
		}

	}
}
