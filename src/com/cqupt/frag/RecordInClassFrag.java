package com.cqupt.frag;

import java.util.LinkedHashMap;

import com.cqupt.act.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RecordInClassFrag extends BaseFrag {

	@ViewInject(R.id.rg_islate)
	RadioGroup isLateRadioGroup;
	@ViewInject(R.id.rg_isadvance)
	RadioGroup isAdvanceRadioGroup;
	@ViewInject(R.id.rg_book_hold)
	RadioGroup bookHoldRadioGroup;

	@ViewInject(R.id.et_should_be_num)
	EditText shouldBeNumEditText;

	@ViewInject(R.id.et_fact_be_num)
	EditText factBeNumEditText;

	@ViewInject(R.id.et_late_num)
	EditText lateNumEditText;

	@ViewInject(R.id.et_late_rate)
	EditText lateRateEditText;

	@ViewInject(R.id.et_comeout_rate)
	EditText comeOutEditText;

	@ViewInject(R.id.et_class_condition)
	EditText classConditionEditText;

	@ViewInject(R.id.et_state_score)
	EditText stateScoreEditText;

	int[] isAdvanceIds = { R.id.rb_advance_yes, R.id.rb_advance_no };

	int[] isLateIds = { R.id.rb_late_yes, R.id.rb_late_no };

	int[] bookHoldIds = { R.id.rb_quality_high, R.id.rb_quality_middle,
			R.id.rb_quality_low };

	@Override
	public void backDataInView() {

		// isAdvanceRadioGroup.getChildAt(
		// parseToPosition(classListenTable.getIs_advance())).setSelected(
		// true);
		// isLateRadioGroup.getChildAt(
		// parseToPosition(classListenTable.getIs_late())).setSelected(
		// true);
		//
		// bookHoldRadioGroup.getChildAt(
		// parseToPosition(classListenTable.getBook_hold())).setSelected(
		// true);

		isAdvanceRadioGroup.check(isAdvanceIds[parseToPosition(classListenTable
				.getIs_advance())]);
		isLateRadioGroup.check(isLateIds[parseToPosition(classListenTable
				.getIs_late())]);
		bookHoldRadioGroup
				.check(bookHoldIds[parseBookHoldToInt(classListenTable
						.getBook_hold())]);

		shouldBeNumEditText.setText(classListenTable.getShould_be());
		factBeNumEditText.setText(classListenTable.getFact_be());
		lateNumEditText.setText(classListenTable.getLate_num());
		lateRateEditText.setText(classListenTable.getLate_rate());
		comeOutEditText.setText(classListenTable.getComeout_rate());
		classConditionEditText.setText(classListenTable.getClass_condition());
		stateScoreEditText.setText(classListenTable.getState_score());

	}

	public int parseBookHoldToInt(String str) {
		if (str.equals("high")) {
			return 0;
		} else if (str.equals("middle")) {
			return 1;
		} else if (str.equals("low")) {
			return 2;
		}
		return 0;

	}

	public int parseToPosition(String str) {
		if (str.equals("yes")) {
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.frag_record_lay, null);

		ViewUtils.inject(this, viewGroup);

		initRadioGroup();
		showDataInView();
		return viewGroup;
	}

	private void initRadioGroup() {
		// TODO Auto-generated method stub
		isLateRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.rb_late_no) {

							classListenTable.is_late = "no";

						} else if (checkedId == R.id.rb_late_yes) {

							classListenTable.is_late = "yes";
						}

					}
				});
		isAdvanceRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.rb_advance_no) {
							classListenTable.is_advance = "no";
						} else if (checkedId == R.id.rb_advance_yes) {
							classListenTable.is_advance = "yes";
						}

					}
				});
		bookHoldRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.rb_quality_high) {

							classListenTable.book_hold = "high";
						} else if (checkedId == R.id.rb_quality_middle) {

							classListenTable.book_hold = "middle";
						} else if (checkedId == R.id.rb_quality_low) {

							classListenTable.book_hold = "low";
						}

					}
				});
	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		Log.i("isLateRadioGroup.getCheckedRadioButtonId()",
				isLateRadioGroup.getCheckedRadioButtonId() + "");
		if (isLateRadioGroup.getCheckedRadioButtonId() <= 0) {
			return "教师是否迟到未填";
		}
		if (isAdvanceRadioGroup.getCheckedRadioButtonId() <= 0) {
			return "教师是否提前下课未填";
		}
		if (bookHoldRadioGroup.getCheckedRadioButtonId() <= 0) {
			return "教材持有率未填";
		}

		if (!TextUtils.isEmpty(shouldBeNumEditText.getText().toString())) {

			classListenTable.should_be = shouldBeNumEditText.getText()
					.toString();

		} else {
			return "应到人数未填";
		}
		if (!TextUtils.isEmpty(factBeNumEditText.getText().toString())) {

			classListenTable.fact_be = factBeNumEditText.getText().toString();
		} else {
			return "实到人数未填";
		}
		if (!TextUtils.isEmpty(lateNumEditText.getText().toString())) {

			classListenTable.late_num = lateNumEditText.getText().toString();

		} else {
			return "迟到人数未填";
		}
		if (!TextUtils.isEmpty(lateRateEditText.getText().toString())) {

			classListenTable.late_rate = lateRateEditText.getText().toString();

		} else {
			return "迟到率未填";
		}
		if (!TextUtils.isEmpty(comeOutEditText.getText().toString())) {

			classListenTable.comeout_rate = comeOutEditText.getText()
					.toString();
		} else {
			return "出勤率未填";
		}
		if (!TextUtils.isEmpty(classConditionEditText.getText().toString())) {

			classListenTable.class_condition = classConditionEditText.getText()
					.toString();
		} else {
			return "课堂情况未填";
		}
		if (!TextUtils.isEmpty(stateScoreEditText.getText().toString())) {

			classListenTable.state_score = stateScoreEditText.getText()
					.toString();
		} else {
			return "状态评分未填";
		}
		return super.collectDataInView();
	}
}
