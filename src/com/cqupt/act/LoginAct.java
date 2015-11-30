package com.cqupt.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.customview.CustomViewUtils.DialogCancelDealListener;
import com.cqupt.http.CustomHttpUtils;
import com.cqupt.http.EncryptByEnTool;
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
		type = REQUST_TYPE.LOGIN;
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
					"«ÎΩ´’À∫≈ªÚ√‹¬Î–≈œ¢≤π≥‰ÕÍ’˚£°");
			return;
		}

		userIdString = userIdEditText.getText().toString();
		String userPwdString = userPwdEditText.getText().toString();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", userIdString);
		// params.put("pwd", userPwdString);
		params.put("pwd", EncryptByEnTool.getEncrypt(userPwdString));
		final ProgressDialog dialog = CustomViewUtils.showProgressDialog(this,
				new DialogCancelDealListener() {

					@Override
					public void dealCancel() {
						// TODO Auto-generated method stub
						httpConnectUtils.cancelTaskByType(type);

					}
				});
		dialog.show();

		httpConnectUtils.sendRequestByPost(REQUST_TYPE.LOGIN,
				new HttpListener() {

					@Override
					public void setResponseResult(String resultString) {

						// TODO Auto-generated method stub
						dialog.cancel();
						if (resultString.equals("ok")) {

							startActivity(new Intent(LoginAct.this,
									MainAct.class));
							customApplication.userId = userIdString;
						} else if (resultString.equals("no")) {
							sendMsgShowInToastDelay("’À∫≈ªÚ√‹¬Î¥ÌŒÛ£°");
						} else if (resultString.equals("error")) {
							sendMsgShowInToastDelay("Õ¯¬Á“Ï≥££¨«Î…‘∫Û‘Ÿ ‘");
						} else {
							sendMsgShowInToastDelay("Œ¥÷™¥ÌŒÛ");
						}

					}
				}, null);

		httpConnectUtils.sendRequestByGet(REQUST_TYPE.LOGIN, params,
				new HttpListener() {

					@Override
					public void setResponseResult(String resultString) {

						// TODO Auto-generated method stub
						dialog.cancel();
						if (resultString.equals("ok")) {

							startActivity(new Intent(LoginAct.this,
									MainAct.class));
							customApplication.userId = userIdString;
						} else if (resultString.equals("no")) {
							sendMsgShowInToastDelay("’À∫≈ªÚ√‹¬Î¥ÌŒÛ£°");
						} else if (resultString.equals("error")) {
							sendMsgShowInToastDelay("Õ¯¬Á“Ï≥££¨«Î…‘∫Û‘Ÿ ‘");
						} else {
							sendMsgShowInToastDelay("Œ¥÷™¥ÌŒÛ");
						}

					}
				});
	}

	@OnClick(R.id.bt_register)
	public void Register(View v) {

	}

}
