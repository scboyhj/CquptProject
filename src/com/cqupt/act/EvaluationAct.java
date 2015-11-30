package com.cqupt.act;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.customview.CustomViewUtils.DialogCancelDealListener;
import com.cqupt.entity.ClassListenTable;
import com.cqupt.frag.AdviceInClassFrag;
import com.cqupt.frag.BaseFrag;
import com.cqupt.frag.BasicInfoFrag;
import com.cqupt.frag.EvaluationcContentFrag;
import com.cqupt.frag.RecordInClassFrag;
import com.cqupt.http.HttpConnectUtils.HttpListener;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EvaluationAct extends BaseAct {

	@ViewInject(R.id.pager_evaluation)
	private ViewPager viewPager;
	@ViewInject(R.id.radiogroup_evalue)
	private RadioGroup radioGroup;

	@ViewInject(R.id.buttom_line)
	private FrameLayout buttomframeLayout;

	private List<Fragment> fragments;

	private int[] radioIds;

	private int moveWidth;

	private int beforePageIndex = 0;
	private int currentPageIndex = 0;
	private View buttomMoveView;

	SharedPreferences preferences;

	private DbUtils dbUtils;

	ProgressDialog progressDialog;

	public interface CollectOrBackDataInViewListener {

		public String collectDataInView();

		public void backDataInView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluationlay);
		type = REQUST_TYPE.SEND_TABLE;
		ViewUtils.inject(this);

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		moveWidth = screenWidth / 4;

		buttomMoveView = new View(customApplication);

		ViewTreeObserver treeObserver = buttomframeLayout.getViewTreeObserver();
		treeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				buttomframeLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				Log.i("height", buttomframeLayout.getHeight() + "");
				LayoutParams params = new LayoutParams(moveWidth,
						buttomframeLayout.getHeight());

				buttomMoveView.setLayoutParams(params);
				buttomMoveView.setBackgroundColor(getResources().getColor(
						R.color.red));
				buttomframeLayout.addView(buttomMoveView);
			}
		});

		initRadioGroup();
		initFragments();
		initFragmentPagerAdapter(getSupportFragmentManager());

		dbUtils = customApplication.dbUtils;
		preferences = customApplication.preferences_table;

		Log.i("pf", "in create:" + preferences.getBoolean("load_back", false)
				+ "");

		loadBackDataToView(preferences.getBoolean("load_back", false));

	}

	private void loadBackDataToView(boolean isload) {
		// TODO Auto-generated method stub
		if (isload) {
			try {
				ClassListenTable classListenTable = dbUtils
						.findFirst(ClassListenTable.class);
				BaseFrag.classListenTable = classListenTable;
				BaseFrag.should_To_Back = true;
				// for (int i = 0; i < fragments.size(); i++) {
				// CollectOrBackDataInViewListener listener =
				// (CollectOrBackDataInViewListener) fragments
				// .get(i);
				// listener.backDataInView();
				// }

			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@OnClick(R.id.bt_send_data)
	public void sendData(View v) {

		String str1 = ((BaseFrag) fragments.get(0)).collectDataInView();

		String str2 = ((BaseFrag) fragments.get(1)).collectDataInView();

		String str3 = ((BaseFrag) fragments.get(2)).collectDataInView();

		String str4 = ((BaseFrag) fragments.get(3)).collectDataInView();

		String[] strs = new String[] { str1, str2, str3, str4 };

		boolean isComplement = true;

		for (int i = 0; i < strs.length; i++) {
			String st = strs[i];
			if (st == null) {
				// ((BaseFrag) fragments.get(i)).showSaveInMapsData();
			} else {
				CustomViewUtils.showInToast(customApplication, st);
				isComplement = false;

				return;
			}
		}
		progressDialog = CustomViewUtils.showProgressDialog(EvaluationAct.this,
				new DialogCancelDealListener() {

					@Override
					public void dealCancel() {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						customApplication.httpConnectUtils
								.cancelTaskByType(type);

					}
				});
		progressDialog.show();
		if (isComplement) {
			CustomViewUtils.showInToast(customApplication, "正在上传...");
			((BaseFrag) fragments.get(0)).showSaveInMapsData();
		}

		String data = ((BaseFrag) fragments.get(0)).showSaveInMapsData();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		try {
			nameValuePairs.add(new BasicNameValuePair("data", new String(data
					.getBytes(), "utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		customApplication.httpConnectUtils.sendRequestByPost(
				REQUST_TYPE.SEND_TABLE, new HttpListener() {

					@Override
					public void setResponseResult(String resultString) {
						// TODO Auto-generated method stub
						progressDialog.cancel();
						if (resultString.equals("ok")) {
							CustomViewUtils.showInToast(customApplication,
									"上传成功！O(∩_∩)O哈哈~");
							Editor editor = preferences.edit();
							editor.putBoolean("load_back", false);
							editor.commit();
							BaseFrag.should_To_Back = false;

							try {
								dbUtils.deleteAll(ClassListenTable.class);
							} catch (DbException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							EvaluationAct.this.finish();

						} else if (resultString.equals("no")) {
							sendMsgShowInToastDelay("服务器拒绝请求！");
							saveDataInDB();
						} else if (resultString.equals("error")) {
							sendMsgShowInToastDelay("网络异常，请稍后再试");
							saveDataInDB();
						} else {
							sendMsgShowInToastDelay("未知错误！");
							saveDataInDB();
						}

					}
				}, nameValuePairs);

	}

	public void saveDataInDB() {
		ClassListenTable listenTable = BaseFrag.classListenTable;

		try {

			dbUtils.deleteAll(ClassListenTable.class);
			dbUtils.save(listenTable);

			Editor editor = preferences.edit();
			editor.putBoolean("load_back", true);
			editor.commit();

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void moveButtomLine() {

		// Transformation transformation=new Transformation();
		TranslateAnimation animation = new TranslateAnimation((beforePageIndex)
				* moveWidth, (currentPageIndex) * moveWidth, 0, 0);
		animation.setDuration(200);
		animation.setFillAfter(true);
		// buttomMoveView.setAnimation(animation);
		buttomMoveView.startAnimation(animation);
		// animation.start();

	}

	private void initFragmentPagerAdapter(FragmentManager supportFragmentManager) {
		// TODO Auto-generated method stub

		EvaluationFragmentAdapter evaluationFragmentAdapter = new EvaluationFragmentAdapter(
				supportFragmentManager);
		viewPager.setOffscreenPageLimit(4);
		viewPager.setAdapter(evaluationFragmentAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				currentPageIndex = position;
				moveButtomLine();
				beforePageIndex = currentPageIndex;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initRadioGroup() {
		// TODO Auto-generated method stub
		radioIds = new int[] { R.id.radio_bt1, R.id.radio_bt2, R.id.radio_bt3,
				R.id.radio_bt4 };
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int index = getPosition(checkedId);

				viewPager.setCurrentItem(index, true);
				currentPageIndex = index;
				moveButtomLine();
				beforePageIndex = currentPageIndex;

			}

		});
	}

	private int getPosition(int checkedId) {
		// TODO Auto-generated method stub
		if (checkedId == radioIds[0]) {
			return 0;
		} else if (checkedId == radioIds[1]) {
			return 1;
		} else if (checkedId == radioIds[2]) {
			return 2;
		} else if (checkedId == radioIds[3]) {
			return 3;
		} else {
			return -1;
		}

	}

	private void initFragments() {
		// TODO Auto-generated method stub
		BasicInfoFrag basicInfoFrag1 = new BasicInfoFrag();
		EvaluationcContentFrag basicInfoFrag2 = new EvaluationcContentFrag();
		RecordInClassFrag basicInfoFrag3 = new RecordInClassFrag();
		AdviceInClassFrag basicInfoFrag4 = new AdviceInClassFrag();

		fragments = new ArrayList<Fragment>();
		fragments.add(basicInfoFrag1);
		fragments.add(basicInfoFrag2);
		fragments.add(basicInfoFrag3);
		fragments.add(basicInfoFrag4);
	}

	class EvaluationFragmentAdapter extends FragmentPagerAdapter {

		public EvaluationFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
	}

}
