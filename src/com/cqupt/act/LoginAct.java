package com.cqupt.act;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.http.CustomHttpUtils;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.http.HttpConnectUtils.HttpListener;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LoginAct extends BaseAct {

	@ViewInject(R.id.et_userid)
	EditText userIdEditText;
	@ViewInject(R.id.et_userpwd)
	EditText userPwdEditText;
	HttpConnectUtils httpConnectUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlay);
		ViewUtils.inject(this);
		httpConnectUtils = customApplication.httpConnectUtils;
	}

	private boolean isEmptyInEdit() {

		return TextUtils.isEmpty(userIdEditText.getText().toString())
				&& TextUtils.isEmpty(userPwdEditText.getText().toString());
	}

	String userIdString;

	@OnClick(R.id.bt_login)
	public void Login(View v) {

		if (isEmptyInEdit()) {
			CustomViewUtils.showInToast(getApplicationContext(),
					"请将账号或密码信息补充完整！");
			return;
		}

		userIdString = userIdEditText.getText().toString();
		String userPwdString = userPwdEditText.getText().toString();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", userIdString);
		params.put("pwd", userPwdString);
		final ProgressDialog dialog = CustomViewUtils.showProgressDialog(this);
		dialog.show();

		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// dialog.cancel();
		// startActivity(new Intent(LoginAct.this, MainAct.class));
		//
		// }
		// }, 500);

		httpConnectUtils.sendRequestByGet(REQUST_TYPE.LOGIN, params,
				new HttpListener() {

					@Override
					public void setResponseResult(String resultString) {
						dialog.cancel();
						// TODO Auto-generated method stub
						if (resultString.equals("ok")) {

							startActivity(new Intent(LoginAct.this,
									MainAct.class));
							customApplication.userId = userIdString;
						} else if (resultString.equals("no")) {

						} else {
							CustomViewUtils.showInToast(
									getApplicationContext(), "未知错误！"
											+ resultString);
						}

					}
				});
	}

	@OnClick(R.id.bt_register)
	public void Register(View v) {

	}

}
