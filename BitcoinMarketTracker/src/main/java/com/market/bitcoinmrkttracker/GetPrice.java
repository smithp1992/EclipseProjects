package com.market.bitcoinmrkttracker;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class GetPrice extends AsyncTask<Void, Void, Void> {

	ProgressDialog pDialog;
	Context activity;
	
	/*
	// Mt. Gox API
	private static final String TAG_MTGOX_DATA = "data";
	// Mt. Gox Price Catagories
	private static final String TAG_MTGOX_LAST_PRICE = "last";
	private static final String TAG_MTGOX_HIGH_PRICE = "high";
	private static final String TAG_MTGOX_LOW_PRICE = "low";
	// Mt. Gox Price Look-up
	private static final String TAG_MTGOX_VALUE = "display";
	*/
	
	// Bitstamp API / Price Catagories
	private static final String TAG_BITSTAMP_LAST_PRICE = "last";
	private static final String TAG_BITSTAMP_HIGH_PRICE = "high";
	private static final String TAG_BITSTAMP_LOW_PRICE = "low";
	
	// BTC-e API
	private static final String TAG_BTCE_DATA = "ticker";
	// BTC-e Price Catagories
	private static final String TAG_BTCE_LAST_PRICE = "last";
	private static final String TAG_BTCE_HIGH_PRICE = "high";
	private static final String TAG_BTCE_LOW_PRICE = "low";
	
	// Camp BX API
	private static final String TAG_CAMPBX_LAST_PRICE = "Last Trade";
	private static final String TAG_CAMPBX_BID_PRICE = "Best Bid";
	private static final String TAG_CAMPBX_ASK_PRICE = "Best Ask";
	
	// LakeBTC API
	private static final String TAG_LAKEBTC_DATA = "USD";
	private static final String TAG_LAKEBTC_LAST_PRICE = "last";
	private static final String TAG_LAKEBTC_HIGH_PRICE = "high";
	private static final String TAG_LAKEBTC_LOW_PRICE = "low";
	
	// API URL
	String url = "";
	
	
	// JSON Objects
	JSONObject jsonObject = null;
	// Mt.Gox JSON Objects
	JSONObject mtGoxMrktPrice = null;
	
	JSONObject mtGoxLastCat = null;
	JSONObject mtGoxHighCat = null;
	JSONObject mtGoxLowCat = null;
	// Bitstamp JSON Objects
	JSONObject bitstampMrktPrice = null;
	// BTC-e JSON Objects
	JSONObject btceMrktPrice = null;
	// Camp BX JSON Objects
		// None
	JSONObject lakebtcMrktPrice = null;
	
	
	// Mt Gox Value Strings
	String mtGoxLastPrice = "";
	String mtGoxHighPrice = "";
	String mtGoxLowPrice = "";
	// Bitstamp Value Strings
	String bitstampLastPrice = "";
	String bitstampHighPrice = "";
	String bitstampLowPrice = "";
	// BTC-e Value Strings
	String btceLastPrice = "";
	String btceHighPrice = "";
	String btceLowPrice = "";
	// Camp BX Value Strings
	String campbxLastPrice = "";
	String campbxBidPrice = "";
	String campbxAskPrice = "";
	// LakeBTC Value Strings
	String lakebtcLastPrice = "";
	String lakebtcHighPrice = "";
	String lakebtcLowPrice = ""; 
	
	
	
	TextView lastPriceText;
	TextView highPriceText;
	TextView lowPriceText;
	Button refreshBT;
	
	public GetPrice() {
		
	}
	
	// Must Get Resources From Activity
	public void getResources(Context pActivity, String pURL, TextView pLastPriceText, TextView pHighPriceText, TextView pLowPricText) {
		activity = pActivity;
		url = pURL;
		lastPriceText = pLastPriceText;
		highPriceText = pHighPriceText;
		lowPriceText = pLowPricText;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		// Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        
        
        Log.d("Response: ", "> " + jsonStr);
        
        if(jsonStr != null) {
        	
        	try {
                jsonObject = new JSONObject(jsonStr);
                /*
                // Mt. Gox API
                if(url.contentEquals("https://data.mtgox.com/api/2/BTCUSD/money/ticker")) {
                	
                	mtGoxMrktPrice = jsonObject.getJSONObject(TAG_MTGOX_DATA);
                	// Get Last Price
                	mtGoxLastCat = mtGoxMrktPrice.getJSONObject(TAG_MTGOX_LAST_PRICE);
                	mtGoxLastPrice = mtGoxLastCat.getString(TAG_MTGOX_VALUE);
                	// Get High Price
                	mtGoxHighCat = mtGoxMrktPrice.getJSONObject(TAG_MTGOX_HIGH_PRICE);
                	mtGoxHighPrice = mtGoxHighCat.getString(TAG_MTGOX_VALUE);
                	// Get Low Price
                	mtGoxLowCat = mtGoxMrktPrice.getJSONObject(TAG_MTGOX_LOW_PRICE);
                	mtGoxLowPrice = mtGoxLowCat.getString(TAG_MTGOX_VALUE);
                	
                }
                */
                // Bitstamp API
                if(url.contentEquals("https://www.bitstamp.net/api/ticker/")) {
                	// Get Last Price
                	bitstampLastPrice = jsonObject.getString(TAG_BITSTAMP_LAST_PRICE);
                	// Get High Price
                	bitstampHighPrice = jsonObject.getString(TAG_BITSTAMP_HIGH_PRICE);
                	// Get Low Price
                	bitstampLowPrice = jsonObject.getString(TAG_BITSTAMP_LOW_PRICE);
                }
                // BTC-e API
        		else if(url.contentEquals("https://btc-e.com/api/2/btc_usd/ticker")) {
        			btceMrktPrice = jsonObject.getJSONObject(TAG_BTCE_DATA);
        			// Get Last Price
        			btceLastPrice = btceMrktPrice.getString(TAG_BTCE_LAST_PRICE);
        			// Get High Price
        			btceHighPrice = btceMrktPrice.getString(TAG_BTCE_HIGH_PRICE);
        			// Get Low Price
        			btceLowPrice = btceMrktPrice.getString(TAG_BTCE_LOW_PRICE);
        			
        		}
                // Camp BX API
        		else if(url.contentEquals("http://campbx.com/api/xticker.php")) {
        			// Get Last Price
        			campbxLastPrice = jsonObject.getString(TAG_CAMPBX_LAST_PRICE);
        			// Get Bit Price
        			campbxBidPrice = jsonObject.getString(TAG_CAMPBX_BID_PRICE);
        			// Get Ask Price
        			campbxAskPrice = jsonObject.getString(TAG_CAMPBX_ASK_PRICE);
        		}
                // LakeBTC API
        		else if(url.contentEquals("https://www.lakebtc.com/api_v1/ticker")) {
                	// Get Data
        			lakebtcMrktPrice = jsonObject.getJSONObject(TAG_LAKEBTC_DATA);
        			// Get Last Price
                	lakebtcLastPrice = lakebtcMrktPrice.getString(TAG_LAKEBTC_LAST_PRICE);
                	// Get High Price
                	lakebtcHighPrice = lakebtcMrktPrice.getString(TAG_LAKEBTC_HIGH_PRICE);
                	// Get Low Price
                	lakebtcLowPrice = lakebtcMrktPrice.getString(TAG_LAKEBTC_LOW_PRICE);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            
        }
        
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}
		/*
		// Mt Gox
		if(url.contentEquals("https://data.mtgox.com/api/2/BTCUSD/money/ticker")) {
			if(!mtGoxLastPrice.equals("")) {
				// Set Last Price
				lastPriceText.setText("Current: " + mtGoxLastPrice);
				// Set Daily High Price
				highPriceText.setText("High: " + mtGoxHighPrice);
				// Set Daily Low Price
				lowPriceText.setText("Low: " + mtGoxLowPrice);
			}
		}
		*/
		// Bitstamp
		if(url.contentEquals("https://www.bitstamp.net/api/ticker/")) {
			// Set Last Price
			lastPriceText.setText("Current: $" + bitstampLastPrice);
			// Set Daily High Price
        	highPriceText.setText("High: $" + bitstampHighPrice);
        	// Set Daily Low Price
        	lowPriceText.setText("Low: $" + bitstampLowPrice);
        }
		// BTC-e
		else if(url.contentEquals("https://btc-e.com/api/2/btc_usd/ticker")) {
			// Set Last Price
			lastPriceText.setText("Current: $" + btceLastPrice);
			// Set Daily High Price
        	highPriceText.setText("High: $" + btceHighPrice);
        	// Set Daily Low Price
        	lowPriceText.setText("Low: $" + btceLowPrice);
		}
		// Camp BX
		else if(url.contentEquals("http://campbx.com/api/xticker.php")) {
			// Set Last Price
			lastPriceText.setText("Current: $" + campbxLastPrice);
			// Set Bid Price
			highPriceText.setText("Bid: $" + campbxBidPrice);
			// Set Ask Price
			lowPriceText.setText("Ask: $" + campbxAskPrice);
		}
		// Lake BTC
		else if(url.contentEquals("https://www.lakebtc.com/api_v1/ticker")) {
			// Set Last Price
			lastPriceText.setText("Current: $" + lakebtcLastPrice);
			// Set Bid Price
			highPriceText.setText("High: $" + lakebtcHighPrice);
			// Set Ask Price
			lowPriceText.setText("Low: $" + lakebtcLowPrice);
		}
		else {
			lastPriceText.setText("url is invalid");
		}
		
	}
}
