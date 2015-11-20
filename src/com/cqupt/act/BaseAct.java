package com.cqupt.act;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseAct extends FragmentActivity {
	protected CustomApplication customApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		customApplication = (CustomApplication) getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}
