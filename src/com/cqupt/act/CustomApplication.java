package com.cqupt.act;

import com.cqupt.http.HttpConnectUtils;
import com.lidroid.xutils.DbUtils;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;

public class CustomApplication extends Application {

	public String userId;
	public HttpConnectUtils httpConnectUtils;

	private static String SHARE_TABLE = "preferences_table";
	public SharedPreferences preferences_table;
	public SharedPreferences preferences_login;

	public DbUtils dbUtils;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		httpConnectUtils = new HttpConnectUtils(new Handler());
		preferences_table = this
				.getSharedPreferences(SHARE_TABLE, MODE_PRIVATE);
		preferences_login = this
				.getSharedPreferences(SHARE_TABLE, MODE_PRIVATE);
		dbUtils = dbUtils.create(this, "cqupt_project");
	}

}
