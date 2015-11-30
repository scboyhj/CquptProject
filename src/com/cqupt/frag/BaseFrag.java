package com.cqupt.frag;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.cqupt.act.CustomApplication;
import com.cqupt.act.EvaluationAct.CollectOrBackDataInViewListener;
import com.cqupt.entity.ClassListenTable;
import com.google.gson.Gson;

public class BaseFrag extends Fragment implements
		CollectOrBackDataInViewListener {

	// private CollectDataInViewListener viewListener;
	// public static List<LinkedHashMap<String, String>> listHashMaps = new
	// ArrayList<LinkedHashMap<String, String>>();

	public static ClassListenTable classListenTable= new ClassListenTable();
	public CustomApplication customApplication;
	public static boolean should_To_Back = false;

//	static {
//		// initHashMaps();
//		classListenTable ;
//	}

	public BaseFrag() {
		// TODO Auto-generated constructor stub
		super();
		
	}

	public void showDataInView() {
		if (should_To_Back) {
			backDataInView();
		}
	}

	public String showSaveInMapsData() {

		Gson gson = new Gson();
		String gsonString = gson.toJson(classListenTable);

		Log.i("json_classListenTable", gsonString);
		return gsonString;

	}

	@Override
	public String collectDataInView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void backDataInView() {
		// TODO Auto-generated method stub

	}

}
