package com.cqupt.http;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

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

	public void sendRequestServer(String url, HttpListener listener) {

		MyTask myTask = new MyTask(url, listener);
		executorService.submit(myTask);
	}

	class MyTask implements Runnable {
		String urlString;
		HttpListener httpListener;
		HttpResponse httpResponse;

		public MyTask(String urlStr, HttpListener listener) {
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

				mainHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						httpListener.setResponseResult(httpResponse.toString());
					}
				});
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
