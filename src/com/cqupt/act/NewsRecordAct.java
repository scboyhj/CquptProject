package com.cqupt.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cqupt.entity.NewsItem;
import com.cqupt.entity.RecordItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;

public class NewsRecordAct extends BaseAct {
	// @ViewInject(R.id.pull_to_refresh_mlistview)
	PullToRefreshListView listView;
	List<NewsItem> recordItems;
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
		recordItems = new ArrayList<NewsItem>();

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
				}, 300);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(NewsRecordAct.this,
						NewsDetailAct.class);
				intent.putExtra("url", recordItems.get(position-1).newsUrl);
				startActivity(intent);
			}
		});
	}

	private void iniItems() {
		// TODO Auto-generated method stub
		NewsItem item1 = new NewsItem();

		item1.newsTime = "2015-9-11 14:21:22";
		item1.newsTitle = "2015重庆邮电大学研究生选拔简章";
		item1.newsUrl = "http://www.baidu.com";

		NewsItem item2 = new NewsItem();

		item2.newsTime = "2015-10-12 9:30:00";
		item2.newsTitle = "关于组织学生参加全国计算机考试说明";
		item2.newsUrl = "http://www.qq.com";

		NewsItem item3 = new NewsItem();

		item3.newsTime = "2015-11-1 19:20:14";
		item3.newsTitle = "关于组织学生参加全国计算机考试说明";
		item3.newsUrl = "http://www.sina.com";

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
						R.layout.news_itemlay, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.timeTextView = (TextView) convertView
						.findViewById(R.id.tv_news_time);

				viewHolder.titleTextView = (TextView) convertView
						.findViewById(R.id.tv_news_title);
				convertView.setTag(viewHolder);

			}
			holder = (ViewHolder) convertView.getTag();
			NewsItem item = recordItems.get(position);
			holder.timeTextView.setText(item.newsTime);
			holder.titleTextView.setText(item.newsTitle);

			return convertView;
		}

		class ViewHolder {
			TextView timeTextView;
			TextView titleTextView;
		}
	}

}
