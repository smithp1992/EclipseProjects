package com.market.bitcoinmarkets;

import com.market.bitcoinmrkttracker.R;
import com.market.bitcoinmrkttracker.BitcoinMarketMenu;
import com.market.bitcoinmrkttracker.GetPrice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BTCeMrkt extends Activity implements OnTouchListener {

	String url = "https://btc-e.com/api/2/btc_usd/ticker";
	
	GetPrice getPrice;
	
	TextView banner;
	TextView price;
	TextView highPrice;
	TextView lowPrice;
	TextView btceChartBanner;
	WebView btceMrktChart;
	
	Button refreshBT;
	Button menuBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.btce_mrkt);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		banner = (TextView) findViewById(R.id.btceBanner);
		price = (TextView) findViewById(R.id.btcePrice);
		highPrice = (TextView) findViewById(R.id.btceHighPrice);
		lowPrice = (TextView) findViewById(R.id.btceLowPrice);
		btceChartBanner = (TextView) findViewById(R.id.btceChartBanner);
		btceMrktChart = (WebView) findViewById(R.id.btceMrktChart);
		
		menuBT = (Button) findViewById(R.id.btceMenuBt);
		refreshBT = (Button) findViewById(R.id.btceRefreshBt);
		refreshBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		
		// Set BTC-e Market Chart
		// String btceChartURL1 = "http://www.pg.gda.pl/~jkozicki/use/charts/png/graph-half-BTC-USD-btc-e-5d-22m.png";
		String btceChartURL2 = "http://bitcoincharts.com/charts/chart.png?width=940&m=btceUSD&SubmitButton=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
		// String btceEURChartURL = "http://bitcoincharts.com/charts/chart.png?width=940&m=btceEUR&SubmitButton=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
		btceMrktChart.loadUrl(btceChartURL2);
		
		getPrice = new GetPrice();
		getPrice.getResources(this, url, price, highPrice, lowPrice);
		getPrice.execute();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == refreshBT.getId()) {
				refreshBT.setTextColor(Color.RED);
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
