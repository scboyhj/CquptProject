package com.cqupt.frag;

import com.cqupt.act.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdviceInClassFrag extends BaseFrag {
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(
				R.layout.fag_advice_lay, null);

		return viewGroup;
	}
}
