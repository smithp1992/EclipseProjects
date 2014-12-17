package com.market.bitcoinmrkttracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.market.bitcoinmrkttracker.R;
import com.market.bitcoinmarkets.BTCeMrkt;
import com.market.bitcoinmarkets.BitstampMrkt;
import com.market.bitcoinmarkets.CampBXMrkt;
import com.market.bitcoinmarkets.LakeBTCMrkt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class BitcoinMarketMenu extends Activity implements OnTouchListener {

	GetMenuPrice getMenuPrice;
	
	// String mtGoxAPI = "https://data.mtgox.com/api/2/BTCUSD/money/ticker";
	String bitstampAPI = "https://www.bitstamp.net/api/ticker/";
	String btceAPI = "https://btc-e.com/api/2/btc_usd/ticker";
	String campBXAPI = "http://campbx.com/api/xticker.php";
	String lakebtcAPI = "https://www.lakebtc.com/api_v1/ticker";
	
	static String BUY_FILENAME = "BUY_PRICE";
	static String SELL_FILENAME = "SELL_PRICE";
	
	// Button mtGox;
	Button bitstamp;
	Button btce;
	Button campBX;
	Button lakebtc;
	Button prefPriceBt;
	Button preferencesBt;
	Button bitNews;
	
	// TextView mtGoxPrice;
	TextView bitstampPrice;
	TextView btcePrice;
	TextView campBXPrice;
	TextView lakebtcPrice;
	TextView prefSellPrice;
	TextView prefBuyPrice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create Icon on top of App Window
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.market_menu);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		// Initialize Buttons
		// mtGox = (Button) findViewById(R.id.menu_button_mtGox);
		bitstamp = (Button) findViewById(R.id.menu_button_bitstamp);
		btce = (Button) findViewById(R.id.menu_button_btce);
		campBX = (Button) findViewById(R.id.menu_button_campbx);
		lakebtc = (Button) findViewById(R.id.menu_button_lakebtc);
		prefPriceBt = (Button) findViewById(R.id.menu_button_pref_price);
		preferencesBt = (Button) findViewById(R.id.menu_preferences);
		bitNews = (Button) findViewById(R.id.menu_news);
		
		// Initialize TextViews
		// mtGoxPrice = (TextView) findViewById(R.id.menu_price_mtGox);
		bitstampPrice = (TextView) findViewById(R.id.menu_price_bitstamp);
		btcePrice = (TextView) findViewById(R.id.menu_price_btce);
		campBXPrice = (TextView) findViewById(R.id.menu_price_campbx);
		lakebtcPrice = (TextView) findViewById(R.id.menu_price_lakebtc);
		prefBuyPrice = (TextView) findViewById(R.id.menu_price_pref_buy);
		prefSellPrice = (TextView) findViewById(R.id.menu_price_pref_sell);
		
		// Set Touch Listeners
		// mtGox.setOnTouchListener(this);
		bitstamp.setOnTouchListener(this);
		btce.setOnTouchListener(this);
		campBX.setOnTouchListener(this);
		lakebtc.setOnTouchListener(this);
		prefPriceBt.setOnTouchListener(this);
		preferencesBt.setOnTouchListener(this);
		bitNews.setOnTouchListener(this);
		
		getLastSavedPrices();
		
		getMenuPrice = new GetMenuPrice();
		getMenuPrice.getResources(this, bitstampAPI, btceAPI, campBXAPI, lakebtcAPI, bitstampPrice, btcePrice, campBXPrice, lakebtcPrice);
		getMenuPrice.execute();
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			/*
			if(v.getId() == mtGox.getId()) {
				mtGox.setTextColor(Color.BLUE);
			}
			*/
			if(v.getId() == bitstamp.getId()) {
				bitstamp.setTextColor(Color.GREEN);
			}
			else if(v.getId() == btce.getId()) {
				btce.setTextColor(Color.RED);
			}
			else if(v.getId() == campBX.getId()) {
				campBX.setTextColor(Color.rgb(102, 51, 153));
			}
			else if(v.getId() == lakebtc.getId()) {
				lakebtc.setTextColor(Color.MAGENTA);
			}
			else if(v.getId() == prefPriceBt.getId()) {
				prefPriceBt.setTextColor(Color.rgb(255, 215, 0));
			}
			else if(v.getId() == bitNews.getId()) {
				bitNews.setTextColor(Color.CYAN);
			}
			else if(v.getId() == preferencesBt.getId()) {
				preferencesBt.setTextColor(Color.rgb(204, 0, 255));
			}
			break;
			
			
		case MotionEvent.ACTION_UP:
			/*
			// Mt.Gox Market
			if(v.getId() == mtGox.getId()) {
				mtGox.setTextColor(Color.BLACK);
				Intent mtGoxInt = new Intent(this, MtGoxMrkt.class);
				this.finish();
				startActivity(mtGoxInt);
			}
			*/
			// Bitstamp Market
			if(v.getId() == bitstamp.getId()) {
				bitstamp.setTextColor(Color.BLACK);
				Intent bitstampInt = new Intent(this, BitstampMrkt.class);
				this.finish();
				startActivity(bitstampInt);
			}
			// BTC-e Market
			else if(v.getId() == btce.getId()) {
				btce.setTextColor(Color.BLACK);
				Intent btceInt = new Intent(this, BTCeMrkt.class);
				this.finish();
				startActivity(btceInt);
			}
			// Camp BX Market
			else if(v.getId() == campBX.getId()) {
				campBX.setTextColor(Color.BLACK);
				Intent campBXInt = new Intent(this, CampBXMrkt.class);
				this.finish();
				startActivity(campBXInt);
			}
			// Lake BTC Market
			else if(v.getId() == lakebtc.getId()) {
				lakebtc.setTextColor(Color.BLACK);
				Intent lakebtcInt = new Intent(this, LakeBTCMrkt.class);
				this.finish();
				startActivity(lakebtcInt);
			}
			// Prefered Price
			else if(v.getId() == prefPriceBt.getId()) {
				prefPriceBt.setTextColor(Color.BLACK);
				Intent prefPriceInt = new Intent(this, PreferredPrice.class);
				this.finish();
				startActivity(prefPriceInt);
			}
			// Bitcoin News
			else if(v.getId() == bitNews.getId()) {
				bitNews.setTextColor(Color.BLACK);
				Intent bitNewsInt = new Intent(this, BitcoinNews.class);
				this.finish();
				startActivity(bitNewsInt);
			}
			// Preferences
			else if(v.getId() == preferencesBt.getId()) {
				preferencesBt.setTextColor(Color.BLACK);
				Intent preferencesInt = new Intent(this, Preferences.class);
				this.finish();
				startActivity(preferencesInt);
			}
			
			break;
		}
		return false;
	}
	
	private void getLastSavedPrices() {
		String buyValue = "";
		String sellValue = "";
		FileInputStream fileInput;
		
		// Get Buy Price from BUY_FILENAME
		try {
			fileInput = openFileInput(BUY_FILENAME);
			byte[] buyInput = new byte[fileInput.available()];
			while (fileInput.read(buyInput) != -1) {
				buyValue += new String(buyInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Get Sell Price from SELL_FILENAME
		try {
			fileInput = openFileInput(SELL_FILENAME);
			byte[] sellInput = new byte[fileInput.available()];
			while (fileInput.read(sellInput) != -1) {
				sellValue += new String(sellInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(buyValue != "") {
			
			prefBuyPrice.setText("$" + buyValue);
		}
		if(sellValue != "") {
			
			prefSellPrice.setText("$" + sellValue);
		}
	}
	
	
}
