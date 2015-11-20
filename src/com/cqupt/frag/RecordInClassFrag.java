package com.cqupt.frag;

import com.cqupt.act.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecordInClassFrag extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.frag_record_lay, null);

		return viewGroup;
	}
}
