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
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class CampBXMrkt extends Activity implements OnTouchListener{

	GetPrice getPrice;
	String url = "http://campbx.com/api/xticker.php";
	
	TextView banner;
	TextView price;
	TextView highPrice;
	TextView lowPrice;
	TextView campbxChartBanner;
	
	WebView campbxMrktChart;
	
	Button menuBT;
	Button refreshBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.campbx_mrkt);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
				
		banner = (TextView) findViewById(R.id.campbxBanner);
		price = (TextView) findViewById(R.id.campbxPrice);
		highPrice = (TextView) findViewById(R.id.campbxHighPrice);
		lowPrice = (TextView) findViewById(R.id.campbxLowPrice);
		campbxChartBanner = (TextView) findViewById(R.id.campbxChartBanner);
		campbxMrktChart = (WebView) findViewById(R.id.campbxMrktChart);
		
		// Set On Touch Methods & Buttons
		refreshBT = (Button) findViewById(R.id.campbxRefreshBt);
		menuBT = (Button) findViewById(R.id.campbxMenuBt);
		refreshBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		

		// Set Bitstamp Market Chart
		String campbxChartURL1 = "http://bitcoincharts.com/charts/chart.png?width=940&m=cbxUSD&SubmitButton=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
		campbxMrktChart.loadUrl(campbxChartURL1);
		
		getPrice = new GetPrice();
		getPrice.getResources(this, url, price, highPrice, lowPrice);
		getPrice.execute();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == refreshBT.getId()) {
				refreshBT.setTextColor(Color.LTGRAY);
			}
			else if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.rgb(102, 51, 153));
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
