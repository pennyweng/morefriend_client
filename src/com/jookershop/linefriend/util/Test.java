package com.jookershop.linefriend.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jookershop.linefriend.account.AccountActivity;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

public class Test {
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private String a = "";
	
	public Future<String> getResponse() {
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		Future<String> result = es.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsyncHttpGet ahg = new AsyncHttpGet("http://www.google.com.tw");
				AsyncHttpClient.getDefaultInstance().executeString(ahg, new AsyncHttpClient.StringCallback() {
					
					@Override
				    public void onCompleted(Exception e, AsyncHttpResponse source, String response) {
						a = response;
						countDownLatch.countDown();
						
					}
				});
				countDownLatch.await();
				return a;
			}
		});
		return result;
	}
}
