package com.cqupt.setting;

public final class HttpSettings {

	public enum REQUST_TYPE {
		LOGIN
	}

	public static String LOCAL_NAME = "http://www.cqupttest";

	public static String LOGIN = "/login";

	public static String getURLByType(REQUST_TYPE type) {

		switch (type) {
		case LOGIN:
			return LOCAL_NAME + LOGIN;

		default:
			return "";
		}

	}
}
