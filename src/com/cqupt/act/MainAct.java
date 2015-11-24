package com.cqupt.act;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

public class MainAct extends BaseAct {
	@ViewInject(R.id.pager_main)
	ViewPager viewPager;

	@ViewInject(R.id.dot1)
	ImageView dotImageView1;
	@ViewInject(R.id.dot2)
	ImageView dotImageView2;
	@ViewInject(R.id.dot3)
	ImageView dotImageView3;

	ImageView[] dotImageViews;

	List<Bitmap> listTopBitmaps;

	List<Bitmap> listDotsBitmaps;

	int befSelectNum = 0;
	int curSelectNum = 0;
	Timer timer;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlay);
		ViewUtils.inject(this);
		dotImageViews = new ImageView[] { dotImageView1, dotImageView2,
				dotImageView3 };
		handler = new Handler();
		loadBitmaps();
		initViewPager();
		startTurns();

	}

	@OnClick(R.id.main_bt1)
	public void bt1(View v) {
		startActivity(new Intent(MainAct.this, EvaluationAct.class));

	}

	@OnClick(R.id.main_bt2)
	public void bt2(View v) {
		startActivity(new Intent(MainAct.this, RecordAct.class));

	}

	@OnClick(R.id.main_bt3)
	public void bt3(View v) {
		startActivity(new Intent(MainAct.this, NewsRecordAct.class));

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

	}

	private void startTurns() {
		// TODO Auto-generated method stub

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				curSelectNum = (++curSelectNum) % 3;
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						viewPager.setCurrentItem(curSelectNum);
					}
				});
			}
		}, 2000, 2000);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		timer.cancel();
	}

	private void initViewPager() {
		viewPager.setOffscreenPageLimit(3);
		TopViewAdapter adapter = new TopViewAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				curSelectNum = position;
				dotImageViews[befSelectNum].setImageBitmap(listDotsBitmaps
						.get(1));
				dotImageViews[curSelectNum].setImageBitmap(listDotsBitmaps
						.get(0));
				befSelectNum = curSelectNum;
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

	private void loadBitmaps() {
		// TODO Auto-generated method stub
		listTopBitmaps = new ArrayList<Bitmap>();

		Bitmap bitmap1 = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.hf1));
		Bitmap bitmap2 = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.hf2));
		Bitmap bitmap3 = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.hf3));

		listTopBitmaps.add(bitmap1);
		listTopBitmaps.add(bitmap2);
		listTopBitmaps.add(bitmap3);

		listDotsBitmaps = new ArrayList<Bitmap>();

		Bitmap bitmap4 = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.dot_red));
		Bitmap bitmap5 = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.dot_green));

		listDotsBitmaps.add(bitmap4);
		listDotsBitmaps.add(bitmap5);

	}

	class TopViewAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@SuppressLint("NewApi")
		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			ImageView imageView = new ImageView(getApplicationContext());
			// imageView.set
			imageView.setBackground(new BitmapDrawable(listTopBitmaps
					.get(position)));
			((ViewPager) container).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((ImageView) object);
		}

	}
}
