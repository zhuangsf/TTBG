package com.android.ttbg;


import com.android.ttbg.util.Urls;
import com.android.ttbg.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebViewActivity extends ActivityPack {
	
    private ImageView  title_back;
	private WebView webView;
	private ProgressBar progressBar;
	private String titleString;
	private String urlString;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		tv_title  = (TextView)findViewById(R.id.tv_title);
		 
		 
	    Bundle bundle = getIntent().getExtras();
	    if(bundle!=null){
	        	titleString = bundle.getString(Urls.URL_TITLE);
	        	urlString = bundle.getString(Urls.URL_CONTENT);
	    }
		 
		 init();
		 
		 Utils.Log("urlString = "+urlString+" titleString = "+titleString );
		 if(urlString != null)
		 {
			 webView.loadUrl(urlString);
		 }
		 if(titleString != null)
		 {
			 tv_title.setText(titleString);
		 }
		 

		 
	}
	
	private void init() {
		// TODO 自动生成的方法存根
		webView=(WebView) findViewById(R.id.webview);
		progressBar=(ProgressBar) findViewById(R.id.progressBar);
		
		webView.setWebViewClient(new WebViewClient(){
			//覆写shouldOverrideUrlLoading实现内部显示网页
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO 自动生成的方法存根
			    view.loadUrl(url);
				return true;
			}
		});
		WebSettings seting=webView.getSettings();
		seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO 自动生成的方法存根
				
				if(newProgress==100){
					progressBar.setVisibility(View.GONE);//加载完网页进度条消失
				}
				else{
					progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
					progressBar.setProgress(newProgress);//设置进度值
				}
				
			}
		});
		
	}

	
    //设置返回键动作（防止按返回键直接退出程序)
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
				webView.goBack();
				return true;
			}
			else {//当webview处于第一页面时,直接退出程序
				finish();
			}
			
		
		}
		return super.onKeyDown(keyCode, event);
	}
	

}