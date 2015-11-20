package com.cqupt.act;

import com.cqupt.http.HttpConnectUtils;

import android.app.Application;
import android.os.Handler;

public class CustomApplication extends Application {

	public HttpConnectUtils httpConnectUtils;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		httpConnectUtils = new HttpConnectUtils(new Handler());
	}
}
