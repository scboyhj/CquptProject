package com.cqupt.act;

import java.util.HashMap;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.entity.User;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MyInfoAct extends BaseAct {

	@ViewInject(R.id.tv_my_id)
	TextView myIdTextView;
	@ViewInject(R.id.tv_my_name)
	TextView myNameTextView;
	@ViewInject(R.id.tv_my_status)
	TextView myStatusTextView;
	@ViewInject(R.id.tv_my_submit)
	TextView mySubmitTextView;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infolay);
		ViewUtils.inject(this);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", customApplication.userId);
		progressDialog = CustomViewUtils.showProgressDialog(MyInfoAct.this);
		progressDialog.show();
		customApplication.httpConnectUtils.sendRequestByGet(
				REQUST_TYPE.GET_MY_INFO, map,
				new HttpConnectUtils.HttpListener() {

					@Override
					public void setResponseResult(String resultString) {
						// TODO Auto-generated method stub
						if (resultString == null || resultString.length() == 0) {
							CustomViewUtils.showInToast(customApplication,
									"Á¬½Ó´íÎó");
						} else {
							Log.i("MyInfoAct", resultString);
							Gson gson = new Gson();
							User user = gson.fromJson(resultString, User.class);
							loadDataToView(user);
						}
						progressDialog.cancel();
					}
				});

	}

	protected void loadDataToView(User user) {
		// TODO Auto-generated method stub
		myIdTextView.setText(user.myId);
		myNameTextView.setText(user.myName);
		myStatusTextView.setText(user.myStatus);
		mySubmitTextView.setText(user.mySubmit);
	}
}
