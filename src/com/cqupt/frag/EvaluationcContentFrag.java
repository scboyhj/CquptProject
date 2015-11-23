package com.cqupt.frag;

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

public class EvaluationcContentFrag extends BaseFrag {
	@ViewInject(R.id.et_content_attitude)
	EditText attitudeEditText;
	@ViewInject(R.id.et_content_content)
	EditText contentEditText;
	@ViewInject(R.id.et_content_method)
	EditText methodEditText;
	@ViewInject(R.id.et_content_manage)
	EditText manageEditText;
	@ViewInject(R.id.et_content_result)
	EditText resultEditText;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.frag_content_lay, null);

		ViewUtils.inject(this, viewGroup);

		return viewGroup;
	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(attitudeEditText.getText().toString())) {

			classListenTable.score_attitude = attitudeEditText.getText()
					.toString();

		} else {
			return "教学态度未评";
		}
		if (!TextUtils.isEmpty(contentEditText.getText().toString())) {

			classListenTable.score_content = contentEditText.getText()
					.toString();
		} else {
			return "教内容未评";
		}
		if (!TextUtils.isEmpty(methodEditText.getText().toString())) {

			classListenTable.score_method = methodEditText.getText().toString();

		} else {
			return "教学方法未评";
		}
		if (!TextUtils.isEmpty(manageEditText.getText().toString())) {

			classListenTable.score_manage = manageEditText.getText().toString();
		} else {
			return "教学管理未评";
		}
		if (!TextUtils.isEmpty(resultEditText.getText().toString())) {

			classListenTable.score_result = resultEditText.getText().toString();
		} else {
			return "教学效果未评";
		}
		return super.collectDataInView();
	}
}
