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
import com.cqupt.entity.NotifyItem;
import com.cqupt.http.HttpConnectUtils;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;

public class NewsRecordAct extends BaseAct {
	// @ViewInject(R.id.pull_to_refresh_mlistview)
	PullToRefreshListView listView;
	List<NotifyItem> notifyItems;
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
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_mlistview);
		handler = new Handler();
		notifyItems = new ArrayList<NotifyItem>();

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
		listView.getLoadingLayoutProxy().setPullLabel("�������");
		listView.getLoadingLayoutProxy().setRefreshingLabel("����ˢ��");
		listView.getLoadingLayoutProxy().setReleaseLabel("����ˢ��");

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
								REQUST_TYPE.GET_NOTIFY_ITEMS, map,
								new HttpConnectUtils.HttpListener() {

									@Override
									public void setResponseResult(
											String resultString) {
										// TODO Auto-generated method stub
										if (resultString == null
												|| resultString.length() == 0) {
											CustomViewUtils.showInToast(
													customApplication, "δ֪����"
															+ resultString);

										} else if (resultString.equals("no")) {
											CustomViewUtils
													.showInToast(
															customApplication,
															"û���˸�����");
											currentPage--;

										} else {
											Log.i("NewsRecordAct", resultString);
											Type type = new TypeToken<ArrayList<NotifyItem>>() {
											}.getType();

											Gson gson = new Gson();
											ArrayList<NotifyItem> arrayList = gson
													.fromJson(resultString,
															type);
											for (int i = 0; i < arrayList
													.size(); i++) {
												notifyItems.add(arrayList
														.get(i));
											}
										}
										adapter.notifyDataSetChanged();
										listView.onRefreshComplete();
									}
								});

					}
				}, 300);
			}
		});
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		//
		// Intent intent = new Intent(NewsRecordAct.this,
		// NewsDetailAct.class);
		// intent.putExtra("url", recordItems.get(position-1).newsUrl);
		// startActivity(intent);
		// }
		// });
	}

	class RecordItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return notifyItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return notifyItems.get(position);
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
						R.layout.news_itemlay, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.timeTextView = (TextView) convertView
						.findViewById(R.id.tv_news_time);

				viewHolder.titleTextView = (TextView) convertView
						.findViewById(R.id.tv_news_title);
				convertView.setTag(viewHolder);

			}
			holder = (ViewHolder) convertView.getTag();
			NotifyItem item = notifyItems.get(position);
			holder.timeTextView.setText(item.time);
			holder.titleTextView.setText(item.title);

			return convertView;
		}

		class ViewHolder {
			TextView timeTextView;
			TextView titleTextView;
		}
	}

}
