package com.cqupt.act;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cqupt.entity.RecordItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;

public class RecordAct extends BaseAct {
	// @ViewInject(R.id.pull_to_refresh_mlistview)
	PullToRefreshListView listView;
	List<RecordItem> recordItems;
	Handler handler;
	RecordItemAdapter adapter;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordlay);
		ViewUtils.inject(this);
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_mlistview);
		handler = new Handler();
		recordItems = new ArrayList<RecordItem>();

		adapter = new RecordItemAdapter();
		initRefreshView();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				listView.setRefreshing();
			}
		}, 200);
	}

	private void initRefreshView() {
		// TODO Auto-generated method stub
		listView.getLoadingLayoutProxy().setPullLabel("加载完成");
		listView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
		listView.getLoadingLayoutProxy().setReleaseLabel("下拉刷新");

		listView.getRefreshableView().setAdapter(adapter);

		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						iniItems();
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});

	}

	private void iniItems() {
		// TODO Auto-generated method stub
		RecordItem item1 = new RecordItem();
		item1.recordTime = "2015-8-21 9:23:11";
		item1.recordTeacher = "张厚云";
		item1.recordTitle = "大学英语";

		RecordItem item2 = new RecordItem();
		item2.recordTime = "2015-9-11 14:21:22";
		item2.recordTeacher = "冉姜";
		item2.recordTitle = "高等数学";

		RecordItem item3 = new RecordItem();
		item3.recordTime = "2015-9-26 10:44:22";
		item3.recordTeacher = "秦林";
		item3.recordTitle = "大学物理";

		recordItems.add(item1);
		recordItems.add(item2);
		recordItems.add(item3);
	}

	class RecordItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return recordItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return recordItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder;
			if (convertView == null) {

				convertView = LayoutInflater.from(customApplication).inflate(
						R.layout.record_itemlay, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.timeTextView = (TextView) convertView
						.findViewById(R.id.tv_record_time);
				viewHolder.nameTextView = (TextView) convertView
						.findViewById(R.id.tv_record_name);
				viewHolder.titleTextView = (TextView) convertView
						.findViewById(R.id.tv_record_title);
				convertView.setTag(viewHolder);

			}
			holder = (ViewHolder) convertView.getTag();
			RecordItem item = recordItems.get(position);
			holder.timeTextView.setText(item.recordTime);
			holder.nameTextView.setText(item.recordTeacher);
			holder.titleTextView.setText(item.recordTitle);

			// Log.i(", msg)
			return convertView;
		}

		class ViewHolder {
			TextView timeTextView;
			TextView nameTextView;
			TextView titleTextView;
		}
	}

}
