package com.market.receiver;


import org.json.JSONException;
import org.json.JSONObject;

import com.market.bitcoinmrkttracker.ServiceHandler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;

public class CheckReceiverPrice extends AsyncTask<Void, Void, Double>  {
	
	
	// Bitstamp API / Price Catagories
	private static final String TAG_BITSTAMP_LAST_PRICE = "last";
	
	// BTC-e API
	private static final String TAG_BTCE_DATA = "ticker";
	// BTC-e Price Catagories
	private static final String TAG_BTCE_LAST_PRICE = "last";
	
	// Camp BX API
	private static final String TAG_CAMPBX_LAST_PRICE = "Last Trade";
	
	// Lake BTC
	private static final String TAG_LAKEBTC_DATA = "USD";
	private static final String TAG_LAKEBTC_LAST_PRICE = "last";

	// JSON Objects
	JSONObject jsonObject = null;
	
	// Bitstamp
	String bitstampLastPrice = "";
	JSONObject bitstampMrktPrice = null;
	// BTC-e
	String btceLastPrice = "";
	JSONObject btceMrktPrice = null;
	// Camp BX
	String campbxLastPrice = "";
	// Lake BTC
	String lakebtcLastPrice = "";
	JSONObject lakebtcMrktPrice = null;
	
	Context myContext;
	Intent myIntent;
	PendingIntent myPendingIntent;
	Vibrator vibrate;
	
	String url = "";
	double lastPrice;
	double price = -1;
	
	double prefBuy;
	double prefSell;
	
	double buy;
	double sell;
	
	
	public CheckReceiverPrice () {
		
	}
	
	// Must Get Resources From Activity
	public void getResources(String pURL, Context pContext, Intent pIntent, double pBuy, double pSell) {
		url = pURL;
		myContext = pContext;
		myIntent = pIntent;
		buy = pBuy;
		sell = pSell;
	}

	@Override
	protected Double doInBackground(Void... params) {
		

		// Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        
        
        Log.d("Response: ", "> " + jsonStr);
        
        if(jsonStr != null) {
        	
        	try {
        		jsonObject = new JSONObject(jsonStr);
                
        		// Bitstamp API
                if(url.contentEquals("https://www.bitstamp.net/api/ticker/")) {
                	// Get Last Price
                	bitstampLastPrice = jsonObject.getString(TAG_BITSTAMP_LAST_PRICE);
                	
                	// Set Price
                	price = Double.parseDouble(bitstampLastPrice);
                }
                // BTC-e API
        		else if(url.contentEquals("https://btc-e.com/api/2/btc_usd/ticker")) {
        			btceMrktPrice = jsonObject.getJSONObject(TAG_BTCE_DATA);
        			// Get Last Price
        			btceLastPrice = btceMrktPrice.getString(TAG_BTCE_LAST_PRICE);
        			
        			// Set Price
        			price = Double.parseDouble(btceLastPrice);
        		}
                // Camp BX API
        		else if(url.contentEquals("http://campbx.com/api/xticker.php")) {
        			// Get Last Price
        			campbxLastPrice = jsonObject.getString(TAG_CAMPBX_LAST_PRICE);
        			
        			price = Double.parseDouble(campbxLastPrice);
        		}
                // Lake BTC API
        		else if(url.contentEquals("https://www.lakebtc.com/api_v1/ticker")) {
        			// Get Data
        			lakebtcMrktPrice = jsonObject.getJSONObject(TAG_LAKEBTC_DATA);
        			
        			// Get Price
        			lakebtcLastPrice = lakebtcMrktPrice.getString(TAG_LAKEBTC_LAST_PRICE);
        			
        			// Set Price
        			price = Double.parseDouble(lakebtcLastPrice);
        		}
        	} catch (JSONException e) {
                    e.printStackTrace();
            }
        }
        else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            
        }
		return price;
	}

	/*
	private void saveAlarm(String pAlarm) {
		File file = new File(ALARM_FILENAME);
		try {
			OutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(file));
			fileOutput.write(pAlarm.getBytes());
			fileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	*/
}
