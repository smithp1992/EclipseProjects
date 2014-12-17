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

public class MtGoxMrkt extends Activity implements OnTouchListener {

	private static String mtGoxURL = "https://data.mtgox.com/api/2/BTCUSD/money/ticker";
	
	GetPrice getPrice;
	
	
	TextView banner;
	TextView price;
	TextView highPrice;
	TextView lowPrice;
	TextView mtGoxToBTCeSpread;
	TextView mtGoxToBitstampSpread;
	WebView mtGoxMrktChart;
	
	
	Button refreshBT;
	Button menuBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.mt_gox_mrkt);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		
		
		banner = (TextView) findViewById(R.id.mtGoxBanner);
		price = (TextView) findViewById(R.id.mtGoxPrice);
		highPrice = (TextView) findViewById(R.id.mtGoxHighPrice);
		lowPrice = (TextView) findViewById(R.id.mtGoxLowPrice);
		mtGoxToBTCeSpread = (TextView) findViewById(R.id.mtGoxToBTCeSpread);
		mtGoxToBitstampSpread = (TextView) findViewById(R.id.mtGoxToBitstampSpread);
		mtGoxMrktChart = (WebView) findViewById(R.id.mtGoxMrktChart);
		refreshBT = (Button) findViewById(R.id.mtGoxRefreshBt);
		menuBT = (Button) findViewById(R.id.mtGoxMenuBt);
		
		refreshBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		
		
		// Set Mt. Gox Market Chart
		// String mtGoxChartURL1 = "http://www.pg.gda.pl/~jkozicki/use/charts/png/graph-half-BTC-USD-mtgox-5d-22m.png";
		String mtGoxChartURL2 = "http://bitcoincharts.com/charts/chart.png?width=940&m=mtgoxUSD&SubmitButton=Draw&r=5&i=&c=0&s=&e=&Prev=&Next=&t=S&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=0&ps=0&l=0&p=0&";
		
        mtGoxMrktChart.loadUrl(mtGoxChartURL2);
		
		// Get Current Price of Mt. Gox
		getPrice = new GetPrice();
		getPrice.getResources(MtGoxMrkt.this, mtGoxURL, price, highPrice, lowPrice);
		getPrice.execute();
		
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == refreshBT.getId()) {
				refreshBT.setTextColor(Color.BLUE);
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
