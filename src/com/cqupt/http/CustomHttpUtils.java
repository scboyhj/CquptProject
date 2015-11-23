package com.cqupt.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

import android.os.Handler;

import com.cqupt.http.HttpConnectUtils.HttpListener;

public class CustomHttpUtils {

	private HttpClient httpClient;
	private ExecutorService executorService;

	private Handler mainHandler;

	public CustomHttpUtils(Handler handler) {
		initClient();
		initExecutor();
		mainHandler = handler;
	}

	private void initClient() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);

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

	public void sendRequestServerByGet(String url, HttpListener listener) {

		MyGetTask myTask = new MyGetTask(url, listener);
		executorService.submit(myTask);
	}

	public void sendRequestServerByPost(String url, HttpListener listener,
			List<NameValuePair> listNameValuePairs) {

		MyPostTask myTask = new MyPostTask(url, listener, listNameValuePairs);
		executorService.submit(myTask);
	}

	class MyGetTask implements Runnable {
		String urlString;
		HttpListener httpListener;
		HttpResponse httpResponse;

		public MyGetTask(String urlStr, HttpListener listener) {
			// TODO Auto-generated constructor stub
			urlString = urlStr;
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
							httpListener.setResponseResult(httpResponse
									.toString());
						}
					});
				} else {

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyPostTask implements Runnable {
		String urlString;
		HttpListener httpListener;
		HttpResponse httpResponse;
		List<NameValuePair> listNameValuePairs;

		public MyPostTask(String urlStr, HttpListener listener,
				List<NameValuePair> list) {
			// TODO Auto-generated constructor stub
			urlString = urlStr;
			httpListener = listener;
			listNameValuePairs = list;
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
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
