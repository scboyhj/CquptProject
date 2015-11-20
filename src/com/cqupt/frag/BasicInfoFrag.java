package com.cqupt.frag;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cqupt.act.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BasicInfoFrag extends BaseFrag {

	@ViewInject(R.id.et_leson_name)
	EditText lessonNameEditText;

	ViewGroup viewGroup;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.frag_basic_infolay, null);

		// ViewUtils.inject(viewGroup);
		ViewUtils.inject(this, viewGroup);

		// lessonNameEditText=(EditText)viewGroup.findViewById(R.id.et_leson_name);
		return viewGroup;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> linkedHashMap = listHashMaps.get(0);
		if (!TextUtils.isEmpty(lessonNameEditText.getText().toString())) {
			linkedHashMap.put("lesson_name", lessonNameEditText.getText()
					.toString());
		} else {
			return "¿Î³ÌÃû³ÆÎ´Ìî";
		}

		return super.collectDataInView();
	}

}
