package com.cqupt.act;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.entity.RecordItem;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;

public class RecordAct extends BaseAct {
	// @ViewInject(R.id.pull_to_refresh_mlistview)
	PullToRefreshListView listView;
	List<RecordItem> recordItems;
	Handler handler;
	RecordItemAdapter adapter;
	int currentPage = 0;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordlay);
		ViewUtils.inject(this);
		type = REQUST_TYPE.GET_RECORD_ITEMS;
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

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("user_id", customApplication.userId);
				map.put("page", (++currentPage) + "");
				customApplication.httpConnectUtils.sendRequestByGet(
						REQUST_TYPE.GET_RECORD_ITEMS, map,
						new HttpConnectUtils.HttpListener() {

							@Override
							public void setResponseResult(String resultString) {
								// TODO Auto-generated method stub
								if (resultString == null
										|| resultString.length() == 0) {

									sendMsgShowInToastDelay("连接错误");
								} else if (resultString.equals("no")) {

									sendMsgShowInToastDelay("没有更多了");
									currentPage--;
								} else if (resultString.equals("error")) {
									sendMsgShowInToastDelay("网络异常，请稍后再试");
								} else {
									Gson gson = new Gson();
									Log.i("RecordAct", resultString);

									Type type = new TypeToken<ArrayList<RecordItem>>() {
									}.getType();

									ArrayList<RecordItem> arrayList = gson
											.fromJson(resultString, type);
									for (int i = 0; i < arrayList.size(); i++) {
										recordItems.add(arrayList.get(i));
									}
									adapter.notifyDataSetChanged();

								}
								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method
										// stub
										listView.onRefreshComplete();
									}
								}, 200);

							}
						});

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(RecordAct.this, RecordTableAct.class);
				intent.putExtra("recordId",
						recordItems.get(position - 1).recordId);
				startActivity(intent);
			}
		});
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

			return convertView;
		}

		class ViewHolder {
			TextView timeTextView;
			TextView nameTextView;
			TextView titleTextView;
		}
	}

}
