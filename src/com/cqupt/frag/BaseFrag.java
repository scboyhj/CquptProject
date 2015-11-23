package com.cqupt.frag;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.cqupt.act.EvaluationAct.CollectDataInViewListener;
import com.cqupt.entity.ClassListenTable;
import com.google.gson.Gson;

public class BaseFrag extends Fragment implements CollectDataInViewListener {

	private CollectDataInViewListener viewListener;
	// public static List<LinkedHashMap<String, String>> listHashMaps = new
	// ArrayList<LinkedHashMap<String, String>>();

	public static ClassListenTable classListenTable;

	static {
		// initHashMaps();
		classListenTable = new ClassListenTable();
	}

	public BaseFrag() {
		// TODO Auto-generated constructor stub
		super();

	}

	public void showSaveInMapsData() {

		Gson gson = new Gson();
		String gsonString = gson.toJson(classListenTable);

		Log.i("json_classListenTable", gsonString);

	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		return null;
	}

}
