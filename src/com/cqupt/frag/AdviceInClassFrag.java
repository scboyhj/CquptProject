package com.cqupt.frag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import com.cqupt.act.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AdviceInClassFrag extends BaseFrag {

	@ViewInject(R.id.et_advice)
	EditText adviceEditText;
	@ViewInject(R.id.et_my_name)
	EditText nameEditText;
	@ViewInject(R.id.tv_listen_time)
	TextView timeTextView;
	LinkedHashMap<String, String> linkedHashMap;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.fag_advice_lay, null);

		ViewUtils.inject(this, viewGroup);
		linkedHashMap = listHashMaps.get(3);
		initTime();
		return viewGroup;
	}

	private void initTime() {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-mm-dd hh:mm:ss");
		String timeString = dateFormat.format(date).toString();
		timeTextView.setText(timeString);
		linkedHashMap.put("listen_time", timeString);
	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(adviceEditText.getText().toString())) {
			linkedHashMap.put("my_advice", adviceEditText.getText().toString());
		} else {
			return "你的意见未填";
		}
		if (!TextUtils.isEmpty(nameEditText.getText().toString())) {
			linkedHashMap.put("my_name", nameEditText.getText().toString());
		} else {
			return "你的名字未填";
		}
		return super.collectDataInView();
	}
}
