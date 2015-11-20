package com.cqupt.http;

import java.util.Map;

import android.os.Handler;
import android.test.AndroidTestCase;
import android.util.Log;

import com.cqupt.setting.HttpSettings;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;

public class HttpConnectUtils extends AndroidTestCase {
	private CustomHttpUtils customHttpUtils;

	public interface HttpListener {
		public void setResponseResult(String resultString);
	}

	public HttpConnectUtils(Handler handler) {
		// TODO Auto-generated constructor stub
		customHttpUtils = new CustomHttpUtils(handler);
	}

	public void sendRequestByGet(REQUST_TYPE type, Map<String, String> maps,
			HttpListener httpListener) {
		String urlString = HttpSettings.getURLByType(type);
		if (maps.size() > 0) {
			urlString += "?";
		}

		for (Map.Entry<String, String> entry : maps.entrySet()) {
			String appStr = entry.getKey() + "=" + entry.getValue() + "&";
			urlString += appStr;
		}
		urlString = urlString.substring(0, urlString.length() - 1);
		Log.i("urlString", urlString);
		customHttpUtils.sendRequestServer(urlString, httpListener);

	}
}
