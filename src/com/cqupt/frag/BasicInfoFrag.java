package com.cqupt.frag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable.ClassLoaderCreator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cqupt.act.R;
import com.cqupt.entity.ClassListenTable;
import com.cqupt.setting.SchoolSetting;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BasicInfoFrag extends BaseFrag {

	@ViewInject(R.id.et_lesson_name)
	EditText lessonNameEditText;
	@ViewInject(R.id.et_class_room_num)
	EditText lessonRoomNumEditText;
	@ViewInject(R.id.et_class_num)
	EditText lessonClassNumEditText;
	@ViewInject(R.id.et_class_content)
	EditText lessonContentEditText;
	@ViewInject(R.id.tv_time_Record)
	TextView lessonTimeTextView;
	@ViewInject(R.id.spinner_course_belong_to_college)
	Spinner spinnerCourseBelong;
	@ViewInject(R.id.spinner_student_belong_to_college)
	Spinner spinnerStudentBelong;
	@ViewInject(R.id.spinner_kind_of_course)
	Spinner spinnerKindOfCourse;
	ViewGroup viewGroup;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.frag_basic_infolay, null);

		ViewUtils.inject(this, viewGroup);

		initSpinnerCourseBelong();
		initSpinnerStudentBelong();
		initSpinnerKindOfCourse();
		initClassTimeBegin();
		return viewGroup;
	}

	private void initClassTimeBegin() {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		lessonTimeTextView.setText(dateFormat.format(date));

		classListenTable.class_time = dateFormat.format(date);

	}

	private void initSpinnerStudentBelong() {
		// TODO Auto-generated method stub
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item);

		final List<String> strings = SchoolSetting.getInstitutes();
		for (String str : strings) {
			arrayAdapter.add(str);
		}

		spinnerStudentBelong.setAdapter(arrayAdapter);
		spinnerStudentBelong
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						classListenTable.student_belong = strings.get(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void initSpinnerKindOfCourse() {
		// TODO Auto-generated method stub

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item);

		final List<String> strings = SchoolSetting.getkindOfCourse();
		for (String str : strings) {
			arrayAdapter.add(str);
		}

		spinnerKindOfCourse.setAdapter(arrayAdapter);

		spinnerKindOfCourse
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						classListenTable.lesson_property = strings
								.get(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void initSpinnerCourseBelong() {
		// TODO Auto-generated method stub

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item);

		final List<String> strings = SchoolSetting.getInstitutes();
		for (String str : strings) {
			arrayAdapter.add(str);
		}

		spinnerCourseBelong.setAdapter(arrayAdapter);

		spinnerCourseBelong
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub

						classListenTable.lesson_belong = strings.get(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub

		if (!TextUtils.isEmpty(lessonNameEditText.getText().toString())) {

			classListenTable.lesson_name = lessonNameEditText.getText()
					.toString();
		} else {
			return "øŒ≥Ã√˚≥∆Œ¥ÃÓ";
		}
		if (!TextUtils.isEmpty(lessonRoomNumEditText.getText().toString())) {

			classListenTable.room_num = lessonRoomNumEditText.getText()
					.toString();
		} else {
			return "ΩÃ “±‡∫≈Œ¥ÃÓ";
		}
		if (!TextUtils.isEmpty(lessonClassNumEditText.getText().toString())) {

			classListenTable.class_num = lessonClassNumEditText.getText()
					.toString();
		} else {
			return "∞‡º∂±‡∫≈Œ¥ÃÓ";
		}
		if (!TextUtils.isEmpty(lessonContentEditText.getText().toString())) {

			classListenTable.class_content = lessonContentEditText.getText()
					.toString();
		} else {
			return "Ω≤øŒƒ⁄»›Œ¥ÃÓ";
		}

		return super.collectDataInView();
	}
}
