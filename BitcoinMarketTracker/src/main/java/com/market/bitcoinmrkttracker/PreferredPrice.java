package com.market.bitcoinmrkttracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.market.bitcoinmrkttracker.R;
import com.market.receiver.Receiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PreferredPrice extends Activity implements OnTouchListener, OnClickListener {

	
	String buyPrice = "";
	String sellPrice = "";
	double currentBuyPrice = 0;
	double currentSellPrice = 0;
	
	boolean buy = false;
	boolean sell = false;
	private static String BUY_FILENAME = "BUY_PRICE";
	private static String SELL_FILENAME = "SELL_PRICE";
	private static String MARKET_FILENAME = "MARKET";
	private static String TIME_FILENAME = "TIME";
	private static String ALARM_FILENAME = "ALARM";
	public static int REQUEST_CODE = 123;
	
	ToggleButton buyToggle;
	EditText buyEdit;
	ToggleButton sellToggle;
	EditText sellEdit;
	TextView currentBuy;
	TextView currentSell;
	TextView buyBanner;
	TextView sellBanner;
	TextView marketBanner;
	TextView timeBanner;
	TextView alarmBanner;
	
	Receiver myReceiver;
	String marketURL = "";
	
	Button startAlarmBT;
	Button stopAlarmBT;
	Button menuBT;
	Button saveBT;
	
	AlarmManager alarmManager;
	boolean createAlarm = false;
	
	int time = 0;
	String timeString;
	String market = "";
	String alarm;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Variables
		boolean buyOption = true;
		boolean sellOption = false;
		
		
		// Create Header
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.preferred_price);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		buyToggle = (ToggleButton) findViewById(R.id.preferred_buy_toggle);
		buyEdit = (EditText) findViewById(R.id.preferred_buy_editTX);
		sellToggle = (ToggleButton) findViewById(R.id.preferred_sell_toggle);
		sellEdit = (EditText) findViewById(R.id.preferred_sell_editTX);
		currentBuy = (TextView) findViewById(R.id.preferred_current_buy_price);
		currentSell = (TextView) findViewById(R.id.preferred_current_sell_price);
		buyBanner = (TextView) findViewById(R.id.preferred_buy_banner);
		sellBanner = (TextView) findViewById(R.id.preferred_sell_banner);
		marketBanner = (TextView) findViewById(R.id.preferred_price_current_market);
		timeBanner = (TextView) findViewById(R.id.preferred_price_current_time);
		alarmBanner = (TextView) findViewById(R.id.preferred_price_alarm);
		
		startAlarmBT = (Button) findViewById(R.id.preferred_price_start_alarm);
		stopAlarmBT = (Button) findViewById(R.id.preferred_price_stop_alarm);
		menuBT = (Button) findViewById(R.id.preferred_price_menuBt);
		saveBT = (Button) findViewById(R.id.preferred_price_saveBt);
		
		startAlarmBT.setOnTouchListener(this);
		stopAlarmBT.setOnTouchListener(this);
		menuBT.setOnTouchListener(this);
		saveBT.setOnTouchListener(this);
		buyToggle.setOnClickListener(this);
		sellToggle.setOnClickListener(this);
		
		
		// Setup current Prices
		currentBuyPrice = getLastSavedPrices(buyOption);
		currentSellPrice = getLastSavedPrices(sellOption);
		
		// Get Current Market and Time Update
		market = getLastSavedMarket();
		time = getLastSavedTime();
		
		// Find File "ALARM"
		File alarmFile = getBaseContext().getFileStreamPath(ALARM_FILENAME);
		if(alarmFile.exists()) {
			// If file exists: Get the current Alarm String
			getLastSavedAlarm("GET", alarm);
		}
		else {
			// Set alarm to default OFF
			alarm = "Alarm: OFF";
			// Save file ALARM_FILENAME ("ALARM")
			getLastSavedAlarm("SAVE", alarm);
		}
		
		// Set Alarm Banner
		alarmBanner.setText(alarm);
		
		// Set Current Market
		marketBanner.setText("Market: " + market);
		
		// Set Current Time
		timeString = setTimeString(time);
		timeBanner.setText("Time: " + timeString);
		
		
		// Set Current Prices to Banners
		currentBuy.setText("Buy: $" + currentBuyPrice);
		currentSell.setText("Sell: $" + currentSellPrice);
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(v.getId() == menuBT.getId()) {
				// Set Menu Button color to Light Grey
				menuBT.setTextColor(Color.LTGRAY);
			}
			else if(v.getId() == saveBT.getId()) {
				// Set Save Button color to Blue
				saveBT.setTextColor(Color.BLUE);
				
				// Check if Save Button is Clickable
				if(saveBT.isClickable() == false) {
					// IF FALSE: Set Save Button color to Black
					saveBT.setTextColor(Color.BLACK);
				}
			}
			else if(v.getId() == startAlarmBT.getId()) {
				// Set Start Alarm Button color to Green
				startAlarmBT.setTextColor(Color.GREEN);
				
				// Check if Start Button is Clickable
				if(startAlarmBT.isClickable() == false) {
					// IF FALSE: Set Start Alarm Button color to Black
					startAlarmBT.setTextColor(Color.BLACK);
					// Let the user know that the Buy and Sell Values are inValid
					Toast.makeText(getBaseContext(), "Enter a Valid Sell and Buy Price", Toast.LENGTH_LONG).show();
				}
			}
			else if(v.getId() == stopAlarmBT.getId()) {
				// Set Cancel Alarm Button color to Red
				stopAlarmBT.setTextColor(Color.RED);
				
			}
			break;
			
			
		case MotionEvent.ACTION_UP:
			if(v.getId() == menuBT.getId()) {
				menuBT.setTextColor(Color.BLACK);
				Intent menuInt = new Intent(this, BitcoinMarketMenu.class);
				this.finish();
				startActivity(menuInt);
			}
			else if(v.getId() == saveBT.getId()) {
				// Set SaveBT Color back to DEFAULT BLACK
				saveBT.setTextColor(Color.BLACK);
				
				
				saveItems();
				
				// Let User Know Items are Saved
				Toast.makeText(getBaseContext(), "Buy and Sell Price Saved", Toast.LENGTH_LONG).show();
				
				// Turn off Save Function Unless Price Change
				saveBT.setClickable(false);
			}
			else if(v.getId() == startAlarmBT.getId()) {
				// Set Start Alarm Button to DEFAULT BLACK
				startAlarmBT.setTextColor(Color.BLACK);
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				// Check if Market, Time, Buy and Sell are Valid
				if(market.equals("") || time == 0 || currentBuyPrice == 0 || currentSellPrice == 0 ) {
					Toast.makeText(getBaseContext(), "Market, Time, Buy Price, or Sell Price \n "
							+ "information has not been found.", Toast.LENGTH_LONG).show();
				}
				else {					
				    
					// Create Intent to goto Broadcast Receiver
				    Intent intent = new Intent(this, Receiver.class);
				    intent.putExtra("market", "start");
				    setMarketPref(intent);
				    
				    createAlarm = true;
				    
				    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getBaseContext(), REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

					// Setup Receiver
					setupReceiver(intent, pendingIntent, createAlarm);
					
					// Set Alarm to ON
					alarm = "Alarm: ON";
					// Save Alarm State to ALARM_FILENAME ("ALARM")
					getLastSavedAlarm("SAVE", alarm);
					// Set Alarm Banner Text to Alarm: ON
					alarmBanner.setText(alarm);
				}
				
			}
			else if(v.getId() == stopAlarmBT.getId()) {
				// Set Cancel Alarm Button to DEFAULT BLACK
				stopAlarmBT.setTextColor(Color.BLACK);				
				
				// Get Current Alarm Settings
				getLastSavedAlarm("GET", alarm);
				
				// Check if Alarm is On or Off
				if(alarm.equals("Alarm: ON")) {
					// Create Intent to goto Broadcast Receiver
				    Intent intent = new Intent(this, Receiver.class);
				    intent.putExtra("market", "cancel");
				    setMarketPref(intent);
				    createAlarm = false;
				    
				    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getBaseContext(), REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

					// Setup Receiver
					setupReceiver(intent, pendingIntent, createAlarm);
					
					// Set Alarm to OFF
					alarm = "Alarm: OFF";
					// Save Alarm State to ALARM_FILENAME ("ALARM")
					getLastSavedAlarm("SAVE", alarm);
					// Set Alarm Banner to Updated Alarm: OFF
					alarmBanner.setText(alarm);
				}
				else {
					Toast.makeText(getBaseContext(), "Alarm is already off", Toast.LENGTH_LONG).show();
					alarmBanner.setText(alarm);
				}
				
			}
			break;
			
			
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferred_buy_toggle:
			getBuyBanner();
			// Check if New Buy Amount is Entered
			if(buy == true) {
				saveBT.setClickable(true);
			}
			break;
		case R.id.preferred_sell_toggle:
			getSellBanner();
			// Check if New Sell Amount is Entered
			if(sell == true) {
				saveBT.setClickable(true);
			}
			break;
		}
		
	}

	private void getBuyBanner() {
		// Get Buy Price from Edit Text
		if(buyToggle.isChecked()) {
			buyPrice = buyEdit.getText().toString();
			if(buyPrice.contentEquals("")) {
				buyBanner.setText("No Amount Entered:");
				buy = false;
			}
			else {
				buyBanner.setText("Buy Amount: $" + buyPrice);
				buy = true;
			}
		}
		else {
			buyPrice = "";
			buyBanner.setText("Buy Amount:");
			buy = false;
		}
		
	}
	
	private void getSellBanner() {
		// Get sell price of edit text
		if(sellToggle.isChecked()) {
			sellPrice = sellEdit.getText().toString();
			if(sellPrice.contentEquals("")) {
				sellBanner.setText("No Amount Entered:");
				sell = false;
			}
			else {
				sellBanner.setText("Sell Amount: $" + sellPrice);
				sell = true;
			}
		}
		else {
			sellPrice = "";
			sellBanner.setText("Sell Amount:");
			sell = false;
		}
	}
	
	private String setTimeString(int pTime) {
		String stringTime = "0 Seconds";
		
		// Check the Time
		if(pTime == 30000) {
			
			stringTime = (pTime / 1000) + " Seconds";
		}
		else if(pTime <= 1800000) {
			
			stringTime = (pTime / 1000 / 60) + " Minute[s]";
		}
		else if(pTime <= 43200000) {
			
			stringTime = (pTime / 1000 / 60 / 60) + "  Hour[s]";
		}
		else if(pTime <= 1206900000) {
			
			stringTime = (pTime / 1000 / 60 / 60 / 24) + "  Day[s]";
		}
		
		return stringTime;
	}
	
	private void saveItems() {
		if(buy == true) {
			
			currentBuy.setText("Buy: $" + buyPrice);
			// Update Current Buy Amount
			double myBuy = Double.parseDouble(buyPrice);
			currentBuyPrice = myBuy;
			
			// Check if Current Buy is Valid
			if(currentBuyPrice > 0) {
				try {
					FileOutputStream fileOutput = openFileOutput(BUY_FILENAME, Context.MODE_PRIVATE);
					fileOutput.write(buyPrice.getBytes());
					fileOutput.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				Toast.makeText(getBaseContext(), "Preferred Buy Amount is Invalid", Toast.LENGTH_LONG).show();
			}
		}
		if(sell == true) {
			
			currentSell.setText("Sell: $" + sellPrice);
			// Update Current Sell Amount
			double mySell = Double.parseDouble(sellPrice);
			currentSellPrice = mySell;
			
			// Check if Current Sell is Valid
			if(currentSellPrice > 0) {
				try {
					FileOutputStream fileOutput = openFileOutput(SELL_FILENAME, Context.MODE_PRIVATE);
					fileOutput.write(sellPrice.getBytes());
					fileOutput.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				Toast.makeText(getBaseContext(), "Preferred Sell Amount is Invalid", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private double getLastSavedPrices(boolean tradeOption) {
		double currentPrice = 0;
		String buyValue = "";
		String sellValue = "";
		FileInputStream fileInput;
		
		// Get Buy Price from BUY_FILENAME
		if(tradeOption == true) {
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
			
			if(buyValue != "") {
				currentPrice = Double.parseDouble(buyValue);
			}
			return currentPrice;
		}
		else {

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
			if(sellValue != "") {
				currentPrice = Double.parseDouble(sellValue);
			}
			return currentPrice;
		}
	}
	
	private String getLastSavedMarket() {
		String currentMarket = "";
		String marketValue = "";
		FileInputStream fileInput;
		try {
			fileInput = openFileInput(MARKET_FILENAME);
			byte[] marketInput = new byte[fileInput.available()];
			while (fileInput.read(marketInput) != -1) {
				marketValue += new String(marketInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(marketValue != "") {
			currentMarket = marketValue;
		}
		return currentMarket;
	}
	
	private int getLastSavedTime() {
		int currentTime = 30000;
		String timeValue = "";
		FileInputStream fileInput;
		try {
			fileInput = openFileInput(TIME_FILENAME);
			byte[] timeInput = new byte[fileInput.available()];
			while (fileInput.read(timeInput) != -1) {
				timeValue += new String(timeInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(timeValue != "") {
			currentTime = Integer.parseInt(timeValue);
		}
		
		return currentTime;
	}
	
	private void getLastSavedAlarm(String pRequest, String pAlarm) {
		
		if(pRequest.equals("GET")) {
			
			String currentAlarm = "";
			FileInputStream fileInput;
			try {
				fileInput = openFileInput(ALARM_FILENAME);
				byte[] alarmInput = new byte[fileInput.available()];
				while (fileInput.read(alarmInput) != -1) {
					currentAlarm += new String(alarmInput);
				}
				fileInput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			alarm = currentAlarm;
		}
		else if(pRequest.equals("SAVE")) {
			try {
				FileOutputStream fileOutput = openFileOutput(ALARM_FILENAME, Context.MODE_PRIVATE);
				fileOutput.write(pAlarm.getBytes());
				fileOutput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setupReceiver(Intent intent, PendingIntent pendingIntent, boolean createAlarm) {
		
	    // Get/Set Alarm
	    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    
		// Create Market Receiver
		if(createAlarm == true) {
			alarmManager.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + (time), (time), pendingIntent);
			
		}
		// Delete Market Receiver
		else if(createAlarm == false) {
			alarmManager.cancel(pendingIntent);
			pendingIntent.cancel();
		}
		
	}
	
	private void setMarketPref(Intent intent) {
		Bundle info = new Bundle();
		
		// First Check if Alarm is Being Canceled by checking extra content
		if(intent.getStringExtra("market").equals("cancel")) {

			// Put Extra Info into Bundle
			info.putString("market", "");
			info.putDouble("buy", currentBuyPrice);
			info.putDouble("sell", currentSellPrice);
			
			// Add Extra Bundle to Intent
			intent.putExtra("marketBundle", info);
			
			// Set Cancel Alarm Text
			Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_LONG).show();
			
		}
		else {
			
			if(market.equals("Bitstamp")) {
			    
				// Put Extra Info into Bundle
				info.putString("market", "Bitstamp");
				info.putDouble("buy", currentBuyPrice);
				info.putDouble("sell", currentSellPrice);
				
				// Add Extra Bundle to Intent
				intent.putExtra("marketBundle", info);
			    // Create Set_Alarm Toast
			    Toast.makeText(this, "Alarm set in " + timeString, Toast.LENGTH_LONG).show();
			    
			}
			else if(market.equals("BTCe")) {
				
				// Put Extra Info into Bundle
				info.putString("market", "BTCe");
				info.putDouble("buy", currentBuyPrice);
				info.putDouble("sell", currentSellPrice);
				
				// Add Extra Bundle to Intent
				intent.putExtra("marketBundle", info);
			    // Create Set_Alarm Toast
				Toast.makeText(this, "Alarm set in " + timeString, Toast.LENGTH_LONG).show();
				
			}
			else if(market.equals("CampBX")) {
				
				// Put Extra Info into Bundle
				info.putString("market", "CampBX");
				info.putDouble("buy", currentBuyPrice);
				info.putDouble("sell", currentSellPrice);
				
				// Add Extra Bundle to Intent
				intent.putExtra("marketBundle", info);
			    // Create Set_Alarm Toast
				Toast.makeText(this, "Alarm set in " + timeString, Toast.LENGTH_LONG).show();
				
			}
			else if(market.equals("LakeBTC")) {
				
				// Put Extra Info into Bundle
				info.putString("market", "LakeBTC");
				info.putDouble("buy", currentBuyPrice);
				info.putDouble("sell", currentSellPrice);
				
				// Add Extra Bundle to Intent
				intent.putExtra("marketBundle", info);
			    // Create Set_Alarm Toast
				Toast.makeText(this, "Alarm set in " + timeString, Toast.LENGTH_LONG).show();
				
			}
			else {
				Toast.makeText(getBaseContext(), "MarketURL Is Wrong", Toast.LENGTH_LONG).show();
			}
		}
	}
}
