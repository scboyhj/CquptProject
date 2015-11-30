package com.cqupt.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import android.os.Handler;
import android.util.Log;

import com.cqupt.http.HttpConnectUtils.HttpListener;
import com.cqupt.setting.HttpSettings.REQUST_TYPE;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

public class CustomHttpUtils {

	private HttpClient httpClient;
	private ExecutorService executorService;

	private Handler mainHandler;

	private LinkedHashMap<REQUST_TYPE, FutureTask<?>> futureTasksHashMap;

	public CustomHttpUtils(Handler handler) {
		initClient();
		initExecutor();
		mainHandler = handler;
		futureTasksHashMap = new LinkedHashMap<REQUST_TYPE, FutureTask<?>>();
	}

	private void initClient() {
		// HttpParams httpParams = new BasicHttpParams();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 443));
		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				httpParams, registry);
		httpClient = new DefaultHttpClient(conman, httpParams);

	}

	private void initExecutor() {
		executorService = Executors.newFixedThreadPool(10);
	}

	public void sendRequestServerByGet(String url, HttpListener listener,
			REQUST_TYPE type) {

		final MyGetTask myTask = new MyGetTask(url, listener, type);
		FutureTask<?> futureTask = new FutureTask<Void>(myTask, null) {

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				futureTasksHashMap.remove(myTask.type);
			}
		};

		if (!futureTasksHashMap.containsKey(type)) {
			futureTasksHashMap.put(type, futureTask);
		} else {
			FutureTask<?> ft = futureTasksHashMap.get(type);
			ft.cancel(true);
			ft = null;
			futureTasksHashMap.put(type, futureTask);

		}
		executorService.submit(futureTask);

	}

	public void cancelTaskByType(REQUST_TYPE type) {
		FutureTask<?> ft = futureTasksHashMap.get(type);
		if (ft != null) {
			ft.cancel(true);

			Log.i("myTag", "ft.isCancelled()" + ft.isCancelled());
			futureTasksHashMap.remove(ft);
		}
	}

	public void sendRequestServerByPost(String url, HttpListener listener,
			List<NameValuePair> listNameValuePairs, REQUST_TYPE type) {

		final MyPostTask myTask = new MyPostTask(url, listener,
				listNameValuePairs, type);
		FutureTask<?> futureTask = new FutureTask<Void>(myTask, null) {

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				futureTasksHashMap.remove(myTask.type);
			}
		};

		if (!futureTasksHashMap.containsKey(type)) {
			futureTasksHashMap.put(type, futureTask);
		} else {
			FutureTask<?> ft = futureTasksHashMap.get(type);
			ft.cancel(true);
			ft = null;
			futureTasksHashMap.put(type, futureTask);

		}
		executorService.submit(futureTask);
	}

	class MyGetTask implements Runnable {
		String urlString;
		HttpListener httpListener;
		HttpResponse httpResponse;
		REQUST_TYPE type;

		public MyGetTask(String urlStr, HttpListener listener,
				REQUST_TYPE reqType) {
			// TODO Auto-generated constructor stub
			urlString = urlStr;
			type = reqType;
			httpListener = listener;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				HttpGet httpGet = new HttpGet(urlString);
				httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					mainHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								InputStream inputStream = httpResponse
										.getEntity().getContent();
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(inputStream));
								String str = reader.readLine();

								httpListener.setResponseResult(new String(str
										.getBytes(), "utf-8"));
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
				} else {
					Log.i("HttpLog",
							""
									+ httpResponse.getStatusLine()
											.getStatusCode()
									+ " and "
									+ EntityUtils.toString(httpResponse
											.getEntity()));
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				httpListener.setResponseResult("error");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				httpListener.setResponseResult("error");
			}

		}
	}

	class MyPostTask implements Runnable {
		String urlString;
		HttpListener httpListener;
		HttpResponse httpResponse;
		List<NameValuePair> listNameValuePairs;
		REQUST_TYPE type;

		public MyPostTask(String urlStr, HttpListener listener,
				List<NameValuePair> list, REQUST_TYPE reqType) {
			// TODO Auto-generated constructor stub
			urlString = urlStr;
			httpListener = listener;
			listNameValuePairs = list;
			type = reqType;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {

				HttpPost httpPost = new HttpPost(urlString);

				httpPost.setEntity(new UrlEncodedFormEntity(listNameValuePairs,
						"utf-8"));

				httpResponse = httpClient.execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					mainHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								httpListener.setResponseResult(EntityUtils
										.toString(httpResponse.getEntity()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				} else {

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				httpListener.setResponseResult("error");
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				httpListener.setResponseResult("error");
				// e.printStackTrace();

			}

		}
	}

}
