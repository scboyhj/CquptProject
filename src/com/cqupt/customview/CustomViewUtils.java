package com.cqupt.customview;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class CustomViewUtils {
	public static void showInToast(Context context, String str) {
		Toast.makeText(context, str, 1000).show();
	}

	public static ProgressDialog showProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		// progressDialog.setMessage("正在加载中...");
		return progressDialog;
	}

}
