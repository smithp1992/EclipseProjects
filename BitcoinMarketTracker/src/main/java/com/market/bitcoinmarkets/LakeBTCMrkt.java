package com.market.bitcoinmarkets;

import com.market.bitcoinmrkttracker.BitcoinMarketMenu;
import com.market.bitcoinmrkttracker.GetPrice;
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
import android.widget.Button;
import android.widget.TextView;

public class LakeBTCMrkt extends Activity implements OnTouchListener {

	GetPrice getPrice;
	
	TextView banner;
	TextView price;
	TextView highPrice;
	TextView lowPrice;
	TextView lakebtcChartBanner;
	
	WebView lakebtcMrktChart;
	
	Button menuBT;
	Button refreshBT;
	
	
	String urlChart = "http://bitcoincharts.com/charts/chart.png?width=940&m=lakeUSD&SubmitButton"
				+ "=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
	
	String urlAPI = "https://www.lakebtc.com/api_v1/ticker";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.lake_btc_mrkt);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		
		banner = (TextView) findViewById(R.id.lakebtcBanner);
		price = (TextView) findViewById(R.id.lakebtcPrice);
		highPrice = (TextView) findViewById(R.id.lakebtcHighPrice);
		lowPrice = (TextView) findViewById(R.id.lakebtcLowPrice);
		lakebtcChartBanner = (TextView) findViewById(R.id.lakebtcChartBanner);
		lakebtcMrktChart = (WebView) findViewById(R.id.lakebtcMrktChart);
		
		// Set Buttons
		refreshBT = (Button) findViewById(R.id.lakebtcRefreshBt);
		menuBT = (Button) findViewById(R.id.lakebtcMenuBt);
		refreshBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		
		
		// Set LakeBTC Market Chart
		lakebtcMrktChart.loadUrl(urlChart);
		
		// Load API
		getPrice = new GetPrice();
		getPrice.getResources(this, urlAPI, price, highPrice, lowPrice);
		getPrice.execute();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == refreshBT.getId()) {
				refreshBT.setTextColor(Color.GREEN);
			}
			else if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.LTGRAY);
			}
			break;
		case MotionEvent.ACTION_UP:
			if(v.getId() == refreshBT.getId()) {
				refreshBT.setTextColor(Color.BLACK);
				this.finish();
				startActivity(getIntent());
			}
			else if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.BLACK);
				Intent menuInt = new Intent(this, BitcoinMarketMenu.class);
				this.finish();
				startActivity(menuInt);
			}
			break;
		}
		return false;
	}
}
