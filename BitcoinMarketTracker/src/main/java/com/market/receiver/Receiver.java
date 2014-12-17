package com.market.receiver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.market.bitcoinmrkttracker.BitcoinMarketMenu;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	
	private static int REQUEST_CODE = 123;
	private static String ALARM_FILENAME = "ALARM";
	
	
	String bitstampURL = "https://www.bitstamp.net/api/ticker/";
	String btceURL = "https://btc-e.com/api/2/btc_usd/ticker";
	String campbxURL = "http://campbx.com/api/xticker.php";
	String lakebtcURL = "https://www.lakebtc.com/api_v1/ticker";
	
	CheckReceiverPrice checkPrice;
	String url = "https://www.bitstamp.net/api/ticker/";
	
	Vibrator vibrate;
	
	Context myContext;
	Intent myIntent;
	
	double currentPrice = 0;
	double buy;
	double sell;
	String myMarket;
	
	PendingIntent myPendingIntent;
	
	// Method Runs when time is up
	@Override
	public void onReceive(Context context, Intent intent) {
		// Variables
		Bundle marketInfo;
		myContext = context;
		myIntent = intent;
		
		// Get Extras from intent
		marketInfo = intent.getBundleExtra("marketBundle");
		
		// Initialize Vibrator
		vibrate = (Vibrator) myContext.getSystemService(Context.VIBRATOR_SERVICE);
		
		// Get Buy, Sell, and Market Values From Bundle
		buy = marketInfo.getDouble("buy");
		sell = marketInfo.getDouble("sell");
		myMarket = marketInfo.getString("market");
		
		// Set Up CheckPrice
		checkPrice = new CheckReceiverPrice();
		
		// Get info from market
		if(marketInfo.getString("market").equals("Bitstamp")) {
			
			// Get Resources and Run CheckReceiverPrice for Bitstamp
			checkPrice.getResources(bitstampURL, myContext, myIntent, buy, sell);
			
			try {
				
				// Get Current Price from Preferred Market
				currentPrice = checkPrice.execute().get();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			// Compare Current Price to Buy and Sell Preferences
			// checkPrice.onPostExecute(currentPrice);
			comparePrices(currentPrice);
			
			// Toast.makeText(context, "Current " + myMarket + " Price: " + currentPrice, Toast.LENGTH_LONG).show();
		}
		else if(marketInfo.getString("market").equals("BTCe")) {
			
			// Get Resources and Run CheckReceiverPrice for BTC-e
			checkPrice.getResources(btceURL, myContext, myIntent, buy, sell);
			
			try {

				// Get Current Price from Preferred Market
				currentPrice = checkPrice.execute().get();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			// Compare Current Price to Buy and Sell Preferences
			// checkPrice.onPostExecute(currentPrice);
			comparePrices(currentPrice);
			
			// Toast.makeText(context, "Current " + myMarket + " Price: " + currentPrice, Toast.LENGTH_LONG).show();
		}
		else if(marketInfo.getString("market").equals("CampBX")) {
			
			// Get Resources and Run CheckReceiverPrice for CampBX
			checkPrice.getResources(campbxURL, myContext, myIntent, buy, sell);
			
			try {

				// Get Current Price from Preferred Market
				currentPrice = checkPrice.execute().get();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			// Compare Current Price to Buy and Sell Preferences
			// checkPrice.onPostExecute(currentPrice);
			comparePrices(currentPrice);
			
			// Toast.makeText(context, "Current " + myMarket + " Price: " + currentPrice, Toast.LENGTH_LONG).show();
		}
		else if(marketInfo.getString("market").equals("LakeBTC")) {
			
			// Get Resources and Run CheckReceiverPrice for LakeBTC
			checkPrice.getResources(lakebtcURL, myContext, myIntent, buy, sell);
			
			try {

				// Get Current Price from Preferred Market
				currentPrice = checkPrice.execute().get();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			// Compare Current Price to Buy and Sell Preferences
			// checkPrice.onPostExecute(currentPrice);
			comparePrices(currentPrice);
			
			// Toast.makeText(context, "Current " + myMarket + " Price: " + currentPrice, Toast.LENGTH_LONG).show();
		}
		else if(marketInfo.getString("market").equals("")) {
			// Initiates when Market is Canceled
				// Does nothing (canceling)
		}
		else {
			Toast.makeText(context, "No Market Found: ( " + marketInfo + " )", Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void comparePrices(double pCurrentPrice) {
		String alarm;
		myPendingIntent = PendingIntent.getBroadcast(myContext, REQUEST_CODE, myIntent, 0);
		
		// Check if pCurrentPrice is Valid
		if(pCurrentPrice >= 0) {
			// Check if Current Price is less than preferred buy amount
			if(pCurrentPrice <= buy) {
				// Create Buy Price Hit Notification
				createBuyNotification(myPendingIntent);
							
				// Change Alarm to OFF
				alarm = "Alarm: OFF";
				saveAlarm(alarm);
			}
			// Check if Current Price is greater than preferred sell amount
			else if(pCurrentPrice >= sell) {
				// Create Sell Price Hit Notification
				createSellNotification(myPendingIntent);
				
				// Change Alarm to OFF
				alarm = "Alarm: OFF";
				saveAlarm(alarm);
			}
		}
		
	}

	private void createBuyNotification(PendingIntent pPendingIntent) {
		
		// Show Buy Hit Toast For User
		// Toast.makeText(myContext, "Buy Price Hit! Aborting Receiver", Toast.LENGTH_LONG).show();
		
		// Show Notifier
		createNotifier("BUY");
		pPendingIntent.cancel();
		vibrate.vibrate(1500);
	}

	private void createSellNotification(PendingIntent pPendingIntent) {
		
		// Show Sell Hit Toast For User
		// Toast.makeText(myContext, "Sell Price Hit! Aborting Receiver", Toast.LENGTH_LONG).show();

		// Show Notifier
		createNotifier("SELL");
		
		pPendingIntent.cancel();
		vibrate.vibrate(1500);
	}
	
	private void createNotifier(String pref) {
		// Set PreNotifier Information
		Intent intent = new Intent(myContext, BitcoinMarketMenu.class);
		// Pending intent for Bitcoin Market Menu when Notifier is Clicked
		PendingIntent pIntent = PendingIntent.getActivity(myContext, 345, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Check if Notifier is for Buy or Sell Price Hit
		if(pref.equals("BUY")) {
			
			// Create Notification Box
			android.support.v4.app.NotificationCompat.Builder biulder  = new NotificationCompat.Builder(myContext)
	        	.setSmallIcon(com.market.bitcoinmrkttracker.R.drawable.bitcoin_icon)
	        	.setContentTitle("Buy Price Hit at " + buy)
	        	.setContentText("Price is: " + currentPrice)
	        	.setContentIntent(pIntent)
	        	.setAutoCancel(true);
			
			// Implement Notification
			Notification notify = biulder.build();
			
			// Create Manager for Notification
			NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
			
			// Notify User
			notificationManager.notify(0, notify);
		}
		else if(pref.equals("SELL")) {
			
			// Create Notification Box
			android.support.v4.app.NotificationCompat.Builder biulder  = new NotificationCompat.Builder(myContext)
	        	.setSmallIcon(com.market.bitcoinmrkttracker.R.drawable.bitcoin_icon)
	        	.setContentTitle("Sell Price Hit at " + sell)
	        	.setContentText("Price is: " + currentPrice)
	        	.setContentIntent(pIntent)
	        	.setAutoCancel(true);
			
			// Implement Notification
			Notification notify = biulder.build();
			
			// Create Manager for Notification
			NotificationManager notificationManager = 
			  (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
			
			// Notify User
			notificationManager.notify(0, notify);
		}
		
	}

	private void saveAlarm(String pAlarm) {		
		try {
			FileOutputStream fileOutput = myContext.openFileOutput(ALARM_FILENAME, Context.MODE_PRIVATE);
			fileOutput.write(pAlarm.getBytes());
			fileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
