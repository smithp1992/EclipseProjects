package com.market.bitcoinmrkttracker;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class GetMenuPrice extends AsyncTask<Void, Void, Void> {

	ProgressDialog pDialog;
	Context activity;
	/*
	// Mt. Gox API
	private static final String TAG_MTGOX_DATA = "data";
	// Mt. Gox Price Catagories
	private static final String TAG_MTGOX_LAST_PRICE = "last";
	private static final String TAG_MTGOX_HIGH_PRICE = "buy";
	private static final String TAG_MTGOX_LOW_PRICE = "sell";
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
	
	// Lake BTC API
	private static final String TAG_LAKEBTC_DATA = "USD";
	private static final String TAG_LAKEBTC_LAST_PRICE = "last";
	private static final String TAG_LAKEBTC_HIGH_PRICE = "high";
	private static final String TAG_LAKEBTC_LOW_PRICE = "low";
	
	
	// API URL
	// String mtGoxURL = "";
	String bitstampURL = "";
	String btceURL = "";
	String campBXURL = "";
	String lakebtcURL = "";
	
	
	// JSON Objects
	JSONObject jsonObject1 = null;
	JSONObject jsonObject2 = null;
	JSONObject jsonObject3 = null;
	JSONObject jsonObject4 = null;
	JSONObject jsonObject5 = null;
	
	/*
	// Mt.Gox JSON Objects
	JSONObject mtGoxMrktPrice = null;
	
	JSONObject mtGoxLastCat = null;
	JSONObject mtGoxHighCat = null;
	JSONObject mtGoxLowCat = null;
	*/
	
	// Bitstamp JSON Objects
	JSONObject bitstampMrktPrice = null;
	// BTC-e JSON Objects
	JSONObject btceMrktPrice = null;
	// Camp BX JSON Objects
		// None
	JSONObject lakebtcMrktPrice = null;
	
	/*
	// Mt Gox Value Strings
	String mtGoxLastPrice = "";
	String mtGoxHighPrice = "";
	String mtGoxLowPrice = "";
	*/
	
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
	// Lake BTC Value Strings
	String lakebtcLastPrice;
	String lakebtcHighPrice;
	String lakebtcLowPrice; 
	
	
	
	// TextView mtGoxPriceText;
	TextView bitstampPriceText;
	TextView btcePriceText;
	TextView campBXPriceText;
	TextView lakebtcPriceText;
	Button refreshBT;
	
	public GetMenuPrice() {
		
	}
	
	// Must Get Resources From Activity
	public void getResources(Context pActivity, String pBitstampURL, String pBTCeURL, String pCampBXURL, String pLakebtcURL, TextView pBitstampPriceText, TextView pBTCePriceText, TextView pCampBXPriceText, TextView pLakebtcPriceText) {
		activity = pActivity;
		// mtGoxURL = pMtGoxURL;
		bitstampURL = pBitstampURL;
		btceURL = pBTCeURL;
		campBXURL = pCampBXURL;
		lakebtcURL = pLakebtcURL;
		
		// mtGoxPriceText = pMtGoxPriceText;
		bitstampPriceText = pBitstampPriceText;
		btcePriceText = pBTCePriceText;
		campBXPriceText = pCampBXPriceText;
		lakebtcPriceText = pLakebtcPriceText;
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
        // String jsonStr1 = sh.makeServiceCall(mtGoxURL, ServiceHandler.GET);
        String jsonStr2 = sh.makeServiceCall(bitstampURL, ServiceHandler.GET);
        String jsonStr3 = sh.makeServiceCall(btceURL, ServiceHandler.GET);
        String jsonStr4 = sh.makeServiceCall(campBXURL, ServiceHandler.GET);
        String jsonStr5 = sh.makeServiceCall(lakebtcURL, ServiceHandler.GET);
        
        
        // Log.d("Response: ", "> " + jsonStr1);
        Log.d("Response: ", "> " + jsonStr2);
        Log.d("Response: ", "> " + jsonStr3);
        Log.d("Response: ", "> " + jsonStr4);
        Log.d("Response: ", "> " + jsonStr5);
        
        if(jsonStr2 != null && jsonStr3 != null && jsonStr4 != null && jsonStr5 != null) {
        	
        	try {
        		/*
                // Mt. Gox API
                if(mtGoxURL.contentEquals("https://data.mtgox.com/api/2/BTCUSD/money/ticker")) {
                	// Get Mt. Gox API Contents
                	jsonObject1 = new JSONObject(jsonStr1);
                	
                	mtGoxMrktPrice = jsonObject1.getJSONObject(TAG_MTGOX_DATA);
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
                if(bitstampURL.contentEquals("https://www.bitstamp.net/api/ticker/")) {
                	// Get Bistamp API Contents
                	jsonObject2 = new JSONObject(jsonStr2);
                	// Get Last Price
                	bitstampLastPrice = jsonObject2.getString(TAG_BITSTAMP_LAST_PRICE);
                	// Get High Price
                	bitstampHighPrice = jsonObject2.getString(TAG_BITSTAMP_HIGH_PRICE);
                	// Get Low Price
                	bitstampLowPrice = jsonObject2.getString(TAG_BITSTAMP_LOW_PRICE);
                }
                // BTC-e API
        		if(btceURL.contentEquals("https://btc-e.com/api/2/btc_usd/ticker")) {
        			// Get BTCe API Contents
        			jsonObject3 = new JSONObject(jsonStr3);
        			
        			btceMrktPrice = jsonObject3.getJSONObject(TAG_BTCE_DATA);
        			// Get Last Price
        			btceLastPrice = btceMrktPrice.getString(TAG_BTCE_LAST_PRICE);
        			// Get High Price
        			btceHighPrice = btceMrktPrice.getString(TAG_BTCE_HIGH_PRICE);
        			// Get Low Price
        			btceLowPrice = btceMrktPrice.getString(TAG_BTCE_LOW_PRICE);
        			
        		}
                // Camp BX API
        		if(campBXURL.contentEquals("http://campbx.com/api/xticker.php")) {
        			jsonObject4 = new JSONObject(jsonStr4);
        			// Get Last Price
        			campbxLastPrice = jsonObject4.getString(TAG_CAMPBX_LAST_PRICE);
        			// Get Bit Price
        			campbxBidPrice = jsonObject4.getString(TAG_CAMPBX_BID_PRICE);
        			// Get Ask Price
        			campbxAskPrice = jsonObject4.getString(TAG_CAMPBX_ASK_PRICE);
        		}
                // Lake BTC API
        		if(lakebtcURL.contentEquals("https://www.lakebtc.com/api_v1/ticker")) {
        			jsonObject5 = new JSONObject(jsonStr5);
        			
        			// Get Data
        			lakebtcMrktPrice = jsonObject5.getJSONObject(TAG_LAKEBTC_DATA);
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
            Log.e("ServiceHandler", "Couldn't get any data from the urls");
            
        }
        
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		// Dismiss Loading Icon
		if (pDialog.isShowing()) {
			pDialog.dismiss();
		}
		/*
		// Mt Gox
		if(mtGoxURL.contentEquals("https://data.mtgox.com/api/2/BTCUSD/money/ticker")) {
			mtGoxPriceText.setText(mtGoxLastPrice);
			mtGoxPriceText.setTextColor(Color.BLACK);
		}
		
		else {
			mtGoxPriceText.setText("WrongURL");
		}
		*/
		
		// Bitstamp
		if(bitstampURL.contentEquals("https://www.bitstamp.net/api/ticker/")) {
			bitstampPriceText.setText("$" + bitstampLastPrice);
			bitstampPriceText.setTextColor(Color.BLACK);
        }
		else {
			bitstampPriceText.setText("WrongURL");
		}
		// BTC-e
		if(btceURL.contentEquals("https://btc-e.com/api/2/btc_usd/ticker")) {
			double lastPrice = Double.parseDouble(btceLastPrice);
			lastPrice = (double)Math.round((lastPrice * 100.0)/100.0);
			btcePriceText.setText("$" + lastPrice);
			btcePriceText.setTextColor(Color.BLACK);
		}
		else {
			btcePriceText.setText("WrongURL");
		}
		// Camp BX
		if(campBXURL.contentEquals("http://campbx.com/api/xticker.php")) {
			campBXPriceText.setText("$" + campbxLastPrice);
			campBXPriceText.setTextColor(Color.BLACK);
		}
		else {
			campBXPriceText.setText("WrongURL");
		}
		// Lake BTC
		if(lakebtcURL.contentEquals("https://www.lakebtc.com/api_v1/ticker")) {
			lakebtcPriceText.setText("$" + lakebtcLastPrice);
			lakebtcPriceText.setTextColor(Color.BLACK);
		}
		else {
			lakebtcPriceText.setText("WrongURL");
		}
		
	}
}
