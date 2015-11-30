package com.cqupt.act;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.setting.HttpSettings;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseAct extends FragmentActivity {
	protected CustomApplication customApplication;
	Handler handler;

	HttpSettings.REQUST_TYPE type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		customApplication = (CustomApplication) getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					CustomViewUtils.showInToast(customApplication,
							(String) msg.obj);
				}

			}
		};

	}

	public void sendMsgShowInToastDelay(String msgString) {
		Message message = handler.obtainMessage();
		message.what = 1;
		message.obj = msgString;
		handler.sendMessageDelayed(message, 500);
	}

}
