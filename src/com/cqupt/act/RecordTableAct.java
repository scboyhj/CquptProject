package com.cqupt.act;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.entity.ClassListenTable;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RecordTableAct extends BaseAct {
	@ViewInject(R.id.rd_tv_class_name)
	TextView classNameTextView;
	@ViewInject(R.id.rd_tv_class_content)
	TextView classContentTextView;
	@ViewInject(R.id.rd_tv_lesson_belong)
	TextView lessonBelongTextView;
	@ViewInject(R.id.rd_tv_student_belong)
	TextView studentBelongTextView;
	@ViewInject(R.id.rd_tv_lesson_property)
	TextView lessonPropertyTextView;
	@ViewInject(R.id.rd_tv_room_num)
	TextView roomNumTextView;
	@ViewInject(R.id.rd_tv_class_num)
	TextView classNumTextView;
	@ViewInject(R.id.rd_tv_lesson_time)
	TextView lessonTimeTextView;

	@ViewInject(R.id.rd_tv_score_attitude)
	TextView scoreAttitudeTextView;
	@ViewInject(R.id.rd_tv_score_content)
	TextView scoreContentTextView;
	@ViewInject(R.id.rd_tv_score_method)
	TextView scoreMethodTextView;
	@ViewInject(R.id.rd_tv_score_manage)
	TextView scoreManageTextView;
	@ViewInject(R.id.rd_tv_score_result)
	TextView scoreResultTextView;
	@ViewInject(R.id.rd_tv_score_total)
	TextView scoreTotalTextView;

	@ViewInject(R.id.rd_tv_class_station)
	TextView classStationTextView;
	@ViewInject(R.id.rd_tv_is_late)
	TextView isLateTextView;
	@ViewInject(R.id.rd_tv_is_advance)
	TextView isAdvanceTextView;
	@ViewInject(R.id.rd_tv_book_hold)
	TextView bookHoldTextView;
	@ViewInject(R.id.rd_tv_should_num)
	TextView shouldNumTextView;
	@ViewInject(R.id.rd_tv_fact_num)
	TextView factNumTextView;
	@ViewInject(R.id.rd_tv_late_num)
	TextView lateNumTextView;
	@ViewInject(R.id.rd_tv_late_rate)
	TextView lateRateTextView;
	@ViewInject(R.id.rd_tv_come_rate)
	TextView comeRateTextView;
	@ViewInject(R.id.rd_tv_state_score)
	TextView stateScoreTextView;

	@ViewInject(R.id.rd_tv_my_advice)
	TextView myAdviceTextView;
	@ViewInject(R.id.rd_tv_my_name)
	TextView myNameTextView;
	@ViewInject(R.id.rd_tv_listen_time)
	TextView listenTimeTextView;
	ProgressDialog progressDialog;
	String itemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_tablelay);
		ViewUtils.inject(this);
		itemId = getIntent().getStringExtra("recordId");
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("item_id", itemId);

		progressDialog = CustomViewUtils.showProgressDialog(RecordTableAct.this);
		progressDialog.show();
		customApplication.httpConnectUtils.sendRequestByGet(
				REQUST_TYPE.GET_RECORD_ITEM, maps,
				new HttpConnectUtils.HttpListener() {

					@Override
					public void setResponseResult(String resultString) {
						// TODO Auto-generated method stub

						if (resultString == null || resultString.length() == 0) {
							CustomViewUtils.showInToast(customApplication,
									"∂¡»° ß∞‹");
						} else {
							Gson gson = new Gson();
							Log.i("RecordTableAct", resultString);
							ClassListenTable classListenTable = gson.fromJson(
									resultString, ClassListenTable.class);
							loadDateToView(classListenTable);

						}
						progressDialog.cancel();

					}
				});

	}

	protected void loadDateToView(ClassListenTable classListenTable) {
		// TODO Auto-generated method stub
		classNameTextView.setText(classListenTable.lesson_name);
		classContentTextView.setText(classListenTable.class_content);
		lessonBelongTextView.setText(classListenTable.lesson_belong);
		studentBelongTextView.setText(classListenTable.student_belong);
		lessonPropertyTextView.setText(classListenTable.lesson_property);
		roomNumTextView.setText(classListenTable.room_num);
		classNumTextView.setText(classListenTable.class_num);
		lessonTimeTextView.setText(classListenTable.class_time);

		scoreAttitudeTextView.setText(classListenTable.score_attitude);
		scoreContentTextView.setText(classListenTable.score_content);
		scoreMethodTextView.setText(classListenTable.score_method);
		scoreManageTextView.setText(classListenTable.score_manage);
		scoreResultTextView.setText(classListenTable.score_result);

		int totalScore = Integer.valueOf(classListenTable.score_attitude)
				+ Integer.valueOf(classListenTable.score_content)
				+ Integer.valueOf(classListenTable.score_method)
				+ Integer.valueOf(classListenTable.score_manage)
				+ Integer.valueOf(classListenTable.score_result);

		scoreTotalTextView.setText(totalScore + "");

		classStationTextView.setText(classListenTable.class_condition);
		isLateTextView.setText(classListenTable.is_late);
		isAdvanceTextView.setText(classListenTable.is_advance);
		bookHoldTextView.setText(classListenTable.book_hold);
		shouldNumTextView.setText(classListenTable.should_be);
		factNumTextView.setText(classListenTable.fact_be);
		lateNumTextView.setText(classListenTable.late_num);
		lateRateTextView.setText(classListenTable.late_rate);
		comeRateTextView.setText(classListenTable.comeout_rate);
		stateScoreTextView.setText(classListenTable.state_score);

		myAdviceTextView.setText(classListenTable.my_advice);
		myNameTextView.setText(classListenTable.my_name);
		listenTimeTextView.setText(classListenTable.listen_time);

	}
}
