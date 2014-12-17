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

public class BitstampMrkt extends Activity implements OnTouchListener {

	GetPrice getPrice;
	String url = "https://www.bitstamp.net/api/ticker/";
	
	TextView banner;
	TextView price;
	TextView highPrice;
	TextView lowPrice;
	TextView bitstampChartBanner;
	
	WebView bitstampMrktChart;
	
	Button menuBT;
	Button refreshBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.bitstamp_mrkt);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		
		banner = (TextView) findViewById(R.id.bitstampBanner);
		price = (TextView) findViewById(R.id.bitstampPrice);
		highPrice = (TextView) findViewById(R.id.bitstampHighPrice);
		lowPrice = (TextView) findViewById(R.id.bitstampLowPrice);
		bitstampChartBanner = (TextView) findViewById(R.id.bitstampChartBanner);
		bitstampMrktChart = (WebView) findViewById(R.id.bitstampMrktChart);
		
		// Set Buttons
		refreshBT = (Button) findViewById(R.id.bitstampRefreshBt);
		menuBT = (Button) findViewById(R.id.bitstampMenuBt);
		refreshBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		
		
		// Set Bitstamp Market Chart
		// String bitstampChartURL1 = "http://www.pg.gda.pl/~jkozicki/use/charts/png/graph-half-BTC-USD-bitstamp-5d-22m.png";
		String bitstampChartURL2 = "http://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
		bitstampMrktChart.loadUrl(bitstampChartURL2);
		
		getPrice = new GetPrice();
		getPrice.getResources(this, url, price, highPrice, lowPrice);
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
