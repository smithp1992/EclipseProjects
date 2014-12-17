package com.market.bitcoinmrkttracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.market.bitcoinmrkttracker.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Preferences extends Activity implements OnTouchListener, OnClickListener {

	String time;
	String market;
	String showTime;
	
	private static String MARKET_FILENAME = "MARKET";
	private static String TIME_FILENAME = "TIME";
	File marketFile;
	File timeFile;
	
	TextView selectMarket;
	TextView timeBanner;
	Button saveButton;
	Button menuButton;
	
	Button time30sec;
	Button time1min;
	Button time5min;
	Button time30min;
	Button time1hour;
	Button time6hour;
	Button time12hour;
	Button time1day;
	Button time7day;
	Button time14day;
	
	ToggleButton toggleBitstamp;
	ToggleButton toggleBTCe;
	ToggleButton toggleCampBX;
	ToggleButton toggleLakeBTC;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create Icon on top of App Window
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.preferences);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header_title);
		
		selectMarket = (TextView) findViewById(R.id.preferences_market_banner);
		timeBanner = (TextView) findViewById(R.id.preferences_time_banner);
		
		saveButton = (Button) findViewById(R.id.preferences_save_bt);
		menuButton = (Button) findViewById(R.id.preferences_menu_bt);
		
		time30sec = (Button) findViewById(R.id.preferences_time_30_sec);
		time1min = (Button) findViewById(R.id.preferences_time_1_min);
		time5min = (Button) findViewById(R.id.preferences_time_5_mins);
		time30min = (Button) findViewById(R.id.preferences_time_30_mins);
		time1hour = (Button) findViewById(R.id.preferences_time_1_hours);
		time6hour = (Button) findViewById(R.id.preferences_time_6_hours);
		time12hour = (Button) findViewById(R.id.preferences_time_12_hours);
		time1day = (Button) findViewById(R.id.preferences_time_1_day);
		time7day = (Button) findViewById(R.id.preferences_time_7_days);
		time14day = (Button) findViewById(R.id.preferences_time_14_days);
		
		toggleBitstamp = (ToggleButton) findViewById(R.id.preferences_toggle_bitstamp);
		toggleBTCe = (ToggleButton) findViewById(R.id.preferences_toggle_btce);
		toggleCampBX = (ToggleButton) findViewById(R.id.preferences_toggle_campbx);
		toggleLakeBTC = (ToggleButton) findViewById(R.id.preferences_toggle_lakebtc);
		
		saveButton.setOnTouchListener(this);
		menuButton.setOnTouchListener(this);
		
		time30sec.setOnTouchListener(this);
		time1min.setOnTouchListener(this);
		time5min.setOnTouchListener(this);
		time30min.setOnTouchListener(this);
		time1hour.setOnTouchListener(this);
		time6hour.setOnTouchListener(this);
		time12hour.setOnTouchListener(this);
		time1day.setOnTouchListener(this);
		time7day.setOnTouchListener(this);
		time14day.setOnTouchListener(this);
		
		toggleBitstamp.setOnClickListener(this);
		toggleBTCe.setOnClickListener(this);
		toggleCampBX.setOnClickListener(this);
		toggleLakeBTC.setOnClickListener(this);
		
		
		// Check if MARKET_FILENAME && TIME_FILENAME is valid
		marketFile = getApplicationContext().getFileStreamPath(MARKET_FILENAME);
		timeFile = getApplicationContext().getFileStreamPath(MARKET_FILENAME);
		if(marketFile.exists() && timeFile.exists()) {
			time = returnTime();
			market = returnMarket();
		}
		else {
			time = "30000";
			market = "Bitstamp";
			Toast.makeText(getBaseContext(), "Market and Time Not Found!", Toast.LENGTH_LONG).show();
		}
		
		// Set Current Market Banner
		selectMarket.setText("Market: " + market);
		int myTime = Integer.parseInt(time);
		
		// Set Time Banner
		showTime = setTimeString(myTime);
		
		timeBanner.setText("Time: " + showTime);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		
		
		case MotionEvent.ACTION_DOWN:
			
			if(v.getId() == time30sec.getId()) {
				time30sec.setTextColor(Color.RED);
			}
			else if(v.getId() == time1min.getId()) {
				time1min.setTextColor(Color.RED);
			}
			else if(v.getId() == time5min.getId()) {
				time5min.setTextColor(Color.RED);
			}
			else if(v.getId() == time30min.getId()) {
				time30min.setTextColor(Color.RED);
			}
			else if(v.getId() == time1hour.getId()) {
				time1hour.setTextColor(Color.RED);
			}
			else if(v.getId() == time6hour.getId()) {
				time6hour.setTextColor(Color.RED);
			}
			else if(v.getId() == time12hour.getId()) {
				time12hour.setTextColor(Color.RED);
			}
			else if(v.getId() == time1day.getId()) {
				time1day.setTextColor(Color.RED);
			}
			else if(v.getId() == time7day.getId()) {
				time7day.setTextColor(Color.RED);
			}
			else if(v.getId() == time14day.getId()) {
				time14day.setTextColor(Color.RED);
			}
			else if(v.getId() == saveButton.getId()) {
				saveButton.setTextColor(Color.CYAN);
			}
			else if(v.getId() == menuButton.getId()) {
				menuButton.setTextColor(Color.LTGRAY);
			}
			break;
			
			
		case MotionEvent.ACTION_UP:	
			
			
			if(v.getId() == time30sec.getId()) {
				time30sec.setTextColor(Color.BLACK);
				// Set Time 30 Seconds
				time = "30000";
				
				// Set Current Time Banner
				timeBanner.setText("Time: 30 seconds");
			}
			else if(v.getId() == time1min.getId()) {
				time1min.setTextColor(Color.BLACK);
				// Set Time 1 Minute
				time = "60000";

				// Set Current Time Banner
				timeBanner.setText("Time: 1 Minute");
			}
			else if(v.getId() == time5min.getId()) {
				time5min.setTextColor(Color.BLACK);
				// Set Time 5 Minutes
				time = "300000";

				// Set Current Time Banner
				timeBanner.setText("Time: 5 Minutes");
			}
			else if(v.getId() == time30min.getId()) {
				time30min.setTextColor(Color.BLACK);
				// Set Time 30 Minutes
				time = "1800000";

				// Set Current Time Banner
				timeBanner.setText("Time: 30 Minutes");
			}
			else if(v.getId() == time1hour.getId()) {
				time1hour.setTextColor(Color.BLACK);
				// Set Time 1 Hour
				time = "3600000";

				// Set Current Time Banner
				timeBanner.setText("Time: 1 Hour");
			}
			else if(v.getId() == time6hour.getId()) {
				time6hour.setTextColor(Color.BLACK);
				// Set Time 6 Hours
				time = "21600000";

				// Set Current Time Banner
				timeBanner.setText("Time: 6 Hours");
			}
			else if(v.getId() == time12hour.getId()) {
				time12hour.setTextColor(Color.BLACK);
				// Set Time 12 Hours
				time = "43200000";

				// Set Current Time Banner
				timeBanner.setText("Time: 12 Hours");
			}
			else if(v.getId() == time1day.getId()) {
				time1day.setTextColor(Color.BLACK);
				// Set Time 1 Day
				time = "86400000";

				// Set Current Time Banner
				timeBanner.setText("Time: 1 Day");
			}
			else if(v.getId() == time7day.getId()) {
				time7day.setTextColor(Color.BLACK);
				// Set Time 7 Days
				time = "604800000";

				// Set Current Time Banner
				timeBanner.setText("Time: 7 Days");
			}
			else if(v.getId() == time14day.getId()) {
				time14day.setTextColor(Color.BLACK);
				// Set Time 14 Days
				time = "1206900000";

				// Set Current Time Banner
				timeBanner.setText("Time: 14 Days");
			}
			else if(v.getId() == saveButton.getId()) {
				saveButton.setTextColor(Color.BLACK);
				// Save Current Market
				saveMarket();
				// Save Current Time
				saveTime();
				
				int myTime = Integer.parseInt(time);
				showTime = setTimeString(myTime);
				Toast.makeText(getBaseContext(), "Market & Time Saved: \n" + market + ": " + showTime, Toast.LENGTH_LONG).show();
			}
			else if(v.getId() == menuButton.getId()) {
				menuButton.setTextColor(Color.BLACK);
				// Return to Menu
				Intent menuInt = new Intent(this, BitcoinMarketMenu.class);
				this.finish();
				startActivity(menuInt);
			}
			break;
			
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preferences_toggle_bitstamp:
			if(toggleBitstamp.isChecked()) {
				market = "Bitstamp";
				selectMarket.setText("Market: " + market);
				toggleBTCe.setClickable(false);
				toggleCampBX.setClickable(false);
				toggleLakeBTC.setClickable(false);
			}
			else {
				toggleBTCe.setClickable(true);
				toggleCampBX.setClickable(true);
				toggleLakeBTC.setClickable(true);
			}
			break;
		case R.id.preferences_toggle_btce:
			if(toggleBTCe.isChecked()) {
				market = "BTCe";
				selectMarket.setText("Market: " + market);
				toggleBitstamp.setClickable(false);
				toggleCampBX.setClickable(false);
				toggleLakeBTC.setClickable(false);
			}
			else {
				toggleBitstamp.setClickable(true);
				toggleCampBX.setClickable(true);
				toggleLakeBTC.setClickable(true);
			}
			break;
		case R.id.preferences_toggle_campbx:
			if(toggleCampBX.isChecked()) {
				market = "CampBX";
				selectMarket.setText("Market: " + market);
				toggleBitstamp.setClickable(false);
				toggleBTCe.setClickable(false);
				toggleLakeBTC.setClickable(false);
			}
			else {
				toggleBitstamp.setClickable(true);
				toggleBTCe.setClickable(true);
				toggleLakeBTC.setClickable(true);
			}
			break;
			
		case R.id.preferences_toggle_lakebtc:
			if(toggleLakeBTC.isChecked()) {
				market = "LakeBTC";
				selectMarket.setText("Market: " + market);
				toggleBitstamp.setClickable(false);
				toggleBTCe.setClickable(false);
				toggleCampBX.setClickable(false);
			}
			else {
				toggleBitstamp.setClickable(true);
				toggleBTCe.setClickable(true);
				toggleCampBX.setClickable(true);
			}
			break;
			
		default:
			break;
		}
		
	}
	
	private void saveMarket() {
		String marketString = market;
		try {
			FileOutputStream fileOutput = openFileOutput(MARKET_FILENAME, Context.MODE_PRIVATE);
			fileOutput.write(marketString.getBytes());
			fileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void saveTime() {
		String timeString = String.valueOf(time);
		try {
			FileOutputStream fileOutput = openFileOutput(TIME_FILENAME, Context.MODE_PRIVATE);
			fileOutput.write(timeString.getBytes());
			fileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String returnTime() {
		String currentTime = "";
		FileInputStream fileInput;
		
		try {
			fileInput = openFileInput(TIME_FILENAME);
			byte[] timeInput = new byte[fileInput.available()];
			while (fileInput.read(timeInput) != -1) {
				currentTime += new String(timeInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return currentTime;
	}
	
	public String returnMarket() {
		String currentMarket = "";
		FileInputStream fileInput;
		
		try {
			fileInput = openFileInput(MARKET_FILENAME);
			byte[] marketInput = new byte[fileInput.available()];
			while (fileInput.read(marketInput) != -1) {
				currentMarket += new String(marketInput);
			}
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return currentMarket;
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

}
