package com.cqupt.frag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cqupt.act.EvaluationAct.CollectDataInViewListener;
import com.cqupt.act.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFrag extends Fragment implements CollectDataInViewListener {

	private CollectDataInViewListener viewListener;
	public static List<LinkedHashMap<String, String>> listHashMaps = new ArrayList<LinkedHashMap<String, String>>();

	public BaseFrag() {
		// TODO Auto-generated constructor stub
		super();
		

		initHashMaps();

	}

	private static void initHashMaps() {
		// TODO Auto-generated method stub

		LinkedHashMap<String, String> basicInfoHashMap = new LinkedHashMap<String, String>();
		
		basicInfoHashMap.put("lesson_name", null);
		basicInfoHashMap.put("institute", null);

		// LinkedHashMap<String, String> evaluationContentHashMap = new
		// LinkedHashMap<String, String>();
		// evaluationContentHashMap.put("lesson_name", null);
		// evaluationContentHashMap.put("institute", null);
		LinkedHashMap<String, String> recordInClasstHashMap = new LinkedHashMap<String, String>();
		recordInClasstHashMap.put("should_be", null);
		// recordInClasstHashMap.put("f", null);

		listHashMaps.add(basicInfoHashMap);
		listHashMaps.add(recordInClasstHashMap);

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

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		return null;
	}

}
