package com.cqupt.frag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cqupt.act.EvaluationAct.CollectDataInViewListener;
import com.cqupt.act.R;
import com.cqupt.setting.SchoolSetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFrag extends Fragment implements CollectDataInViewListener {

	private CollectDataInViewListener viewListener;
	public static List<LinkedHashMap<String, String>> listHashMaps = new ArrayList<LinkedHashMap<String, String>>();

	static {
		initHashMaps();
	}

	public BaseFrag() {
		// TODO Auto-generated constructor stub
		super();

	}

	private static void initHashMaps() {
		// TODO Auto-generated method stub

		LinkedHashMap<String, String> basicInfoHashMap = new LinkedHashMap<String, String>();

		basicInfoHashMap.put("lesson_belong", SchoolSetting.getInstitutes()
				.get(0));
		basicInfoHashMap.put("student_belong", SchoolSetting.getInstitutes()
				.get(0));
		basicInfoHashMap.put("lesson_property", SchoolSetting.getkindOfCourse()
				.get(0));
		basicInfoHashMap.put("lesson_name", null);
		basicInfoHashMap.put("room_num", null);
		basicInfoHashMap.put("class_num", null);
		basicInfoHashMap.put("class_content", null);
		basicInfoHashMap.put("class_time", null);
		listHashMaps.add(basicInfoHashMap);

		LinkedHashMap<String, String> evaluationContentHashMap = new LinkedHashMap<String, String>();

		evaluationContentHashMap.put("score_attitude", null);
		evaluationContentHashMap.put("score_content", null);
		evaluationContentHashMap.put("score_method", null);
		evaluationContentHashMap.put("score_manage", null);
		evaluationContentHashMap.put("score_result", null);

		listHashMaps.add(evaluationContentHashMap);

		LinkedHashMap<String, String> recordInClassHashMap = new LinkedHashMap<String, String>();

		recordInClassHashMap.put("is_late", null);
		recordInClassHashMap.put("is_advance", null);
		recordInClassHashMap.put("book_hold", null);

		recordInClassHashMap.put("should_be", null);
		recordInClassHashMap.put("fact_be", null);
		recordInClassHashMap.put("late_num", null);
		recordInClassHashMap.put("late_rate", null);
		recordInClassHashMap.put("comeout_rate", null);
		recordInClassHashMap.put("class_condition", null);
		recordInClassHashMap.put("state_score", null);

		listHashMaps.add(recordInClassHashMap);

	}

	public boolean checkAllMaps() {

		for (int i = 0; i < listHashMaps.size(); i++) {
			LinkedHashMap<String, String> linkedHashMap = listHashMaps.get(i);

			for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
				if (entry.getValue() == null) {
					return false;
				}
			}

		}
		return true;

	}

	public void showSaveInMapsData() {
		for (int i = 0; i < listHashMaps.size(); i++) {
			LinkedHashMap<String, String> hashMap = listHashMaps.get(i);
			Log.i("map-" + i + "-", hashMap.toString());
		}
	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		return null;
	}

}
