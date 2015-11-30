package com.cqupt.act;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cqupt.customview.CustomViewUtils;
import com.cqupt.entity.ArrangeItem;
import com.cqupt.entity.NotifyItem;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;

public class ArrangeAct extends BaseAct {
	// @ViewInject(R.id.pull_to_refresh_mlistview)
	PullToRefreshListView listView;
	List<ArrangeItem> arrageItems;
	Handler handler;
	ArrangeItemAdapter adapter;
	int currentPage = 0;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordlay);
		type=REQUST_TYPE.GET_ARRANGE_ITEMS;
		ViewUtils.inject(this);
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_mlistview);
		handler = new Handler();
		arrageItems = new ArrayList<ArrangeItem>();

		adapter = new ArrangeItemAdapter();
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

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("user_id", customApplication.userId);
						map.put("page", (++currentPage) + "");
						customApplication.httpConnectUtils.sendRequestByGet(
								REQUST_TYPE.GET_ARRANGE_ITEMS, map,
								new HttpConnectUtils.HttpListener() {

									@Override
									public void setResponseResult(
											String resultString) {
										// TODO Auto-generated method stub
										if (resultString == null
												|| resultString.length() == 0) {
											CustomViewUtils.showInToast(
													customApplication, "未知错误！"
															+ resultString);

										} else if (resultString.equals("no")) {
											CustomViewUtils
													.showInToast(
															customApplication,
															"没有了更多了");
											currentPage--;

										} else if (resultString.equals("error")) {
											sendMsgShowInToastDelay("网络异常，请稍后再试");
										} else {
											Log.i("ArrangeAct", resultString);
											Type type = new TypeToken<ArrayList<ArrangeItem>>() {
											}.getType();

											Gson gson = new Gson();
											ArrayList<ArrangeItem> arrayList = gson
													.fromJson(resultString,
															type);
											for (int i = 0; i < arrayList
													.size(); i++) {
												arrageItems.add(arrayList
														.get(i));
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
				}, 300);
			}
		});

	}

	class ArrangeItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrageItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrageItems.get(position);
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
						R.layout.arrange_itemlay, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.timeTextView = (TextView) convertView
						.findViewById(R.id.tv_listen_time);
				viewHolder.publishTextView = (TextView) convertView
						.findViewById(R.id.tv_news_time);
				viewHolder.placeTextView = (TextView) convertView
						.findViewById(R.id.tv_listen_place);
				convertView.setTag(viewHolder);

			}
			holder = (ViewHolder) convertView.getTag();
			ArrangeItem item = arrageItems.get(position);
			holder.placeTextView.setText(item.arragePlace);
			holder.timeTextView.setText(item.arrageTime);
			holder.publishTextView.setText(item.publishTime);
			return convertView;
		}

		class ViewHolder {
			TextView publishTextView;
			TextView timeTextView;
			TextView placeTextView;
		}
	}

}
