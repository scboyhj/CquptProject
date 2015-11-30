package com.cqupt.customview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.widget.Toast;

public class CustomViewUtils {

	public interface DialogCancelDealListener {
		public void dealCancel();
	}

	public static void showInToast(Context context, String str) {
		Toast.makeText(context, str, 1000).show();
	}

	public static ProgressDialog showProgressDialog(Context context,
			final DialogCancelDealListener cancelDealListener) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
			
				cancelDealListener.dealCancel();
			}
		});
		// progressDialog.setMessage("正在加载中...");
		return progressDialog;
	}

}
