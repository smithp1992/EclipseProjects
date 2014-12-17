package com.market.bitcoinmrkttracker;

import com.market.bitcoinmrkttracker.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class BitcoinNews extends Activity implements OnTouchListener {

	TextView bitnewsTitle;
	Button menuBT;
	Button backBT;
	WebView bitnewsWeb;
	
	String newsURL1 = "https://www.google.com/m/search?q=bitcoin+news&gbv=2&source=univ&tbm=nws";
	String newsURL2 = "http://www.reddit.com/r/Bitcoin";
	String newsURL3 = "http://i.reddit.com/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.bitcoin_news);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		bitnewsTitle = (TextView) findViewById(R.id.bitnews_title);
		menuBT = (Button) findViewById(R.id.bitnews_MenuBt);
		backBT = (Button) findViewById(R.id.bitnews_backBT);
		bitnewsWeb = (WebView) findViewById(R.id.bitnews_webview);
		
		menuBT.setOnTouchListener(this);
		backBT.setOnTouchListener(this);
		bitnewsWeb.setOnTouchListener(this);
		
		bitnewsWeb.getSettings().setBuiltInZoomControls(true);
		// bitnewsWeb.getSettings().setLoadWithOverviewMode(true);
		bitnewsWeb.getSettings().setUseWideViewPort(true);
		bitnewsWeb.setWebViewClient(new WebViewClient());
		bitnewsWeb.loadUrl(newsURL2);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.LTGRAY);
			}
			else if(v.getId() == backBT.getId()) {
				backBT.setTextColor(Color.CYAN);
			}
			break;
		case MotionEvent.ACTION_UP:
			if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.BLACK);
				Intent menuInt = new Intent(this, BitcoinMarketMenu.class);
				this.finish();
				startActivity(menuInt);
			}
			else if(v.getId() == backBT.getId()) {
				backBT.setTextColor(Color.BLACK);
				bitnewsWeb.goBack();
			}
			break;
		}
		return false;
	}

	
}
